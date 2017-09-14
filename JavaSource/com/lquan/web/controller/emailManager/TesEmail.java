package com.lquan.web.controller.emailManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lquan.common.mail.EmailSender;
import com.lquan.common.weixin.redpack.service.SendRedPackService;
import com.lquan.common.weixin.util.ValidationUtil;

@Controller
@RequestMapping(value="senderMail")
public class TesEmail {
	
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
        
        
	}
}
