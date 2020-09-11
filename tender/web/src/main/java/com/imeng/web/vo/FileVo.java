package com.imeng.web.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wwj
 * 2020/7/14 09:15
 */
@Data
@NoArgsConstructor
public class FileVo {

    private Integer businessType;
    private String fileName;
    private String filePath;
    private Long   fileSize;

    public FileVo(String fileName, String filePath,Long   fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public static void main(String[] args) throws Exception {
        //1.8 之前使用的线程不安全的转换类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //1.8之前用的日期转换工具内,线程不安全
        Callable<Date> task = () -> sdf.parse("20200711"); //线程不安全

        ArrayList<Future> list = new ArrayList<>();

        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            list.add(pool.submit(task));
        }

        for (Future future : list) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }

}
