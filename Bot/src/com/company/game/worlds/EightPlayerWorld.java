package com.company.game.worlds;

import com.company.game.AbstractWorld;
import com.company.game.Quest;

import java.util.LinkedList;


public class EightPlayerWorld extends AbstractWorld {

    public EightPlayerWorld(){
        super();
        this.quests = new LinkedList<>();
        this.quests.add(new Quest(3, 1));
        this.quests.add(new Quest(4, 1));
        this.quests.add(new Quest(4, 1));
        this.quests.add(new Quest(5, 2));
        this.quests.add(new Quest(5, 1));
    }


}