package cn.com.oumeng.common.core.controller;


import cn.com.oumeng.common.core.mapper.AbstractMapper;
import cn.com.oumeng.common.core.service.AbstractService;
import cn.com.oumeng.common.core.util.HttpUtil;
import cn.com.oumeng.common.core.util.TypeUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @param <T>
 * @param <Service>
 */
public class AbstractSpringController<T, Service extends AbstractService<? extends
        AbstractMapper<T>, T>> extends AbstractBaseSpringController {

    @Autowired
    protected Service baseService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Object page(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10")
            Integer pageSize, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "false") Boolean isAsc) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        Map<String, Object> params = HttpUtil.getRequestParameters();
        params.remove("pageIndex");
        params.remove("pageSize");
        params.remove("orderBy");
        params.remove("isAsc");
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        Set<String> fieldSet = new HashSet<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            TableField annotation = field.getAnnotation(TableField.class);
            if (annotation != null) {
                fieldSet.add(annotation.value());
            } else {
                fieldSet.add(field.getName());
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isNullOrEmpty(key))
                continue;
            String paramKey = null;
            if (key.startsWith("like_")) {
                paramKey = key.substring(5);
                if (fieldSet.contains(paramKey)) {
                    if (value instanceof String) {
                        if (!StringUtils.isNullOrEmpty((String) value)) {
                            wrapper.like(paramKey, (String) value);
                        }
                    }
                }
            } else if (key.startsWith("gt_")) {
                paramKey = key.substring(3);
                if (fieldSet.contains(paramKey)) {
                    wrapper.gt(paramKey, value);
                }
            } else if (key.startsWith("ge_")) {
                paramKey = key.substring(3);
                if (fieldSet.contains(paramKey)) {
                    wrapper.ge(paramKey, value);
                }
            } else if (key.startsWith("lt_")) {
                paramKey = key.substring(3);
                if (fieldSet.contains(paramKey)) {
                    wrapper.lt(paramKey, value);
                }
            } else if (key.startsWith("le_")) {
                paramKey = key.substring(3);
                if (fieldSet.contains(paramKey)) {
                    wrapper.le(paramKey, value);
                }
            } else {
                if (fieldSet.contains(key) && value != null) {
                    if (value instanceof String) {
                        if (!StringUtils.isNullOrEmpty((String) value)) {
                            wrapper.eq(key, value);
                        }
                    } else {
                        wrapper.eq(key, value);
                    }
                }
            }
        }
        if (StringUtils.isNullOrEmpty(orderBy)) {
            wrapper.orderBy(true, false, "id");
        } else {
            wrapper.orderBy(true, isAsc == null ? true : isAsc, orderBy);
        }
        Page<T> page = (Page<T>) baseService.page(new Page(pageIndex, pageSize), wrapper);
        return success("查询成功!", page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@Valid @RequestBody T entity, BindingResult result) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor("id",
                    entity.getClass());
            Method setMethod = pd.getWriteMethod();
            setMethod.invoke(entity, null);
        } catch (Exception e) {
            logger.error("此对象没有id属性:" + entity);
        }
        if (result.hasErrors()) {
            return generateReturnError(result);
        }
        if (baseService.save(entity)) {
            return success("保存成功!");
        }
        return error("保存失败!");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@Valid @RequestBody T entity, BindingResult result) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor("id",
                    entity.getClass());
            Method getMethod = pd.getReadMethod();
            Object o = getMethod.invoke(entity);
            if (StringUtils.isNullOrEmpty((String) o)) {
                return error("更新时,id不能为空");
            }
        } catch (Exception e) {
            logger.error("此对象没有id属性:" + entity);
        }
        if (result.hasErrors()) {
            return generateReturnError(result);
        }
        if (baseService.updateById(entity)) {
            return success("更新成功!");
        }
        return error("更新失败!");
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam String ids) {
        if (StringUtils.isNullOrEmpty(ids)) {
            return error("参数错误!");
        }
        if (ids.indexOf(",") == -1) {
            if (baseService.removeById(Long.parseLong(ids))) {
                return success("删除成功!");
            }
        } else {
            String[] idArr = ids.split(",");
            Long[] longIds = TypeUtil.stringArrToLongArr(idArr);
            if (baseService.removeByIds(Arrays.asList(longIds))) {
                return success("删除成功!");
            }
        }
        return error("删除失败!");
    }

    @Override
    protected Object generateReturnError(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage() + ";");
        }
        return error(sb.toString());
    }


    public Page createPageObject(Map<String, Object> params) {
        Integer pageSize = null;
        Integer pageIndex = null;
        if (params.get("pageIndex") != null && !"".equals(params.get("pageIndex"))) {
            pageIndex = Integer.parseInt(params.get("pageIndex").toString());
        } else {
            pageIndex = 0;
        }
        if (params.get("pageSize") != null && !"".equals(params.get("pageIndex"))) {
            pageSize = Integer.parseInt(params.get("pageSize").toString());
        } else {
            pageSize = 1;
        }
        return new Page<>(pageIndex, pageSize);
    }

    protected String getToken(String tokenName){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(tokenName);
    }

}