package com.sourceSoft.digitalProjct.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ObjMapUtil {

	 public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
	        if (map == null)  
	            return null;    
	  
	        Object obj = beanClass.newInstance();  
	  
	        Field[] fields = obj.getClass().getDeclaredFields();   
	        for (Field field : fields) {    
	            int mod = field.getModifiers();    
	            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
	                continue;    
	            }    
	  
	            field.setAccessible(true);    
	            field.set(obj, map.get(field.getName()));   
	        }   
	  
	        return obj;    
	    }    
	  
	    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
	        if(obj == null){    
	            return null;    
	        }   
	  
	        Map<String, Object> map = new HashMap<String, Object>();    
	  
	        Field[] declaredFields = obj.getClass().getDeclaredFields();    
	        for (Field field : declaredFields) {    
	            field.setAccessible(true);  
	            map.put(field.getName(), field.get(obj));  
	        }    
	  
	        return map;  
	    }   
	    /**
	     * 将对象中不为null的数据放入到map中
	     * @param obj
	     * @return
	     * @throws Exception
	     */
	    public static Map<String, Object> objectToMapValid(Object obj) throws Exception {    
	        if(obj == null){    
	            return null;    
	        }   
	        Map<String, Object> map = new HashMap<String, Object>();    
	  
	        Field[] declaredFields = obj.getClass().getDeclaredFields();    
	        for (Field field : declaredFields) {    
	            field.setAccessible(true);  
	            if(field.get(obj)!=null){
	            	map.put(field.getName(), field.get(obj)); 
	            }
	        }    
	        return map;  
	    }   
}
