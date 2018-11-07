package org.fyh.tengxun.sdk.weixin.pay;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom2.JDOMException;

/**
 * 
 * @Description:
 * @author:fangyunhe
 * @time:2018年11月5日 上午10:26:32
 * @version 1.0
 */
public abstract class BaseWxRequest {

	public SortedMap<String, String> parameters = new TreeMap<String, String>();
	
	public SortedMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(SortedMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getParamString() {
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		StringBuilder sb = new StringBuilder("<xml>");
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append("<" + k + ">" + v + "</" + k + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public Map<String, String> execute(String url) throws JDOMException, IOException {
		String resContent = null;
		String paramString = getParamString();
		System.out.println("请求参数：" + paramString);
		System.out.println("请求url：" + url);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		if (httpClient.callHttpPost(url, paramString)) {
			resContent = httpClient.getResContent();
			System.out.println("返回值：" + resContent);
			Map<String, String> map = XMLUtil.doXMLParse(resContent);
			return map;
		}
		return null;
	}

	public void set(String key, String value) {
		this.parameters.put(key, value);
	}

	public void createMD5Sign() {
		StringBuilder sb = new StringBuilder();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		String params = sb.append("key=" + WxPayConstant.KEY).substring(0);
		String sign = MD5Util.MD5Encode(params, "utf8");
		sign = sign.toUpperCase();
		getParameters().put("sign", sign);
	}

	public static String getRandomString(int length) {
		// 随机字符串的随机字符库
		String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}

}
