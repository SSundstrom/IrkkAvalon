package com.company.game;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractWorld {
    protected List<Quest> quests;

    private static List<Quest> getQuestsWithStatus(List<Quest> quests, Quest.Status status) {
        List<Quest> filtered = new LinkedList<>();
        for (Quest q : quests) {
            if (q.getStatus() == status) {
                filtered.add(q);
            };
        }
        return filtered;
    }

    public List<Quest> getSuccededQuests() {
        return AbstractWorld.getQuestsWithStatus(quests, Quest.Status.SUCCEDED);
    }

    public List<Quest> getFailedQuests() {
        return AbstractWorld.getQuestsWithStatus(quests, Quest.Status.FAILED);
    }

    public List<Quest> getAvailableQuests() {
        return AbstractWorld.getQuestsWithStatus(quests, Quest.Status.AVAILAVBLE);
    }

}
