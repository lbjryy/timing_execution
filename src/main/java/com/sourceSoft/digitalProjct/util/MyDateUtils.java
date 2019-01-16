package com.sourceSoft.digitalProjct.util;
  
  
import java.text.DateFormat;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;  
  
public class MyDateUtils {  
    //年月日  
    static final String formatPattern = "yyyy/MM/dd";  
    //年月日 时分 
    static final String formatPattern_1 = "yyyy/MM/dd HH:mm";
    
    static final String formatPattern_Short = "yyyyMMdd"; 
    //时间戳
    static final String formatPattern_2 = "yyyyMMddHHmmss";
    //时间戳
    static final String formatPattern_3 = "yyyyMMddHHmmssSSS";
     
    public static void main(String[] args) throws ParseException {
//    	String time =MyDateUtils.getPrefixDate("7");
//    	System.out.println(time);
//    	long timeago = 5*60*1000;
//    	String s = MyDateUtils.getTimeAgo(timeago);
//    	System.out.println(s);
//    	String time =MyDateUtils.getTimeByNum(1428025866l);
//    	System.out.println(time);
//    	String b =Long.toString(1428025866l);
//    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//    	Date date = df.parse(b); 
    	System.out.println(MyDateUtils.getNowTime());
    	System.out.println(MyDateUtils.getNowTime());
    	System.out.println(MyDateUtils.getNowTime());
    	Date date1 = new Date();
    	System.out.println(date1.getTime());
    	Date date2 = new Date();
    	System.out.println(date2.getTime());
    	Date date3 = new Date();
    	System.out.println(date3.getTime());
//    	1428026591.607872
//    	Date date = new Date();
//    	System.out.println(date.getTime());
//    	double d = 1428026591.607872d;
//    	double anotherDoubleValue = Math.floor(d*1000);
//    	long intValue = (long)anotherDoubleValue;
//    	System.out.println(intValue);
//    	long time=Long.valueOf(intValue);  
//    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
//    	System.out.println(sdf.format(new Date(time))); 
	}
      
    /**
     * 将数字转成时间
     * @throws ParseException 
     */
    public static String getTimeByNum(long num) throws ParseException{
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	return df.format(new Date(num));
    }
    
    /**
	 * 获取当前时刻多少秒之前的时刻
	 */
	public static String getTimeAgo(long time){
		DateFormat df =  new SimpleDateFormat(formatPattern_1);
		Date date = new Date();
		Date afterDate = new Date(date.getTime()-time);
		String s = df.format(afterDate);
		return s;
	}
    
    /** 
     * 获取当前日期 
     * @return 
     */  
    public static String getCurrentDate(){  
        SimpleDateFormat format = new SimpleDateFormat(formatPattern_1);        
        return format.format(new Date());  
    } 
    
    /** 
     * 获取当前时刻作为时间戳
     * @return 
     */  
    public static String getNowTime(){  
        SimpleDateFormat format = new SimpleDateFormat(formatPattern_3);        
        return format.format(new Date());  
    } 
      
    /** 
     * 获取制定毫秒数之前的日期 
     * @param timeDiff 
     * @return 
     */  
    public static String getDesignatedDate(long timeDiff){  
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);  
        long nowTime = System.currentTimeMillis();  
        long designTime = nowTime - timeDiff;         
        return format.format(designTime);  
    }  
      
    /** 
     *  
     * 获取前几天的日期 
     */  
    public static String getPrefixDate(String count){  
        Calendar cal = Calendar.getInstance();  
        int day = 0-Integer.parseInt(count);  
        cal.add(Calendar.DATE,day);   // int amount   代表天数   
        Date datNew = cal.getTime();   
        SimpleDateFormat format = new SimpleDateFormat(formatPattern_1);  
        return format.format(datNew);  
    }  
    
    /** 
     * 日期转换成字符串 
     * @param date 
     * @return 
     */  
    public static String dateToString(Date date){  
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);  
        return format.format(date);  
    }  
    /** 
     * 字符串转换日期 
     * @param str 
     * @return 
     */  
    public static Date stringToDate(String str){  
        //str =  " 2008-07-10 19:20:00 " 格式   
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);  
        if(!str.equals("")&&str!=null){  
            try {  
                return format.parse(str);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
      
    //java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .   
    public void timeSubtract(){  
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        Date begin = null;   
        Date end = null;   
        try {   
        begin = dfs.parse("2004-01-02 11:30:24");   
        end = dfs.parse("2004-03-26 13:31:40");   
        } catch (ParseException e) {   
        e.printStackTrace();   
        }   
  
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒    
  
        long day1 = between / (24 * 3600);   
        long hour1 = between % (24 * 3600) / 3600;   
        long minute1 = between % 3600 / 60;   
        long second1 = between % 60;   
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分"   
        + second1 + "秒");   
    }  
    
	/**
	 * 数字转星期字符串 H.S
	 */
	public static String dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return getChar(dayForWeek);
	}

	public static String getChar(Integer day) {
		switch (day) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 7:
			return "星期日";
		}
		return "未知数";
	}
	public static String getWeekByOn(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		String monDay = df.format(cal.getTime());
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		
		String sunDay = df.format(cal.getTime());
		String onDay = monDay +"\t"+sunDay;
		return onDay;
		
	}
	
	/**
	 * 将秒转化成时:分:秒的格式
	 */
	public static String changStyle(long num){
		long h = 60*60;//1小时多少秒
		long h_mo = num/h;//小时位 0
		String hw = String.valueOf(h_mo);
		if(hw.length()==1){
			hw = "0"+ hw;
		}
		long h_yushu = num%h;//2400
		long m = 60;
		long m_mo = h_yushu/m;
		String mw = String.valueOf(m_mo);
		if(mw.length()==1){
			mw = "0"+ mw;
		}
		long m_yushu = h_yushu%m;
		String sw = String.valueOf(m_yushu);
		if(sw.length()==1){
			sw = "0"+ sw;
		}
		return hw+":"+mw+":"+sw;
	}
	
	public static boolean validEpgDate(String str){
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		 format.setLenient(false);
         try {
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess=false;
			return convertSuccess;
		}
			return convertSuccess;
	}
} 