package com.sourceSoft.digitalProjct.controller.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sourceSoft.digitalProjct.util.TimerUtil;

@Controller
public class TimeTestController {
	
	@RequestMapping(value = "testAuto", method = RequestMethod.GET)
	@ResponseBody
	public String testAuto(String timeStr){
		try{
			if(StringUtils.isNotEmpty(timeStr)){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Class[] paramClass = {String.class,Integer.class};
				Object[] valueObj = {"张三",15};
				TimerUtil<TimeTestController> timeUtil = new TimerUtil<TimeTestController>(new TimeTestController(), "testCallBack", paramClass, valueObj);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(timeStr));
				timeUtil.timeExecut(calendar);
				return "suc";
			}else{
				return "empty";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "err";
		}
	}
	
	public static void main(String[] args) throws ParseException {
		String timeStr = "2019-01-16 14:01:59";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Class[] paramClass = {String.class,Integer.class};
		Object[] valueObj = {"张三",15};
		TimerUtil<TimeTestController> timeUtil = new TimerUtil<TimeTestController>(new TimeTestController(), "testCallBack", paramClass, valueObj);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(timeStr));
		timeUtil.timeExecut(calendar);
		
	}
	
	
	public void testCallBack(String name,Integer num){
		System.out.println("姓名："+name+",年龄："+num);
	}
	
	public void testEmptyCallBack(){
		System.out.println("运行了空方法");
	}
}
