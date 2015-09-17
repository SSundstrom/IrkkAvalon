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
            return fellowship.add(player);
        }
        return false;
    }

    public boolean removeMember(Player player) {
        return fellowship.remove(player);
    }
}
