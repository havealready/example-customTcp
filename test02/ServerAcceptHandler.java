package top.newaction.MsgTest.test02;/**
 * Created by wangshuo on 2019/8/30.
 */

import java.io.IOException;
import java.nio.ByteBuffer;
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
 * @since 2019/8/30 1:13 AM
 */
public class ServerAcceptHandler {

	private AsynchronousSocketChannel serverSocketChannel;

	public ServerAcceptHandler() {

	}

	public void accept(AsynchronousSocketChannel serverSocketChannel) {
		byte[] reqLengthByte = new byte[2];
		ByteBuffer reqLengthWrap = ByteBuffer.wrap(reqLengthByte);
		serverSocketChannel.read(reqLengthWrap, serverSocketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
			@Override
			public void completed(Integer result, AsynchronousSocketChannel attachment) {

				int reqLength = (reqLengthByte[0] & 0xff) << 8 | (reqLengthByte[1] & 0xff);
				byte[] reqContent = new byte[reqLength];
				ByteBuffer reqContentWrap = ByteBuffer.wrap(reqContent);

				attachment.read(reqContentWrap, attachment, new CompletionHandler<Integer, AsynchronousSocketChannel>() {

					@Override
					public void completed(Integer result, AsynchronousSocketChannel attachment) {
						String requestStr = new String(reqContent);
						String[] split = requestStr.split(",");

//										System.out.println("receive : n = " + split[0]);
						//				System.out.println("receive : no = " + split[1]);
						//				System.out.println("receive : c = " + split[2]);

						byte[] resContent = split[0].getBytes();
						Short resLength = (short) resContent.length;
						byte b = resLength.byteValue();

						byte[] resLengthByte = new byte[2];

						resLengthByte[0] = (byte) ((resLength >> 8) & 0xff);
						resLengthByte[1] = (byte) (resLength & 0xff);

						ByteBuffer wrap = ByteBuffer.wrap(resLengthByte);
						attachment.write(wrap, attachment, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
							@Override
							public void completed(Integer result, AsynchronousSocketChannel attachment) {
								ByteBuffer wrap = ByteBuffer.wrap(resContent);
								attachment.write(wrap, attachment, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
									@Override
									public void completed(Integer result, AsynchronousSocketChannel attachment) {
//										System.out.println(" : " + result);
										try {
//											attachment.shutdownInput();
//											attachment.shutdownOutput();
											attachment.close();

										} catch (IOException e) {
											e.printStackTrace();
										}
									}

									@Override
									public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
										System.out.println("failed to send resp"
												+ attachment.isOpen() + " - " + exc.getLocalizedMessage());
										exc.printStackTrace();
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

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
						System.out.println("failed to send resp"
								+ attachment.isOpen() + " - " + exc.getLocalizedMessage());
						exc.printStackTrace();
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

}
