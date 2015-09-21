package com.company.game;

import com.company.game.roles.AbstractRole;

public class AvalonGameController {
    private AvalonGameModel game;

    public AvalonGameController(AvalonGameModel game) {
        this.game = game;
    }

    public void addPlayer(String nick) {
            game.addPlayer(nick);
    }

    public void addRole(AbstractRole role) {
            game.addRole(role);
    }

    public void startGame() {
        if (game.isFullPlayers() && game.isFullRoles()) {
            game.distributeRoles();
            game.selectFirstKing();
            game.setPhase(AvalonGameModel.Phase.DISCUSSION);
        }
    }

    public void selectQuest(Quest quest){
        game.selectQuest(quest);
    }

    public String currentKing() {
        return game.getKing().getNick();
    }




}
