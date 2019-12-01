package com.yld.netty;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.Selector;

public abstract class AbstractReactorThread extends Thread {

	public abstract void process(Channel channel) throws IOException;
	
	protected Selector selector;
	
	/*@Override
	public void run() {
		while(true) {
			try {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> keySet = selectionKeys.iterator();
				while(keySet.hasNext()) {
					SelectionKey key = keySet.next();
					int readKeyOps = key.readyOps();
					if ((readKeyOps & (SelectionKey.OP_ACCEPT | SelectionKey.OP_READ)) != 0 || readKeyOps == 0) {
						process(key.channel());
					}
					keySet.remove();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
}
