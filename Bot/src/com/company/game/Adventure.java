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
        System.out.println(p.getNick() + " added to the fellowship!");
        fellowship.add(p);
    }

    public void removeMember(Player player) {
        System.out.println(player.getNick() + " removed from the fellowship");
        fellowship.remove(player);
    }

    public List getFellowship() {
        return fellowship;
    }

    public void getNicksInFellowship() {
        for (int i = 0; i < fellowship.size(); i++) {
            System.out.print("  |  " + fellowship.get(i).getNick());
        }
        if (fellowship.size() < quest.getNumberOfKnights()) {
            for (int i = quest.getNumberOfKnights() - fellowship.size(); i > 0; i--) {
                System.out.print("  |  Empty Slot");
            }
        }
        System.out.println("  |");


    }
}
