package AvalonGame;

import AvalonGame.roles.AbstractRole;

public class Player {

    private String nick;
    private AbstractRole role;

    public Player(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public AbstractRole getRole() {
        return role;
    }

    public void setRole(AbstractRole role) {
        this.role = role;
    }
}
