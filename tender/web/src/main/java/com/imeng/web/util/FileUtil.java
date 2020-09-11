package com.imeng.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

/**
 * 文件工具类
 *
 * @author 作者 owen E-mail: 624191343@qq.com
 */
@Slf4j
public class FileUtil {
    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 文件的md5
     *
     * @param inputStream
     * @return
     */
    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            log.error("fileMd5-error", e);
        }
        return null;
    }

    public static String saveFile(MultipartFile file, String path) {
        try {
            File targetFile = new File(path);
            if (targetFile.exists()) {
                return path;
            }
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
            return path;
        } catch (Exception e) {
            log.error("saveFile-error", e);
        }
        return null;
    }

    public static boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            boolean flag = file.delete();
            if (flag) {
                File[] files = file.getParentFile().listFiles();
                if (files == null || files.length == 0) {
                    file.getParentFile().delete();
                }
            }
            return flag;
        }
        return false;
    }

    public static boolean cleanPath(String pathName) throws IOException {
        File file = new File(pathName);
        if (!file.exists()){
            return true;
        }
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            String name = file.getName();
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                cleanPath(f.getPath());
            }else {
                //删除失败  如何处理
                Files.delete(f.toPath());
            }
        }
        file.delete();
        return true;
    }

}
