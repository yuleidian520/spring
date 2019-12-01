package com.yld.netty;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 处理客户端连接的thread
 * @author yuleidian
 * @date 2019年6月25日
 */
public class MainReactorThread extends AbstractReactorThread {

	private Selector selector;
	
	private SubReactorThread[] group;
	
	private AtomicInteger count = new AtomicInteger(0);
	
	public MainReactorThread (int groupCapacity, ServerSocketChannel channel) throws IOException {
		if (Objects.isNull(channel)) {
			throw new IllegalArgumentException();
		}
		group = new SubReactorThread[groupCapacity];
		selector = Selector.open();
		SelectionKey register = channel.register(selector, 0);
		register.interestOps(SelectionKey.OP_ACCEPT);
		initThreadGroup();
	}
	
	private void initThreadGroup() throws IOException {
		for (int i = 0; i < group.length; i ++) {
			group[i] = new SubReactorThread();
		}
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
					if (key.isAcceptable()) {
						int index = calcGroupThreadIndex();
						group[index].register((ServerSocketChannel) key.channel());
						group[index].doStart();
					}
					keySet.remove();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private int calcGroupThreadIndex() {
		return count.incrementAndGet() % group.length;
	}

	@Override
	public void process(Channel channel) throws IOException {
		int index = calcGroupThreadIndex();
		group[index].register((ServerSocketChannel) channel);
		group[index].doStart();
	}

}
