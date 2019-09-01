package top.newaction.MsgTest.test03; /**
 * Created by wangshuo on 2019/8/26.
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
 * @since 2019/8/26 1:16 AM
 */
public class Client {

	public void start() throws InterruptedException {
		new Thread(new Receive(19291)).start();
		Thread.sleep(1000);
//		for (int i = 0; i < 10; i++){
			new Thread(new Send("127.0.0.1", 19291,1)).start();
			new Thread(new Send("127.0.0.1", 19291,2)).start();
//			new Thread(new Send("127.0.0.1", 19291,3)).start();
//			new Thread(new Send("127.0.0.1", 19291,4)).start();
//			new Thread(new Send("127.0.0.1", 19291,5)).start();
//			new Thread(new Send("127.0.0.1", 19291,6)).start();
//			new Thread(new Send("127.0.0.1", 19291,7)).start();
//			new Thread(new Send("127.0.0.1", 19291,8)).start();
//		}
	}


}
