package com.yld.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * 处理客户端连接io线程
 * @author yuleidian
 * @date 2019年6月25日
 */
public class SubReactorThread extends AbstractReactorThread {

	private Selector selector;
	
	private volatile boolean running = false;
	
	public SubReactorThread() throws IOException {
		selector = Selector.open();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> keySet = selectionKeys.iterator();
				while(keySet.hasNext()) {
					SelectionKey key = keySet.next();
					// 轮询分配连接到线程组中
					if (key.isReadable()) {
						//process(key.channel());
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer readByte = ByteBuffer.allocate(1024);
						while (channel.isOpen() && channel.read(readByte) != -1) {
							if (readByte.position() > 0) {
								break;
							}
						}
						if (readByte.position() == 0) {
							return;
						}
						readByte.flip();
						byte[] content = new byte[readByte.remaining()];
						readByte.get(content);
						System.out.println(new String(content));
					}
					keySet.remove();
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void register(ServerSocketChannel channel) throws IOException {
		SocketChannel accept = channel.accept();
		accept.configureBlocking(false);
		accept.register(selector, SelectionKey.OP_READ);
	}
	
	public void doStart() {
		if (!running) {
			synchronized(selector) {
				if (!running) {
					running = true;
					super.start();
				}
			}
		}
	}

	@Override
	public void process(Channel channel) {
		
	}

	
}
