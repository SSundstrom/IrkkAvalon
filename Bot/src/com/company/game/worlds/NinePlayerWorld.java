package com.company.game.worlds;

import com.company.game.AbstractWorld;
import com.company.game.Quest;

import java.util.LinkedList;


public class NinePlayerWorld extends AbstractWorld {

    public NinePlayerWorld(){
        super();
        this.quests = new LinkedList<>();
        this.quests.add(new Quest(1, 3, 1));
        this.quests.add(new Quest(2, 4, 1));
        this.quests.add(new Quest(3, 4, 1));
        this.quests.add(new Quest(4, 5, 2));
        this.quests.add(new Quest(5, 5, 1));
    }


}