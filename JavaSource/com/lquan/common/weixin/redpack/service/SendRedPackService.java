package com.lquan.common.weixin.redpack.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.ssl.SSLContexts;
import org.json.JSONException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

import com.lquan.common.weixin.redpack.SendRedPack;
import com.lquan.common.weixin.util.WXTool;

public class SendRedPackService {
	
	private SendRedPack sendRedPack;
	public void sendHongBao(String nonce_str,String mch_billno,String re_openid,int total_amount,int num,String wishing,String client_ip,
			String act_name,String remark,String scene_id){
		sendRedPack.setNonce_str(nonce_str);// 随机数
		sendRedPack.setMch_billno(mch_billno); //商品id
		sendRedPack.setRe_openid(re_openid);// 用户的openid
		sendRedPack.setTotal_amount(total_amount);// 红包礼金 单位分
		sendRedPack.setTotal_num(num);	// 发送的个数
		sendRedPack.setWishing(wishing); // 祝福语
		sendRedPack.setClient_ip(client_ip); // 调用接口的IP
		sendRedPack.setAct_name(act_name);  // 活动名称
		sendRedPack.setRemark(remark);  // 备注信息
		sendRedPack.setScene_id(scene_id); // 红包发放类型
		
		String urlParamsByMap = sendRedPack.urlWXParams(nonce_str, mch_billno, re_openid, total_amount, num, wishing, client_ip, act_name, remark, scene_id);
  	   	String sign = WXTool.parseStrToMd5L32(urlParamsByMap).toUpperCase();
        sendRedPack.setSign(sign);
        //微信要求按照参数名ASCII字典序排序，这里巧用treeMap进行字典排序
        TreeMap treeMap = new TreeMap(WXTool.toMap(sendRedPack));
        //然后转换成xml格式
        String soapRequestData;
		try {
			
			soapRequestData = WXTool.getSoapRequestData(treeMap);
		
	         //发起请求前准备
	        RequestBody body = RequestBody.create(MediaType.parse("text/xml;charset=UTF-8"), soapRequestData);
	        Request request = new Request.Builder()
	                 .url("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack")
	                 .post(body)
	                 .build();
	        //为http请求设置证书
	        SSLSocketFactory socketFactory = getSSL().getSocketFactory();
	        X509TrustManager x509TrustManager = Platform.get().trustManager(socketFactory);
	        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(socketFactory, x509TrustManager).build();
	        //得到输出内容
	        Response response = okHttpClient.newCall(request).execute();
	        String content = response.body().string();
	        System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
	 
	 
	    /**
	     * 证书加载
	     * @return
	     * @throws KeyStoreException
	     * @throws IOException
	     * @throws CertificateException
	     * @throws NoSuchAlgorithmException
	     * @throws UnrecoverableKeyException
	     * @throws KeyManagementException
	     */
	    private  SSLContext getSSL() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
	        KeyStore keyStore = KeyStore.getInstance("PKCS12");
	        //证书位置自己定义
	       // FileInputStream instream = new FileInputStream(new File("D:/soft/apiclient_cert.p12"));
	        String pathac = sendRedPack.getPath()+"/apiclient_cert.p12";
	        FileInputStream instream = new FileInputStream(new File(pathac));
	        try {
	           // keyStore.load(instream, "填写证书密码，默认为商户号".toCharArray());//1229014502
	            keyStore.load(instream, sendRedPack.getCertPassword().toCharArray());
	        } finally {
	            instream.close();
	        }
	        SSLContext sslcontext = SSLContexts.custom()
	                //.loadKeyMaterial(keyStore, "填写证书密码，默认为商户号".toCharArray())
	                .loadKeyMaterial(keyStore, sendRedPack.getCertPassword().toCharArray())
	                .build();
	        return sslcontext;
	    }

	    
		 public SendRedPack getSendRedPack() {
				return sendRedPack;
			}


			public void setSendRedPack(SendRedPack sendRedPack) {
				this.sendRedPack = sendRedPack;
			}

}
