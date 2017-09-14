package com.lquan.common.weixin.redpack;


/**
 * 
 * @data 2017年9月12日14:27:20
 * @author liuquan
 * 
 */
public class SendRedPack {

    private String nonce_str; 	// 随机字符串不超过32位
    private String sign;		// 签名
    private String mch_billno;	// 商户订单号
    private String mch_id;		// 微信支付分配的商户号
    private String wxappid;		// 公众账号appid
    private String send_name;	// 商户名称 
    private String re_openid;     // 接受红包的用户 用户在wxappid下的openid
    private Integer total_amount;   // 付款金额，单位分
    private Integer total_num;		// 红包发放总人数  
    private String wishing;		// 红包祝福语
    private String client_ip; // 调用接口的机器Ip地址
    private String act_name; // 活动名称
    private String remark;	// 红包备注信息
    private String scene_id; //　红包发放类型
    private String risk_info; // 活动信息
    private String consume_mch_id;// 资金授权商户号
    private String key;// 拼接我们再前期准备好的API密钥
    private String path;// 加载安全证书的路径 
    private String certPassword;// 商户号证书密码


	public SendRedPack(){}
    
    // act_name=活动名称-test&client_ip=182.92.174.166&mch_billno=20170903185101001&mch_id=1229014502&nonce_str=20170903185100001&re_openid=odVktsxYkNw0ZC_J6jFWgyst-XrA&remark=备注信息-test&scene_id=PRODUCT_5&send_name=汽车调查&total_amount=100&total_num=1&wishing=红包祝福语-test&wxappid=wx48a1c2bfe83b057b&key=MaXiNsIgHt2016092009MaXiNsIgHt03
    /**
     * 
     * @param nonce_str nonce	随机数
     * @param mch_billno 		商户订单号
     * @param re_openid 		用户OpenId
     * @param total_amount 		付款金额，单位分
     * @param num 				红包发放总人数  
     * @param wishing			红包祝福语
     * @param client_ip			调用接口的机器Ip地址
     * @param act_name			活动名称
     * @param remark			红包备注信息
     * @param scene_id			红包发放类型
     * @return
     */
    public String urlWXParams(String nonce_str,String mch_billno,String re_openid,int total_amount,int num,String wishing,String client_ip,String act_name,String remark,String scene_id){
    	StringBuffer url = new StringBuffer();
    	
    	// 微信公众账户ID
    	if(wxappid!=null && !"".equals(wxappid.trim())){
    		url.append("wxappid=").append(getWxappid());
    	}
    	//微信分配的商户号
    	if(mch_id!=null && !"".equals(mch_id.trim())){
    		url.append("&mch_id=").append(getMch_id());
    	}
    	//商户名称
    	if(send_name!=null && !"".equals(send_name.trim())){
    		url.append("&send_name=").append(getSend_name());
    	}
    	// 发放人
    	if(re_openid!=null && !"".equals(re_openid.trim())){
    		url.append("&re_openid=").append(getRe_openid());
    	}
    	// 发放金额，单位是分
    	if(total_amount!=0 && total_amount>0){
    		url.append("&total_amount=").append(getTotal_amount());
    	}
    	// 红包发放总人数  
    	if(total_num!=0 && total_num>0){
    		url.append("&total_num=").append(getTotal_num());
    	}
    	// 红包祝福语
    	if(wishing!=null && !"".equals(wishing.trim())){
    		url.append("&wishing=").append(getWishing());
    	}
    	// 调用机器的IP
    	if(client_ip!=null && !"".equals(client_ip.trim())){
    		url.append("&client_ip=").append(getClient_ip());
    	}
    	// 发放红包类型
    	if(scene_id!=null && !"".equals(scene_id.trim())){
    		url.append("&scene_id=").append(getScene_id());
    	}
    	// 填写API密钥
    	if(key!=null && !"".equals(key.trim())){
    		url.append("&key=").append(getKey());
    	}
    	
		return url.toString();
    }
    
    
    /**
     * 
     * @param nonce_str
     * @param mch_billno
     * @param mch_id
     * @param wxappid
     * @param send_name
     * @param re_openid
     * @param total_amount
     * @param total_num
     * @param wishing
     * @param client_ip
     * @param act_name
     * @param remark
     * @param scene_id
     */
    public SendRedPack(String nonce_str, String mch_billno, String mch_id, String wxappid, String send_name, String re_openid, Integer total_amount, Integer total_num, String wishing, String client_ip, String act_name, String remark, String scene_id) {
        this.nonce_str = nonce_str; //随机字符串不超过32位
        this.mch_billno = mch_billno; // 商户订单号
        this.mch_id = mch_id; //1229014502
        this.wxappid = wxappid; // wx48a1c2bfe83b057b
        this.send_name = send_name;// 汽车调查
        this.re_openid = re_openid; // odVktsxYkNw0ZC_J6jFWgyst-XrA
        this.total_amount = total_amount; // 100分
        this.total_num = total_num; // 人数
        this.wishing = wishing; // "红包祝福语"
        this.client_ip = client_ip; //  调用接口的IP
        this.act_name = act_name; // 活动名称
        this.remark = remark; // 备注信息
        //this.scene_id = scene_id; // 红包发放类型
    }


    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getRisk_info() {
        return risk_info;
    }

    public void setRisk_info(String risk_info) {
        this.risk_info = risk_info;
    }

    public String getConsume_mch_id() {
        return consume_mch_id;
    }

    public void setConsume_mch_id(String consume_mch_id) {
        this.consume_mch_id = consume_mch_id;
    }
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCertPassword() {
		return certPassword;
	}

	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}
	
}
