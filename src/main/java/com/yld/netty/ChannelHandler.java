package com.yld.netty;

import java.nio.channels.Channel;

/**
 * 
 * @author yuleidian
 * @date 2019年6月25日
 */
public interface ChannelHandler {

	void handle(Channel channel);
	
}
