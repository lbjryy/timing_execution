package com.sourceSoft.digitalProjct.util;

import java.io.PrintStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
  private static final String[] str = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m" };

  private static final String[] str1 = { "8", "9", "10", "11", "12", "13", "14", "15" };
  private static final String[] str2 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

  public static String sortStr(String inStr)
  {
    String outStr = "";
    char[] arr_ch = inStr.toCharArray();
    int len = arr_ch.length;
    List list = new ArrayList();
    for (int i = 0; i < len; i++) {
      char c = arr_ch[i];
      list.add(Character.valueOf(c));
    }

    Collections.sort(list);
    StringBuffer sb = new StringBuffer();
    int sortLen = list.size();
    for (int i = 0; i < sortLen; i++) {
      sb.append(list.get(i));
    }
    outStr = sb.toString();
    return outStr;
  }

  public static int parseInt(String input)
  {
    try
    {
      return Integer.parseInt(input);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static String formatDateToString(Date date, String format)
  {
    if (date == null) {
      return "";
    }

    SimpleDateFormat dateformat = new SimpleDateFormat(format);

    return dateformat.format(date);
  }

  public static Date parseStringToDate(String input, String format)
  {
    SimpleDateFormat dateformat = new SimpleDateFormat(format);
    try {
      return dateformat.parse(input);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String formatDateToDHMSString(Date date)
  {
    if (date == null) {
      return "";
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("d日 HH:mm:ss");

    return dateformat.format(date);
  }

  public static String formatDateToHMSString(Date date)
  {
    if (date == null) {
      return "";
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");

    return dateformat.format(date);
  }

  public static Date parseHMSStringToDate(String input)
  {
    SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
    try
    {
      return dateformat.parse(input);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String formatDateToMysqlString(Date date)
  {
    if (date == null) {
      return "";
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return dateformat.format(date);
  }

  public static Date parseStringToMysqlDate(String input)
  {
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      return dateformat.parse(input);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String formatDateToMMddHHmm(Date date)
  {
    if (date == null) {
      return "";
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("M月d日 HH:mm");

    return dateformat.format(date);
  }

  public static String formatDateToyyMMddHHmm(Date date)
  {
    if (date == null) {
      return "";
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("yy年M月d日HH:mm");

    return dateformat.format(date);
  }

  public static String genTimeStampString(Date date)
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");

    return df.format(date);
  }

  public static String replace(String source, String oldStr, String newStr)
  {
    return replace(source, oldStr, newStr, true);
  }

  public static String replace(String source, String oldStr, String newStr, boolean matchCase)
  {
    if (source == null) {
      return null;
    }

    if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
      return source;
    }
    int findStartPos = 0;
    int a = 0;
    while (a > -1) {
      int b = 0;

      String str1 = source;
      String str2 = str1.toLowerCase();
      String str3 = oldStr;
      String str4 = str3.toLowerCase();
      String strB;
      String strA;
      if (matchCase) {
        strA = str1;
        strB = str3;
      } else {
        strA = str2;
        strB = str4;
      }
      a = strA.indexOf(strB, findStartPos);
      if (a > -1) {
        b = oldStr.length();
        findStartPos = a + b;
        StringBuffer bbuf = new StringBuffer(source);
        source = bbuf.replace(a, a + b, newStr) + "";

        findStartPos = findStartPos + newStr.length() - b;
      }
    }
    return source;
  }

  public static String trimTailSpaces(String input)
  {
    if (isEmpty(input)) {
      return "";
    }

    String trimedString = input.trim();

    if (trimedString.length() == input.length()) {
      return input;
    }
    return input.substring(0, input.indexOf(trimedString) + trimedString.length());
  }

  public static String clearNull(String input)
  {
    return isEmpty(input) ? "" : input;
  }

  public static String limitStringLength(String input, int maxLength)
  {
    if (isEmpty(input)) {
      return "";
    }
    if (input.length() <= maxLength) {
      return input;
    }
    return input.substring(0, maxLength - 3) + "...";
  }

  public static String scriptAlert(String message)
  {
    return "<SCRIPT language=\"javascript\">alert(\"" + message + "\");</SCRIPT>";
  }

  public static String scriptRedirect(String url)
  {
    return "<SCRIPT language=\"javascript\">document.location=\"" + url + "\";</SCRIPT>";
  }

  public static String scriptHistoryBack()
  {
    return "<SCRIPT language=\"javascript\">history.back();</SCRIPT>";
  }

  public static String replaceHtmlCode(String content)
  {
    if (isEmpty(content)) {
      return "";
    }

    String[] eventKeywords = { "onmouseover", "onmouseout", "onmousedown", "onmouseup", "onmousemove", "onclick", "ondblclick", "onkeypress", "onkeydown", "onkeyup", "ondragstart", "onerrorupdate", "onhelp", "onreadystatechange", "onrowenter", "onrowexit", "onselectstart", "onload", "onunload", "onbeforeunload", "onblur", "onerror", "onfocus", "onresize", "onscroll", "oncontextmenu" };

    content = replace(content, "<script", "&ltscript", false);
    content = replace(content, "</script", "&lt/script", false);
    content = replace(content, "<marquee", "&ltmarquee", false);
    content = replace(content, "</marquee", "&lt/marquee", false);
    content = replace(content, "\r\n", "<BR>");

    for (int i = 0; i < eventKeywords.length; i++) {
      content = replace(content, eventKeywords[i], "_" + eventKeywords[i], false);
    }

    return content;
  }

  public static String replaceHtmlToText(String input)
  {
    if (isEmpty(input)) {
      return "";
    }
    return setBr(setTag(input));
  }

  public static String setTag(String s)
  {
    int j = s.length();
    StringBuffer stringbuffer = new StringBuffer(j + 500);

    for (int i = 0; i < j; i++) {
      char ch = s.charAt(i);
      if (ch == '<')
      {
        stringbuffer.append("〈");
      } else if (ch == '>')
      {
        stringbuffer.append("〉");
      } else if (ch == '&')
      {
        stringbuffer.append("〃");
      } else if (ch == '%')
      {
        stringbuffer.append("※");
      }
      else stringbuffer.append(ch);

    }

    return stringbuffer.toString();
  }

  public static String setBr(String s)
  {
    int j = s.length();
    StringBuffer stringbuffer = new StringBuffer(j + 500);
    for (int i = 0; i < j; i++)
    {
      if ((s.charAt(i) != '\n') && (s.charAt(i) != '\r'))
      {
        stringbuffer.append(s.charAt(i));
      }
    }

    return stringbuffer.toString();
  }

  public static String setNbsp(String s)
  {
    int j = s.length();
    StringBuffer stringbuffer = new StringBuffer(j + 500);
    for (int i = 0; i < j; i++) {
      if (s.charAt(i) == ' ')
        stringbuffer.append("");
      else {
        stringbuffer.append(s.charAt(i) + "");
      }
    }
    return stringbuffer.toString();
  }

  public static boolean isNumeric(String input)
  {
    if (isEmpty(input)) {
      return false;
    }

    for (int i = 0; i < input.length(); i++) {
      char charAt = input.charAt(i);

      if (!Character.isDigit(charAt)) {
        return false;
      }
    }
    return true;
  }

  public static String toChi(String input)
  {
    try
    {
      byte[] bytes = input.getBytes("ISO8859-1");
      return new String(bytes, "GBK");
    } catch (Exception ex) {
    }
    return input;
  }

  public static String toISO(String input)
  {
    return changeEncoding(input, "GBK", "ISO8859-1");
  }

  public static String changeEncoding(String input, String sourceEncoding, String targetEncoding)
  {
    if ((input == null) || (input.equals(""))) {
      return input;
    }
    try
    {
      byte[] bytes = input.getBytes(sourceEncoding);
      return new String(bytes, targetEncoding);
    } catch (Exception ex) {
    }
    return input;
  }

  public static String replaceSql(String input)
  {
    return replace(input, "'", "''");
  }

  public static String encode(String value)
  {
    if (isEmpty(value)) {
      return "";
    }
    try
    {
      value = URLEncoder.encode(value, "GB2312");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return value;
  }

  public static String decode(String value)
  {
    if (isEmpty(value)) {
      return "";
    }
    try
    {
      return URLDecoder.decode(value, "GB2312");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return value;
  }

  public static boolean isEmpty(String input)
  {
    return (input == null) || (input.length() == 0);
  }

  public static int getBytesLength(String input)
  {
    if (input == null) {
      return 0;
    }

    int bytesLength = input.getBytes().length;

    return bytesLength;
  }

  public static String isEmpty(String input, String errorMsg)
  {
    if (isEmpty(input)) {
      return errorMsg;
    }
    return "";
  }

  public static String getExtension(String fileName)
  {
    if (fileName != null) {
      int i = fileName.lastIndexOf(46);
      if ((i > 0) && (i < fileName.length() - 1)) {
        return fileName.substring(i + 1).toLowerCase();
      }
    }
    return "";
  }

  public static String getPrefix(String fileName)
  {
    if (fileName != null) {
      fileName = fileName.replace('\\', '/');

      if (fileName.lastIndexOf("/") > 0) {
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
      }

      int i = fileName.lastIndexOf(46);
      if ((i > 0) && (i < fileName.length() - 1)) {
        return fileName.substring(0, i);
      }
    }
    return "";
  }

  public static String getShortFileName(String fileName)
  {
    if (fileName != null) {
      fileName = fileName.replace('\\', '/');

      if (fileName.lastIndexOf("/") > 0) {
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
      }

      return fileName;
    }
    return "";
  }

  public static String getPath(String resourceName)
  {
    if (!resourceName.startsWith("/")) {
      resourceName = "/" + resourceName;
    }
    URL classUrl = new StringUtil().getClass().getResource(resourceName);

    if (classUrl != null) {
      return classUrl.getFile();
    }
    return null;
  }

  public static String getDirectory(String resourceUrl, String resourceName)
  {
    int n_pos = resourceUrl.indexOf(resourceName);
    return resourceUrl.substring(0, n_pos);
  }

  public static String dateToChineseString(Date date)
  {
    if (date == null) {
      return null;
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    return dateformat.format(date);
  }

  public static String dateTo14String(Date date)
  {
    if (date == null) {
      return null;
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");

    return dateformat.format(date);
  }

  public static Date string14ToDate(String input)
  {
    if (isEmpty(input)) {
      return null;
    }

    if (input.length() != 14) {
      return null;
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
    try
    {
      return dateformat.parse(input);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public static String textToHtml(String input)
  {
    if (isEmpty(input)) {
      return "";
    }

    input = replace(input, "<", "<");
    input = replace(input, ">", ">");

    input = replace(input, "\n", "<br>\n");
    input = replace(input, "\t", "    ");
    input = replace(input, "  ", "  ");

    return input;
  }

  public static String getRepStr(String data_)
  {
    if (data_.indexOf(45) != -1) {
      data_ = data_.replace("-", "");
    }
    return data_;
  }

  public static String getTrdpwd()
  {
    int b = (int)(Math.random() * 8.0D);
    String s3 = "6";
    String s = "";
    for (int i = 0; i < Integer.parseInt(s3); i++) {
      int a = (int)(Math.random() * 36.0D);
      s = s + str[a];
    }
    return s;
  }
  public static String getFundpassword() {
    String s = "";
    for (int i = 0; i < 6; i++) {
      int a = (int)(Math.random() * 10.0D);
      s = s + str2[a];
    }
    return s;
  }

  public static String stringFilterSC(String str, String subalphabet)
  {
    String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    return m.replaceAll(subalphabet).trim();
  }

  public static void main(String[] args)
  {
    String str = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
    System.out.println(str);
    System.out.println(stringFilterSC(str, "_"));
  }
}