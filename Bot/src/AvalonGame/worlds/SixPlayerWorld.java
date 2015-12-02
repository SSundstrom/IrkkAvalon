package AvalonGame.worlds;


import AvalonGame.Quest;

import java.util.LinkedList;

public class SixPlayerWorld extends AbstractWorld {

    public SixPlayerWorld(){
        super();
        this.quests = new LinkedList<>();
        this.quests.add(new Quest(1, 2, 1));
        this.quests.add(new Quest(2, 3, 1));
        this.quests.add(new Quest(3, 4, 1));
        this.quests.add(new Quest(4, 3, 1));
        this.quests.add(new Quest(5, 4, 1));
    }


}