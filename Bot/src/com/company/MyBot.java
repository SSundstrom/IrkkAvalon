package com.company;

import com.fridenmf.ircframework.core.events.OnConnectEvent;
import com.fridenmf.ircframework.core.events.OnMessageEvent;
import com.fridenmf.ircframework.core.extendable.IrcBot;
import com.fridenmf.ircframework.core.extendable.IrcPlugin;

public class MyBot extends IrcPlugin {

	/* This implementation is a bit hacky, but it does the trick */

    private class PluginBot extends IrcBot {
        public PluginBot(String host, int port, String nick, String user, String description, IrcPlugin ip){
            addPlugin(ip);
            connect(host, port, nick, user, description);
        }
    }

    public MyBot(String host, int port, String nick, String user) {
        this(host, port, nick, user, null);
    }

    public MyBot(String host, int port, String nick, String user, String description) {
        super(null, null);
        pluginName = "IrcPlugin";
        ircBot = new PluginBot(host, port, nick, user, description, this);
    }

    @Override
    public void onConnect(OnConnectEvent e) {
        super.onConnect(e);
        join("#Irkkalon");
    }

    @Override
    public void onMessage(OnMessageEvent e) {
        super.onMessage(e);
        sendMessage(e.getChannel(),"Hello!");
    }
}

