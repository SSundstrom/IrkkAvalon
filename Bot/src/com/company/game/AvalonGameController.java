package com.company.game;

import java.util.LinkedList;
import java.util.List;

public class AvalonGameController {
    private AvalonGameModel game;

    public AvalonGameController(AvalonGameModel game) {
        this.game = game;
    }

    public AvalonGameModel getGame() {
        return game;
    }

    public boolean addPlayer(String nick) {
       return game.addPlayer(nick);
    }
    public boolean removePlayer(String nick) {
        return game.removePlayer(nick);
    }
    public void addRole(String role) {
        game.addSpecialRole(role);
    }
    public void removeRole(String role) {
        game.resetRole(role);
    }

    public void startGame() {
        if (game.getPhase() == AvalonGameModel.Phase.INNIT) {
            if (game.isFullPlayers() && game.isFullRoles()) {
                game.distributeRoles();
                messageInfoToPlayers();
                game.selectFirstKing();
                game.setPhase(AvalonGameModel.Phase.DISCUSSION);
                System.out.println("\n--- Started game ---\n");
            } else {
                System.out.println("\nDid not start game\n");
            }
        }
    }

    public boolean selectQuest(String sQuest){
        try {
            int iQuest = Integer.parseInt(sQuest);
            return game.selectQuest(iQuest);
        } catch (IllegalArgumentException s) {
            System.out.println("Not a valid quest number");
            return false;
        }
    }
    public boolean nominatePlayer(String s) {
        if (game.getAdventure() != null && game.getAdventure().getQuest() != null) {
            return game.addMemberToAdventure(s);
        }
        return false;
    }
    public boolean removeNominatedPlayer(String s) {
        return game.removeMemberFromAdventure(s);
    }

    public void goToVote() {
        if (game.getPhase() == AvalonGameModel.Phase.DISCUSSION) {
            if (game.getAdventure().getQuest().getStatus() == Quest.Status.AVAILABLE) {
                if (game.getAdventure().getFellowship().size() == game.getAdventure().getQuest().getNumberOfKnights()) {
                    game.setPhase(AvalonGameModel.Phase.VOTE);
                    System.out.println("\n--- Time to Vote ---\n");
                    if (game.askIfVoteSuccess()) {
                        System.out.println("\n--- Success ---\n");
                        game.setPhase(AvalonGameModel.Phase.QUEST);
                        game.resetKingCounter();
                        questOutcome();
                    } else {
                        game.nextKing();
                        System.out.println("\n--- Fail ---\nTime for a new king\n" + game.getKing().getNick() + " is the new leader");
                        game.setPhase(AvalonGameModel.Phase.DISCUSSION);
                    }
                    game.selectQuest((game.getAdventure().getQuestNumber()%5)+1);
                } else {
                    System.out.println("Not enough players nominated for quest");
                }
            } else {
                System.out.println("Quest not available");
            }
        } else {
            System.out.println("Wrong phase");
        }
    }

    public void questOutcome() {
        if (game.getPhase() == AvalonGameModel.Phase.QUEST) {
            game.getQuestResults();
            if (game.getWorld().getSuccedeedQuests().size() == 3) {
                game.setPhase(AvalonGameModel.Phase.ASSASINATION);
                assassinate();
            } else if (game.getWorld().getFailedQuests().size() == 3) {
                game.setPhase(AvalonGameModel.Phase.GAMEOVER);
                System.out.println("\t--- The Evil Win ---");
            } else {
                game.setPhase(AvalonGameModel.Phase.DISCUSSION);
            }

        }


    }

    public void assassinate() {
        if (game.getPhase() == AvalonGameModel.Phase.ASSASINATION) {
            System.out.println("The good have won!\nUnless the Assassin can find Merlin\ntype Nick to shoot that person");
            String nick;
            do {
                nick = game.askPlayer();
            } while (game.isPlayer(nick));
            if (game.selectPlayer(nick).getRole().getName().equals("Merlin")) {
                System.out.println("\t--- The Evil Win ---");
            } else {
                System.out.println("\t--- The Good Win ---");
            }
            game.setPhase(AvalonGameModel.Phase.GAMEOVER);
        }
    }

    public String currentKing() {
        return game.getKing().getNick();
    }

    public void displayGameState() {
        System.out.println("This is king number " + game.getKingCounter() + ".");
        game.showQuests();
        if (game.getAdventure() != null) {
            System.out.println("Players currently nominated for quest: ");
            game.printNominatedPlayersNick();
        }


    }

    public void showRoles() {
        game.showRoles();
    }

    public void printVote(int voteNbr) {
        game.printResults(game.getAllVotes().get(voteNbr - 1));
    }

    public void printQuest(int questNbr) {
        game.printResults(game.getAllQuestOutcomes().get(questNbr - 1));
    }

    public void messageInfoToPlayers () {
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
            if (p.getRole().isSeeMordred()) {

            }
            if (p.getRole().isSeeEvil() && !p.getRole().getName().equals("Merlin")) {
                System.out.print("/msg " + p.getNick() + " :\tThe evil players are  -  ");
                game.printList(evil);
                System.out.println();
            }
            if (p.getRole().getName().equals("Merlin")) {
                System.out.print("/msg " + p.getNick() + " :\tThe evil players are  -  ");
                game.printList(evilWithoutMordred);
                System.out.println();
            }
            if (p.getRole().isSeeMerlin()) {
                if (merlinOrMorgana.size() < 2) {
                    System.out.println("/msg " + p.getNick() + " :\tMerlin is " + merlinOrMorgana.get(0));
                } else {
                    System.out.println("/msg " + p.getNick() + " :\t" + merlinOrMorgana.get(0) + " or " + merlinOrMorgana.get(1) + " is Merlin, the other Morgana");
                }
            }


        }
    }



}
