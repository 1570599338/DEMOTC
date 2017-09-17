package com.lquan.web.controller.emailManager;

import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import snt.common.web.util.WebUtils;

import com.lquan.common.mail.EmailSender;
import com.lquan.common.weixin.bean.AccessToken;
import com.lquan.common.weixin.bean.TextMessage;
import com.lquan.common.weixin.redpack.service.SendRedPackService;
import com.lquan.common.weixin.util.MessageUtil;
import com.lquan.common.weixin.util.ValidationUtil;
import com.lquan.common.weixin.util.WXFunctionUtil;
import com.lquan.common.weixin.util.XStreamUtil;

@Controller
@RequestMapping(value="senderMail")
public class TesEmail {
	private static Logger log = LoggerFactory.getLogger(TesEmail.class);
	
	@Resource(name = "emailSender")
	public EmailSender emailSender;
	
	@Resource(name = "sendRedPackService")
	public SendRedPackService sendRedPackService;
	
	@RequestMapping(value="/test")
	public void toTestMail(HttpServletRequest request){
		String to ="quan.liu@maxinsight.cn";
		String cc ="";
		String bcc ="1570599338@qq.com;";
		emailSender.send(to,cc,bcc, "测试邮件请忽略", "Dear All,\r\n\r\n 这是一封测试邮件，请大家直接忽略 .\r\n\r\nThank you!\r\n\r\n 美好的一天从现在开始！");
		
	}

	
	@RequestMapping(value="/hb")
	public void toTestMailx(HttpServletRequest request){
		sendRedPackService.sendHongBao(null, 0, 0, null, null, null, null, null);
		
	}
	
	@RequestMapping(value="/wx")
	public void toTestMailWX(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getMethod();
		if("get".equalsIgnoreCase(method)){
		
			// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。   
	        String signature =request.getParameter("signature");   
	        System.out.println("***signature***"+signature);
	        // 时间戳   
	        String timestamp =request.getParameter("timestamp");
	        System.out.println("***timestamp***"+timestamp);
	        // 随机数   
	        String nonce =request.getParameter("nonce");
	        System.out.println("**nonce****"+nonce);
	        // 随机字符串   
	        String echostr =request.getParameter("echostr");
	        System.out.println("***echostr***"+echostr);
	        
			
	    	
	    	//	1474112581 -- 2072156169 -- d2b3014d1e17ec22d81b370613896872201e0c1a  --  8906074054333431013
			/*timestamp = "1474112581";
			nonce = "2072156169";
			signature = "d2b3014d1e17ec22d81b370613896872201e0c1a";
			echostr = "8906074054333431013";*/
	    		
			System.out.println(timestamp+" -- " + nonce + " -- " + signature + "  --  " + echostr);
			boolean b = ValidationUtil.checkSignature(signature, timestamp, nonce);
			if(b){
				PrintWriter out = response.getWriter();
				out.print(echostr);
			}
		}else {
			 try {
				 System.out.println("****消息start*****:"+method);
				 /* String wxMsgXml = IOUtils.toString(request.getInputStream(),"utf-8");
				 System.out.println("*********wxMsgXml:"+wxMsgXml);*/
				 Map<String, String> map = MessageUtil.parseXml(request);
				 log.info(map.toString());
			        // 发送方帐号（一个OpenID）
			        String fromUserName = map.get("FromUserName");
			        System.out.println("*********fromUserName:"+fromUserName);
			        // 开发者微信号
			        String toUserName = map.get("ToUserName");
			        System.out.println("*********toUserName:"+toUserName);
			        // 消息类型
			        String msgType = map.get("MsgType");
			        System.out.println("*********msgType:"+msgType);
			        // 默认回复一个"success"
			        String responseMessage = "success";
			        // 对消息进行处理
			        if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {// 文本消息
			            TextMessage textMessage = new TextMessage();
			            textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
			            textMessage.setToUserName(fromUserName);
			            textMessage.setFromUserName(toUserName);
			            textMessage.setCreateTime(System.currentTimeMillis());
			            textMessage.setContent("我已经受到你发来的消息了");
			            //responseMessage = MessageUtil.textMessageToXml(textMessage);
			            responseMessage = XStreamUtil.objToXmlDefindFiledCData(true, textMessage);
			        }
			        System.out.println("responseMessage:"+responseMessage);
			        log.info(responseMessage);
			        PrintWriter out = response.getWriter();
					out.print(responseMessage);
				 } catch (Exception e) {
						e.printStackTrace();
					}
		}
        
	}
	
	/***
	 * 测试获取acess_token的数据
	 * 
	 * @param request
	 * @throws ConnectException
	 */
	@RequestMapping(value="/token")
	public void toTestAcess_token(HttpServletRequest request) throws ConnectException{
		 String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		
	
			AccessToken accessToken = null;
			// 开发者ID
			String appid = WebUtils.getModuleProperty("weixin.AppID");
			// 开发者密码
			String appsecret = WebUtils.getModuleProperty("weixin.appsecrect");

			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject = null;
			try {
				jsonObject = WXFunctionUtil.httpRequest(requestUrl, "GET", null);
			}  catch (ConnectException ce) {
	            log.error("连接超时：{}", ce);
	        } catch (Exception e1) {
	            log.error("https请求异常：{}", e1);
	        }
			// 如果请求成功
			if (null != jsonObject) {
				try {
					accessToken = new AccessToken();
					accessToken.setToken(jsonObject.getString("access_token"));
					System.out.println("access_token:"+jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
					System.out.println("expires_in:"+jsonObject.getString("expires_in"));
				} catch (JSONException e) {
					accessToken = null;
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
		
	}
	
	@RequestMapping(value="/testo")
	public void testo(HttpServletRequest request, HttpServletResponse response){
		 try {
			request.setCharacterEncoding("UTF-8");
		
	     response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html;charset=UTF-8");
		 /* String wxMsgXml = IOUtils.toString(request.getInputStream(),"utf-8");
		 System.out.println("*********wxMsgXml:"+wxMsgXml);*/
		 Map<String, String> map = MessageUtil.parseXml(request);
		 log.info(map.toString());
	        // 发送方帐号（一个OpenID）
	        String fromUserName = map.get("FromUserName");
	        System.out.println("*********fromUserName:"+fromUserName);
	        // 开发者微信号
	        String toUserName = map.get("ToUserName");
	        System.out.println("*********toUserName:"+toUserName);
	        // 消息类型
	        String msgType = map.get("MsgType");
	        System.out.println("*********msgType:"+msgType);
	        // 默认回复一个"success"
	        String responseMessage = "success";
	        // 对消息进行处理
	        if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {// 文本消息
	            TextMessage textMessage = new TextMessage();
	            textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
	            textMessage.setToUserName(fromUserName);
	            textMessage.setFromUserName(toUserName);
	            textMessage.setCreateTime(System.currentTimeMillis());
	            textMessage.setContent("我已经受到你发来的消息了");
	            responseMessage = MessageUtil.textMessageToXml(textMessage);
	        }
	        System.out.println("responseMessage:"+responseMessage);
	        log.info(responseMessage);
	        PrintWriter out = response.getWriter();
			out.print(responseMessage);
		 } catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
