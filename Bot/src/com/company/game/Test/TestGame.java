package com.company.game.Test;

import com.company.game.AvalonGameController;
import com.company.game.AvalonGameFactory;

import java.util.Scanner;


public class TestGame {

    public static void main (String [] args){

        String a="asdf";
        String b = "ASDF";

        System.out.println(a.toLowerCase().equals(b.toLowerCase()));

        Scanner sc = new Scanner(System.in);
        int nrOfPlayers;

        System.out.println("How many are playing?");
        nrOfPlayers = sc.nextInt();
        sc.nextLine();
        AvalonGameController gameA = new AvalonGameController(AvalonGameFactory.createNewGame(nrOfPlayers));

        System.out.println("Enter the names of the players");

        while(gameA.getGame().getNmbrOfInitPlayers()<gameA.getGame().getPlayers().length){
            gameA.addPlayer(sc.nextLine());
        }

        do {
            gameA.displayGameState();
            System.out.println("What quest?");
            if(gameA.selectQuest(gameA.getGame().askPlayer())) {
                do {
                    System.out.println("Who should join?");
                    gameA.nominatePlayer(gameA.getGame().askPlayer());
                } while (gameA.getGame().getAdventure().getFellowship().size()
                        != gameA.getGame().getAdventure().getQuest().getNumberOfKnights());
                gameA.goToVote();
            }else{
                System.out.println("Try selecting another quest");
            }
        } while (!gameA.getGame().gameOver());


    }



}
