package cn.com.oumeng.file.rest;

import cn.com.oumeng.common.core.controller.AbstractBaseSpringController;
import cn.com.oumeng.common.core.entity.CheckFileResult;
import cn.com.oumeng.file.constant.UpLoadConstant;
import cn.com.oumeng.file.model.SystemFile;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload/chunkUpload")
@Slf4j
public class ChunkUploadController extends AbstractBaseSpringController {

    @Resource
    private AppendFileStorageClient appendFileStorageClient;

    @Autowired
    RedisTemplate redisTemplate;

    //获取配置
   /* @RequestMapping("/config")
    @ResponseBody
    public String config(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileServerUrl", "http:\\\\"+ReadProper.getResourceValue("fileServerUrl")+"/");
        jsonObject.put("maxSize", ReadProper.getResourceValue("maxSize"));
        return jsonObject.toString();
    }*/



    @PostMapping("/upload_do")
    @ResponseBody
    public Object upload_do(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws IOException {

        String noGroupPath;//存储在fastdfs不带组的路径
        String fileMd5= (String) paramMap.get("fileMd5");
        String chunklockName= UpLoadConstant.chunkLock+fileMd5;

        String temOwner= RandomUtil.randomUUID();
        boolean currOwner=false;//真正的拥有者
        try {
            String userName=  (String) request.getSession().getAttribute("name");
            if (StrUtil.isEmpty(userName)){
                return error("请登录后上传！");
            }
            if (!paramMap.containsKey("chunk")){
                paramMap.put("chunk","0");
            }
            if (!paramMap.containsKey("chunks")){
                paramMap.put("chunks","1");
            }

            Long lock= this.incrBy(chunklockName,1);
            if (lock>1){
                return this.error("请求块锁失败");
            }

            //写入锁的当前拥有者
            currOwner=true;

            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            MultipartFile file = null;
            BufferedOutputStream stream = null;
            String chunk= (String) paramMap.get("chunk");
            String chunkCurrkey= UpLoadConstant.chunkCurr+fileMd5; //redis中记录当前应该穿第几块(从0开始)
            //String  chunkCurr=  RedisUtil.getString(chunkCurrkey);
            String  chunkCurr = (String) redisTemplate.opsForValue().get(chunkCurrkey);
            noGroupPath = "";
            //切块大小
            Integer chunkSize= Convert.toInt(paramMap.get("chunkSize"));

            if (StrUtil.isEmpty(chunkCurr)){
                return this.error("无法获取当前文件chunkCurr");
            }
            //当前切块编号
            Integer chunkCurr_int= Convert.toInt(chunkCurr);
            //切块编号
            Integer chunk_int= Convert.toInt(chunk);

            if (chunk_int<chunkCurr_int){
                return this.error("当前文件块已上传");
            }else if (chunk_int>chunkCurr_int){
                return this.error("当前文件块需要等待上传,稍后请重试！");
            }

            StorePath path = null;
            //暂时不支持多文件上传,后续版本可以再加上
            for (int i = 0; i < files.size(); ++i) {
                file = files.get(i);
                if (!file.isEmpty()) {
                    try {
                        //获取已经上传文件大小
                        Long historyUpload=0L;
                        //String historyUploadStr= RedisUtil.getString(UpLoadConstant.historyUpload+fileMd5);
                        String historyUploadStr= (String) redisTemplate.opsForValue().get(UpLoadConstant.historyUpload+fileMd5);

                        if (StrUtil.isNotEmpty(historyUploadStr)){
                            historyUpload= Convert.toLong(historyUploadStr);
                        }
                        log.debug("historyUpload大小:"+historyUpload);
                        if (chunk_int==0){
                            //RedisUtil.setString(chunkCurrkey, Convert.toStr(chunkCurr_int+1));
                            redisTemplate.opsForValue().set(chunkCurrkey,Convert.toStr(chunkCurr_int+1));
                            log.debug(chunk+":redis块+1");
                            try {
                                path = appendFileStorageClient.uploadAppenderFile(UpLoadConstant.DEFAULT_GROUP, file.getInputStream(),
                                        file.getSize(), FileUtil.extName((String) paramMap.get("name")));
                                log.debug(chunk+":更新完fastdfs");
                                if (path== null ){
                                    //RedisUtil.setString(chunkCurrkey, Convert.toStr(chunkCurr_int));
                                    redisTemplate.opsForValue().set(chunkCurrkey,Convert.toStr(chunkCurr_int));
                                    return   this.error("获取远程文件路径出错");
                                }

                            } catch (Exception e) {
                                //RedisUtil.setString(chunkCurrkey, Convert.toStr(chunkCurr_int));
                                redisTemplate.opsForValue().set(chunkCurrkey,Convert.toStr(chunkCurr_int));
                                //还原历史块
                                log.error("初次上传远程文件出错", e);
                                return   this.error("上传远程服务器文件出错");
                            }
                            noGroupPath=path.getPath();

                            //RedisUtil.setString(UpLoadConstant.fastDfsPath+fileMd5,path.getPath());
                            redisTemplate.opsForValue().set(UpLoadConstant.fastDfsPath+fileMd5,path.getPath());
                            log.debug("上传文件 result={}", path);
                        }else {
                            //RedisUtil.setString(chunkCurrkey, Convert.toStr(chunkCurr_int+1));
                            redisTemplate.opsForValue().set(chunkCurrkey,Convert.toStr(chunkCurr_int+1));
                            log.debug(chunk+":redis块+1");
                            //noGroupPath = RedisUtil.getString(UpLoadConstant.fastDfsPath+fileMd5);
                            noGroupPath = (String) redisTemplate.opsForValue().get(UpLoadConstant.fastDfsPath+fileMd5);

                            if (noGroupPath== null ){
                                return   this.error("无法获取上传远程服务器文件出错");
                            }

                            try {
                                //追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
                                appendFileStorageClient.modifyFile(UpLoadConstant.DEFAULT_GROUP, noGroupPath, file.getInputStream(),
                                        file.getSize(),historyUpload);
                                log.debug(chunk+":更新完fastdfs");
                            } catch (Exception e) {
                                //RedisUtil.setString(chunkCurrkey, Convert.toStr(chunkCurr_int));
                                redisTemplate.opsForValue().set(chunkCurrkey, Convert.toStr(chunkCurr_int));
                                log.error("更新远程文件出错", e);
                                return  this.error("更新远程文件出错");
                            }

                        }


                        //修改历史上传大小
                        historyUpload=historyUpload+file.getSize();
                        //RedisUtil.setString(UpLoadConstant.historyUpload+fileMd5, Convert.toStr(historyUpload));
                        redisTemplate.opsForValue().set(UpLoadConstant.historyUpload+fileMd5, Convert.toStr(historyUpload));
                        //最后一块,清空upload,写入数据库
                        String  fileName=  (String) paramMap.get("name");
                        Long size= Convert.toLong(paramMap.get("size"));
                        Integer chunks_int= Convert.toInt(paramMap.get("chunks"));
                        if (chunk_int+1==chunks_int){
                            //持久化上传完成文件,也可以存储在mysql中
                            SystemFile systemFile = new SystemFile();
                            systemFile.setFileMd5(fileMd5);
                            systemFile.setFileName(fileName);
                            systemFile.setFileLength(size);
                            systemFile.setPathUrl(UpLoadConstant.DEFAULT_GROUP+"/"+noGroupPath);

                            //RedisUtil.rpush(UpLoadConstant.completedList, JSONUtil.toJsonStr(fileResult));
                            redisTemplate.opsForValue().append(UpLoadConstant.completedList, JSONUtil.toJsonStr(systemFile));
                            redisTemplate.delete(new String[]{UpLoadConstant.chunkCurr+fileMd5,
                                    UpLoadConstant.fastDfsPath+fileMd5,
                                    UpLoadConstant.currLocks+fileMd5,
                                    UpLoadConstant.lockOwner+fileMd5});
                          /*  RedisUtil.delKeys(new String[]{UpLoadConstant.chunkCurr+fileMd5,
                                    UpLoadConstant.fastDfsPath+fileMd5,
                                    UpLoadConstant.currLocks+fileMd5,
                                    UpLoadConstant.lockOwner+fileMd5
                            });*/
                        }


                    } catch (Exception e) {
                        log.error("上传文件错误", e);
                        //e.printStackTrace();
                        return this.error("上传错误 " + e.getMessage());
                    }
                }
                break;
            }
        } finally {
            //锁的当前拥有者才能释放块上传锁
            if (currOwner){
                //RedisUtil.setString(chunklockName,"0");
                redisTemplate.opsForValue().set(chunklockName,"0");
            }

        }
        return  this.success(UpLoadConstant.DEFAULT_GROUP+"/"+noGroupPath);
    }


    @PostMapping("/checkFile")
    @ResponseBody
    public Object checkFile(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws IOException {
      String userName=  (String) request.getSession().getAttribute("name");
      if (StrUtil.isEmpty(userName)){
          return  this.error("请先登录");
      }

        String fileMd5= (String) paramMap.get("fileMd5");
        if (StrUtil.isEmpty(fileMd5)){
            return  this.error("fileMd5不能为空");
        }
        CheckFileResult checkFileResult=new CheckFileResult();

       //模拟从mysql中查询文件表的md5,这里从redis里查询
        //List<String> fileList=RedisUtil.getListAll(UpLoadConstant.completedList);
        List<String> fileList = (List<String>) redisTemplate.opsForValue().get(UpLoadConstant.completedList);
        if (CollUtil.isNotEmpty(fileList)){
            for (String e:fileList){
                JSONObject obj= JSONUtil.parseObj(e);
                if (obj.get("md5").equals(fileMd5)){
                    checkFileResult.setTotalSize(obj.getLong("lenght"));
                    checkFileResult.setViewPath(obj.getStr("url"));
                    return  this.success(checkFileResult);
                }
            }
        }

        //查询锁占用

        String lockName=UpLoadConstant.currLocks+fileMd5;
        Long lock= this.incrBy(lockName,1);

        String lockOwner=UpLoadConstant.lockOwner+ fileMd5;
        String chunkCurrkey=UpLoadConstant.chunkCurr+fileMd5;
        if (lock>1){
            checkFileResult.setLock(1);
            //检查是否为锁的拥有者,如果是放行
            //String oWner= RedisUtil.getString(lockOwner);
            String oWner= (String) redisTemplate.opsForValue().get(lockOwner);
            if (StrUtil.isEmpty(oWner)){
                return  this.error("无法获取文件锁拥有者");
            }else {
                if (oWner.equals(request.getSession().getAttribute("name"))){
                    //String  chunkCurr=  RedisUtil.getString(chunkCurrkey);
                    String  chunkCurr= (String) redisTemplate.opsForValue().get(chunkCurrkey);
                    if (StrUtil.isEmpty(chunkCurr)){
                        return this.error("无法获取当前文件chunkCurr");
                    }

                    checkFileResult.setChunkCurr(Convert.toInt(chunkCurr));
                    return  this.success(checkFileResult);
                }else {
                    return   this.error("当前文件已有人在上传,您暂无法上传该文件");
                }

            }

        }else {
            //初始化锁.分块
            //RedisUtil.setString(lockOwner, (String) request.getSession().getAttribute("name"));
            //RedisUtil.setString(chunkCurrkey,"0"); //第一块索引是0,与前端保持一致
            redisTemplate.opsForValue().set(lockOwner, (String) request.getSession().getAttribute("name"));
            redisTemplate.opsForValue().set(chunkCurrkey,"0");
            checkFileResult.setChunkCurr(0);
            return  this.success(checkFileResult);
        }

    }



    public  Long incrBy(String key, long delta){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return operations.increment(key, delta);
    }
}
