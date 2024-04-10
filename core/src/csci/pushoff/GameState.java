package csci.pushoff;

public class GameState {
    private int scorePlayerOne = 0;
    private int scorePlayerTwo = 0;

    // Increment score and check for wins
    public void incrementScore(boolean isPlayerOne) {
        if (isPlayerOne) {
            scorePlayerOne++;
        } else {
            scorePlayerTwo++;
        }

        if (scorePlayerOne >= 3 || scorePlayerTwo >= 3) {
            // Handle game win logic here
            System.out.println(isPlayerOne ? "Player One Wins!" : "Player Two Wins!");
            // Reset scores or end game
        }
    }

    public int getScorePlayerOne() {
        return scorePlayerOne;
    }

    public int getScorePlayerTwo() {
        return scorePlayerTwo;
    }

    // Reset the game state for a new round
    public void reset() {
        // This method can reset positions, states, and potentially scores if needed
    }
}

