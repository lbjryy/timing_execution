package com.sourceSoft.digitalProjct.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class DateUtil
{
  public static final long DAY_MILLI = 86400000L;
  public static final long MINUTE_MILLI = 60000L;
  public static String DATE_FORMAT_DATEONLYNOSP = "yyyyMMdd";

  public static String DATE_FORMAT_YEARMONTH = "yyyy-MM";

  public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";

  public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

  public static String DATE_FORMAT_DATETIME2 = "yyyy-MM-dd HH:mm:ss.SSS";

  public static String DATE_FORMAT_DATETIME3 = "yyyyMMddHHmmssSSS";

  public static String DATE_FORMAT_DATETIME4 = "yyyyMMddHHmmss";

  public static String DATE_FORMAT_TIME = "HH:mm:ss";

  public static String DATE_FORMAT_YYMMDD = "yy/MM/dd";
  public static final String DATE_FORMAT_SESSION = "yyyyMMddHHmm";
  public static SimpleDateFormat sdfDateOnlyNoSp = new SimpleDateFormat(DATE_FORMAT_DATEONLYNOSP);

  public static SimpleDateFormat sdfYearMonth = new SimpleDateFormat(DATE_FORMAT_YEARMONTH);

  public static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DATE_FORMAT_DATEONLY);

  public static SimpleDateFormat sdfDateTime = new SimpleDateFormat(DATE_FORMAT_DATETIME);

  public static SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_FORMAT_TIME);

  public static SimpleDateFormat sdfDateTime2 = new SimpleDateFormat(DATE_FORMAT_DATETIME2);

  public static SimpleDateFormat sdfDateTime3 = new SimpleDateFormat(DATE_FORMAT_DATETIME3);

  public static SimpleDateFormat sdfDateTime4 = new SimpleDateFormat(DATE_FORMAT_DATETIME4);

  private static SimpleDateFormat sdfDateSession = new SimpleDateFormat("yyyyMMddHHmm");

  public static SimpleDateFormat sdfyyyymmdd = new SimpleDateFormat(DATE_FORMAT_YYMMDD);

  private GregorianCalendar gcal = null;

  private Timestamp time = null;

  private static TimeZone chinaTimeZone = TimeZone.getTimeZone("GMT+08:00");
  private String holidayString;
  public static int[] DAY_OF_MONTH_LEAP_YEAR = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  public static int[] DAY_OF_MONTH_NON_LEAP_YEAR = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
  private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public DateUtil()
  {
    this.gcal = new GregorianCalendar();
    this.time = new Timestamp(System.currentTimeMillis());
  }

  public boolean isHoliday()
  {
    return false;
  }

  public String getHolidayString()
  {
    return this.holidayString;
  }

  public void setHolidayString(String holidayString)
  {
    this.holidayString = holidayString;
  }

  public static java.util.Date toDate(String sDate)
  {
    return toDate(sDate, sdfDateOnly);
  }

  public static java.util.Date toDate(String sDate, String sFmt)
  {
    if (sFmt.equals(DATE_FORMAT_DATETIME))
    {
      return toDate(sDate, sdfDateTime);
    }

    if (sFmt.equals(DATE_FORMAT_DATEONLY)) {
      return toDate(sDate, sdfDateOnly);
    }
    return null;
  }

  public static java.util.Date toDate(String sDate, SimpleDateFormat formatter)
  {
    java.util.Date dt = null;
    try {
      dt = formatter.parse(sDate);
    } catch (Exception e) {
      e.printStackTrace();
      dt = null;
    }
    return dt;
  }

  public static String DateToString(java.util.Date date, String format)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    if (date != null) {
      return sdf.format(date);
    }
    return "";
  }

  public static String toString(java.util.Date dt)
  {
    if (dt == null) {
      return "";
    }
    return toString(dt, sdfDateOnly);
  }

  public static String toString(java.util.Date dt, String sFmt)
  {
    if (dt == null) {
      return "";
    }
    if (sFmt.equals(DATE_FORMAT_DATETIME))
    {
      return toString(dt, sdfDateTime);
    }
    return toString(dt, sdfDateOnly);
  }

  public static String toString(java.util.Date dt, SimpleDateFormat formatter)
  {
    String sRet = null;
    try
    {
      sRet = formatter.format(dt).toString();
    } catch (Exception e) {
      e.printStackTrace();
      sRet = null;
    }

    return sRet;
  }

  public static boolean isSameDay(Timestamp date1, Timestamp date2)
  {
    String s2 = null;
    String s1 = date1.toString().substring(0, DATE_FORMAT_DATEONLY.length());
    s2 = date2.toString().substring(0, DATE_FORMAT_DATEONLY.length());

    return s1.equalsIgnoreCase(s2);
  }

  public static List getAllWeekDays(int year, int weekDay)
  {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT_DATEONLY);
    c.set(year, 0, 1);

    List strWeekDayList = new ArrayList();

    int i = -1;
    while (true) {
      i++;
      if (c.get(1) != year) {
        break;
      }
      if (c.get(7) == weekDay) {
        strWeekDayList.add(f.format(c.getTime()).toString());
      }
      c.set(5, c.get(5) + 1);
    }

    return strWeekDayList;
  }

  public static Timestamp getFirstDayOfWeek(Timestamp timestamp)
  {
    int no = getWeekdayOfTimestamp(timestamp);
    Timestamp out = addDays(timestamp, 1 - no);
    return out;
  }

  public static Timestamp getLastDayOfWeek(Timestamp timestamp)
  {
    int no = getWeekdayOfTimestamp(timestamp);
    Timestamp out = addDays(timestamp, 7 - no);
    return out;
  }

  public static Timestamp getFirstSundayOfMonth(Timestamp timestamp)
  {
    Timestamp out = null;
    if (timestamp == null) {
      return null;
    }
    out = getFirstDayOfMonth(timestamp);
    out = getFirstDayOfWeek(out);
    return out;
  }

  public static Timestamp getLastSaturdayOfMonth(Timestamp timestamp)
  {
    Timestamp out = null;
    if (timestamp == null) {
      return null;
    }

    out = getLastDayOfMonth(timestamp);

    out = getLastDayOfWeek(out);

    return out;
  }

  public static Timestamp getFirstDayOfMonth(Timestamp timestamp)
  {
    Timestamp out = null;
    if (timestamp == null) {
      return null;
    }
    int day = getDayOfTimestamp(timestamp);
    out = addDays(timestamp, 1 - day);

    return out;
  }

  public static long daysBetween(Timestamp t1, Timestamp t2)
  {
    return (t2.getTime() - t1.getTime()) / 86400000L;
  }

  private static String formatYMD(int year, int month, int day)
  {
    String temp = String.valueOf(year) + "/";
    if (month < 10)
      temp = temp + "0" + String.valueOf(month) + "/";
    else {
      temp = temp + String.valueOf(month) + "/";
    }
    if (day < 10)
      temp = temp + "0" + String.valueOf(day);
    else {
      temp = temp + String.valueOf(day);
    }
    return temp;
  }

  public static String formatYMD(String dmyString)
  {
    String day = "";
    String month = "";
    String year = "";
    String[] dmy = dmyString.split("/");
    if ((dmy != null) && (dmy.length > 0)) {
      day = dmy[0];
      month = dmy[1];
      year = dmy[2];
    }
    StringBuffer temp = new StringBuffer();
    if (Integer.parseInt(year) < 100)
      temp.append("20" + year + "-");
    else {
      temp.append("21" + year + "-");
    }
    if (Integer.parseInt(month) < 10)
      temp.append("0" + month + "-");
    else {
      temp.append(month + "-");
    }
    if (Integer.parseInt(day) < 10)
      temp.append("0" + day);
    else {
      temp.append(day);
    }
    return temp.toString();
  }

  public static Timestamp getLastDayOfMonth(Timestamp timestamp) {
    Timestamp out = null;
    if (timestamp == null) {
      return null;
    }

    GregorianCalendar obj = convertTimestampToCalendar(timestamp);

    int day = 0;
    int year = obj.get(1);
    int month = obj.get(2) + 1;
    if (obj.isLeapYear(obj.get(1)))
      day = DAY_OF_MONTH_LEAP_YEAR[(month - 1)];
    else {
      day = DAY_OF_MONTH_NON_LEAP_YEAR[(month - 1)];
    }

    out = toSqlTimestamp(formatYMD(year, month, day));

    return out;
  }

  public static int getYearOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(1);
  }

  public static int getMonthOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(2) + 1;
  }

  public static int getDayOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(5);
  }

  public static int getWeekdayOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(7);
  }

  public static Timestamp getZeroTime(Timestamp timestamp)
  {
    String tempStr = timestamp.toString().substring(0, 10);
    return toSqlTimestamp(tempStr);
  }

  public static String getHourAndMinuteString(Timestamp timestamp)
  {
    String out = null;
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    int hour = obj.get(11);
    int minute = obj.get(12);
    if (minute < 10)
      out = String.valueOf(hour) + ":0" + String.valueOf(minute);
    else {
      out = String.valueOf(hour) + ":" + String.valueOf(minute);
    }
    return out;
  }

  public static String getCurrentHourAndMinuteString()
  {
    String out = null;
    Timestamp time = new Timestamp(System.currentTimeMillis());
    GregorianCalendar obj = convertTimestampToCalendar(time);
    int hour = obj.get(11);
    int minute = obj.get(12);
    if (minute < 10)
      out = String.valueOf(hour) + "0" + String.valueOf(minute);
    else {
      out = String.valueOf(hour) + String.valueOf(minute);
    }
    return out;
  }

  public static String getCurrentHourString()
  {
    String out = null;
    Timestamp time = new Timestamp(System.currentTimeMillis());
    GregorianCalendar obj = convertTimestampToCalendar(time);
    int hour = obj.get(11);
    if (hour < 10)
      out = "0" + String.valueOf(hour);
    else {
      out = String.valueOf(hour);
    }
    return out;
  }

  public static String getCurrentMinuteString()
  {
    String out = null;
    Timestamp time = new Timestamp(System.currentTimeMillis());
    GregorianCalendar obj = convertTimestampToCalendar(time);
    int minute = obj.get(12);
    if (minute < 10)
      out = "0" + String.valueOf(minute);
    else {
      out = String.valueOf(minute);
    }
    return out;
  }

  public static String getCurrentSecendString()
  {
    String out = null;
    Timestamp time = new Timestamp(System.currentTimeMillis());
    GregorianCalendar obj = convertTimestampToCalendar(time);
    int second = obj.get(13);
    if (second < 10)
      out = "0" + String.valueOf(second);
    else {
      out = String.valueOf(second);
    }
    return out;
  }

  public static int getHourOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(11);
  }

  public static int getMinuteOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(12);
  }

  public static int getSecondOfTimestamp(Timestamp timestamp)
  {
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);
    return obj.get(13);
  }

  /** @deprecated */
  public static GregorianCalendar convertTimestampToCalendar(Timestamp timestamp)
  {
    return convertToCalendar(timestamp);
  }

  public static GregorianCalendar convertToCalendar(Timestamp timestamp)
  {
    GregorianCalendar obj = new GregorianCalendar();

    obj.setTime(convertTimestampToDate(timestamp));

    return obj;
  }

  public static java.util.Date convertTimestampToDate(Timestamp timestamp)
  {
    java.util.Date date = null;

    date = new java.util.Date(timestamp.getTime());
    return date;
  }

  public static long getSysDateLong()
  {
    return System.currentTimeMillis();
  }

  public static Timestamp getSysDateTimestamp()
  {
    return new Timestamp(System.currentTimeMillis());
  }

  public static Timestamp getSysDateTimestamps()
  {
    String time = toString(new java.util.Date(System.currentTimeMillis()), sdfDateTime);
    new Timestamp(System.currentTimeMillis()); return Timestamp.valueOf(time);
  }

  public static String getSysYearMonthString()
  {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfYearMonth);
  }

  public static String getSysDateString() {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateOnly);
  }

  public static String getNoSpSysDateString() {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateOnlyNoSp);
  }

  public static String getSysDateTimeString()
  {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateTime);
  }

  public static String getSysDateTime2String() {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateTime2);
  }

  public static String getSysDateTime3String() {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateTime3);
  }

  public static String getSysDateTime4String() {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateTime4);
  }

  public static String getSysDateyyyyhhddmmhhss()
  {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfDateSession);
  }

  public static java.sql.Date toSqlDate(String sDate)
  {
    if ((sDate == null) || (sDate.equals(""))) {
      return null;
    }
    if (sDate.equals("1899-12-30")) {
      return null;
    }
    return java.sql.Date.valueOf(sDate.replace('/', '-'));
  }

  public static String toSqlDateString(java.sql.Date dt)
  {
    String temp = null;
    temp = dt.toString();
    return temp.replace('-', '/');
  }

  public static Timestamp toSqlTimestamp(String sDate)
  {
    if (sDate == null) {
      return null;
    }
    if (sDate.length() != DATE_FORMAT_DATEONLY.length()) {
      return null;
    }
    return toSqlTimestamp(sDate, DATE_FORMAT_DATEONLY);
  }

  public static Timestamp toSqlTimestamp(String sDate, String sFmt)
  {
    String temp = null;
    if ((sDate == null) || (sFmt == null)) {
      return null;
    }
    if (sDate.length() != sFmt.length()) {
      return null;
    }
    if (sFmt.equals(DATE_FORMAT_DATETIME)) {
      temp = sDate.replace('/', '-');
      temp = temp + ".000000000";
    } else if (sFmt.equals(DATE_FORMAT_DATEONLY)) {
      temp = sDate.replace('/', '-');
      temp = temp + " 00:00:00.000000000";
    }
    else
    {
      return null;
    }

    return Timestamp.valueOf(temp);
  }

  public static String toSqlTimestampString(Timestamp dt)
  {
    if (dt == null) {
      return null;
    }
    return toSqlTimestampString(dt, DATE_FORMAT_DATEONLY);
  }

  public static String toSqlTimestampString2(Timestamp dt)
  {
    if (dt == null) {
      return null;
    }
    String temp = toSqlTimestampString(dt, DATE_FORMAT_DATETIME);
    return temp.substring(0, 16);
  }

  public static String toString(Timestamp dt) {
    return dt == null ? "" : toSqlTimestampString2(dt);
  }

  public static String convertTimestampToChinaCalendar(Timestamp timestamp)
  {
    StringBuffer sb = new StringBuffer();
    if (timestamp == null) {
      sb.append("&nbsp");
    } else {
      sb = new StringBuffer();
      sb.append(getYearOfTimestamp(timestamp));
      sb.append("年");
      sb.append(getMonthOfTimestamp(timestamp));
      sb.append("月");
      sb.append(getDayOfTimestamp(timestamp));
      sb.append("日");
      sb.append("　");
      sb.append(getHourOfTimestamp(timestamp));
      sb.append(":");
      if (getMinuteOfTimestamp(timestamp) < 10) {
        sb.append(0);
        sb.append(getMinuteOfTimestamp(timestamp));
      } else {
        sb.append(getMinuteOfTimestamp(timestamp));
      }
      sb.append(":");
      if (getSecondOfTimestamp(timestamp) < 10) {
        sb.append(0);
        sb.append(getSecondOfTimestamp(timestamp));
      } else {
        sb.append(getSecondOfTimestamp(timestamp));
      }
    }
    return sb.toString();
  }

  public static String toSqlTimestampString(Timestamp dt, String sFmt)
  {
    String temp = null;
    String out = null;
    if ((dt == null) || (sFmt == null)) {
      return null;
    }
    temp = dt.toString();
    if ((sFmt.equals(DATE_FORMAT_DATETIME)) || (sFmt.equals(DATE_FORMAT_DATEONLY)))
    {
      temp = temp.substring(0, sFmt.length());
      out = temp.replace('/', '-');
    }

    return out;
  }

  public static String toHourMinString(Timestamp dt)
  {
    String temp = null;
    temp = dt.toString();

    temp = temp.substring(11, 16);
    return temp;
  }

  private static boolean isLastDayOfMonth(GregorianCalendar obj)
  {
    int year = obj.get(1);
    int month = obj.get(2) + 1;
    int day = obj.get(5);
    if (obj.isLeapYear(year)) {
      if (day == DAY_OF_MONTH_LEAP_YEAR[(month - 1)]) {
        return true;
      }
    }
    else if (day == DAY_OF_MONTH_NON_LEAP_YEAR[(month - 1)]) {
      return true;
    }

    return false;
  }

  public static Timestamp addMonths(Timestamp timestamp, int mon)
  {
    Timestamp out = null;
    GregorianCalendar obj = convertTimestampToCalendar(timestamp);

    int year = obj.get(1);
    int month = obj.get(2) + 1;
    int day = obj.get(5);
    month += mon;
    if (month < 1) {
      month += 12;
      year--;
    } else if (month > 12) {
      month -= 12;
      year++;
    }
    if (isLastDayOfMonth(obj)) {
      if (obj.isLeapYear(year))
        day = DAY_OF_MONTH_LEAP_YEAR[(month - 1)];
      else {
        day = DAY_OF_MONTH_NON_LEAP_YEAR[(month - 1)];
      }
    }
    String temp = formatYMD(year, month, day);
    out = toSqlTimestamp(temp);
    return out;
  }

  public static int getMonthAllDays(int year, int month)
  {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month, 0);
    return cal.get(5);
  }

  public static Timestamp addDays(Timestamp timestamp, int days)
  {
    java.util.Date date = convertTimestampToDate(timestamp);
    long temp = date.getTime();

    return new Timestamp(temp + 86400000L * days);
  }

  public static java.util.Date addMinute(java.util.Date date, int minute)
  {
    long temp = date.getTime();

    return new java.util.Date(temp + 60000L * minute);
  }

  public static String addMinute(int minute)
  {
    return toString(new java.util.Date(System.currentTimeMillis() + minute * 60000L), sdfDateTime);
  }

  public static java.util.Date addDays(java.util.Date date, int days)
  {
    long temp = date.getTime();
    return new java.util.Date(temp + 86400000L * days);
  }

  public static int getAge(String birthday)
  {
    if ((birthday == null) || (birthday.equals(""))) {
      return 0;
    }
    String year = birthday.substring(0, 4);
    int birth = Integer.parseInt(year);

    String nowyear = getSysDateString().substring(0, 4);
    int sysYear = Integer.parseInt(nowyear);
    int result = sysYear - birth;
    return result;
  }

  public static boolean isBefore(String date)
  {
    long sysTime = System.currentTimeMillis();
    java.util.Date sysDate = new java.util.Date(sysTime);
    java.util.Date lastTime = new java.util.Date(date);
    if (sysDate.before(lastTime)) {
      return true;
    }
    return false;
  }

  public static String getSystemWeekdayString() {
    int day = getWeekdayOfTimestamp(new Timestamp(System.currentTimeMillis()));
    if (day == 1) {
      return "星期日";
    }
    if (day == 2) {
      return "星期一";
    }
    if (day == 3) {
      return "星期二";
    }
    if (day == 4) {
      return "星期三";
    }
    if (day == 5) {
      return "星期四";
    }
    if (day == 6) {
      return "星期五";
    }
    if (day == 7) {
      return "星期六";
    }
    return "";
  }

  public static Long getDaysBetweanStr(String date1, String date2)
  {
    long DAY = 86400000L;
    try {
      DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      java.util.Date d1 = sdf.parse(date1);
      java.util.Date d2 = sdf.parse(date2);

      long returnNum = (d2.getTime() - d1.getTime()) / 86400000L;
      return Long.valueOf(returnNum);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Long.valueOf(0L);
  }

  public static String getSysTimeString()
  {
    return toString(new java.util.Date(System.currentTimeMillis()), sdfTime);
  }

  public static String getTimeString(long timeMill) {
    return toString(new java.util.Date(timeMill), sdfTime);
  }

  public static java.util.Date str2Date(String str, String format)
  {
    if ((null == str) || ("".equals(str))) {
      return null;
    }

    if ((null == format) || ("".equals(format))) {
      format = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    java.util.Date date = null;
    try {
      return sdf.parse(str);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String date2Str(java.util.Date date, String format)
  {
    if (null == date) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static String timestamp2Str(Timestamp time)
  {
    java.util.Date date = null;
    if (null != time) {
      date = new java.util.Date(time.getTime());
    }
    return date2Str(date, "yyyy-MM-dd HH:mm:ss");
  }

  public static Timestamp str2Timestamp(String str)
  {
    java.util.Date date = str2Date(str, "yyyy-MM-dd HH:mm:ss");
    return new Timestamp(date.getTime());
  }

  public static Timestamp currentTimestamp()
  {
    java.util.Date date = new java.util.Date();
    return new Timestamp(date.getTime());
  }

  public static void main(String[] args)
  {
    System.out.println(getSysDateTimeString());
  }

  public static String getCurrentTime(String format)
  {
    SimpleDateFormat df = new SimpleDateFormat(format);
    return df.format(new java.util.Date());
  }

  public static List<String[]> getDateOrWeek(String dateFormat, List<java.util.Date> days)
  {
    List returnValue = new ArrayList();

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    for (java.util.Date date : days)
    {
      returnValue.add(sdf.format(date).split(";"));
    }
    return returnValue;
  }

  public static List<java.util.Date> dateToWeek(java.util.Date mdate)
  {
    int b = mdate.getDay();

    List listDate = new ArrayList();
    Long fTime = Long.valueOf(mdate.getTime() - b * 24 * 3600000);
    for (int a = 1; a <= 7; a++) {
      java.util.Date fdate = new java.util.Date();
      fdate.setTime(fTime.longValue() + a * 24 * 3600000);
      listDate.add(a - 1, fdate);
    }
    return listDate;
  }

  public static java.util.Date getAppointDateFront(java.util.Date mdate, int i)
  {
    java.util.Date fdate = new java.util.Date();
    Long fTime = Long.valueOf(mdate.getTime() - i * 24 * 3600000);
    fdate.setTime(fTime.longValue());
    return fdate;
  }

  public static java.util.Date getAppointDateBehind(java.util.Date mdate, int i)
  {
    java.util.Date fdate = new java.util.Date();
    Long fTime = Long.valueOf(mdate.getTime() + i * 24 * 3600000);
    fdate.setTime(fTime.longValue());
    return fdate;
  }

  public static String getWeekMondday(String strYMD)
  {
    try
    {
      java.util.Date date = sdfyyyymmdd.parse(strYMD);
      return getWeekDay(0, date);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getWeekDay(int dayNum, java.util.Date date)
  {
    int mondayPlus = getMondayPlus(date);
    GregorianCalendar currentDate = new GregorianCalendar(chinaTimeZone);
    currentDate.setTime(date);
    currentDate.add(5, mondayPlus + dayNum);
    java.util.Date day = currentDate.getTime();
    String monday = sdfyyyymmdd.format(day);
    return monday;
  }

  public static int getMondayPlus(java.util.Date date)
  {
    Calendar cd = Calendar.getInstance(chinaTimeZone);
    cd.setTime(date);

    int dayOfWeek = cd.get(7) - 1;
    if (dayOfWeek == 1) {
      return 0;
    }
    return 1 - dayOfWeek;
  }

  public static String getWeekSunday(String strYMD)
  {
    try
    {
      java.util.Date date = sdfyyyymmdd.parse(strYMD);
      return getWeekDay(6, date);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static StringBuffer getDateSequence()
  {
    StringBuffer strBitrateUrl = new StringBuffer();
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("yyyy"));
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("MM"));
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("dd"));
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("yyyy_MM_dd_HH_mm_ss_SSS"));
    strBitrateUrl.append("/");
    return strBitrateUrl;
  }

  public static StringBuffer getDateSequence2()
  {
    StringBuffer strBitrateUrl = new StringBuffer();
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("yyyy"));
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("MM"));
    strBitrateUrl.append("/");
    strBitrateUrl.append(getCurrentTime("dd"));
    strBitrateUrl.append("/");
    return strBitrateUrl;
  }

  public static String getTimeDiff(java.util.Date date)
  {
    long ONE_SECOND = 1000L;
    long ONE_MINUTE = 60000L;
    long ONE_HOUR = 3600000L;
    long ONE_DAY = 86400000L;
    long ONE_MONTH = 2592000000L;
    long ONE_YEAR = 31104000000L;

    long currentTime = System.currentTimeMillis();
    String timeDiff = "刚刚";
    long between = currentTime - date.getTime();

    if (between < 1000L)
      return timeDiff;
    if (between < 60000L) {
      long timeIntoFormat = between / 1000L;
      timeDiff = timeIntoFormat + "秒前";
    } else if (between < 3600000L) {
      long timeIntoFormat = between / 60000L;
      timeDiff = timeIntoFormat + "分钟前";
    } else if (between < 86400000L) {
      long timeIntoFormat = between / 3600000L;
      timeDiff = timeIntoFormat + "小时前";
    } else if (between < 2592000000L) {
      long timeIntoFormat = between / 86400000L;
      timeDiff = timeIntoFormat + "天前";
    } else if (between < 31104000000L) {
      long timeIntoFormat = between / 2592000000L;
      timeDiff = timeIntoFormat + "个月前";
    } else {
      long timeIntoFormat = between / 31104000000L;
      timeDiff = timeIntoFormat + "年前";
    }
    return timeDiff;
  }

  public static String getDisplayTimeForSeconds(int seconds) {
    int unit = 60;
    if (seconds < unit) {
      return seconds + "秒";
    }
    int min = seconds / unit;
    int sec = seconds % unit;
    if (min < unit) {
      String time = min + "分";
      if (sec > 0) {
        time = time + sec + "秒";
      }
      return time;
    }
    int hour = min / unit;
    min %= unit;
    String time = hour + "时";
    if (min > 0) {
      time = time + min + "分";
    }
    if (sec > 0) {
      time = time + sec + "秒";
    }
    return time;
  }

  public static String generateTime(long time)
  {
    int totalSeconds = (int)(time / 1000L);
    int seconds = totalSeconds % 60;
    int minutes = totalSeconds / 60 % 60;
    int hours = totalSeconds / 3600;

    return String.format("%02d:%02d:%02d", new Object[] { Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds) });
  }
}