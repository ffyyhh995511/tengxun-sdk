package org.fyh.tengxun.sdk.yuntongxin;
/**
 * 
 * @Description:测试
 * @author:fangyunhe
 * @time:2018年11月7日 下午7:43:41
 * @version 1.0
 */
public class ImTest {
	public static void main(String[] args) {
		YunTongXinIMClient client = new YunTongXinIMClient();
		//注册(导入)用户100000001
		boolean register1 = client.register("100000001");
		System.out.println(register1);
		//注册(导入)用户100000002
		boolean register2 = client.register("100000002");
		System.out.println(register2);
		//用户100000002发送文本给用户100000002
		boolean sendMsg = client.sendMsg("100000001", "100000002", "发送内容");
		System.out.println(sendMsg);
	}
}
