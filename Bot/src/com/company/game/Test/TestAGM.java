package com.company.game.Test;

import com.company.game.*;

public class TestAGM {

    public static void main(String[] args) {

        AvalonGameModel worldA = AvalonGameFactory.createNewGame(5);

        System.out.println("Is the roles list full? " + worldA.isFullRoles());

        System.out.println();

//        worldA.printRoles();
//        worldA.addSpecialRole();
//        worldA.addSpecialRole(new Percival());
//        worldA.addSpecialRole(new Morgana());
//        worldA.addSpecialRole(new Knight());
//        System.out.println();
//        worldA.addSpecialRole(new Knight());

        System.out.println();


        worldA.addPlayer("P1");
        worldA.addPlayer("P2");
        worldA.addPlayer("P3");
        worldA.addPlayer("P4");
        worldA.addPlayer("P5");
        worldA.addPlayer("P5");
        worldA.addPlayer("P6");

        System.out.println();

        System.out.println("Is the player list full ? " + worldA.isFullPlayers());

        System.out.println();

        System.out.println("Startup phase = " + worldA.getPhase());
        worldA.setPhase(AvalonGameModel.Phase.ASSASINATION);
        System.out.println("After trying to switch to assassination, phase = " + worldA.getPhase());

        System.out.println();

        worldA.selectFirstKing();

        System.out.println("The king is " + worldA.getKing().getNick());

        System.out.println();

        worldA.showQuests();

        System.out.println();
        worldA.selectQuest(1);
        System.out.println("Current quest number  = " + worldA.getAdventure());

        System.out.println();

        System.out.println("Got " + worldA.selectPlayer("P2").getNick());

        System.out.println();

        worldA.addMemberToAdventure("P2");

        System.out.println("After add member");


        System.out.println();

        System.out.println("King counter = " + worldA.getKingCounter());
        worldA.incKingCounter();
        System.out.println("King counter = " + worldA.getKingCounter());


        worldA.setPhase(AvalonGameModel.Phase.DISCUSSION);
        System.out.println(worldA.getPhase());



    }
}