package com.sourceSoft.digitalProjct.util;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 发布消息定时器
 * Created by lvbj on 2018/7/6.
 */
public class TimerUtil<T> {
    private T obj;
    private String methodname;
    private Object[] value;
    private Class<T>[] parameterTypes;
    
    /**
     * @param obj            //执行定时消息的本类
     * @param methodname     //定时发送执行的方法名称
     * @param parameterTypes //方法中传递参数的包装类型
     * @param value          //方法中传递参数包装类型的引用类型
     */
    public TimerUtil(T obj, String methodname, Class<T>[] parameterTypes, Object... value) {
        this.obj = obj;
        this.methodname = methodname;
        this.value = value;
        this.parameterTypes = parameterTypes;
    }


    /**
     * 指定时间进行发送
     * @param calendar 指定日期时间
     */
	public void timeExecut(Calendar calendar) {
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new RemindTask<T>(obj, methodname, parameterTypes, value), time);
        System.out.println("方法执行时间："+time);
    }


	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public void setValue(Object[] value) {
		this.value = value;
	}

	public void setParameterTypes(Class<T>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
}


class RemindTask<T> extends TimerTask {
    private T obj;
    private String methodname;
    private Object[] value;
    Class<T>[] parameterTypes;

    public RemindTask(T obj, String methodname, Class<T>[] parameterTypes, Object... value) {
        this.obj = obj;
        this.methodname = methodname;
        this.value = value;
        this.parameterTypes = parameterTypes;
    }

    public void run() {
        Method m = null;
        try {
            m = obj.getClass().getDeclaredMethod(methodname, parameterTypes);
            //调用方法（value根据自己实际情况做修改）
            m.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

