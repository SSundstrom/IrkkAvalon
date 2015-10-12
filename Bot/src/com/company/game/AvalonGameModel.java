package com.company.game;

import com.company.Utils.ArrayTools;
import com.company.game.roles.AbstractRole;
import com.company.game.roles.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;

public class AvalonGameModel {
    private AbstractWorld world;
    private Player[] players;
    private List<Player> playerList;
    private Phase phase = Phase.INNIT;


    private int  nmbrOfInitPlayers, kingCounter = 1;
    private AbstractRole[] roles;
    private Player king;
    private Adventure adventure;
    private List<List<String>> allVotes;
    private List<List<String>> allQuestOutcomes;
    private List<String> answerYes;
    private List<String> answerNo;

    public AvalonGameModel(AbstractWorld world, int amountPlayers) {
        this.world = world;
        this.players = new Player[amountPlayers];
        this.roles = new AbstractRole[amountPlayers];
        this.allVotes = new LinkedList<>();
        this.allQuestOutcomes = new LinkedList<>();
        this.playerList = new LinkedList<>();
        this.nmbrOfInitPlayers = 0;
        this.answerYes = new ArrayList<>();
        answerYes.add("YES");answerYes.add("Y");answerYes.add("JA");answerYes.add("J");
        this.answerNo = new ArrayList<>();
        answerNo.add("NO");answerNo.add("N");answerNo.add("NEJ");
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
        for (AbstractRole r : roles) {
            System.out.print("   |   " + r.getName());
        }
        System.out.println("    |");
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
                if (this.phase == Phase.ASSASINATION || this.phase == Phase.QUEST) {
                    this.phase = phase;
                    break;
                }
        }
    }

    public void setKing(Player king) {
        this.king = king;
    }

    public void showQuests() {
        System.out.println("Quest");
        for (Quest q : world.quests) {
            System.out.print("Nr. " + q.getNumber() + " is " + q.getStatus() + " and require " + q.getNumberOfKnights() + " knights");
            if (q.getNumberOfFails() == 2) {
                System.out.print(" and need two fails");
            }
            if (this.getAdventure() != null && adventure.getQuest() != null && q == adventure.getQuest()) {
                System.out.println(" <-- Current Quest");
            } else {
                System.out.println();
            }
        }
    }

    public boolean selectQuest(int quest) {
        if (phase == Phase.DISCUSSION) {
            if (quest - 1 < world.quests.size()) {
                adventure = new Adventure(world.getQuest(quest));
                System.out.println("Selected quest Nr. " + quest);
                return true;
            }
            System.out.print("Choose a number from 1-5");
            return false;
        }
        System.out.println("Can't select Quest, wrong phase!");
        return false;
    }

    public Player selectPlayer(String nick) {
        for (Player p : players) {
            if (p.getNick().equals(nick)) {
                return p;
            }
        }
        System.out.println("Found no player with the nick : " + nick);
        return selectPlayer(askPlayer());
    }

    public void printNominatedPlayersNick() {
        adventure.getNicksInFellowship();
    }


    public boolean addPlayer(String nick) {
        if (phase == Phase.INNIT) {
            if(!nick.equals("")) {
                if(Character.isLetter(nick.charAt(0))){



                for (int i = 0; i < players.length; i++) {
                    if (players[i] == null) {
                        players[i] = new Player(nick);

                        nmbrOfInitPlayers++;

                        System.out.print("Added " + nick + " - Now " + nmbrOfInitPlayers);
                        if (nmbrOfInitPlayers == 1) {
                            System.out.println(" Player");
                        } else {
                            System.out.println(" Players");
                        }
                        return true;

                        //I
                    } else if (players[i].getNick().toLowerCase().equals(nick.toLowerCase())) {
                        System.out.println("The name " + nick + " is already taken!");
                        return false;
                    }
                }
                System.out.println("Player list is full, pick a bigger map!");
                return false;
                }
                System.out.println("Your name must start with a letter");
                return false;
            }
            System.out.println("Names can't be empty.");
            return false;
        }
        System.out.println("Wrong phase, can't add players in " + getPhase());
        return false;
    }

    public boolean removePlayer(String nick) {
        if (phase == Phase.INNIT) {
            for (int i = 0; i < players.length; i++) {
                if (players[i].getNick() == nick) {
                    players[i] = null;
                    int j = 0;
                    for (Player p : players) {
                        if (p != null)
                            j++;
                    }
                    System.out.print("Removed " + nick + " - Now " + j);
                    if (j == 1) {
                        System.out.println(" Player");
                    } else {
                        System.out.println(" Players");
                    }
                    return true;
                } else if (players[i].getNick() == nick) {
                    System.out.println("The name " + nick + " is already taken!");

                    return true;
                } else if (players[i] == null) {
                    System.out.println(nick + " is not a valid name.");
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    public void fillRoles() {
        roles[0] = new Merlin();
        System.out.println(roles[0].getName() + " added");
        roles[1] = new Assasin();
        System.out.println(roles[1].getName() + " added");
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
                System.out.println("Player list not full");
                return false;
            }
        }
        return true;
    }

    public boolean isFullRoles() {
        for (AbstractRole p : roles) {
            if (p == null) {
                System.out.println("Role list not full");
                return false;
            }
        }
        return true;
    }

    public void distributeRoles() {
        ArrayTools.shuffleArray(roles);
        for (int i = 0; i < players.length; i++) {
            players[i].setRole(roles[i]);
            System.out.println("/msg " + players[i].getNick() + " : You are playing as " + roles[i].getName());
        }
    }

    public void selectFirstKing() {
        if (phase == Phase.INNIT) {
            ArrayTools.shuffleArray(players);
            setKing(players[0]);
        }
    }

    public void nextKing() {
        if (phase == Phase.QUEST || phase == Phase.VOTE) {
            int index = java.util.Arrays.asList(players).indexOf(getKing());
            index = (index + 1) % players.length;
            setKing(players[index]);
            incKingCounter();
        }
    }

    public enum Phase {
        INNIT, DISCUSSION, VOTE, QUEST, ASSASINATION, GAMEOVER
    }

    public boolean printRoles() {
        System.out.println("The current roles are");
        for (int i = 0; i < roles.length; i++) {
            if (roles[i] == null) {
                System.out.println();
                return false;
            }
            System.out.println(roles[i].getName());
        }
        System.out.println();
        return true;
    }

    public boolean addMemberToAdventure(String s) {
        if (this.adventure != null) {
            Player p = selectPlayer(s);
            if (!adventure.getFellowship().contains(p)
                    && adventure.getFellowship().size() < adventure.getQuest().getNumberOfKnights()) {
                adventure.addMember(p);
                return true;
            }
            System.out.println("Could not add " + s + " to the fellowship");
            return false;
        }
        System.out.println("Quest not initiated");
        return false;
    }
    public boolean removeMemberFromAdventure(String s) {
        if (phase == Phase.DISCUSSION && adventure.getFellowship().contains(selectPlayer(s))) {
            Player p = selectPlayer(s);
            adventure.removeMember(p);
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
            System.out.println("Remove a special role first to add " + role);
            showRoles();
        }
        return false;
    }

    private static AbstractRole selectRole(String role) {

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
                System.out.println("The role" + role + "could not be added");
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
    public boolean askIfVoteSuccess() {
        if (phase == Phase.VOTE) {
            List<String>votes = new LinkedList<>();
            votes.add("Nominated by " + king.getNick());
            votes.add(showQuestsMini());
            int success = 0;
            for (int i = 0; i < players.length; i++) {
                System.out.println("/msg " + players[i].getNick() + " : " + " Time to vote - Do you want to send ");
                System.out.print("/msg " + players[i].getNick() + " : ");
                printNominatedPlayersNick();
                System.out.println("/msg " + players[i].getNick() + " :  on quest Nr. " + adventure.getQuest().getNumber() + "? Yes/No");
                String answer;
                boolean hasAnswer=false;
                while(!hasAnswer) {

                    answer = askPlayer();

                    switch (answer.toLowerCase()){

                        case "yes":
                        case "ja":
                        case "y":
                        case "j":
                            hasAnswer=true;
                            success++;
                            votes.add(players[i].getNick() + " voted\tYES");
                            break;
                        case "no":
                        case "n":
                        case "nej":
                            hasAnswer=true;
                            success--;
                            votes.add(players[i].getNick() + " voted\tNO");
                            break;
                        default:
                            break;
                    }
                }
            }
            allVotes.add(votes);
            printResults(votes);
            return success > 0;
        }
        System.out.println("Fel i askIfVoteSuccess");
        return false;
    }
    public String askPlayer() {
        Scanner in = new Scanner(System.in);
        String ans = in.next();
        return ans;
    }
    public void printResults(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
    public void printList(List<String> list) {
        for (String s : list) {
            System.out.print(s);
        }
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


    //Returns the amount of players that are initiated,
    public int getNmbrOfInitPlayers() {
        return nmbrOfInitPlayers;
    }

    public boolean getQuestResults() {
        List<String>questOutcome = new LinkedList<>();

        int failes = 0;
        for (Player p : getNominees()) {
            if (p.getRole().isGetToVoteFail()) {
                System.out.print("/msg " + p.getNick() + " : \tWant to vote Fail or Success?");
                String answer;
                do {
                    answer = askPlayer();
                } while (!answer.toUpperCase().equals("FAIL") && !answer.toUpperCase().equals("SUCCESS"));
                if (answer.toUpperCase().equals("FAIL")) {
                    failes++;
                    questOutcome.add("Fail\t");
                } else {
                    questOutcome.add("Success\t");
                }
            } else {
                System.out.println("/msg " + p.getNick() + " : \tYou can only vote success. Write yes when ready");
                questOutcome.add("Success\t");
                String answer;
                do {
                    answer = askPlayer();
                } while (!answer.toUpperCase().equals("YES"));

            }
        }
        if (failes >= adventure.getQuest().getNumberOfFails()) {
            adventure.getQuest().setFail();
        } else {
            adventure.getQuest().setSucceeded();
        }
        Collections.shuffle(questOutcome);
        questOutcome.add(0, "Quest nr " + adventure.getQuest().getNumber() + " outcome was");
        printResults(questOutcome);
        allQuestOutcomes.add(questOutcome);
        return failes >= adventure.getQuest().getNumberOfFails();
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
    public boolean gameOver() {
        return this.phase == Phase.GAMEOVER;
    }
}
