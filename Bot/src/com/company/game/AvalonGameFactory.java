package com.company.game;

import com.company.game.worlds.*;

public class AvalonGameFactory {
    public static AvalonGameModel createNewGame(int amountPlayers) {
        switch (amountPlayers) {
            case 5:
                return new AvalonGameModel(new FivePlayerWorld(), 5);
            case 6:
                return new AvalonGameModel(new SixPlayerWorld(), 6);
            case 7:
                return new AvalonGameModel(new SevenPlayerWorld(), 7);
            case 8:
                return new AvalonGameModel(new EightPlayerWorld(), 8);
            case 9:
                return new AvalonGameModel(new NinePlayerWorld(), 9);
            case 10:
                return new AvalonGameModel(new TenPlayerWorld(), 10);
            default:
                throw new IllegalArgumentException("Must be between 5 and 10");
        }
    }
}