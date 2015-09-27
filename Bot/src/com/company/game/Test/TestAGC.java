package com.company.game.Test;

import com.company.game.*;
import com.company.game.roles.*;
import com.company.game.worlds.EightPlayerWorld;
import com.company.game.worlds.FivePlayerWorld;

public class TestAGC {

    public static void main(String[] args) {


        AvalonGameController gameA = new AvalonGameController(AvalonGameFactory.createNewGame(5));

        System.out.println("\n----- Part 1 -----\n");

        System.out.println();
        gameA.addRole("Morgana");
        gameA.addRole("Percival");
        gameA.addRole("Mordred");
//        gameA.removeRole("Morgana");
//        gameA.addRole("Mordred");

        System.out.println();

        gameA.addPlayer("P1");
        gameA.addPlayer("P2");
        gameA.addPlayer("P3");
        gameA.addPlayer("P4");
        gameA.addPlayer("P5");
        //        gameA.removePlayer("P3");
        gameA.addPlayer("Simon");
        gameA.addPlayer("P7");

        System.out.println();

        System.out.println("\n----- Part 2 -----\n");

        gameA.startGame();

        gameA.addPlayer("Simon1");

        gameA.startGame();
/*
        gameA.selectQuest(1);

        System.out.println();

        gameA.nominatePlayer("Simon");
        gameA.nominatePlayer("P2");
        gameA.nominatePlayer("P4");

        System.out.println();

        gameA.displayGameState();

        System.out.println();

        gameA.removeNominatedPlayer("P2");
        gameA.nominatePlayer("P4");

        System.out.println();

        gameA.displayGameState();

        System.out.println();

        System.out.println("\n----- Part 3 -----\n");

        gameA.selectQuest(2);

        System.out.println();

        gameA.nominatePlayer("Simon");
        gameA.nominatePlayer("P4");

        System.out.println();

        gameA.selectQuest(5);


        gameA.displayGameState();
*/
        do {
            gameA.displayGameState();
            System.out.println("What quest?");
            gameA.selectQuest(Integer.parseInt(gameA.getGame().askPlayer()));
            do {
                System.out.println("Who should join?");
                gameA.nominatePlayer(gameA.getGame().askPlayer());
            } while (gameA.getGame().getAdventure().getFellowship().size()
                    != gameA.getGame().getAdventure().getQuest().getNumberOfKnights());
            gameA.goToVote();
        } while (gameA.getGame().getPhase() != AvalonGameModel.Phase.GAMEOVER);
//        gameA.displayGameState();
//        gameA.printVote(1);


    }

}