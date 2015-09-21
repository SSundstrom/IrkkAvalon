package com.company.game.worlds;

import com.company.game.AbstractWorld;
import com.company.game.Quest;

import java.util.LinkedList;


public class FivePlayerWorld extends AbstractWorld {

    public FivePlayerWorld(){
        super();
        this.quests = new LinkedList<>();
        this.quests.add(new Quest(1, 2, 1));
        this.quests.add(new Quest(2, 3, 1));
        this.quests.add(new Quest(3, 2, 1));
        this.quests.add(new Quest(4, 3, 1));
        this.quests.add(new Quest(5, 3, 1));
    }


}