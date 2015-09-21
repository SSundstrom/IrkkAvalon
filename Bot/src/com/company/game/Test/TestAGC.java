package com.company.game.Test;

import com.company.game.*;
import com.company.game.roles.*;
import com.company.game.worlds.EightPlayerWorld;
import com.company.game.worlds.FivePlayerWorld;

public class TestAGC {

    public static void main(String[] args) {


        AvalonGameController gameA = new AvalonGameController(AvalonGameFactory.createNewGame(5));

        System.out.println();
        gameA.addRole(new Morgana());
        gameA.addRole(new Percival());
        gameA.addRole(new Morgana());
        gameA.addRole(new Knight());
        System.out.println();
        gameA.addRole(new Knight());

        System.out.println();


        System.out.println();

        gameA.addPlayer("P1");
        gameA.addPlayer("P2");
        gameA.addPlayer("P3");
        gameA.addPlayer("P4");
        gameA.addPlayer("P5");
        gameA.addPlayer("P5");

        System.out.println();

        gameA.startGame();

        System.out.println(gameA.currentKing());


    }
}