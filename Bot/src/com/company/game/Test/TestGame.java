package com.company.game.Test;

import com.company.game.AvalonGameController;
import com.company.game.AvalonGameFactory;

import java.util.Scanner;


public class TestGame {

    public static void main (String [] args){



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


    }



}
