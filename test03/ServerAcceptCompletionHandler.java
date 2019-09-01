package top.newaction.MsgTest.test03;/**
 * Created by wangshuo on 2019/8/31.
 */

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

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
 * @since 2019/8/31 12:50 AM
 */
public class ServerAcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

	@Override
	public void completed(AsynchronousSocketChannel socketChannel, AsynchronousServerSocketChannel serverSocketChannel) {
//		System.out.println("接收邀请");
		serverSocketChannel.accept(serverSocketChannel, new ServerAcceptCompletionHandler());
		ServerAcceptHandler serverAcceptHandler = new ServerAcceptHandler();
		serverAcceptHandler.accept(socketChannel);
	}

	@Override
	public void failed(Throwable exc, AsynchronousServerSocketChannel serverSocketChannel) {
		System.out.println("failed to send resp" + " - " + exc.getLocalizedMessage());
		exc.printStackTrace();
	}


}
