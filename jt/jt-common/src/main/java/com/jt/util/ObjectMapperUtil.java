package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 该工具类中主要实现对象于JSON之间的互转
 * static 方法用户直接调用
 * @author lenovo
 *
 */
public class ObjectMapperUtil {
     private static final ObjectMapper objectMapper =new ObjectMapper();
     //1.将对象转换为JSON
     public static String toJSON(Object target) {
    	String result =null;
    	 try {
    		 result=objectMapper.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    	 return result;
     }
     //2.转化对象
     public static <T> T toObject(String json,Class<T> targetClass) {
    	T target=null;
    	 try {
    		 target= objectMapper.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return target;
     }
     
     
     
     
}
