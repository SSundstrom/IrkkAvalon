package AvalonGame.ircframework.examples;

import AvalonGame.ircframework.core.events.OnConnectEvent;
import AvalonGame.ircframework.core.events.OnPrivateMessageEvent;
import AvalonGame.ircframework.core.extendable.SimpleBot;

public class HelloSimpleBot extends SimpleBot {
	
	public static void main(String[] arg){
		new HelloSimpleBot("irc.server.org", 6667, "BotNick", "BotUser");
	}

	public HelloSimpleBot(String host, int port, String nick, String user) {
		super(host, port, nick, user);
	}
	
	@Override
	public void onConnect(OnConnectEvent e) {
		join("#test123");
	}
	
	@Override
	public void onPrivateMessage(OnPrivateMessageEvent e) {
		sendPrivateMessage(e.getNick(), "I hear you");
		System.out.println("Got a message from "+e.getNick()+": "+e.getMessage());
	}
}
