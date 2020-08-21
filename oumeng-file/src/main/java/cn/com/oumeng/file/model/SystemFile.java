package cn.com.oumeng.file.model;

import cn.com.oumeng.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_file")
public class SystemFile extends BaseEntity {


    @TableField("file_name")
    private String fileName;

    @TableField("file_md5")
    private String fileMd5;

    @TableField("file_length")
    private Long  fileLength;

    @TableField("path_url")
    private String pathUrl;

    @TableField("file_size")
    private Long fileSize;




}
