import com.company.game.*;

public class TestAGC {

    public static void main(String[] args) {


        AvalonGameController gameA = new AvalonGameController(AvalonGameFactory.createNewGame(5));

        System.out.println("\n----- Part 1 -----\n");

        System.out.println();
        gameA.addRole("Morgana");
        gameA.addRole("Percival");
        gameA.addRole("Mordred");

        System.out.println();

        gameA.addPlayer("P1");
        gameA.addPlayer("P2");
        gameA.addPlayer("P3");
        gameA.addPlayer("P4");
        gameA.addPlayer("P5");

        System.out.println();

        System.out.println("\n----- Part 2 -----\n");

        gameA.startGame();


        do {
            gameA.displayGameState();
            System.out.println("What quest?");
            gameA.selectQuest(gameA.getGame().askPlayer());
            if (gameA.getGame().getAdventure().getQuest() != null) {
                do {
                    System.out.println("Who should join?");
                    gameA.nominatePlayer(gameA.getGame().askPlayer());
                } while (gameA.getGame().getAdventure().getFellowship().size()
                        != gameA.getGame().getAdventure().getQuest().getNumberOfKnights());
                gameA.goToVote();
            }
        } while (!gameA.getGame().gameOver());
    }

}