package com.stardust.app.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 封装FastJson库
 *
 */
public class FastJsonUtil {

	private static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	 
//	private static final Feature[] deserializerFeature = { 
//		Feature.AutoCloseSource,   
//		Feature.InternFieldNames,  
//		Feature.UseBigDecimal,    
//		Feature.AllowUnQuotedFieldNames,    
//		Feature.AllowSingleQuotes,    
//		Feature.AllowArbitraryCommas,           
//		Feature.SortFeidFastMatch,          
//		Feature.IgnoreNotMatch //FastJson默认值                                                  
//	};
	
	private static final SerializerFeature[] serializerFeature = { 
		SerializerFeature.QuoteFieldNames,
		SerializerFeature.SkipTransientField,
		SerializerFeature.WriteEnumUsingToString,
		SerializerFeature.SortField, //FastJson默认值
		
		SerializerFeature.WriteMapNullValue, // 输出空置字段        
		SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null         
		SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null         
		SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null         
		SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null                                                   }; 
	};
	
	//--------------------------deserializer-------------------------------------
	public static final Object parse(String text)
	{
		return  JSON.parse(text);
	}
	
	public static final Object parse(String text, Feature... features)
	{
		return  JSON.parse(text, features);
	}

	public static final <T> T parseObject(String text, TypeReference<T> type)
	{ 
		return parseObject(text, type,new Feature[0]);
	}
	
	public static final <T> T parseObject(String text, TypeReference<T> type, Feature... features)
	{ 
		return JSON.parseObject(text, type,features);
	}

	public static final <T> T parseObject(String text, Class<T> clazz)
	{ 
		return parseObject(text, clazz,new Feature[0]);
	}
	
	public static final <T> T parseObject(String text, Class<T> clazz, Feature... features)
	{
		return JSON.parseObject(text, clazz,features);
	}
	//--------------------------serializer-------------------------------------
	
	public static final String toJSONString(Object object)
	{
		return JSON.toJSONStringWithDateFormat(object,DEFFAULT_DATE_FORMAT,serializerFeature);
	}
	public static final String toJSONString(Object object, SerializerFeature... features)
	{
		return JSON.toJSONStringWithDateFormat(object,DEFFAULT_DATE_FORMAT,features);
	}
}
