package com.company.game;

import java.util.List;

public class Adventure {

    private Quest quest;
    private List<Player> fellowship;

    public Adventure(Quest quest) {
        this.quest = quest;
    }

    public boolean addMember(Player player) {
        if (quest.getNumberOfKnights() > fellowship.size()) {
            System.out.println( player + " added to the fellowship!" );
            return fellowship.add(player);
        }
        return false;
    }

    public boolean removeMember(Player player) {
        System.out.println(player + " removed from the fellowship");
        return fellowship.remove(player);
    }

    public List getFellowship() {
        return fellowship;
    }

    public String getFellowshipToString() {
        return fellowship.toString();
    }
}
