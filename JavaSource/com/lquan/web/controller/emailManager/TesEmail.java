package com.lquan.web.controller.emailManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lquan.common.mail.EmailSender;
import com.lquan.common.weixin.redpack.service.SendRedPackService;

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
}
