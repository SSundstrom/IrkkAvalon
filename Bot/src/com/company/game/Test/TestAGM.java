package com.company.game.Test;

import com.company.game.*;
import com.company.game.roles.*;
import com.company.game.worlds.EightPlayerWorld;
import com.company.game.worlds.FivePlayerWorld;

public class TestAGM {

    public static void main(String[] args) {

        AvalonGameModel worldA = AvalonGameFactory.createNewGame(8);

        System.out.println("Is the roles list full? " + worldA.isFullRoles());

        System.out.println();

        worldA.printRoles();
        worldA.addRole(new Morgana());
        worldA.addRole(new Percival());
        worldA.addRole(new Morgana());
        worldA.addRole(new Knight());
        System.out.println();
        worldA.addRole(new Knight());

        System.out.println();


        worldA.addPlayer("P1");
        worldA.addPlayer("P2");
        worldA.addPlayer("P3");
        worldA.addPlayer("P4");
        worldA.addPlayer("P5");
        worldA.addPlayer("P5");

        System.out.println();

        System.out.println("Is the player list full ? " + worldA.isFullPlayers());

        System.out.println();

        System.out.println(worldA.getPhase());
        worldA.setPhase(AvalonGameModel.Phase.ASSASINATION);
        System.out.println(worldA.getPhase());
        worldA.setPhase(AvalonGameModel.Phase.DISCUSSION);
        System.out.println(worldA.getPhase());


        System.out.println(worldA.getWorld());
        System.out.println();

        worldA.showQuests();





    }
}