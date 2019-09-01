package top.newaction.MsgTest.test01; /**
 * Created by wangshuo on 2019/8/25.
 */

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 * Copyright: Copyright (C) 2019 tonglianchengda, Inc. All rights reserved.
 * <p>
 * Company: 北京通联诚达科技有限公司
 * <p>
 *
 * @author wangshuo
 * @since 2019/8/25 9:35 PM
 */
public class MsgTest01 {

	/**
	 * 启动两个线程,分别为大爷1,大爷2
	 * 两个线程的实现都是一样的 要做到能够同时接受,同时发送
	 * 发送消息 :
	 * {
	 * 会话编号
	 * 会话内容
	 * <p>
	 * }
	 * <p>
	 * 请求,tcp协议
	 * 接受,返回
	 * 返回消息:
	 * {
	 * 会话编号
	 * }
	 */
	public static int count = 10001;
	public static int getCount(){
		return --count;
	}
	public static void main(String[] args) throws InterruptedException {

		Client c = new Client();
		c.start();

	}




}

