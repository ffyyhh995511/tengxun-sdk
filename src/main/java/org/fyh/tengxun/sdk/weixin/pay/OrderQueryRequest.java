package org.fyh.tengxun.sdk.weixin.pay;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;

/**
 * 
 * @Description:查询订单
 * @author:fangyunhe
 * @time:2018年11月5日 上午11:40:40
 * @version 1.0
 */
public class OrderQueryRequest extends BaseWxRequest {
	String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	public Map<String, String> orderQuery(String outTradeNo) throws JDOMException, IOException{
		set("out_trade_no", outTradeNo);
		set("appid", WxPayConstant.APP_ID);
		set("mch_id", WxPayConstant.MCH_ID);
		set("nonce_str", getRandomString(8));
		createMD5Sign();
		Map<String, String> map = super.execute(orderQueryUrl);
		return map;
	}
}
