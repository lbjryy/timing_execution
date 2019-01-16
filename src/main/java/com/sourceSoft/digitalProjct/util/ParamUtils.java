package com.sourceSoft.digitalProjct.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.*;

import com.sourceSoft.digitalProjct.constant.SysConstant;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author tang yong feng & all
 * @version 1.0
 */

public class ParamUtils {
	 private static int iIndex = 0;
    public ParamUtils() {
    }


    public static String getParameter(HttpServletRequest request, String param) {
        String str = request.getParameter(param);
        if (str != null) {
            return str.trim();
        } else {
            return "";
        }
    }

    public static String getParameter(HttpServletRequest request, String param, String moren) {
        String str = request.getParameter(param);
        if (str != null) {
            return str.trim();
        } else {
            return moren;
        }
    }


    public static String getSession(HttpSession session, String sessionName) {
        String retStr = "";
        try {
            retStr = session.getAttribute(sessionName).toString();
        } catch (Exception e) {
            retStr = "";
        }
        return retStr;
    }

    public static String getStringParameter(HttpServletRequest request,
            String paramName, String defaultString) {
        String temp = request.getParameter(paramName);
        if ((temp == null) || (temp.compareTo("") == 0)) {
            return defaultString;
        } else {
            return temp;
        }
    }

    /**
     * Gets a parameter as a int.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param paramName
     *            The name of the parameter you want to get
     * @return The int value of the parameter specified or the default value if
     *         the parameter is not found.
     */
    public static int getIntParameter(HttpServletRequest request,
            String paramName, int defaultNum) {
        String temp = request.getParameter(paramName);
        if (temp != null && !temp.equals("")) {
            int num = defaultNum;
            try {
                num = Integer.parseInt(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    /**
     * Gets a parameter as a string.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @param emptyStringsOK
     *            Return the parameter values even if it is an empty string.
     * @return The value of the parameter or null if the parameter was not
     *         found.
     */
    public static String getParameter(HttpServletRequest request, String name,
            boolean emptyStringsOK) {
        String temp = request.getParameter(name);
        if (temp != null) {
            if (temp.equals("") && !emptyStringsOK) {
                return null;
            } else {
                return temp;
            }
        } else {
            return null;
        }
    }

    /**
     * Gets a parameter as a boolean.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @return True if the value of the parameter was "true", false otherwise.
     */
    public static boolean getBooleanParameter(HttpServletRequest request,
            String name) {
        return getBooleanParameter(request, name, false);
    }

    /**
     * Gets a parameter as a boolean.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @return True if the value of the parameter was "true", false otherwise.
     */
    public static boolean getBooleanParameter(HttpServletRequest request,
            String name, boolean defaultVal) {
        String temp = request.getParameter(name);
        if ("true".equals(temp) || "on".equals(temp)) {
            return true;
        } else if ("false".equals(temp) || "off".equals(temp)) {
            return false;
        } else {
            return defaultVal;
        }
    }

    /**
     * Gets a list of int parameters.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @param defaultNum
     *            The default value of a parameter, if the parameter can't be
     *            converted into an int.
     */
    public static int[] getIntParameters(HttpServletRequest request,
            String name, int defaultNum) {
        String[] paramValues = request.getParameterValues(name);
        if (paramValues == null) {
            return null;
        }
        if (paramValues.length < 1) {
            return new int[0];
        }
        int[] values = new int[paramValues.length];
        for (int i = 0; i < paramValues.length; i++) {
            try {
                values[i] = Integer.parseInt(paramValues[i]);
            } catch (Exception e) {
                values[i] = defaultNum;
            }
        }
        return values;
    }

    /**
     * ȡ��һ���ַ�������
     * 
     * @param request
     * @param name
     * @return
     */
    public static String[] getParameters(HttpServletRequest request, String name) {

        String[] paramValues = request.getParameterValues(name);
        if (paramValues == null) {
            return null;
        }
        if (paramValues.length < 1) {
            return new String[0];
        }
        return paramValues;

    }

    /**
     * Gets a parameter as a double.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @return The double value of the parameter specified or the default value
     *         if the parameter is not found.
     */
    public static double getDoubleParameter(HttpServletRequest request,
            String name, double defaultNum) {
        String temp = request.getParameter(name);
        if (temp != null && !temp.equals("")) {
            double num = defaultNum;
            try {
                num = Double.parseDouble(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    /**
     * Gets a parameter as a long.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @return The long value of the parameter specified or the default value if
     *         the parameter is not found.
     */
    public static long getLongParameter(HttpServletRequest request,
            String name, long defaultNum) {
        String temp = request.getParameter(name);
        if (temp != null && !temp.equals("")) {
            long num = defaultNum;
            try {
                num = Long.parseLong(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    /**
     * Gets a list of long parameters.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @param defaultNum
     *            The default value of a parameter, if the parameter can't be
     *            converted into a long.
     */
    public static long[] getLongParameters(HttpServletRequest request,
            String name, long defaultNum) {
        String[] paramValues = request.getParameterValues(name);
        if (paramValues == null) {
            return null;
        }
        if (paramValues.length < 1) {
            return new long[0];
        }
        long[] values = new long[paramValues.length];
        for (int i = 0; i < paramValues.length; i++) {
            try {
                values[i] = Long.parseLong(paramValues[i]);
            } catch (Exception e) {
                values[i] = defaultNum;
            }
        }
        return values;
    }

    /**
     * Gets a parameter as a string.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @return The value of the parameter or null if the parameter was not found
     *         or if the parameter is a zero-length string.
     */
    public static String getAttribute(HttpServletRequest request, String name) {
        return getAttribute(request, name, false);
    }

    /**
     * Gets a parameter as a string.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the parameter you want to get
     * @param emptyStringsOK
     *            Return the parameter values even if it is an empty string.
     * @return The value of the parameter or null if the parameter was not
     *         found.
     */
    public static String getAttribute(HttpServletRequest request, String name,
            boolean emptyStringsOK) {
        String temp = (String) request.getAttribute(name);
        if (temp != null) {
            if (temp.equals("") && !emptyStringsOK) {
                return null;
            } else {
                return temp;
            }
        } else {
            return null;
        }
    }

    /**
     * Gets an attribute as a boolean.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the attribute you want to get
     * @return True if the value of the attribute is "true", false otherwise.
     */
    public static boolean getBooleanAttribute(HttpServletRequest request,
            String name) {
        String temp = (String) request.getAttribute(name);
        if (temp != null && temp.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets an attribute as a int.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the attribute you want to get
     * @return The int value of the attribute or the default value if the
     *         attribute is not found or is a zero length string.
     */
    public static int getIntAttribute(HttpServletRequest request, String name,
            int defaultNum) {
        String temp = (String) request.getAttribute(name);
        if (temp != null && !temp.equals("")) {
            int num = defaultNum;
            try {
                num = Integer.parseInt(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    /**
     * Gets an attribute as a long.
     * 
     * @param request
     *            The HttpServletRequest object, known as "request" in a JSP
     *            page.
     * @param name
     *            The name of the attribute you want to get
     * @return The long value of the attribute or the default value if the
     *         attribute is not found or is a zero length string.
     */
    public static long getLongAttribute(HttpServletRequest request,
            String name, long defaultNum) {
        String temp = (String) request.getAttribute(name);
        if (temp != null && !temp.equals("")) {
            long num = defaultNum;
            try {
                num = Long.parseLong(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }
    
    /**
     * 方法名 : getMaxID
     * 说明   ：得到指定数据源表名的主键的sequence
     * 引数   : String  strDbSource  , 数据源名称
     *         String  strTableName , 表名
     * 返回   : String , 成功返回主键sequence(有区分数据库的前缀则加上sequence前缀)
     */
     public static String getMaxID(String strDbSource, String strTableName)
     {
         String[] str = getMaxSeq(strDbSource, strTableName);
         if(str[1] != null && !str[1].equals(""))return str[1] + "_" + str[2];
         else return str[2];
     }
     
     /**
      * 方法名 : getMaxSeq
      * 说明   ：得到指定数据源表名的主键的sequence
      * 引数   : String  strDbSource  , 数据源名称
      *         String  strTableName , 表名
      * 返回   : String[] , [0]为主键字段名称 , [1]为前缀名称 , [2]为不带前缀的sequence
      */
      public static String[] getMaxSeq(String strDbSource, String strTableName)
      {
          String[] strArr      = new String[3];
          String[] strIdProp   = new String[2];
          String   strSeqPre   = "";
          String   strSeqDate  = "";
          String   strIdName   = "";

          try
          {
              PorpOperate porpOperate = new PorpOperate();
              strIdProp = porpOperate.getKey(strDbSource, strTableName);
              if(strIdProp != null && strIdProp.length != 0)
              {
                  strIdName = strIdProp[0];
                  strSeqPre = strIdProp[1];
              }
              else return strArr;

              strSeqDate = getSeqDate();

              strArr[0] = strIdName;
              strArr[1] = strSeqPre;
              strArr[2] = strSeqDate;
          }
          catch (Exception ex)
          {
              ex.printStackTrace();
          }
          return strArr;
      }
      
      /**
       * 方法名 : getSeqDate
       * 说明   ：得到以当前时间戳的sequence
       * 引数   : 无
       * 返回   : String , 成功返回主键sequence, 失败返回空字符串
       */
       public synchronized static String getSeqDate()
       {
           String strSeqDate = "";
           try
           {
               String           strDateTemp = "";
               Calendar         cal         = Calendar.getInstance();
               SimpleDateFormat formatter   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String           strDate     = formatter.format(cal.getTime());

               //strDateTemp = strDate.substring(2,3) + strDate.substring(5,6) + strDate.substring(8,9)
               //           + strDate.substring(11,12) + strDate.substring(14,15) + strDate.substring(17,18);

               strDateTemp = strDate.substring(2,4) + strDate.substring(5,7) + strDate.substring(8,10)
                           + strDate.substring(11,13) + strDate.substring(14,16) + strDate.substring(17,19);

               /***  2004－09－06 周琛修改开始  ZhouCh Modify Start  ***/
               //Random random = new Random();
               //double dRand  = random.nextFloat();
               if(iIndex == 1000)iIndex = 0;
               strSeqDate = strDateTemp + String.valueOf(iIndex);
               iIndex ++;
               /***  2004－09－06 周琛修改结束  ZhouCh Modify End    ***/


           }
           catch(Exception ex)
           {
               ex.printStackTrace();
           }
           return strSeqDate;
       }
       
       

}