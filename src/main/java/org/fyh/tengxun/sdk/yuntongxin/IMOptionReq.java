package org.fyh.tengxun.sdk.yuntongxin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Description
 * @author:fangyunhe
 * @time:2018年4月11日 上午10:10:09
 * @version 1.0
 */
public class IMOptionReq {
	
	/**
	 * 0表示单聊消息支持推送
	 */
	private final static Integer CAN_OFF_LINE_PUSH = 0;
	
	/**
	 * 1表示单聊消息不支持推送
	 */
	private final static Integer NO_OFF_LINE_PUSH = 1; 

	/**
	 * 构建注册body
	 * 
	 * @param account
	 * @return
	 * @Description:TODO
	 * @author:fangyunhe
	 * @time:2018年4月11日 上午10:14:05
	 */
	public static String buildRegisterBody(String account) {
		Map<String, Object> body = new HashMap<String, Object>(1);
		body.put("Identifier", account);
		return JSON.toJSONString(body);
	}
	
	/**
	 * 构建单聊body
	 * @param toAccount
	 * @param msgContentString
	 * @return
	 * @Description:TODO
	 * @author:fangyunhe
	 * @time:2018年4月11日 上午11:05:41
	 */
	public static String buildSendMsgBody(String fromAccount, String toAccount, String msgContentString) {
		return buildSendMsgBodyOfflinePushInfo(fromAccount, toAccount, msgContentString, NO_OFF_LINE_PUSH, null, null);
	}
	
	public static String buildSendMsgBody(String toAccount, String msgContentString) {
		return buildSendMsgBodyOfflinePushInfo(null, toAccount, msgContentString, NO_OFF_LINE_PUSH, null, null);
	}
	
