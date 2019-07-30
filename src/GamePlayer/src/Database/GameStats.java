/**
 * This class was used as a data structure to store game stats data as individual objects. GameStats objects store stats and achievements that a particular user as accrued for any particular game. To use this class, declare a GameStats object and fill each of the fields with the setter methods
 * @author Connor Ghazaleh
 */

package Database;

public class GameStats {
    private String game = "";
    private String username = "";
    private int highScore = 0;
    private int numTimesPlayed = 0;
    private int totalScore = 0;
    private double avgScore = 0;

    public GameStats(){

    }

    /**
     * Set game field
     * @param game
     */
    public void setGame(String game) {
        this.game = game;
    }

    /**
     * Set username field
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set highScore field
     * @param highScore
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Set numTimesPlayed field
     * @param numTimesPlayed
     */
    public void setNumTimesPlayed(int numTimesPlayed) {
        this.numTimesPlayed = numTimesPlayed;
    }

    /**
     * Set totalScore field
     * @param totalScore
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Set avgScore field
     * @param avgScore
     */
    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    /**
     * Get game field
     * @return
     */
    public String getGame() {
        return game;
    }

    /**
     * Get username field
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * get highScore field
     * @return
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Get numTimesPlayed field
     * @return
     */
    public int getNumTimesPlayed() {
        return numTimesPlayed;
    }

    /**
     * Get totalScore field
     * @return
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Get avgScore field
     * @return
     */
    public double getAvgScore() {
        return avgScore;
    }

    /**
     * Get string representation of GameStats object
     * @return
     */
    @Override
    public String toString(){
        return String.format("{Game: %s, User: %s, HighScore %d, TimesPlayed: %d}\n",game,username,highScore,numTimesPlayed);
    }

}
