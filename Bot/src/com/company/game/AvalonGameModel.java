package com.company.game;

import com.company.Bot.MyBot;
import com.company.Utils.ArrayTools;
import com.company.game.roles.AbstractRole;
import com.company.game.roles.*;

import java.util.*;

public class AvalonGameModel {
    private AbstractWorld world;
    private Player[] players;
    private List<Player> playerList;
    private Phase phase = Phase.INNIT;
    private int kingCounter = 1;
    private AbstractRole[] roles;
    private Player king;
    private Adventure adventure;
    private List<List<String>> allVotes;
    private List<List<String>> allQuestOutcomes;
    private List<String> answerYes;
    private List<String> answerNo;
    private MyBot bot;
    private String channelName;

    public void setBot(MyBot bot) {
        this.bot = bot;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void resetAdventure() {
        this.adventure = null;
    }

    public AvalonGameModel(AbstractWorld world, int amountPlayers) {
        this.world = world;
        this.players = new Player[amountPlayers];
        this.roles = new AbstractRole[amountPlayers];
        this.allVotes = new LinkedList<>();
        this.allQuestOutcomes = new LinkedList<>();
        this.playerList = new LinkedList<>();
        this.answerYes = new ArrayList<>();
        answerYes.add("YES");
        answerYes.add("Y");
        answerYes.add("JA");
        answerYes.add("J");
        this.answerNo = new ArrayList<>();
        answerNo.add("NO");
        answerNo.add("N");
        answerNo.add("NEJ");
        fillRoles();
    }

    public List<Player> getPlayerList() {
        for (Player p : players) {
            playerList.add(p);
        }
        return playerList;
    }

    public List<List<String>> getAllVotes() {
        return allVotes;
    }

    public AbstractWorld getWorld() {
        return world;
    }

    public Player[] getPlayers() {
        return players;
    }

    public List<List<String>> getAllQuestOutcomes() {
        return allQuestOutcomes;
    }

    public AbstractRole[] getRoles() {
        return roles;
    }

    public void showRoles() {
        String a = "";
        for (AbstractRole r : roles) {
            a += "   |   " + r.getName();
        }
        a += "    |";
        bot.sendMessage(channelName, a);
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getKing() {
        return king;
    }

    public int getKingCounter() {
        return this.kingCounter;
    }

    public Adventure getAdventure() {
        return adventure;
    }

    public void resetKingCounter() {
        this.kingCounter = 1;
    }

    public void incKingCounter() {
        if (kingCounter < 5) {
            kingCounter++;
        } else {
            setPhase(Phase.GAMEOVER);
        }
    }

    public void setPhase(Phase phase) {
        switch (phase) {
            case DISCUSSION:
                if (this.phase == Phase.INNIT || this.phase == Phase.VOTE || this.phase == Phase.QUEST) {
                    this.phase = phase;
                }
                break;
            case VOTE:
                if (this.phase == Phase.DISCUSSION) {
                    this.phase = phase;
                }
                break;
            case QUEST:
                if (this.phase == Phase.VOTE) {
                    this.phase = phase;
                    break;
                }
            case ASSASINATION:
                if (this.phase == Phase.QUEST) {
                    this.phase = phase;
                }
                break;
            case GAMEOVER:
                    this.phase = phase;
                    break;
        }
    }

    public void setKing(Player king) {
        this.king = king;
    }

    public void showQuests() {
        bot.sendMessage(channelName, "Quest");
        for (Quest q : world.quests) {
            String a = "Nr. " + q.getNumber() + " is " + q.getStatus() + " and require " + q.getNumberOfKnights() + " knights";
            if (q.getNumberOfFails() == 2) {
                a += " and need two fails";
            }
            if (this.getAdventure() != null && adventure.getQuest() != null && q == adventure.getQuest()) {
                a += " <-- Current Quest";
            }
            bot.sendMessage(channelName, a);
        }
    }

    public boolean selectQuest(int quest) {
        if (phase == Phase.DISCUSSION) {
            if (quest - 1 < world.quests.size()) {
                adventure = new Adventure(world.getQuest(quest));
                bot.sendMessage(channelName, "Selected quest Nr. " + quest);
                return true;
            } else {
                System.out.print("Choose a number from 1-5");
                return false;
            }
        }
        bot.sendMessage(channelName, "Can't select Quest, wrong phase!");
        return false;
    }

    public Player selectPlayer(String nick) {
        for (Player p : players) {
            if (p.getNick().equals(nick)) {
                return p;
            }
        }
        return null;
    }

    public String printNominatedPlayersNick() {
        return adventure.getNicksInFellowship();
    }


    public boolean addPlayer(String nick) {
        if (nick.length() == 0) {
            bot.sendMessage(channelName, "nick.length = 0");
            return false;
        }
        System.out.println(nick);
        if (phase == Phase.INNIT) {
            for (int i = 0; i < players.length; i++) {
                if (players[i] == null) {
                    players[i] = new Player(nick);
                    int j = 0;
                    for (Player p : players) {
                        if (p != null)
                            j++;
                    }
                    String a  = "Added " + nick + " - Now " + j;
                    if (j == 1) {
                        a = a + " Player";
                    } else {
                        a = a + " Players";
                    }
                    bot.sendMessage(channelName, a);
                    return true;
                } else if (players[i].getNick().equals(nick)) {
                    bot.sendMessage(channelName, "The name " + nick + " is already taken!");
                    return false;
                }
            }
            bot.sendMessage(channelName, "Player list is full, pick a bigger map!");
            return false;
        }
        bot.sendMessage(channelName, "Wrong phase, can't add players in " + getPhase());
        return false;
    }

    public boolean removePlayer(String nick) {
        if (nick.length() == 0) {
            bot.sendMessage(channelName, "nick.length = 0");
            return false;
        }
        if (phase == Phase.INNIT) {
            int j = 0;
            for (int i = 0; i < players.length; i++) {
                if (players[i] != null) {
                    if (players[i].getNick().equals(nick)) {
                        players[i] = null;
                        for (Player p : players) {
                            if (p != null) {
                                j++;
                            }
                        }
                        String a = "Removed " + nick + " - Now " + j;
                        if (j == 1) {
                            a = a + " Player";
                        } else {
                            a = a + " Players";
                        }
                        bot.sendMessage(channelName, a);

                        return true;
                    } else if (players[i].getNick().equals(nick)) {
                        bot.sendMessage(channelName, "The name " + nick + " is already taken!");

                        return true;
                    } else if (players[i] == null) {
                        bot.sendMessage(channelName, nick + " is not a valid name.");
                        return false;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public void fillRoles() {
        roles[0] = new Merlin();
        roles[1] = new Assasin();
        int good = 2;
        int evil = 1;
        switch (players.length) {
            case 5 :
                break;
            case 6 :
                good = 3;
                break;
            case 7 :
                good = 3;
                evil = 2;
                break;
            case 8 :
                good = 4;
                evil = 2;
                break;
            case 9 :
                good = 5;
                evil = 2;
                break;
            case 10 :
                good = 5;
                evil = 3;
                break;
        }
        for (int j = 2; j < good + 2; j++) {
            roles[j] = new Knight();
            System.out.println(roles[j].getName() + " added");
        }
        for (int i = (good + 2); i < (evil + good + 2); i++) {
            roles[i] = new Evil();
            System.out.println(roles[i].getName() + " added");
        }
    }

    public boolean isFullPlayers() {
        for (Player p : players) {
            if (p == null) {
                bot.sendMessage(channelName, "Player list not full");
                return false;
            }
        }
        return true;
    }

    public boolean isFullRoles() {
        for (AbstractRole p : roles) {
            if (p == null) {
                bot.sendMessage(channelName, "Role list not full");
                return false;
            }
        }
        return true;
    }

    public void distributeRoles() {
        ArrayTools.shuffleArray(roles);
        for (int i = 0; i < players.length; i++) {
            players[i].setRole(roles[i]);
            bot.sendPrivateMessage(players[i].getNick(), "You are playing as "
                    + roles[i].getName() + " in " + channelName);
        }
    }

    public void selectFirstKing() {
        if (phase == Phase.INNIT) {
            ArrayTools.shuffleArray(players);
            setKing(players[0]);
            bot.sendMessage(channelName, "The first king is " + getKing().getNick()
                    + " - You can now choose quest and add people to it");
        }
    }

    public void nextKing() {
        if (phase == Phase.QUEST || phase == Phase.VOTE) {
            int index = java.util.Arrays.asList(players).indexOf(getKing());
            index = (index + 1) % players.length;
            setKing(players[index]);
            System.out.println(players[index].getNick());
            incKingCounter();
        }
    }

    public enum Phase {
        INNIT, DISCUSSION, VOTE, QUEST, ASSASINATION, GAMEOVER
    }

    public boolean printRoles() {
        bot.sendMessage(channelName, "The current roles are");
        for (int i = 0; i < roles.length; i++) {
            if (roles[i] == null) {
                bot.sendMessage(channelName, "" );
                return false;
            }
            bot.sendMessage(channelName, roles[i].getName());
        }
        bot.sendMessage(channelName, "");
        return true;
    }

    public boolean addMemberToAdventure(String s) {
        if (this.adventure != null) {
            Player p = selectPlayer(s);
            if (p == null) {
                bot.sendMessage(channelName, "Found no player with the nick : " + s);
                return false;
            }
            if (!adventure.getFellowship().contains(p)
                    && adventure.getFellowship().size() < adventure.getQuest().getNumberOfKnights()) {
                adventure.addMember(p);
                bot.sendMessage(channelName, "Added " + s + " to quest  -  Now " + adventure.getFellowship().size() + " players");
                return true;
            }
            bot.sendMessage(channelName, "Could not add " + s + " to the quest");
            return false;
        }
        bot.sendMessage(channelName, "Quest not initiated");
        return false;
    }
    public boolean removeMemberFromAdventure(String s) {
        if (phase == Phase.DISCUSSION && adventure.getFellowship().contains(selectPlayer(s))) {
            Player p = selectPlayer(s);
            adventure.removeMember(p);
            bot.sendMessage(channelName, "Added " + p.getNick() + " to quest");
            return true;
        }
        return false;
    }


    public boolean addSpecialRole(String role) {
        if (phase == Phase.INNIT) {
            AbstractRole newRole = selectRole(role);
            for (int i = 2; i < roles.length; i++) {
                if (newRole.getClass() == Percival.class && roles[i].getClass() == Knight.class) {
                    roles[i] = newRole;
                    showRoles();
                    return true;
                } else if (roles[i].getClass() == Evil.class) {
                    roles[i] = newRole;
                    showRoles();
                    return true;
                }
            }
            bot.sendMessage(channelName, "Remove a special role first to add " + role);
            showRoles();
        }
        return false;
    }

    private AbstractRole selectRole(String role) {

        switch (role.toUpperCase()) {
            case "MORGANA":
                return new Morgana();
            case "MORDRED":
                return new Mordred();
            case "OBERON":
                return new Mordred();
            case "PERCIVAL":
                return new Percival();
            default:
                bot.sendMessage(channelName, "The role" + role + "could not be added");
        }
        return null;
    }
    public void resetRole(String role){
        AbstractRole roleToRemove = selectRole(role);
        for (int i = 2; i < roles.length; i++) {
            if (roleToRemove.isSeeMerlin() && roles[i].isSeeMerlin() ) {
                roles[i] = new Knight();
                showRoles();
            } else if (roleToRemove.isSeeEvil() && roles[i].getClass() == roleToRemove.getClass()) {
                roles[i] = new Evil();
                showRoles();
            }
        }
    }
    public boolean askIfVoteSuccess(List<String> votesIn) {
        if (phase == Phase.VOTE) {
            List<String>votes = new ArrayList<>();
            votes.add("Nominated by " + king.getNick());
            votes.add(showQuestsMini());
            int success = 0;
            for (String b : votesIn) {
                votes.add(b);
                if (b.trim().toUpperCase().contains("YES")) {
                    success++;

                } else if (b.trim().toUpperCase().contains("NO")) {
                    success--;
                }
            }

            allVotes.add(votes);
            printResults(votes);
            return success > 0;
        }
        bot.sendMessage(channelName, "Fel i askIfVoteSuccess");
        return false;
    }
    public void printResults(List<String> list) {
        for (String s : list) {
            bot.sendMessage(channelName, s);
        }
    }
    public String printListTo(List<String> list) {
        String a = "";
        for (String s : list) {
            a += s;
        }
        return a;
    }
    public String showQuestsMini() {
        String questStatus = "";
        for (Quest q : world.getQuests()) {
            if (adventure.getQuest() == q) {
                questStatus += ">[ ]< ";
            } else {
                switch (q.getStatus()) {
                    case AVAILABLE:
                        questStatus += " [ ]  ";
                        break;
                    case SUCCEEDED:
                        questStatus += " [O]  ";
                        break;
                    case FAILED:
                        questStatus +=" [X]  ";
                }
            }
        }
        return questStatus;
    }
    public List<Player> getNominees() {
        return adventure.getFellowship();
    }

    public boolean getQuestResults(List<String> s) {
        List<String> questVotes = s;
        int fails = 0;
        for (String a : questVotes) {
            if (a.toUpperCase().contains("FAIL")) {
                fails++;
            }
        }
        if (fails >= adventure.getQuest().getNumberOfFails()) {
            adventure.getQuest().setFail();
        } else {
            adventure.getQuest().setSucceeded();
        }
        Collections.shuffle(questVotes);
        questVotes.add(0, "Quest nr " + adventure.getQuest().getNumber() + " with " + printNominatedPlayersNick() + "had outcome:");
        printResults(questVotes);
        allQuestOutcomes.add(questVotes);
        return fails >= adventure.getQuest().getNumberOfFails();
    }

    public boolean isPlayer(String nick) {
        boolean isPlayer = false;
        for (Player p : playerList) {
            if (p.getNick().equals(nick)) {
                isPlayer = true;
            }
        }
        return isPlayer;
    }
    public String getAssassin() {
        String s = "";
        for (Player p : players) {
            if (p.getRole().getName().equals("Assasin")) {
                s = p.getNick();
            }
        }
        return s;
    }

    public boolean gameOver() {
        setPhase(Phase.GAMEOVER);
        return this.phase == Phase.GAMEOVER;
    }
}
