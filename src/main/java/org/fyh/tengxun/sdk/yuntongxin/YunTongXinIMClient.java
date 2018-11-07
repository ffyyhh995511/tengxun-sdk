package org.fyh.tengxun.sdk.yuntongxin;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 云通信服务端api集成封装
 * 
 * @author:fangyunhe
 * @time:2018年4月10日 下午7:40:17
 * @version 1.0
 */
public class YunTongXinIMClient {
	
	/**
	 * 这些配置需优化到属性配置文件里
	 */
	private static final String HOST = "https://console.tim.qq.com/";

	private static final String USER_SIG = "eJxlj0tPg0AUhff8CjJbjB0GpqUmrihpqVBb2ykjGzKWab0aHoHpgxj-u4pNnMS7-b6Tc*6HYZom2kTrW7HbVcdSZaqrJTLvTITRzR*sa8gzoTKnyf9BeamhkZnYK9n00KaUEox1B3JZKtjD1RB5AaWG2-w96zt*8*532CN0RHUFDj2MA*aHq8nr03zcbVkSNOnIiwl1B7xMUgYJn0lizfzOe6kGj1v2plbhgU*VxZUVBtGynfNFfIn4mba*KNLJ84PvOtPQXgA7n9ojvtcqFRTy*hBxyHDojPVBJ9m0UJW9QLBNbeLgn0PGp-EFnp9cLA__";

	private static final String IDENTIFIER = "admin";

	private static final String SDK_APP_ID = "1400082575";

	private static final String CONTENT_TYPE = "json";
	
	private static final String API_STATUS = "OK";

	/**
	 * 注册云通信账号
	 * 
	 * @param account
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月10日 下午8:24:36
	 */
	public boolean register(String account) {
		// 独立模式帐号导入
		String endPoint = "v4/im_open_login_svc/account_import";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildRegisterBody(account);
		return send(url, body);
	}

