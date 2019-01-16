package com.sourceSoft.digitalProjct.util;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;

/**
 * <p>标题: 属性信息的缓存机制实现类</p>
 * <p>类功能描述: 属性信息的缓存机制实现类，由于属性信息是一个xml配置文件，所以加载
 * 属性信息后会缓存起来，避免重新加载，提高程序性能</p>
 * <p>公司:中科软件 </p>
 * @author 王鹏飞
 * @version 1.0
 */

public class PorpOperate {
  public PorpOperate() {
  }

  private static Map cache = new HashMap();
  private static String filePath = "";
  /**
   *<p>说明:从属性文件中读取数据源为strDbsource,表名为strTableName的主键和前缀,附加了缓存机制</p>
   *@param strDbsource 数据源 strTableName 表名
   *@return 成功返回数组 String[],【0】主键名称，【1】前缀pre
            失败返回null
   */
  public  synchronized String[] getKey(String strDbsource, String strTableName) {
    writeCache();
    Map tableMap = (Map) ( (MapModelEntry) cache.get(filePath)).databaseMap.get(
        strDbsource);
    if (tableMap == null) {
      return null;
    }
    else {
      return (String[]) tableMap.get(strTableName.toUpperCase());
    }
  }

  /**
    *<p>说明:从属性文件中读取所有的属性键值对</p>
    *@param 无
    *@return Map
    */
 
  public  synchronized Map getProp() {
    writeCache();
    return (Map) ( (MapModelEntry) cache.get(filePath)).propMap;
  }

  /**
   *<p>说明:如果是第一次读取或文件修改过,读取属性信息到缓存</p>
   *@param 无
   *@return 无
   */
  public  void writeCache() {
    try {

      File xsltFile = new File(filePath);
      //System.out.println("PropOperate writeCache()配置文件的路径为：：：：" + filePath);
      long xslLastModified = xsltFile.lastModified();
      MapModelEntry entry = (MapModelEntry) cache.get(filePath);

      if (entry != null) {
        if (xslLastModified > entry.lastModified) {
          entry = null;
        }
      }

      if (entry == null) {
        javax.xml.parsers.DocumentBuilderFactory dbf =
            javax.xml.parsers.DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        //System.out.println("PropOperate writeCache()开始解析xml配置文件");
        Document doc = docBuilder.parse(new File(filePath));
        //System.out.println("PropOperate writeCache()解析的xml配置文件对象：："+doc);
        Map databaseMap = getDatabaseMap(doc);
        //System.out.println("PropOperate writeCache()读取数据前缀和key配置产生的map对象：："+databaseMap);
        Map propMap = new TreeMap();
        getPropMap(doc, "connectionPool", propMap);
        //System.out.println("PropOperate writeCache()读取连接池配置产生的map对象：："+propMap);
        getPropMap(doc, "resource", propMap);
        getPropMap(doc, "affix", propMap);
       
        entry = new MapModelEntry(xslLastModified, databaseMap, propMap);
        cache.put(filePath, entry);
      }

    }
    catch (Exception e) {
      e.printStackTrace();

    }
  }

  private  void getPropMap(Document doc, String strNodeName, Map map) {
    NodeList list = doc.getDocumentElement().getElementsByTagName(
        strNodeName);

    for (int i = 0; i < list.getLength(); i++) {
      Element node = (Element) list.item(i);
      NodeList propNodeList = node.getElementsByTagName("prop");
      for (int t = 0; t < propNodeList.getLength(); t++) {
        node = (Element) propNodeList.item(t);
        if (node.hasAttribute("name")) {
          if (node.getChildNodes().item(0) != null) {
            map.put(node.getAttribute("name").trim(),
                    node.getChildNodes().item(0).getNodeValue());
          }
          else {
            map.put(node.getAttribute("name").trim(), "");
          }
        }
      }
    }

  }

  /**
   *<p>说明:读取配置的数据库和前缀</p>
   *@Document 解析后的Document
   *@return 无
   */
  private   Map getDatabaseMap(Document doc) {
    Map databaseMap = new TreeMap();
    NodeList list = doc.getDocumentElement().getElementsByTagName(
        "database");
    for (int i = 0; i < list.getLength(); i++) {
      Map tableMap = new TreeMap();
      String datasource = "";
      Element node = (Element) list.item(i);
      if (node.hasAttribute("datasource")) {
        datasource = node.getAttribute("datasource").trim();
      }
      //System.out.println(datasource);
      NodeList tableNodeList = node.getElementsByTagName("table");
      for (int t = 0; t < tableNodeList.getLength(); t++) {
        node = (Element) tableNodeList.item(t);
        String[] strArray = new String[2];
        if (node.hasAttribute("key")) {
          strArray[0] = node.getAttribute("key").trim();
        }
        if (node.hasAttribute("pre")) {
          strArray[1] = node.getAttribute("pre").trim();
        }
        tableMap.put(node.getAttribute("name").trim().toUpperCase(), strArray);

      }
      databaseMap.put(datasource, tableMap);
    }
    return databaseMap;

  }

  static class MapModelEntry {
    long lastModified;
    Map databaseMap; //存放数据库表主键配置
    Map propMap; //存放其它属性配置

    MapModelEntry(long lastModified, Map databaseMap, Map propMap) {
      this.lastModified = lastModified;
      this.databaseMap = databaseMap;
      this.propMap = propMap;
    }
  }

  public  void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public static void main(String[] args) {

    PorpOperate op = new PorpOperate();
    op.setFilePath("c:" + File.separator + "prop.xml");
    String[] str = op.getKey("security", "DDd");
    //System.out.println(str[0]);



  }

}