package ohtu;

public class TennisGame {
    
    private int player1Score = 0;
    private int player2Score = 0;
    private String player1Name;
    private String player2Name;
    private int maxScoreWithNormalOutput = 3;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            player1Score += 1;
        } else {
            player2Score += 1;
        }
    }

    public String getScore() {
        String scoreString;
        if (player1Score == player2Score) {
            scoreString = tiedScoreString();
        } else if (player1Score>maxScoreWithNormalOutput || player2Score>maxScoreWithNormalOutput) {
            scoreString = advantageOrWin();
        } else {
            scoreString = scoreString(player1Score) + "-" + scoreString(player2Score);
        }
        return scoreString;
    }
    
    private String tiedScoreString() {
        if (player1Score <= maxScoreWithNormalOutput) {
            return scoreString(player1Score) + "-All";
        } else {
            return "Deuce";
        }
    }
    
    private String scoreString(int score) {
        switch(score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return "";
        }
    }
    
    private String advantageOrWin() {
        String scoreString;
        int minusResult = Math.abs(player1Score - player2Score);
        if (minusResult == 1) {
            scoreString ="Advantage " + leadingPlayer();
        } else {
            scoreString = "Win for " + leadingPlayer();
        }
        return scoreString;
    }
    
    private String leadingPlayer() {
        if (player1Score > player2Score) {
            return player1Name;
        } else if (player2Score > player1Score) {
            return player2Name;
        } else {
            return "nobody";
        }
    }
}