package csci.pushoff;

public class GameState {
    private int scorePlayerOne = 0;
    private int scorePlayerTwo = 0;
    private boolean waitingForReset = false;

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

    public void setWaitingForReset(boolean waiting) {
        this.waitingForReset = waiting;
    }

    public boolean isWaitingForReset() {
        return waitingForReset;
    }
}

