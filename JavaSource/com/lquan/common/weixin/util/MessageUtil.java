package com.lquan.common.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lquan.common.weixin.bean.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	// 各种消息类型,除了扫带二维码事件
		/**
		 * 文本消息
		 */
		public static final String MESSAGE_TEXT = "text";
		/**
		 * 图片消息
		 */
		public static final String MESSAtGE_IMAGE = "image";
		/**
		 * 图文消息
		 */
		public static final String MESSAGE_NEWS = "news";
		/**
		 * 语音消息
		 */
		public static final String MESSAGE_VOICE = "voice";
		/**
		 * 视频消息
		 */
		public static final String MESSAGE_VIDEO = "video";
		/**
		 * 小视频消息
		 */
		public static final String MESSAGE_SHORTVIDEO = "shortvideo";
		/**
		 * 地理位置消息
		 */
		public static final String MESSAGE_LOCATION = "location";
		/**
		 * 链接消息
		 */
		public static final String MESSAGE_LINK = "link";
		/**
		 * 事件推送消息
		 */
		public static final String MESSAGE_EVENT = "event";
		/**
		 * 事件推送消息中,事件类型，subscribe(订阅)
		 */
		public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";
		/**
		 * 事件推送消息中,事件类型，unsubscribe(取消订阅)
		 */
		public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";
		/**
		 * 事件推送消息中,上报地理位置事件
		 */
		public static final String MESSAGE_EVENT_LOCATION_UP = "LOCATION";
		/**
		 * 事件推送消息中,自定义菜单事件,点击菜单拉取消息时的事件推送
		 */
		public static final String MESSAGE_EVENT_CLICK = "CLICK";
		/**
		 * 事件推送消息中,自定义菜单事件,点击菜单跳转链接时的事件推送
		 */
		public static final String MESSAGE_EVENT_VIEW = "VIEW";

		 /**
	     * 将xml转化为Map集合
	     * 
	     * @param request
	     * @return
	     */
	    public static Map<String, String> xmlToMap(HttpServletRequest request) {
	        Map<String, String> map = new HashMap<String, String>();
	        SAXReader reader = new SAXReader();
	        InputStream ins = null;
	        try {
	            ins = request.getInputStream();
	           
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	        Document doc = null;
	        try {
	          //  doc = reader.read(ins);
	            
	            StringBuffer content = new StringBuffer();
               // BufferedReader br = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/aa.xml"),"UTF-8"));
                String line = null;
                while((line = br.readLine()) != null ){
                 content.append(line+"\n");
                }
                br.close();
                doc = DocumentHelper.parseText(content.toString());
	        } catch (Exception  e1) {
	            e1.printStackTrace();
	        }
	        // 获取根路径
	        Element root = doc.getRootElement();
	        @SuppressWarnings("unchecked")
	        List<Element> list = root.elements();
	        for (Element e : list) {
	            map.put(e.getName(), e.getText());
	            System.out.println("key:"+e.getName()+"--value--:"+e.getText());
	        }
	        try {
	            ins.close();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	        return map;
	    }
		
		/**
		 * 文本消息转化为xml
		 * 
		 * @param textMessage
		 * @return
		 */
		public static String textMessageToXml(TextMessage textMessage) {
			XStream xstream = new XStream();
			xstream.alias("xml", textMessage.getClass());
			return xstream.toXML(textMessage);

		}

		/**
		 * 文本消息转化为xml
		 * @param <T>
		 * 
		 * @param textMessage
		 * @return
		 */
		public static <T> String textMessageToXml(Class<T> T) {
			XStream xstream = new XStream();
			xstream.alias("xml", T.getClass());
			return xstream.toXML(T);

		}

		
		/** 

	     * 解析微信发来的请求（XML） 

	     *  

	     * @param request 

	     * @return 

	     * @throws Exception 

	     */  

	    public static  Map<String, String> parseXml(HttpServletRequest request) throws Exception {  

	        // 将解析结果存储在HashMap中  
	        Map<String, String> map = new HashMap<String, String>();  
	        // 从request中取得输入流  
	        InputStream inputStream = request.getInputStream();  

	       // 读取输入流  
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream); 
	        String requestXml = document.asXML();
	        String subXml = requestXml.split(">")[0]+">";
	        requestXml = requestXml.substring(subXml.length());

	        // 得到xml根元素  
	        Element root = document.getRootElement();  
	        // 得到根元素的全部子节点  
	        List<Element> elementList = root.elements();  

	        // 遍历全部子节点  
	        for (Element e : elementList)  {
	            map.put(e.getName(), e.getText()); 
	           }
	        map.put("requestXml", requestXml);

	        // 释放资源  
	        inputStream.close();  
	        inputStream = null;  
	        return map;  

	    }
}
