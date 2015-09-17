package com.company.game;

import com.company.Utils.ArrayTools;
import com.company.game.roles.AbstractRole;
import com.company.game.roles.*;

public class AvalonGameModel {
    private AbstractWorld world;
    private Player[] players;
    private Phase phase = Phase.INNIT;
    private int kingCounter = 0;
    private AbstractRole[] roles;
    private Player king;
    private Adventure adventure;

    public AvalonGameModel(AbstractWorld world, int amountPlayers) {
        this.world = world;
        this.players = new Player[amountPlayers];
        this.roles = new AbstractRole[amountPlayers];
        addRole(new Merlin());
        addRole(new Assasin());
    }

    public AbstractWorld getWorld() {
        return world;
    }

    public Player[] getPlayers() {
        return players;
    }

    public AbstractRole[] getRoles() {
        return roles;
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getKing() {
        return king;
    }

    public int getKingCounter() {
        return this.kingCounter;
    }

    public Adventure getAdventure() {
        return adventure;
    }

    public void resetKingCounter() {
        this.kingCounter = 0;
    }

    public void incKingCounter() {
        if (kingCounter < 5) {
            kingCounter++;
        }
    }

    public void setPhase(Phase phase) {
        switch (phase) {
            case DISCUSSION:
                if (this.phase == Phase.INNIT || this.phase == Phase.VOTE || this.phase == Phase.QUEST) {
                    this.phase = phase;
                }
                break;
            case VOTE:
                if (this.phase == Phase.DISCUSSION) {
                    this.phase = phase;
                }
                break;
            case QUEST:
                if (this.phase == Phase.VOTE) {
                    this.phase = phase;
                    break;
                }
            case ASSASINATION:
                if (this.phase == Phase.QUEST) {
                    this.phase = phase;
                }
                break;
        }
    }

    public void setKing(Player king) {
        this.king = king;
    }

    public void selectQuest(Quest quest) {
        if (phase == Phase.DISCUSSION && world.getAvailableQuests().contains(quest)) {
            adventure = new Adventure(quest);
        }
    }

    public boolean addPlayer(String nick) {
        if (phase == Phase.INNIT) {
            for (int i = 0; i < players.length; i++) {
                if (players[i] == null) {
                    players[i] = new Player(nick);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean addRole(AbstractRole role) {
        if (phase == Phase.INNIT) {
            for (int i = 0; i < players.length; i++) {
                if (role == roles[i] && role.isUniqe()) {
                    return false;
                } else if (roles[i] == null) {
                    roles[i] = role;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFullPlayers() {
        for (Player p : players) {
            if (p == null) {
                return false;
            }
        }
        return false;
    }

    public boolean isFullRoles() {
        for (AbstractRole p : roles) {
            if (p == null) {
                return false;
            }
        }
        return false;
    }

    public void distributeRoles () {
        ArrayTools.shuffleArray(roles);
        for (int i = 0; i < players.length; i++) {
            players[i].setRole(roles[i]);
        }
    }
    public void selectFirstKing() {
        if (phase == Phase.INNIT) {
            ArrayTools.shuffleArray(players);
            setKing(players[0]);
        }
    }
    public void nextKing() {
        int index = java.util.Arrays.asList(players).indexOf(getKing());
        index = (index + 1) % players.length;
        setKing(players[index]);
    }

    public enum Phase {
        INNIT, DISCUSSION, VOTE, QUEST, ASSASINATION, GAMEOVER
    }
}

