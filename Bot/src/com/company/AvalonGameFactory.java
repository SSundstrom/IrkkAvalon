package com.company;

import com.company.game.AvalonGameModel;
import com.company.game.worlds.EightPlayerWorld;

public class AvalonGameFactory {
    public static AvalonGameModel createNewGame(int amountPlayers) {

        switch (amountPlayers) {
//            case 5:
//                break;
//            case 6:
//                break;
//            case 7:
//                break;
            case 8:
                return new AvalonGameModel(new EightPlayerWorld(), 8);
//            case 9:
//                break;
//            case 10:
//                break;
            default: throw new IllegalArgumentException("Must be between 5 and 10");
        }

    }
}