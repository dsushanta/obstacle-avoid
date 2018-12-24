package com.bravo.johny.comon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.config.DifficultyLevel;

/**
 * Created by bittu on 24,December,2018
 */
public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGH_SCORE_KEY = "highscore";
    private static final String DIFFICULTY_KEY = "difficulty";

    private int highscore;
    private DifficultyLevel difficultyLevel;
    private Preferences PREFS;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(ObstacleAvoidGame.class.getSimpleName());
        highscore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
        String difficultyName = PREFS.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
    }

    public String getHighScore() {
        return String.valueOf(highscore);
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void updateHighScore(int score) {
        if(score <= highscore)
            return;

        highscore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highscore);
        PREFS.flush();              // It flushes the preferences from memory and saves it in file
    }

    public void updateDifficultyLevel(DifficultyLevel newDifficultyLevel) {
        if(difficultyLevel == newDifficultyLevel)
            return;
        difficultyLevel = newDifficultyLevel;
        PREFS.putString(DIFFICULTY_KEY, difficultyLevel.name());
        PREFS.flush();
    }
}
