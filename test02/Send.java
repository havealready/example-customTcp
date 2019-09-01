package top.newaction.MsgTest.test02; /**
 * Created by wangshuo on 2019/8/25.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

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
class Send implements Runnable {
	private int no;
	private int port;
	private String host;

	public Send(String host, int port, int no) {
		this.host = host;
		this.port = port;
		this.no = no;
	}

	@Override
	public void run() {
		try {
			long l = System.currentTimeMillis();
			while (true) {
				int count = MsgTest02.getCount();

				if (0 >= count) {
					System.out.println(System.currentTimeMillis() - l);
					break;
				}
				AsynchronousSocketChannel socket = AsynchronousSocketChannel.open();
//				socket.bind();
//				AsynchronousSocketChannel socket = .bind();
				Future<Void> connect = socket.connect(new InetSocketAddress(host, port));
				while (! connect.isDone()) {
//					this.wait(10);
				}
//				Socket socket = new Socket(host, port);
//				OutputStream reqOut = socket.getOutputStream();

				byte[] reqContent = (count + ",n-" + no + ",content-" + count).getBytes();
				Short reqLength = (short) reqContent.length;
				byte[] reqLengthByte = new byte[2];
				reqLengthByte[0] = (byte) ((reqLength >> 8) & 0xff);
				reqLengthByte[1] = (byte) (reqLength & 0xff);

//				socket.read(ByteBuffer.wrap(reqLengthByte));
//				socket.read(ByteBuffer.wrap(reqContent));
				ByteBuffer wrap = ByteBuffer.wrap(reqLengthByte);
				socket.write(wrap, socket, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
					@Override
					public void completed(Integer result, AsynchronousSocketChannel attachment) {
//						System.out.println("sendWriteLength - " + result);
						socket.write(ByteBuffer.wrap(reqContent), socket, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
							@Override
							public void completed(Integer result, AsynchronousSocketChannel attachment) {
//								System.out.println("sendWriteContentResult - " + result);
							}

							@Override
							public void failed(Throwable exc, AsynchronousSocketChannel attachment) {

							}
						});
					}

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel attachment) {

					}
				});

				byte[] resLengthByte = new byte[2];
				socket.read(ByteBuffer.wrap(resLengthByte), socket, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
					@Override
					public void completed(Integer result, AsynchronousSocketChannel attachment) {

						int resLength = (resLengthByte[0] & 0xff) << 8 | (resLengthByte[1] & 0xff);
						byte[] resContent = new byte[resLength];
						socket.read(ByteBuffer.wrap(resContent), attachment, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
							@Override
							public void completed(Integer result, AsynchronousSocketChannel attachment) {
								String responseStr = new String(resContent);
//								System.out.println("send : r - " + responseStr);
//								System.out.println("sendRead : r - " + responseStr);
								try {
									attachment.shutdownInput();
//									attachment.shutdownOutput();
									attachment.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
								System.out.println("111111");
								//System.out.println("send : r - " + responseStr);
							}
						});


					}

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel attachment) {

						System.out.println("failed to send resp"
								+ attachment.isOpen() + " - " + exc.getLocalizedMessage());
						exc.printStackTrace();
					}
				});
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
