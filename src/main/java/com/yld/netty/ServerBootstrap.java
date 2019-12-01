package com.yld.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.locks.LockSupport;

public class ServerBootstrap {

	
	public ServerBootstrap(int port) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		MainReactorThread mainReactorThread = new MainReactorThread(8, serverSocketChannel);
		mainReactorThread.start();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
	}
	
}
