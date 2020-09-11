package com.imeng.web.util;

import cn.hutool.core.codec.Base64Decoder;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 主要用于读取合同信息
 * pdf操作工具类
 * @author wwj
 * 2020/6/11 10:14
 */
public class PdfReaderUtil {

    /**
     * 读PDF文件，使用了pdfbox开源项目
     * @param fileName
     */
    public static  String readPDF(String fileName) {
        File file = new File(fileName);
        FileInputStream in = null;
        String result = "";
        try {
            in = new FileInputStream(fileName);
            // 新建一个PDF解析器对象
            PDFParser parser = new PDFParser(new RandomAccessFile(file,"rw"));
            // 对PDF文件进行解析
            parser.parse();
            // 获取解析后得到的PDF文档对象
            PDDocument pdfdocument = parser.getPDDocument();
            // 新建一个PDF文本剥离器
            PDFTextStripper stripper = new PDFTextStripper();
            stripper .setSortByPosition(true); //sort设置为true 则按照行进行读取，默认是false
            // 从PDF文档对象中剥离文本
            result = stripper.getText(pdfdocument);
        } catch (Exception e) {
            System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + e);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }

    /**
     * 读PDF文件，使用了pdfbox开源项目
     * @param file
     */
    public static String readPDFFile(File file) {
        String result = "";
        try {
            // 新建一个PDF解析器对象
            PDFParser parser = new PDFParser(new RandomAccessFile(file,"rw"));
            // 对PDF文件进行解析
            parser.parse();
            // 获取解析后得到的PDF文档对象
            PDDocument pdfdocument = parser.getPDDocument();
            // 新建一个PDF文本剥离器
            PDFTextStripper stripper = new PDFTextStripper();
            stripper .setSortByPosition(true); //sort设置为true 则按照行进行读取，默认是false
            // 从PDF文档对象中剥离文本
            result = stripper.getText(pdfdocument);
        } catch (Exception e) {
            System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + e);
            e.printStackTrace();
        } finally {

        }
        return result;
    }

    public static String readPDFFile(String base64) {
        String result = "";
        try {
            // 获取解析后得到的PDF文档对象
            PDDocument pdfdocument = PDDocument.load(Base64Decoder.decode(base64));
            // 新建一个PDF文本剥离器
            PDFTextStripper stripper = new PDFTextStripper();
            stripper .setSortByPosition(true); //sort设置为true 则按照行进行读取，默认是false
            // 从PDF文档对象中剥离文本
            result = stripper.getText(pdfdocument);
        } catch (Exception e) {
            System.out.println("读取PDF文件失败！" + e);
            e.printStackTrace();
        } finally {

        }
        return result;
    }
}
