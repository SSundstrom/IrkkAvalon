package com.company.game.roles;

public class Oberon extends AbstractRole {
    public Oberon() {
        super("Oberon",     //Name
                true,       //see Evil
                false,      //see Merlin
                true,       //see Mordred
                false,       //seen as Evil
                false,       //seen as Merlin
                true,       //get to vote fail
                true);      //Unique
    }
}
