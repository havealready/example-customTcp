package top.newaction.MsgTest.test03; /**
 * Created by wangshuo on 2019/8/25.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;

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
 * @since 2019/8/25 10:59 PM
 */
class Receive implements Runnable {
	private int port;

	public Receive(int port) {
		this.port = port;
	}

	@Override
	public void run() {

		try {
			AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open();
			serverSocket.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			serverSocket.bind(new InetSocketAddress(port));

//			ServerSocket receive = new ServerSocket(port);
//			System.out.println("循环");
			serverSocket.accept(serverSocket, new ServerAcceptCompletionHandler());

		} catch (IOException e) {
			e.printStackTrace();
		}

//		}
	}
}
