/**
 * This class is responsible for retrieving or storing any information pertinent to game stats in a mySQL database. This class extends DBManager so it can use 2 general query methods that either retrieve data or modify data in the database. This class depends on having an active database set up that it can query and depends on the methods in the superclass DBManager. This class should be used for any manipulation or retrieval of the information storing game stats for specific users. To do so, declare an instance of it in code and call the public methods listed below with appropriate input parameters.
 * @author Connor Ghazaleh
 */

package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DBGameStats extends DBManager{

    public DBGameStats(){
        super();
    }

    /**
     * Updates the stats for a particular user and a particular game with a new score
     * @param user user whose stats are being updated
     * @param game game for which user's stats are being updated
     * @param score new score used to update stats
     */
    public void updateGameStats(String user, String game, int score) {
        GameStats currentStats = getStatsForGame(user,game);
        System.out.println(currentStats);
        String query = "";
        if (currentStats != null){
            GameStats updatedStats = calcNewGameStats(currentStats, score);
            System.out.println(updatedStats.getAvgScore());
            query = String.format("UPDATE user_info.game_stats " +
                    "SET high_score = %d, avg_score = %.2f, times_played = %d, total_score = %d WHERE username = '%s' AND game = '%s';",
                    updatedStats.getHighScore(),updatedStats.getAvgScore(),updatedStats.getNumTimesPlayed(),updatedStats.getTotalScore(),user,game);
        }else{
            query = String.format("INSERT INTO user_info.game_stats VALUE('%s',%d,%d,'%s',%.2f,%d);",user,score,1,game,(double)score,score);
        }
        super.insertQuery(query);
    }

    private GameStats calcNewGameStats(GameStats currentStats, int score){
        GameStats stats = new GameStats();
        stats.setTotalScore(currentStats.getTotalScore()+score);
        stats.setNumTimesPlayed(currentStats.getNumTimesPlayed()+1);
        stats.setAvgScore((double)stats.getTotalScore()/(double)stats.getNumTimesPlayed());
        if (score > currentStats.getHighScore()) {
            stats.setHighScore(score);
        }else{
            stats.setHighScore(currentStats.getHighScore());
        }
        return stats;
    }

    /**
     * Retrieves stats for game for a particular user
     * @param user user whose stats are being retrieved
     * @param game game for which stats are being retrieved
     * @return GameStats object returned for particular user and game
     */
    public GameStats getStatsForGame(String user, String game){
        String query = String.format("SELECT * FROM user_info.game_stats WHERE username = '%s' AND game = '%s'",user,game);
        ArrayList<Object> gameStats = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,gameStats);
        super.selectQuery(query,consumer);
        if (gameStats.isEmpty()){
            return null;
        }
        return (GameStats) gameStats.get(0);
    }

    /**
     * Gets game stats for all games played by a user
     * @param user user whose stats are being retrieved
     * @return List of GameStats objects representing stats for every game
     */
    public List<GameStats> getGamesPlayed(String user){
        ArrayList<GameStats> gamesPlayed = new ArrayList<>();
        String query = String.format("SELECT * FROM user_info.game_stats WHERE username = '%s'",user);
        ArrayList<Object> games = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,games);
        super.selectQuery(query,consumer);
        for (Object game : games){
            gamesPlayed.add((GameStats)game);
        }
        return gamesPlayed;
    }



    private void populateArray(ResultSet rs, List<Object> gameStats){
        try{
            while(rs.next()){
                GameStats stats = new GameStats();
                stats.setUsername(rs.getString(1));
                stats.setHighScore(rs.getInt(2));
                stats.setNumTimesPlayed(rs.getInt(3));
                stats.setGame(rs.getString(4));
                stats.setAvgScore(rs.getDouble(5));
                stats.setTotalScore(rs.getInt(6));
                gameStats.add(stats);
            }
        }catch(SQLException e){
            System.out.println("Could not extract results from result set");
        }
    }

}
