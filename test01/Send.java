package top.newaction.MsgTest.test01; /**
 * Created by wangshuo on 2019/8/25.
 */

import java.io.*;
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
class Send implements Runnable {
	private int no;
	private int port;
	private String host;
	public Send(String host, int port, int no){
		this.host = host;
		this.port = port;
		this.no = no;
	}
	@Override
	public void run() {
		try {
			long l = System.currentTimeMillis();
			while (true){
				int count = MsgTest01.getCount();

				if (0 >= count){
					System.out.println(System.currentTimeMillis() - l);
					break;
				}
				Socket socket = new Socket(host, port);
				OutputStream reqOut = socket.getOutputStream();
				byte[] reqContent = (count + ",n-"+no+",content-" + count).getBytes();
				Short reqLength = (short) reqContent.length;
				byte[] reqLengthByte = new byte[2];
				reqLengthByte[0]=(byte) ((reqLength>>8)&0xff);
				reqLengthByte[1]=(byte) (reqLength&0xff);
				reqOut.write(reqLengthByte);
				reqOut.write(reqContent);
				reqOut.flush();

				InputStream resIn = socket.getInputStream();
				byte[] resLengthByte = new byte[2];
				resIn.read(resLengthByte);
				int resLength = (resLengthByte[0]&0xff) << 8 | (resLengthByte[1]&0xff);
				byte[] resContent = new byte[resLength];
				resIn.read(resContent);
				resIn.close();
				String responseStr = new String(resContent);
//				System.out.println("send : r - " + responseStr);
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
