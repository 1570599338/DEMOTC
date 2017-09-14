package com.lquan.common.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ValidationUtil {
	// 令牌
	private static String token = "lquan666";
	
	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		//1）将token、timestamp、nonce三个参数进行字典序排序 
		String[] StrSort = new String[]{token,timestamp,nonce}; 
		Arrays.sort(StrSort);
		
		//2）将三个参数字符串拼接成一个字符串进行sha1加密 d2b3014d1e17ec22d81b370613896872201e0c1a
		StringBuffer buf =  new StringBuffer();
		for(String arr : StrSort){
			buf.append(arr);
		}
		// 加密
		String getsSignature = sercet(buf.toString());
		if(signature !=null && getsSignature.equals(signature.toUpperCase()))
			return true;
		else
			return false;
	}
	
	// 加密
	private static String sercet(String str){
		MessageDigest md = null;
		String result =null;
		try {
			md =  MessageDigest.getInstance("SHA-1");
			byte[] b = md.digest(str.getBytes());
			//
			result = bytesToString(b);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 将自己字节数组转化为16进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String bytesToString(byte[] byteArray){
		String strDigest = "";
		for(int i=0; i<byteArray.length;i++){
			strDigest = strDigest + byteToHexStr(byteArray[i]);
		}
		
		return strDigest;
	}
	
	/**
	 * 将一个字节转换成一个字符串
	 * @param byteTem
	 * @return
	 */
	private static String byteToHexStr(byte byteTem){
		char[] digest  = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] tem =   new char[2];
		tem[0] = digest[(byteTem>>>4) & 0X0F];
		tem[1] = digest[byteTem & 0X0F];
		
		String result = new String(tem);
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
