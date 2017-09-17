package com.lquan.common.weixin.util;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;  
import com.thoughtworks.xstream.core.util.QuickWriter;  
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;  
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;  
import com.thoughtworks.xstream.io.xml.XppDriver;  

/**
 * 扩展  XStream支持CDATA标签   
 * @author lquan
 * 2017年9月17日15:27:06
 */
 public class XStreamUtil {  
	 
	 public static String PREFIX_CDATA = "<![CDATA[";  
	 public static String SUFFIX_CDATA = "]]>";  
   
     /** 
      * 全部转化 
      */  
     public static XStream initXStream() {  
         return new XStream(new XppDriver() {  
             @Override  
             public HierarchicalStreamWriter createWriter(Writer out) {  
                 return new PrettyPrintWriter(out) {  
                     protected void writeText(QuickWriter writer, String text) {  
                         // if (text.startsWith(PREFIX_CDATA) &&  
                         // text.endsWith(SUFFIX_CDATA)) {  
                         writer.write(PREFIX_CDATA + text + SUFFIX_CDATA);  
                         // } else {  
                         // super.writeText(writer, text);  
                         // }  
                     }  
                 };  
             }  
         });  
     }  
   
     /** 
      * 初始化XStream可支持某一字段可以加入CDATA标签,如果需要某一字段使用原文,就需要在String类型的text的头加上 
      * "<![CDATA["和结尾处加上"]]>"标签， 以供XStream输出时进行识别 
      * @param isAddCDATA 是否支持CDATA标签 
      * @param isAddCDATA true时根绝拼接字段显示CDATA标签 
      * @return
      */  
     public static XStream initXStream(boolean isAddCDATA) {  
         XStream xstream = null;  
         if (isAddCDATA) {  
             xstream = new XStream(new XppDriver() {  
                 @Override  
                 public HierarchicalStreamWriter createWriter(Writer out) {  
                     return new PrettyPrintWriter(out) {  
                         protected void writeText(QuickWriter writer, String text) {  
                             if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {  
                                 writer.write(text);  
                             } else {  
                                 super.writeText(writer, text);  
                             }  
                         }  
                     };  
                 }  
             });  
         } else {  
             xstream = new XStream();  
         }  
         return xstream;  
     } 
     
     
     
     /** 
      * 文本消息对象转换成xml,类的字段在转换成xml时会自动增加 "<![CDATA["和结尾处加上"]]>"标签
      * @param t 实体类
      * @return
      */
     public static <T> String objToXmlByAllFiledCData(T t) {  
    	 XStream xstream = initXStream();;  
         xstream.alias("xml", t.getClass());  
         return xstream.toXML(t);  
     }
     
     
     /** 
      * 文本消息对象转换成xml,类的字段在转换成xml时
      * 根据需要进行转换：
      * 		1、当isAddCDATA为true是为自定义类型及在对应字段的开头和结尾拼接 "<![CDATA["和结尾处加上"]]>"标签
      * 		2、当isAddCDATA为false时是原样输出，不过，可能会解析错误
      * 
      * @param t 实体类
      * @return
      */
     public static <T> String objToXmlDefindFiledCData(boolean isAddCDATA,T t) {  
    	 XStream xstream = initXStream(isAddCDATA);  
         xstream.alias("xml", t.getClass());  
         return xstream.toXML(t);  
     }
 }