	/**
	 * 云通信api调用
	 * 
	 * @param url
	 * @param body
	 * @author:fangyunhe
	 * @time:2018年4月18日 下午6:10:17
	 */
	private boolean send(String url, String body) {
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpsPost(url, body);
		JSONObject parseObject = JSON.parseObject(result);
		String status = (String) parseObject.get("ActionStatus");
		if(API_STATUS.equals(status)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 发送消息,不填发送人默认为管理员
	 * 
	 * @param toAccount
	 * @param msgContentString
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月11日 上午11:07:36
	 */
	public boolean sendMsg(String toAccount, String msgContentString) {
		String endPoint = "v4/openim/sendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildSendMsgBody(toAccount, msgContentString);
		return send(url, body);
	}
	
	/**
	 * 指定用户发送消息
	 * @param fromAccount
	 * @param toAccount
	 * @param msgContentString
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月21日 下午7:30:36
	 */
	public boolean sendMsg(String fromAccount, String toAccount, String msgContentString) {
		String endPoint = "v4/openim/sendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildSendMsgBody(fromAccount, toAccount, msgContentString);
		return send(url, body);
	}
	
	/**
	 * 发送消息，不填发送人默认为管理员, 同时支持离线推送和透传
	 * 
	 * @param toAccount
	 * @param msgContentString
	 * @param pushFlag  0表示推送，1表示不离线推送
	 * @param offLineMsg  离线推送内容
	 * @param extMsg  离线推送内容
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月18日 下午5:24:26
	 */
	public boolean sendMsgOfflinePush(String toAccount, String msgContentString, int pushFlag, String offLineMsg, String extMsg) {
		String endPoint = "v4/openim/sendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildSendMsgBodyOfflinePushInfo(null, toAccount, msgContentString, pushFlag, offLineMsg, extMsg);
		return send(url, body);
	}
	
	/**
	 * 指定用户发送消息
	 * 
	 * @param fromAccount
	 * @param toAccount
	 * @param msgContentString
	 * @param pushFlag  0表示推送，1表示不离线推送
	 * @param offLineMsg  离线推送内容
	 * @param extMsg  离线推送内容
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月18日 下午5:24:26
	 */
	public boolean sendMsgOfflinePush(String fromAccount,String toAccount, String msgContentString, int pushFlag, String offLineMsg, String extMsg) {
		String endPoint = "v4/openim/sendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildSendMsgBodyOfflinePushInfo(fromAccount, toAccount, msgContentString, pushFlag, offLineMsg, extMsg);
		return send(url, body);
	}
	
	/**
	 * 批量发送消息,不填发送人默认为管理员
	 * @param toAccounts
	 * @param msgContentString
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月18日 下午5:05:30
	 */
	public boolean batchSendMsg(List<String> toAccounts, String msgContentString) {
		String endPoint = "v4/openim/batchsendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildBatchSendMsgBody(null, toAccounts, msgContentString);
		return send(url, body);
	}
	
	/**
	 * 指定人批量发送消息
	 * @param fromAccount
	 * @param toAccounts
	 * @param msgContentString
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月21日 下午7:28:18
	 */
	public boolean batchSendMsg(String fromAccount, List<String> toAccounts, String msgContentString) {
		String endPoint = "v4/openim/batchsendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildBatchSendMsgBody(fromAccount, toAccounts, msgContentString);
		return send(url, body);
	}
	
	/**
	 * 批量发送消息,不填发送人默认为管理员, 同时支持离线推送和透传
	 * @param toAccounts
	 * @param msgContentString
	 * @param pushFlag  0表示推送，1表示不离线推送
	 * @param offLineMsg  离线推送内容
	 * @param extMsg  离线透传内容
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月18日 下午6:04:15
	 */
	public boolean batchSendMsgOfflinePush(List<String> toAccounts, String msgContentString, int pushFlag, String offLineMsg, String extMsg) {
		String endPoint = "v4/openim/batchsendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildBatchSendMsgBodyOfflinePushInfo(null, toAccounts, msgContentString, pushFlag, offLineMsg, extMsg);
		return send(url, body);
	}
	
	/**
	 * 指定用户批量发送消息 同时支持离线推送和透传
	 * @param fromAccount
	 * @param toAccounts
	 * @param msgContentString
	 * @param pushFlag 0表示推送，1表示不离线推送
	 * @param offLineMsg 离线推送内容
	 * @param extMsg 离线透传内容
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月21日 下午7:32:52
	 */
	public boolean batchSendMsgOfflinePush(String fromAccount, List<String> toAccounts, String msgContentString, int pushFlag, String offLineMsg, String extMsg) {
		String endPoint = "v4/openim/batchsendmsg";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildBatchSendMsgBodyOfflinePushInfo(fromAccount, toAccounts, msgContentString, pushFlag, offLineMsg, extMsg);
		return send(url, body);
	}
	
	/**
	 * 
	 * 设置用户属性
	 * 
	 * @param toAccount
	 * @param avatar
	 * @param nickName
	 * @param sex
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月21日 下午7:06:16
	 */
	public boolean updateUserAttr(String toAccount, String avatar, String nickName, SexEnum sex) {
		String endPoint = "v4/profile/portrait_set";
		String url = buildOptionUrl(endPoint);
		String body = IMOptionReq.buildUpdateUserAttrBody(toAccount, avatar, nickName, sex);
		return send(url, body);
	}

	/**
	 * 构建不同api访问路径
	 * 
	 * @param endPoint
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月10日 下午8:08:12
	 */
	private String buildOptionUrl(String endPoint) {
		StringBuilder sb = new StringBuilder();
		sb.append(HOST);
		sb.append(endPoint + "?");
		sb.append("usersig=" + USER_SIG + "&");
		sb.append("identifier=" + IDENTIFIER + "&");
		sb.append("sdkappid=" + SDK_APP_ID + "&");
		sb.append("contenttype=" + CONTENT_TYPE);
		return sb.toString();
	}
	
	/**
	 * 获取userSig（用户帐号签名）
	 * @author pomi
	 * @create_time 2018年4月21日 下午4:02:30
	 * @return
	 */
	public String getUserSig() {
		return USER_SIG;
	}
}
