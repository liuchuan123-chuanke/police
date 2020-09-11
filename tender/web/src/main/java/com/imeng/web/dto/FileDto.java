package com.imeng.web.dto;

import lombok.Data;

/**
 * @author wwj
 * 2020/7/13 17:08
 */
@Data
public class FileDto {

    //文件类型
    private Integer fileType;
    //文件信息
    private String fileName;

    private String filePath;

    private long   fileSize;
}
