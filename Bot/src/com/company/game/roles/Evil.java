package com.company.game.roles;

public class Evil extends AbstractRole{
    public Evil() {
        super("Evil",       //Name
                true,       //see Evil
                false,      //see Merlin
                true,       //see Mordred
                true,       //seen as Evil
                false,      //seen as Merlin
                true,       //get to vote fail
                false);      //Unique
    }
}
