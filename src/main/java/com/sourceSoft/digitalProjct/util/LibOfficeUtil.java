package com.sourceSoft.digitalProjct.util;

import org.apache.commons.io.FileUtils;
 
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 



import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
 
public class LibOfficeUtil {
 
    /**
     * 打开libreOffice服务的方法
     * @return
     */
    public String getLibreOfficeHome() {
        String osName = System.getProperty("os.name");
 
        if (Pattern.matches("Linux.*", osName)) {
            //获取linux系统下libreoffice主程序的位置
            return "/opt/libreoffice 5/program/soffice";
        } else if (Pattern.matches("Windows.*", osName)) {
            //获取windows系统下libreoffice主程序的位置
            return "D:\\Program Files\\LibreOffice";
        }
        return null;
    }
 
    /**
     * 转换libreoffice支持的文件为pdf
     * @param inputfile
     * @param outputfile
     */
    public void libreOffice2PDF(File inputfile, File outputfile) {
        String LibreOffice_HOME = getLibreOfficeHome();
        String fileName = inputfile.getName();
        System.out.println(fileName.substring(fileName.lastIndexOf(".")));
        if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".txt")) {
            System.out.println("处理txt文件");
            new LibOfficeUtil().TXTHandler(inputfile);
        }
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        // libreOffice的安装目录
        configuration.setOfficeHome(new File(LibreOffice_HOME));
        // 端口号
        configuration.setPortNumber(8100);
        configuration.setTaskExecutionTimeout(1000 * 60 * 25L);
//         设置任务执行超时为10分钟
        configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
//         设置任务队列超时为24小时
        OfficeManager officeManager = configuration.buildOfficeManager();
        officeManager.start();
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        converter.getFormatRegistry();
        try {
            converter.convert(inputfile, outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            officeManager.stop();
        }
 
    }
 
    public static String showPreview(String path,HttpServletRequest request){
    	//TODO 暂时用项目路径
    	String strPath=request.getRealPath("/");
        File f = new File(strPath+path);
        String pdfPath = AssetUtil.getPDFInfoPath() + f.getName().substring(0, f.getName().lastIndexOf(".")) + ".pdf";
        System.out.println(strPath + pdfPath);
        File pdfFile = new File(strPath + pdfPath);
        if(!pdfFile.exists()){
        	new LibOfficeUtil().libreOffice2PDF(f, pdfFile);
        }
        return pdfPath;
    }
    
    
    /**
     * 测试的方法
     * @param args
     */
    public static void main(String[] args) {
        //使用Files类遍历图片文件夹的文件
        Path path = Paths.get("E:\\file");
        try {
            DirectoryStream<Path> paths = Files.newDirectoryStream(path);
            for (Path p : paths) {
 
                File f = p.toFile();
                System.out.println(path.getParent().toString() + "/pdf/" + f.getName().substring(0, f.getName().lastIndexOf(".")) + ".pdf");
                new LibOfficeUtil().libreOffice2PDF(f, new File(path.getParent().toString() + "/pdf/" + f.getName().substring(0, f.getName().lastIndexOf(".")).toString() + ".pdf"));
            }
        } catch (IOException e) {
 
            e.printStackTrace();
 
        }
    }
 
    /**
     * 转换txt文件编码的方法
     *
     * @param file
     * @return
     */
    public File TXTHandler(File file) {
        //或GBK
        String code = "gb2312";
        byte[] head = new byte[3];
        try {
            InputStream inputStream = new FileInputStream(file);
            inputStream.read(head);
            if (head[0] == -1 && head[1] == -2) {
                code = "UTF-16";
            } else if (head[0] == -2 && head[1] == -1) {
                code = "Unicode";
            } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
                code = "UTF-8";
            }
            inputStream.close();
 
            System.out.println(code);
            if (code.equals("UTF-8")) {
                return file;
            }
            String str = FileUtils.readFileToString(file, code);
            FileUtils.writeStringToFile(file, str, "UTF-8");
            System.out.println("转码结束");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return file;
    }
}

