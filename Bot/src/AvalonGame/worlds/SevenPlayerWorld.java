package AvalonGame.worlds;

import AvalonGame.Quest;

import java.util.LinkedList;

public class SevenPlayerWorld extends AbstractWorld {

    public SevenPlayerWorld(){
        super();
        this.quests = new LinkedList<>();
        this.quests.add(new Quest(1, 2, 1));
        this.quests.add(new Quest(2, 3, 1));
        this.quests.add(new Quest(3, 3, 1));
        this.quests.add(new Quest(4, 4, 2));
        this.quests.add(new Quest(5, 4, 1));
    }


}