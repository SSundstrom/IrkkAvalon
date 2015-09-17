package com.company.game;

import com.company.game.roles.AbstractRole;

public class Player {
    private String nick;
    private AbstractRole role;

    public String getNick() {
        return nick;
    }

    public AbstractRole getRole() {
        return role;
    }

    public Player(String nick) {
        this.nick = nick;
    }

    public void setRole(AbstractRole role) {
        this.role = role;
    }
}
