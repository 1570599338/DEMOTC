package com.lquan.common.weixin.bean;

import com.lquan.common.weixin.util.XStreamUtil;


/**
 * 文本消息
 * @author lquan
 *
 */
public class TextMessage extends BaseWechatMessage {
	/**
	 * 文本消息内容
	 */
	private String Content;
	
	public String getContent() {
		return Content;
	}
	
	public void setContent(String content) {
		Content =XStreamUtil.PREFIX_CDATA + content+XStreamUtil.SUFFIX_CDATA;
	}
	

}
