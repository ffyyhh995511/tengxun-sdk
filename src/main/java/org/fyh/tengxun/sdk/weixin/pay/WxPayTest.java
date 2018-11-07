package org.fyh.tengxun.sdk.weixin.pay;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.jdom2.JDOMException;
/**
 * 
 * @Description:微信支付测试类
 * @author:fangyunhe
 * @time:2018年11月7日 下午7:59:45
 * @version 1.0
 */
public class WxPayTest {
	public static void main(String[] args) throws JDOMException, IOException {
		unifiedOrder();
//		orderQuery("6a18b7942eef4ca78ff15a08cc3fbcdc");
	}
	
	/**
	 * 查询订单
	 * @param outTradeNo
	 * @throws JDOMException
	 * @throws IOException
	 * @author:fangyunhe
	 * @time:2018年11月5日 下午12:05:14
	 */
	private static void orderQuery(String outTradeNo) throws JDOMException, IOException {
		OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
		Map<String, String> orderQuery = orderQueryRequest.orderQuery(outTradeNo);
		System.out.println(orderQuery);
	}
	
	/**
	 * 创建订单
	 * @throws JDOMException
	 * @throws IOException
	 * @author:fangyunhe
	 * @time:2018年11月5日 下午12:05:04
	 */
	private static void unifiedOrder() throws JDOMException, IOException {
		UnifiedOrderRequest request = new UnifiedOrderRequest();
		String body = "APP支付测试";
		String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "").substring(12);
		String totalFee = "1";
		String ip = "113.65.211.71";
		String notifyUrl = "http://www.weixin.qq.com/wxpay/pay.php";
		String prepayId = request.getPrepayId(body, outTradeNo, totalFee, ip, notifyUrl);
		System.out.println("prepayId=" + prepayId);
	}
}
