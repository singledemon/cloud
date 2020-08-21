package cn.com.oumeng.file.fastdfs;

//fastdfs Client 的使用
/*@Component
public class FastDFSClient {


    @Autowired
    FastConnectionPool fastConnectionPool;


    public String uploadFile(File file) {
        byte[] buff = getFileBuff(file);
        String file_ext_name =  getFileExtName(file.getName());
        return send(buff, file_ext_name, null);
    }


    public String uploadFile(File file, String file_ext_name) {
        byte[] buff = getFileBuff(file);
        return send(buff,file_ext_name,null);
    }


    public String uploadFile(byte[] buff,String file_ext_name) {
        return send(buff,file_ext_name,null);
    }

    //上传缓存数据到storage服务器
    private String send(byte[] buff, String file_ext_name, NameValuePair[] mate_list){
        String upPath = null;
        StorageClient1 storageClient1 = null;
       // FastConnectionPool pool = FastConnectionPool.getFastConnectionPool();
        storageClient1 = fastConnectionPool.checkout(10);
        try {
            upPath = storageClient1.upload_file1(buff, file_ext_name, mate_list);
            fastConnectionPool.checkin(storageClient1);
        } catch (IOException e) {
            //如果出现了IO异常应该销毁此连接
            fastConnectionPool.drop(storageClient1);
            e.printStackTrace();
        } catch (Exception e) {
            fastConnectionPool.drop(storageClient1);
            e.printStackTrace();
        }

        return upPath;
    }

    //将文件缓存到字节数组中
    private byte[] getFileBuff(File file){
        byte[] buff = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            buff = new byte[inputStream.available()];
            inputStream.read(buff);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buff;
    }

    //通过文件名称获取文件扩展名
    private String getFileExtName(String name){
        String ext_name = null;
        if(name != null){
            if(name.contains(".")){
                ext_name = name.substring(name.indexOf(".")+1);
            }
        }
        return ext_name;
    }


}*/



    /*public String uploadFile(byte[] buff,String fileName) {
        return uploadFile(buff, fileName, null,null);
    }

    public String uploadFile(byte[] buff, String fileName, Map<String, String> metaList, String groupName) {
        try {
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name, value);
                }
            }

            //获取可用的tracker,并创建存储server
            StorageClient1 storageClient = connectionPool.checkout();
            String path = null;
            if (!StringUtils.isEmpty(groupName)) {
                // 上传到指定分组
                path = storageClient.upload_file1(groupName, buff,
                    FilenameUtils.getExtension(fileName), nameValuePairs);
            } else {
                path = storageClient.upload_file1(buff, FilenameUtils.getExtension(fileName),
                    nameValuePairs);
            }
           //上传完毕及时释放连接
            connectionPool.checkin(storageClient);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getFileMetadata(String fileId)
    {
        try
        {
            //获取可用的tracker,并创建存储server
            StorageClient1 storageClient = connectionPool.checkout();
            NameValuePair[] metaList = storageClient.get_metadata1(fileId);
            //上传完毕及时释放连接
            connectionPool.checkin(storageClient);
            if (metaList != null)
            {
                HashMap<String, String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList)
                {
                    map.put(metaItem.getName(), metaItem.getValue());
                }
                return map;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteFile(String fileId)
    {
        try
        {
        //获取可用的tracker,并创建存储server
            StorageClient1 storageClient = connectionPool.checkout();

            int i = storageClient.delete_file1(fileId);
        //上传完毕及时释放连接

            connectionPool.checkin(storageClient);
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public byte[] downloadFile(String fileId)
    {
        try
        {
            //获取可用的tracker,并创建存储server
            StorageClient1 storageClient = connectionPool.checkout();

            byte[] content = storageClient.download_file1(fileId);
            //上传完毕及时释放连接

            connectionPool.checkin(storageClient);

            return content;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (MyException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public FileInfo getFileInfo(String fileId)
    {
        try
        {
            //获取可用的tracker,并创建存储server
            StorageClient1 storageClient = connectionPool.checkout();
            FileInfo fileInfo = storageClient.get_file_info1(fileId);
            //上传完毕及时释放连接

            connectionPool.checkin(storageClient);

            return fileInfo;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (MyException e)
        {
            e.printStackTrace();
        }
        return null;
    }*/

