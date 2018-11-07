package org.fyh.tengxun.sdk.weixin.pay;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;
/**
 * 
 * @Description:统一下单
 * @author:fangyunhe
 * @time:2018年11月5日 上午11:39:47
 * @version 1.0
 */
public class UnifiedOrderRequest extends BaseWxRequest {
	String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 
	 * @param body 商品描述
	 * @param outTradeNo 订单号
	 * @param totalFee 总金额
	 * @param ip
	 * @param notifyUrl 通知地址
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @author:fangyunhe
	 * @time:2018年11月5日 上午11:29:47
	 */
	public String getPrepayId(String body, String outTradeNo, String totalFee, String ip, String notifyUrl)
			throws JDOMException, IOException {
		set("body", body);
		set("out_trade_no", outTradeNo);
		set("total_fee", totalFee);
		set("spbill_create_ip", ip);
		set("notify_url", notifyUrl);
		set("appid", WxPayConstant.APP_ID);
		set("mch_id", WxPayConstant.MCH_ID);
		set("nonce_str", getRandomString(8));
		set("trade_type", WxPayConstant.TRADE_TYPE);
		createMD5Sign();
		String prepayId = null;
		Map<String, String> map = super.execute(unifiedorderUrl);
		if (map != null) {
			prepayId = map.get("prepay_id");
		}
		return prepayId;
	}

}
