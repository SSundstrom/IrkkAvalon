package com.company.game;

import com.company.Bot.MyBot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AvalonGameController {
    private AvalonGameModel game;
    private MyBot bot;
    private int gameNbr;
    private String channalName;
    private List<String> voteList;
    private List<String> questList;
    private List<String> answeredList;
    private String gameOwner;
    private List<String> playersVoted;

    public AvalonGameController(int gameNbr, MyBot bot, AvalonGameModel game, String gameOwner) {
        this.game = game;
        this.bot = bot;
        this.gameNbr = gameNbr;
        this.channalName = "#AvalonGame" + gameNbr;
        this.voteList = new ArrayList<>();
        this.answeredList = new ArrayList<>();
        this.questList = new ArrayList<>();
        this.gameOwner = gameOwner;
        this.playersVoted = new ArrayList<>();
        game.setBot(bot);
        game.setChannelName(channalName);
        bot.join(channalName);
//        bot.sendMessage("#Irkkalon", "New " + game.getPlayers().length + " player game started");
    }

    public AvalonGameModel getGame() {
        return game;
    }

    public int getGameNbr() {
        return gameNbr;
    }

    public void addPlayer(String nick, String king) {
        if (game.getPhase() == AvalonGameModel.Phase.INNIT) {
            if (gameOwner.equals(king)) {
                game.addPlayer(nick);
            }
        } else if (!king.equals(game.getKing().getNick())) {
            System.out.println(king + " is not king");
        } else {
            game.addMemberToAdventure(nick);
        }
    }

    public void removePlayer(String nick, String king) {
        if (game.getPhase() == AvalonGameModel.Phase.INNIT) {
            if (gameOwner.equals(king)) {
                game.removePlayer(nick);
            }
        }else if (!king.equals(game.getKing().getNick())) {
            System.out.println(king + " is not king");

        } else {
            game.removeMemberFromAdventure(nick);
        }
    }

    public void addRole(String role, String owner) {
        if (gameOwner.equals(owner)) {
            game.addSpecialRole(role);
        }
    }
    public void removeRole(String role, String owner) {
        if (gameOwner.equals(owner)) {
            game.resetRole(role);
        }
    }
    public void startGame(String nick) {
        if (game.getPhase() == AvalonGameModel.Phase.INNIT) {
            if (!gameOwner.equals(nick)) {
                System.out.println(nick + " is not the Owner");
                System.out.println(gameOwner + "is the Owner");
                return;
            }
            if (game.isFullPlayers() && game.isFullRoles()) {
                game.distributeRoles();
                messageInfoToPlayers();
                game.selectFirstKing();
                game.setPhase(AvalonGameModel.Phase.DISCUSSION);
                bot.sendMessage(channalName, "--- Started game ---");
                bot.sendMessage(channalName, "*** Write §Help to get the commandos you can currently use ***");
                displayGameState();
            } else {
                bot.sendMessage(channalName, "Did not start game");
            }
        }
    }

    public boolean selectQuest(String sQuest, String king) {
        if (!king.equals(game.getKing().getNick())) {
            System.out.println(king + " is not current king\nThe king is " + game.getKing().getNick());
            return false;
        }
        try {
            int iQuest = Integer.parseInt(sQuest);
            return game.selectQuest(iQuest);
        } catch (IllegalArgumentException s) {
            bot.sendMessage(channalName, "Not a valid quest number");
            return false;
        }
    }

    public void goToVote(String nick) {
        if (nick.equals(game.getKing().getNick())) {
            if (game.getPhase() == AvalonGameModel.Phase.DISCUSSION) {
                if (game.getAdventure().getQuest().getStatus() == Quest.Status.AVAILABLE) {
                    if (game.getAdventure().getFellowship().size() == game.getAdventure().getQuest().getNumberOfKnights()) {
                        game.setPhase(AvalonGameModel.Phase.VOTE);
                        playersVoted.clear();
                        bot.sendMessage(channalName, "--- Time to Vote ---");
                        bot.sendMessage(channalName, "Do you want to send " + game.printNominatedPlayersNick() + " on quest Nr. "
                                + game.getAdventure().getQuest().getNumber() + "? Write \"/msg Botalon §Vote [yes/no] [" + gameNbr + "]\" to Botalon");

                    } else {
                        bot.sendMessage(channalName, "Not enough players nominated for quest");
                    }
                } else {
                    bot.sendMessage(channalName, "Quest not available");
                }
            } else {
                bot.sendMessage(channalName, "Wrong phase");
            }
        }
    }

    public void outcome() {

        if (game.getPhase() == AvalonGameModel.Phase.QUEST) {
            game.getQuestResults(questList);
            answeredList.clear();
            questList.clear();
            if (game.getWorld().getSuccedeedQuests().size() == 3) {
                game.setPhase(AvalonGameModel.Phase.ASSASINATION);
                bot.sendMessage(channalName, "Its time to find Merlin! " + game.getAssassin() + ", you have the last word. Type §Shoot [nick] to kill that person");
            } else if (game.getWorld().getFailedQuests().size() == 3) {
                game.setPhase(AvalonGameModel.Phase.GAMEOVER);
                bot.sendMessage(channalName, "--- The Evil Win ---");
                gameOver(gameOwner);
                bot.removeAGCFromList(this);
                bot.quit(channalName);
            } else {
                game.nextKing();
                game.resetKingCounter();
                game.setPhase(AvalonGameModel.Phase.DISCUSSION);
                game.resetAdventure();
                bot.sendMessage(channalName, game.getKing().getNick() + " is the new king");
                displayGameState();
                System.out.println(game.getPhase());
            }

        }

        if (game.getPhase() == AvalonGameModel.Phase.VOTE) {
            if (voteList.size() == game.getPlayers().length) {
                if (game.askIfVoteSuccess(voteList)) {
                    voteList.clear();
                    bot.sendMessage(channalName, "--- Approve ---");
                    game.setPhase(AvalonGameModel.Phase.QUEST);
                    startQuestPhase();
                } else {
                    voteList.clear();
                    game.nextKing();
                    bot.sendMessage(channalName, "- Fail - " + game.getKing().getNick() + " is the new king -");
                    game.setPhase(AvalonGameModel.Phase.DISCUSSION);
                    game.resetAdventure();
                    displayGameState();
                }
            }

        }

    }

    public void assassinate(String a, String nick) {
        if (game.getPhase() == AvalonGameModel.Phase.ASSASINATION) {
            if (!nick.equals(game.getAssassin())) bot.sendPrivateMessage(nick, "You are not the Assassin");
            if (game.selectPlayer(a).getRole().getName().equals("Merlin")) {
                bot.sendMessage(channalName, "--- The Evil Win ---");
            } else {
                bot.sendMessage(channalName, "--- The Good Win ---");
            }
            gameOver(gameOwner);
            bot.removeAGCFromList(this);
            bot.quit(channalName);
            game.setPhase(AvalonGameModel.Phase.GAMEOVER);
        }
    }

    public void currentKing() {
        String players = "";
        for (Player p : game.getPlayers()) {
            if (p == game.getKing()) {
                players = players + " | " + "-->" + p.getNick() + "<--";
            } else {
                players = players + " | " + p.getNick();
            }
        }
        bot.sendMessage(channalName, "King Counter : " + game.getKingCounter());
        bot.sendMessage(channalName, players);
    }

    public void displayGameState() {
        bot.sendMessage(channalName, "King Counter: " + game.getKingCounter() + ".");
        game.showQuests();
        if (game.getAdventure() != null) {
            bot.sendMessage(channalName, "Players currently nominated for quest: ");
            game.printNominatedPlayersNick();
        }


    }

    public void showRoles() {
        game.showRoles();
    }

    public void printVoteQuest(String[] s, String nick) {
        System.out.println("started printVoteQuest() in AGC");
        System.out.println("getAllVotes.size = " + game.getAllVotes().size());

        int voteNbr = 0;
        try {
            voteNbr = Integer.parseInt(s[2]);
        } catch (Exception e) {
            bot.sendPrivateMessage(nick, "Not a number, try again");
        }
        if (voteNbr !=0) {
            if (s[1].toUpperCase().contains("V") && voteNbr < game.getAllVotes().size()) {
                game.printResults(game.getAllVotes().get(voteNbr - 1));
            }

            if (s[1].toUpperCase().contains("Q") && voteNbr < game.getAllVotes().size()) {
                game.printResults(game.getAllQuestOutcomes().get(voteNbr - 1));
            }
        }
    }
    public void messageInfoToPlayersCheck(String nick) {
        if (game.getKing().getNick().equals(nick)) {
            messageInfoToPlayers();
        }
    }

    public void messageInfoToPlayers() {
        List<String> evil = new LinkedList<>();
        List<String> evilWithoutMordred = new LinkedList<>();
        List<String> merlinOrMorgana = new LinkedList<>();
        for (Player p : game.getPlayers()) {
            if (p.getRole().isSeenAsEvil()) {
                evilWithoutMordred.add(p.getNick() + "\t");
            }
            if (p.getRole().isGetToVoteFail()) {
                evil.add(p.getNick() + "\t");
            }
            if (p.getRole().isSeenAsMerlin()) {
                merlinOrMorgana.add(p.getNick());
            }
        }

        for (Player p : game.getPlayers()) {

            if (p.getRole().isSeeEvil() && !p.getRole().getName().equals("Merlin")) {
                bot.sendPrivateMessage(p.getNick(), "The evil players are  -  " + game.printListTo(evil));
            }
            if (p.getRole().getName().equals("Merlin")) {
                bot.sendPrivateMessage(p.getNick(), "The evil players are  -  " + game.printListTo(evilWithoutMordred));

            }
            if (p.getRole().isSeeMerlin()) {
                if (merlinOrMorgana.size() < 2) {
                    bot.sendPrivateMessage(p.getNick(), "Merlin is " + merlinOrMorgana.get(0));
                } else {
                    bot.sendPrivateMessage(p.getNick(), merlinOrMorgana.get(0) + " or " + merlinOrMorgana.get(1) + " is Merlin, the other Morgana");
                }
            }


        }
    }

    public void tellCommandos(String nick) {
        bot.sendPrivateMessage(nick, "If there are brackets remove them");
        String response = "";
        switch (game.getPhase()) {
            case INNIT:
                if (nick.equals(gameOwner)) {
                    response = "§Add -p [player nick] - will add player to game |*| " +
                            "§RM -p [player nick] - will remove player from game |*| " +
                            "§Add -r [Role name] - will remove a \"common\" role add [role] to game |*| " +
                            "§RM -r [Role name] - will remove [role] from game and add \"common\" role |*| " +
                            "§Start - will start the game if the player slots are filled.";

                } else {
                    response = "These is currently nothing you can do";
                }
                break;
            case DISCUSSION:
                response = "§GS - will show the current Game State |*| " +
                        "§King - will show the current king |*| " +
                        "§SHOW [V or Q] [number] - will show you the result of a previous [V]ote or [Q]uest";
                if (game.getKing().getNick().equals(nick)) {
                    response = response + " |*| §Quest [Quest number] - will select the quest with the number you chose |*| " +
                            "§ADD [nick] - will add player to the Quest |*| " + "§RM [nick] - will remove player from quest |*| " +
                            "§Vote - when you have enough players for the quest this will go to vote |*| " +
                            "§TellInfo - will give everyone the information they got from the start again";
                }
                break;
            case VOTE:
                response = "§Vote [yes or no] [game number] - will let you vote yes or no to a quest. The game number is the number in the channel name";
                break;
            case ASSASINATION:
                if (game.getAssassin().equals(nick)) {
                    response = "§shoot [nick] - will kill the players whose nick you wrote";
                } else {
                    response = "These is currently nothing you can do";
                }
                break;
            case QUEST:
                if (game.getNominees().contains(game.selectPlayer(nick))) {
                    response = "To vote type §vote [S or F] [game number] - type S for Success and F for Fail. The game number is the number in the channel name";
                } else {
                    response = "These is currently nothing you can do";
                }
                break;
            default :
                System.out.println("Didnt get a correct phase for help.");
                break;
        }
        bot.sendPrivateMessage(nick, response);
    }

    public void votes(String a) {
        String[] tempSplit;
        tempSplit = a.split(" ");
        if (game.getPhase() != AvalonGameModel.Phase.VOTE) {
            bot.sendPrivateMessage(tempSplit[0], "Wrong phase, can't vote right now");
            return;
        }
        boolean found = false;

        for (Player p : game.getPlayers()) {
            if (tempSplit[0].equals(p.getNick())) {
                found = true;
            }
        }

        if (!found) return;
        String changeVote = null;
        for (String b : voteList) {
            String[] tempSplit2 = b.split(" ");
            if (tempSplit2[0].equals(tempSplit[0])) {
                changeVote = b;
            }
        }
        if (changeVote != null) {
            voteList.remove(changeVote);
        }

        bot.sendMessage(channalName, "Vote from " + tempSplit[0] + " registered");
        voteList.add(a);
        playersVoted.add(tempSplit[0]);
        if(voteList.size() == game.getPlayers().length) {
            bot.sendMessage(channalName, "All players have voted");
            outcome();
        }
    }

    public boolean questAnswer(String a, String nick) {
        if (game.getPhase() != AvalonGameModel.Phase.QUEST) {
            bot.sendPrivateMessage(nick, "Wrong phase, can't vote right now");
            return false;
        }
        if (answeredList.contains(nick)) {
            bot.sendPrivateMessage(nick, "You have already given your answer.");
            return false;
        }
        for (Player p : game.getNominees()) {
            if (p.getNick().equals(nick)) {
                if (a.trim().equals("F") && game.selectPlayer(nick).getRole().isGetToVoteFail()) {
                    questList.add("Fail");
                    bot.sendMessage(channalName, nick + " have made his choice");
                    answeredList.add(nick);
                } else if (a.trim().equals("S")) {
                    questList.add("Success");
                    bot.sendMessage(channalName, nick + " have made his choice");
                    answeredList.add(nick);
                } else {
                    bot.sendPrivateMessage(nick, "Input could not register vote");
                }
            }
        }
        boolean allAnswered = (game.getNominees().size() == questList.size());
        System.out.println("questlist size = " + questList.size());
        System.out.println("getNominees size = " + game.getNominees().size());
        if (allAnswered) {
            outcome();
        }

        return false;
    }

    private void startQuestPhase() {
        for (Player p : game.getNominees()) {
            if (p.getRole().isGetToVoteFail()) {
                bot.sendPrivateMessage(p.getNick(), "Want to vote Fail or Success? Write \"/msg Botalon §vote [F or S] " + gameNbr + "\" to Botalon to answer");
            } else {
                bot.sendPrivateMessage(p.getNick(), "You can only vote success. Write \"/msg Botalon §vote S " + gameNbr + "\" to Botalon to answer");
            }
        }
    }
    public boolean gameOver(String nick) {
        if (gameOwner.equals(nick)) {
            return game.gameOver();
        }
        return false;
    }
    public void admin(String nick, String msg) {
        if (!nick.trim().equals(gameOwner)) {
            bot.sendPrivateMessage(nick, "Only owner can use these");
        }
        try {
            String[] tempMsg = msg.toLowerCase().split(" ");
            switch (tempMsg[1]) {
                case "getphase":
                    bot.sendMessage(channalName, game.getPhase().toString());
                    break;
                case "setphase":
                    setPhase(tempMsg[2]);
                    break;
            }
        } catch (Exception e) {

        }
    }
    public void setPhase(String s) {
        switch (s.trim()) {
            case "quest" :
                game.setPhase(AvalonGameModel.Phase.QUEST);
                break;
            case "vote" :
                game.setPhase(AvalonGameModel.Phase.VOTE);
                break;
            case "disc" :
                game.setPhase(AvalonGameModel.Phase.DISCUSSION);
                break;

        }
    }
    public void getNotVoted() {
        String a = "These people have not yet voted :";
        for (Player p : game.getPlayers()) {
            if (!playersVoted.contains(p.getNick())) {
                a = a + "  " + p.getNick();
            }
        }
    }
}