	/**
	 * 构建单聊body，同时支持离线推送和透传
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
	public static String buildSendMsgBodyOfflinePushInfo(String fromAccount, String toAccount, String msgContentString, int pushFlag, String offLineMsg, String extMsg) {
		Map<String, Object> body = new HashMap<String, Object>(20);
		Map<String, Object> msgBody = new HashMap<String, Object>(10);
		Map<String, Object> offlinePushInfo = new HashMap<String, Object>(3);
		Map<String, Object> msgContent = new HashMap<String, Object>(1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(1);
		list.add(msgBody);
		// "SyncOtherMachine": 1,消息同步至发送方
		body.put("SyncOtherMachine", 1);
		if(StringUtils.isNotBlank(fromAccount)) {
			body.put("From_Account", fromAccount);
		}
		body.put("To_Account", toAccount);
		// 消息离线保存时长（秒数）设置一年
		Long liftTime = 3600 * 24 * 365L;
		body.put("MsgLifeTime", liftTime);
		body.put("MsgRandom", getNowTimeStamp());
		body.put("MsgTimeStamp", getNowTimeStamp());
		body.put("MsgBody",list);
		body.put("OfflinePushInfo", offlinePushInfo);
		msgBody.put("MsgType", "TIMTextElem");		
		msgBody.put("MsgContent", msgContent);
		msgContent.put("Text", msgContentString);
		//离线推送内容和透传的内容
		offlinePushInfo.put("PushFlag", pushFlag);
		offlinePushInfo.put("Desc", offLineMsg);
		offlinePushInfo.put("Ext", extMsg);
		return JSON.toJSONString(body);
	}
	
	/**
	 * 构建设置用户属性body
	 * 
	 * @param toAccount
	 * @param avatar
	 * @param sex
	 * @return
	 * @Description:TODO
	 * @author:fangyunhe
	 * @time:2018年4月11日 上午11:54:43
	 */
	public static String buildUpdateUserAttrBody(String toAccount, String avatar, String nickName, SexEnum sex) {
		Map<String, Object> body = new HashMap<String, Object>(1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(1);
		Map<String, Object> avatarItme = new HashMap<String, Object>(2);
		Map<String, Object> nickNameItme = new HashMap<String, Object>(2);
		Map<String, Object> sexItme = new HashMap<String, Object>(2);
		body.put("From_Account", toAccount);
		body.put("ProfileItem", list);
		avatarItme.put("Tag", "Tag_Profile_IM_Image");
		avatarItme.put("Value", avatar);
		
		nickNameItme.put("Tag", "Tag_Profile_IM_Nick");
		nickNameItme.put("Value", nickName);
		
		sexItme.put("Tag", "Tag_Profile_IM_Gender");
		sexItme.put("Value", getSexString(sex));
		
		list.add(avatarItme);
		list.add(nickNameItme);
		list.add(sexItme);
		return JSON.toJSONString(body);
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * 
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月11日 上午10:31:49
	 */
	private static Long getNowTimeStamp() {
		long time = System.currentTimeMillis();
		return (time / 1000);
	}
	
	/**
	 * 获取随机数
	 * 
	 * @param length
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月11日 上午10:55:33
	 */
	private static String getRandomStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 构建批量单聊body
	 * 
	 * @param toAccounts
	 * @param msgContentString
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月18日 下午5:03:35
	 */
	public static String buildBatchSendMsgBody(List<String> toAccounts, String msgContentString) {
		return buildBatchSendMsgBodyOfflinePushInfo(null, toAccounts, msgContentString, NO_OFF_LINE_PUSH, null, null);
	}
	
	public static String buildBatchSendMsgBody(String fromAccount, List<String> toAccounts, String msgContentString) {
		return buildBatchSendMsgBodyOfflinePushInfo(fromAccount, toAccounts, msgContentString, NO_OFF_LINE_PUSH, null, null);
	}
	
	/**
	 * 构建批量单聊body，同时支持离线推送和透传
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
	public static String buildBatchSendMsgBodyOfflinePushInfo(String fromAccount, List<String> toAccounts, String msgContentString, int pushFlag, String offLineMsg, String extMsg) {
		Map<String, Object> body = new HashMap<String, Object>(20);
		Map<String, Object> msgBody = new HashMap<String, Object>(10);
		Map<String, Object> msgContent = new HashMap<String, Object>(1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(1);
		Map<String, Object> offlinePushInfo = new HashMap<String, Object>(3);
		list.add(msgBody);
		// "SyncOtherMachine": 1,消息同步至发送方
		body.put("SyncOtherMachine", 1);
		if(StringUtils.isNotBlank(fromAccount)) {
			body.put("From_Account", fromAccount);
		}
		body.put("To_Account", toAccounts);
		// 消息离线保存时长（秒数）设置一年
		Long liftTime = 3600 * 24 * 365L;
		body.put("MsgLifeTime", liftTime);
		body.put("MsgRandom", getNowTimeStamp());
		body.put("MsgTimeStamp", getNowTimeStamp());
		body.put("MsgBody",list);
		body.put("OfflinePushInfo", offlinePushInfo);
		msgBody.put("MsgType", "TIMTextElem");		
		msgBody.put("MsgContent", msgContent);
		msgContent.put("Text", msgContentString);
		//离线推送内容和透传的内容
		offlinePushInfo.put("PushFlag", pushFlag);
		offlinePushInfo.put("Desc", offLineMsg);
		offlinePushInfo.put("Ext", extMsg);
		return JSON.toJSONString(body);
	}
	
	/**
	 * 获取枚举对应的tim sdk 性别字符串
	 * 
	 * @param sex
	 * @return
	 * @author:fangyunhe
	 * @time:2018年4月21日 下午7:12:04
	 */
	private static String getSexString(SexEnum sex) {
		String sexStr = null;
		switch (sex) {
		case UNKNOWN:
			sexStr = "Gender_Type_Unknown";
			break;
		case WOMEN:
			sexStr = "Gender_Type_Female";
			break;
		case MAN:
			sexStr = "Gender_Type_Female";
			break;
		}
		return sexStr;
	}

}
