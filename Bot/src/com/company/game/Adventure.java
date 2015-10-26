package com.company.game;

import sun.invoke.empty.Empty;

import java.util.ArrayList;
import java.util.List;

public class Adventure {

    private Quest quest;
    private List<Player> fellowship;

    public Adventure(Quest quest) {
        fellowship = new ArrayList<Player>();
        this.quest = quest;
    }

    public int getQuestNumber() {
        return quest.getNumber();
    }

    public Quest getQuest() {
        return quest;
    }

    public void addMember(Player p) {
        fellowship.add(p);
    }

    public void removeMember(Player player) {
        fellowship.remove(player);
    }

    public List getFellowship() {
        return fellowship;
    }

    public String getNicksInFellowship() {
        String a = "";
        for (int i = 0; i < fellowship.size(); i++) {
            a += "  |  " + fellowship.get(i).getNick();
        }
        if (fellowship.size() < quest.getNumberOfKnights()) {
            for (int i = quest.getNumberOfKnights() - fellowship.size(); i > 0; i--) {
                a += "  |  Empty Slot";
            }
        }
        a += "  |";
        return a;
    }
}
