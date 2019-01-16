package com.sourceSoft.digitalProjct.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil
{
  private static final int BUFFER_SIZE = 102400;

  public static boolean deleteFolder(String sPath)
  {
    boolean flag = false;
    File file = new File(sPath);

    if (!file.exists()) {
      return flag;
    }

    if (file.isFile()) {
      return deleteFile(sPath);
    }
    return deleteDirectory(sPath);
  }

  public static boolean deleteFile(String sPath)
  {
    boolean flag = false;
    File file = new File(sPath);

    if ((file.isFile()) && (file.exists())) {
      file.delete();
      flag = true;
    }
    return flag;
  }

  public static boolean deleteDirectory(String sPath)
  {
    if (!sPath.endsWith(File.separator)) {
      sPath = sPath + File.separator;
    }
    File dirFile = new File(sPath);

    if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
      return false;
    }
    boolean flag = true;

    File[] files = dirFile.listFiles();
    for (int i = 0; i < files.length; i++)
    {
      if (files[i].isFile()) {
        flag = deleteFile(files[i].getAbsolutePath());
        if (!flag) break; 
      }
      else
      {
        flag = deleteDirectory(files[i].getAbsolutePath());
        if (!flag) break;
      }
    }
    if (!flag) return false;

    if (dirFile.delete()) {
      return true;
    }
    return false;
  }

  public static boolean localUpload(MultipartFile mFile, String fileName, String path)
  {
    boolean flag = false;
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    InputStream inputStream = null;
    FileOutputStream outputStream = null;
    try {
      inputStream = mFile.getInputStream();
      path = path + fileName;
      outputStream = new FileOutputStream(path);
      byte[] b = new byte[2048];
      int length = -1;
      while ((length = inputStream.read(b)) != -1) {
        outputStream.write(b, 0, length);
      }
      outputStream.flush();
      flag = true;
    }
    catch (IOException e) {
    }
    finally {
      try {
        if (inputStream != null)
          inputStream.close();
      }
      catch (IOException e1)
      {
      }
      try {
        if (outputStream != null)
          outputStream.close();
      }
      catch (IOException e)
      {
      }
    }
    return flag;
  }

  public static boolean uploadingFile(InputStream in, String path, String fileName)
  {
    boolean flag = false;
    OutputStream out = null;
    try {
      File destFile = new File(path);
      if (!destFile.exists()) {
        destFile.mkdirs();
      }
      out = new BufferedOutputStream(new FileOutputStream(destFile.getPath() + File.separator + fileName), 102400);
      in = new BufferedInputStream(in, 102400);
      int len = 0;
      byte[] buffer = new byte[102400];
      while ((len = in.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      out.flush();
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != in) {
          in.close();
        }
        if (null != out)
          out.close();
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
    return flag;
  }

  public static String getNumberName(String fileName)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
    int rand = new Random().nextInt(1000);
    String numberName = format.format(new Date()) + rand;
    return getNewFileName(fileName, numberName);
  }

  public static String getNewFileName(String fileName, String newName) {
    String suffix = getSuffix(fileName);
    if (suffix != null) {
      newName = newName + suffix;
    }
    return newName;
  }

  public static String getSuffix(String fileName) {
    int index = fileName.lastIndexOf(".");
    if (index != -1) {
      String suffix = fileName.substring(index);
      return suffix;
    }
    return null;
  }

  public static void writeFile(File f, String content)
  {
    writeFile(f, content, "utf-8");
  }

  public static void writeFile(File f, String content, String encode)
  {
    try
    {
      if (!f.exists()) {
        f.createNewFile();
      }
      OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), encode);
      BufferedWriter utput = new BufferedWriter(osw);
      utput.write(content);
      utput.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void writeFile(String path, String content, String encode)
  {
    File f = new File(path);
    writeFile(f, content, encode);
  }

  public static void writeFile(String path, String content)
  {
    File f = new File(path);
    writeFile(f, content, "utf-8");
  }

  public static String readFile(File file)
  {
    return readFile(file, "UTF-8");
  }

  public static String readFile(File file, String encode)
  {
    String output = "";

    if (file.exists()) {
      if (file.isFile()) {
        try {
          InputStreamReader isr = new InputStreamReader(new FileInputStream(file), encode);
          BufferedReader input = new BufferedReader(isr);
          StringBuffer buffer = new StringBuffer();
          String text;
          while ((text = input.readLine()) != null)
            buffer.append(text + "\n");
          output = buffer.toString();
        }
        catch (IOException ioException) {
          System.err.println("File Error！");
        }
      } else if (file.isDirectory()) {
        String[] dir = file.list();
        output = output + "Directory contents：\n";
        for (int i = 0; i < dir.length; i++) {
          output = output + dir[i] + "\n";
        }
      }
    }
    else {
      System.err.println("Does not exist！");
    }

    return output;
  }

  public static String readFile(String fileName, String encode)
  {
    File file = new File(fileName);
    return readFile(file, encode);
  }

  public static String readFile(String fileName)
  {
    return readFile(fileName, "utf-8");
  }

  public static List<File> getFiles(String folder)
  {
    File file = new File(folder);
    List files = new ArrayList();
    if (file.exists()) {
      File[] sonFiles = file.listFiles();
      if ((sonFiles != null) && (sonFiles.length > 0)) {
        for (int i = 0; i < sonFiles.length; i++) {
          if (!sonFiles[i].isDirectory()) {
            files.add(sonFiles[i]);
          }
        }
      }
    }
    return files;
  }

  public static List<File> getFolders(String folder)
  {
    File file = new File(folder);
    List files = new ArrayList();
    if (file.exists()) {
      File[] sonFiles = file.listFiles();
      if ((sonFiles != null) && (sonFiles.length > 0)) {
        for (int i = 0; i < sonFiles.length; i++) {
          if (sonFiles[i].isDirectory()) {
            files.add(sonFiles[i]);
          }
        }
      }
    }
    return files;
  }

  public static boolean hasSonFolder(String folder)
  {
    File file = new File(folder);
    return hasSonFolder(file);
  }

  public static boolean hasSonFolder(File file)
  {
    if (file.exists()) {
      File[] sonFiles = file.listFiles();
      if ((sonFiles != null) && (sonFiles.length > 0)) {
        for (int i = 0; i < sonFiles.length; i++) {
          if (sonFiles[i].isDirectory()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static void mkdir(String folder)
  {
    File file = new File(folder);
    if (!file.exists())
      file.mkdir();
  }

  public static void copy(File src, File dst)
  {
    try
    {
      int BUFFER_SIZE = 32768;
      InputStream in = null;
      OutputStream out = null;
      try {
        in = new FileInputStream(src);
        out = new FileOutputStream(dst);
        byte[] buffer = new byte[BUFFER_SIZE];
        int count;
        while ((count = in.read(buffer)) != -1) {
          out.write(buffer, 0, count);
        }

        if (null != in) {
          in.close();
        }
        if (null != out)
          out.close();
      }
      finally
      {
        if (null != in) {
          in.close();
        }
        if (null != out)
          out.close();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static boolean copy(MultipartFile src, File dst)
  {
    boolean boolValue = false;
    try {
      if (src != null) {
        InputStream strinput = src.getInputStream();
        OutputStream stroutput = new FileOutputStream(dst);
        int byteread = 0;
        byte[] buffer = new byte[8192];
        while ((byteread = strinput.read(buffer, 0, 8192)) != -1) {
          stroutput.write(buffer, 0, byteread);
        }
        strinput.close();
        stroutput.close();
        boolValue = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return boolValue;
  }

  public static boolean copyImg(MultipartFile src, File dst, boolean flag, int width, int height)
  {
    boolean boolValue = false;
    try {
      if (src != null) {
        InputStream strinput = src.getInputStream();

        String fileName = dst.getName();
        String pictureFormat = StringUtil.getExtension(dst.getPath());
        if (flag) {
          String formatName = StringUtil.getExtension(fileName);
          if ((ImageUtils.judgeImageType(pictureFormat)) && 
            (strinput != null)) {
            System.out.println("##########切割");
            strinput = ImageUtils.byteToInputStream(ImageUtils.getByteImatZoom(width, height, formatName, strinput));
          }

        }
        else if (ImageUtils.judgeImageType(pictureFormat)) {
          strinput = ImageUtils.byteToInputStream(ImageUtils.bufferedImageCompressTobytes(strinput, pictureFormat, 0.35F));
        }

        OutputStream stroutput = new FileOutputStream(dst);
        int byteread = 0;
        byte[] buffer = new byte[8192];
        while ((byteread = strinput.read(buffer, 0, 8192)) != -1) {
          stroutput.write(buffer, 0, byteread);
        }
        strinput.close();
        stroutput.close();
        boolValue = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return boolValue;
  }

  public static void copyDirectiory(String sourceDir, String targetDir)
    throws IOException
  {
    if (new File(sourceDir).exists())
    {
      File targetFolder = new File(targetDir);
      if (!targetFolder.exists()) {
        targetFolder.mkdirs();
      }

      File[] file = new File(sourceDir).listFiles();
      for (int i = 0; i < file.length; i++) {
        if (file[i].isFile())
        {
          File sourceFile = file[i];

          File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
          copy(sourceFile, targetFile);
        }
        if (file[i].isDirectory())
        {
          String dir1 = sourceDir + "/" + file[i].getName();

          String dir2 = targetDir + "/" + file[i].getName();
          copyDirectiory(dir1, dir2);
        }
      }
    }
  }

  public static String getExt(File src)
  {
    if (src != null) {
      String name = src.getName();
      return name.substring(name.lastIndexOf("."), name.length());
    }
    return "";
  }

  public static String getFileName(MultipartFile uploadFile)
  {
    if (uploadFile != null) {
      String name = uploadFile.getOriginalFilename().replace("\\", "/");
      return name.substring(name.lastIndexOf("/") + 1, name.length());
    }
    return "";
  }

  public static String getExt(String src)
  {
    if (src != null) {
      return src.substring(src.lastIndexOf("."), src.length());
    }
    return "";
  }

  public static void del(String path)
  {
    File file = new File(path);
    deleteFile(file);
  }

  public static void deleteFile(File file)
  {
    if (file.exists()) {
      if (file.isFile()) {
        file.delete();
      } else if (file.isDirectory()) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
          deleteFile(files[i]);
        }
      }
      file.delete();
    }
  }

  public static void outPut(HttpServletResponse response, String filepath, String name)
  {
    try
    {
      File file = new File(filepath);
      String filename = file.getName();
      if (StringUtils.isBlank(name)) {
        name = filename;
      }
      String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
      InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
      byte[] buffer = new byte[fis.available()];
      fis.read(buffer);
      fis.close();
      response.reset();
      response.addHeader("Content-Length", "" + file.length());
      response.addHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
      response.setContentLength((int)file.length());
      OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
      if (ext.equals("xls"))
        response.setContentType("application/msexcel");
      else {
        response.setContentType("application/octet-stream;charset=GBK");
      }
      toClient.write(buffer);
      toClient.flush();
      toClient.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void upzip() throws Exception { File file = new File("D:\\test.zip");
    ZipFile zipFile = new ZipFile(file);

    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
    ZipEntry zipEntry = null;
    while ((zipEntry = zipInputStream.getNextEntry()) != null) {
      String fileName = zipEntry.getName();
      File temp = new File("D:\\un\\" + fileName);
      temp.getParentFile().mkdirs();
      OutputStream os = new FileOutputStream(temp);

      InputStream is = zipFile.getInputStream(zipEntry);
      int len = 0;
      while ((len = is.read()) != -1)
        os.write(len);
      os.close();
      is.close();
    }
    zipInputStream.close();
  }
}