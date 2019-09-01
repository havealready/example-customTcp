package top.newaction.MsgTest.test01; /**
 * Created by wangshuo on 2019/8/25.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
			ServerSocket receive = new ServerSocket(port);
			while (true) {
				Socket accept = receive.accept();
				InputStream reqIn = accept.getInputStream();
				byte[] reqLengthByte = new byte[2];
				reqIn.read(reqLengthByte);
				int reqLength = (reqLengthByte[0]&0xff) << 8 | (reqLengthByte[1]&0xff);

				byte[] reqContent = new byte[reqLength];
				reqIn.read(reqContent);
				String requestStr = new String(reqContent);
				String[] split = requestStr.split(",");

//				System.out.println("receive : n = " + split[0]);
//				System.out.println("receive : no = " + split[1]);
//				System.out.println("receive : c = " + split[2]);
				OutputStream resOut = accept.getOutputStream();
				byte[] resContent = split[0].getBytes();
				Short resLength = (short) resContent.length;
				byte b = resLength.byteValue();
				byte[] resLengthByte = new byte[2];

				resLengthByte[0]=(byte) ((resLength>>8)&0xff);
				resLengthByte[1]=(byte) (resLength&0xff);
				resOut.write(resLengthByte);
				resOut.write(resContent);
				resOut.flush();
				accept.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

//		}
	}
}
