package com.company;

import com.company.game.AvalonGameController;
import com.company.game.AvalonGameFactory;
import com.fridenmf.ircframework.core.events.*;
import com.fridenmf.ircframework.core.extendable.*;

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
        sendMessage(e.getChannel(), "Hello!");
    }

    @Override
    public void onPrivateMessage(OnPrivateMessageEvent e) {
        String[] message;
        if (e.getMessage().contains("§NewGame")) {
            message = e.getMessage().split(" - ");
            int messageInt = Integer.parseInt(message[1]);
            new AvalonGameController(AvalonGameFactory.createNewGame(messageInt));
        }
    }
}

