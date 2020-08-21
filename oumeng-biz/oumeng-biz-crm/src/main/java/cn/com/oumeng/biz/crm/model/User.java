package cn.com.oumeng.biz.crm.model;

import cn.com.oumeng.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class User extends BaseEntity {


    @TableField("username")
    private String userName;

    @TableField("password")
    private String password;

}
