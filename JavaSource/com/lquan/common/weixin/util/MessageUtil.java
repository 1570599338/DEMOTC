package com.lquan.common.weixin.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
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
		 * 文本消息转化为xml
		 * 
		 * @param textMessage
		 * @return
		 */
		public static String textMessageToXml(TextMessage textMessage) {
			
			XStream xstream = XStreamUtil.initXStream(true);
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
		public static <T> String objectToXml(T obj) {
			XStream xstream = XStreamUtil.initXStream(true);
			xstream.alias("xml", obj.getClass());
			return xstream.toXML(obj);
		}
	    
	    /** 
	     * 解析微信发来的请求（XML） 
	     *  
	     * @param request 
	     * @return 
	     * @throws Exception 
	     */  
	    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
	        // 将解析结果存储在HashMap中  
	        Map<String, String> map = new HashMap<String, String>();  
	  
	        // 从request中取得输入流  
	        InputStream inputStream = request.getInputStream();  
	        System.out.println("**********"+inputStream.toString());
	        // 读取输入流  
	        SAXReader reader = new SAXReader();  
	        Document document = reader.read(inputStream);  
	        // 得到xml根元素  
	        Element root = document.getRootElement();  
	        // 得到根元素的所有子节点  
	          
	        @SuppressWarnings("unchecked")  
	        List<Element> elementList = root.elements();  
	  
	        // 遍历所有子节点  
	        for (Element e : elementList)  
	            map.put(e.getName(), e.getText());  
	  
	        // 释放资源  
	        inputStream.close();  
	        inputStream = null;  
	  
	        return map;  
	    }
}
