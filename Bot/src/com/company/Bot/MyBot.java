package com.company.Bot;

import com.company.game.AvalonGameController;
import com.company.game.AvalonGameFactory;
import com.fridenmf.ircframework.core.events.*;
import com.fridenmf.ircframework.core.extendable.*;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends IrcPlugin {
    private List<AvalonGameController> listAGC;
    private static int gameNbr;

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
        gameNbr = 0;
        listAGC = new ArrayList<>();

    }

    @Override
    public void onConnect(OnConnectEvent e) {
        super.onConnect(e);
        join("#Irkkalon");
    }

    @Override
    public void onMessage(OnMessageEvent e) {
        if (e.getChannel().equals("#Irkkalon") && (e.getMessage().toUpperCase().contains("HEJ") && e.getMessage().toUpperCase().contains("BOTALON"))) {
            sendMessage(e.getChannel(), "Hello " + e.getNick() + "!");
        } else {
            int channelNumber = 0;
            String[] channel = e.getChannel().split("Game");
            try {
                channelNumber = Integer.parseInt(channel[1]);
            } catch (NumberFormatException e1) {
                sendPrivateMessage(e.getNick(), "Not running game in this channel");
            }
            if (checkGameNbr(channelNumber) == null) {
                sendPrivateMessage(e.getNick(), "The current game number is unavailable, try again");
            } else {
                AvalonGameController AGC = checkGameNbr(channelNumber);
                String message = e.getMessage();
                String nick = e.getNick().toUpperCase();
                String[] tempSplit;
                if (!message.contains("§")) {
                    return;
                }
                if (message.toUpperCase().contains("§START")) {
                    AGC.startGame(e.getNick());
                } else if (message.toLowerCase().contains("-p")) {
                    tempSplit = message.split("-p");

                    if (tempSplit.length < 2) {
                        sendPrivateMessage(nick, "Need to enter a nick");
                    } else if (tempSplit.length > 2) {
                        sendPrivateMessage(nick, "Can currently only take one nick att the time");
                    } else {

                        if (tempSplit[0].toUpperCase().contains("§ADD")) {

                            for (int i = 1; i < tempSplit.length; i++) {
                                String a = tempSplit[i].trim();
                                AGC.addPlayer(a, e.getNick());
                            }

                        } else if (tempSplit[0].toUpperCase().contains("§RM")) {

                            for (int i = 1; i < tempSplit.length; i++) {
                                String a = tempSplit[i].trim();
                                AGC.removePlayer(a, e.getNick());
                            }
                        }
                    }
                } else if (message.toLowerCase().contains("-r")) {
                    tempSplit = message.split("-r");

                    if (tempSplit.length < 2) {
                        sendPrivateMessage(nick, "Need to enter a role");
                    } else if (tempSplit.length > 2) {
                        sendPrivateMessage(nick, "Can currently only take one role att the time");
                    } else {

                        if (tempSplit[0].toUpperCase().contains("§ADD")) {

                            for (int i = 1; i < tempSplit.length; i++) {
                                String a = tempSplit[i].trim();
                                AGC.addRole(a, e.getNick());
                            }

                        } else if (tempSplit[0].toUpperCase().contains("§RM")) {

                            for (int i = 1; i < tempSplit.length; i++) {
                                String a = tempSplit[i].trim();
                                AGC.removeRole(a, e.getNick());
                            }
                        }
                    }
                } else if (message.toUpperCase().contains("§SHOW")) {
//                      ex. Syntax = §SHOW V 3 = ger 3e Vote resultaten
                    String[] s = message.split(" ");
                    AGC.printVoteQuest(s, e.getNick());

                } else if (message.toUpperCase().contains("§QUEST")) {
//                    ex. Syntax = §Quest 3 = ger quest nr 3
                    tempSplit = message.split(" ");
                    if (tempSplit.length < 2) {
                        sendPrivateMessage(nick, "Need to enter a quest number");
                    } else if (tempSplit.length > 2) {
                        sendMessage(e.getChannel(), "Need to enter a quest number");
                    } else {
                        String a = tempSplit[1].trim();
                        AGC.selectQuest(a, e.getNick());
                    }
                } else if (message.toUpperCase().contains("§GS")) {
                    AGC.displayGameState();
                } else if (message.toUpperCase().contains("§VOTE") || message.toUpperCase().equals("§V")) {
                    AGC.goToVote(e.getNick());
                } else if (message.toUpperCase().contains("§KING")) {
                    AGC.currentKing();
                } else if (message.toUpperCase().contains("§TELLINFO")) {
                    AGC.messageInfoToPlayersCheck(e.getNick());
                } else if (message.toUpperCase().contains("§SHOOT")) {
                    String[] s = message.split(" ");
                    AGC.assassinate(s[1], e.getNick());
                } else if (message.toUpperCase().contains("§SLACKER")) {
                    AGC.getNotVoted();
                } else if (message.toUpperCase().contains("§ADMIN")) {
                    AGC.admin(e.getNick(), e.getMessage());
                } else if (message.toUpperCase().contains("§ENDGAME")) {
                    if (AGC.gameOver(e.getNick())) {
                        quit(e.getChannel());
                        removeAGCFromList(AGC);
                    }
                } else if (message.toUpperCase().contains("§HELP") || message.toUpperCase().equals("§H")) {
                    AGC.tellCommandos(e.getNick());
                } else {
                    sendPrivateMessage(e.getNick(), "Type §Help in your #AvalonGame channel for commands");
                }
            }
        }
    }

    public AvalonGameController checkGameNbr(int channelNumber) {
        for (AvalonGameController AGC : listAGC) {
            if (AGC.getGameNbr() == channelNumber) {
                return AGC;
            }
        }
        return null;
    }



    /**
     * Auto-generated code, do not ever ever ever change anything in this method.
     * @author neon the one and only
     */

    @Override
    public void onPrivateMessage(OnPrivateMessageEvent e) {
        String[] message;
        String privateMessage = e.getMessage().toUpperCase();
        if (privateMessage.contains("§NEWGAME")) {
            message = e.getMessage().split(" ");
            int messageInt;
            if (message.length < 2) {
                sendPrivateMessage(e.getNick(), "Need to be a number from 5 to 10");
            } else {
                try {
                    messageInt = Integer.parseInt(message[1]);
                } catch (NumberFormatException s) {
                    messageInt = 0;
                }
                if (messageInt < 5 || messageInt > 10) {
                    sendPrivateMessage(e.getNick(), "Need to be between 5 and 10");
                } else {
                    gameNbr++;
                    listAGC.add(new AvalonGameController(gameNbr, this, AvalonGameFactory.createNewGame(messageInt), e.getNick()));
                    sendPrivateMessage(e.getNick(), "Started " + messageInt + " player game with game number " + gameNbr);
                    invite("AvalonGame" + gameNbr, e.getNick());

                }
            }
        } else if (privateMessage.toUpperCase().contains("§VOTE")) {
            String[] tempSplit = privateMessage.toUpperCase().split(" ");
            if (tempSplit.length != 3) {
                sendPrivateMessage(e.getNick(), "Wrongful input - §Vote [your answer] [game number]");
            } else {
                for (String s : tempSplit) {
                    try {
                        int i = Integer.parseInt(tempSplit[2]);
                        if (i != 0) {
                            AvalonGameController AGC = checkGameNbr(i);
                            if (AGC == null) {
                                sendPrivateMessage(e.getNick(), "No game with that number active");
                                return;
                            }
                            if (s.trim().equals("YES") || s.trim().equals("NO")) {
                                String a = e.getNick() + " voted " + s;
                                AGC.votes(a);
                            } else if (s.trim().equals("S") || s.trim().equals("F")) {
                                AGC.questAnswer(s, e.getNick());
                            }
                        }
                    } catch (NumberFormatException d) {
                        System.out.println("Parse went wrong");
                    }
                }
            }
        } else {
            sendPrivateMessage(e.getNick(), "Hello " + e.getNick() + ", these are the \"working\" commandos");
            sendPrivateMessage(e.getNick(), "§NewGame -\"Number of players\"              - Starts a new game");
            sendPrivateMessage(e.getNick(), "§Rules");

        }
    }
    public void removeAGCFromList(AvalonGameController agc) {
        listAGC.remove(agc);
    }
}

