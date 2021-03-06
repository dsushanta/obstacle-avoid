package com.bravo.johny.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.comon.GameManager;
import com.bravo.johny.config.DifficultyLevel;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.entity.Background;
import com.bravo.johny.entity.Obstacle;
import com.bravo.johny.entity.Player;

import static com.bravo.johny.config.GameConfig.MAX_PLAYER_X_SPEED;

public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private float obstacleTimer, scoreTimer;
    private int score;
    private int displayScore;
    private int lives = GameConfig.LIVES_START;
    private Pool<Obstacle> obstaclePool;
    private Background background;
    private Sound hit;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;
    private final float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
    private final float startPlayerY = 1 - GameConfig.PLAYER_SIZE / 2f;

    public GameController(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        init();
    }

    private void init() {

        player = new Player();
        player.setPosition(startPlayerX, startPlayerY);
        obstaclePool = Pools.get(Obstacle.class, 40); // Obstacle class must have a no-argument constructor in order to use pooling
        background = new Background();
        background.setPosition(0,0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hit = assetManager.get(AssetDescriptors.HIT_SOUND);
    }

    public void update(float delta) {

        if(isGameOver()) {
            return;
        }
        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if(isPlayerColliding()) {
            log.info("Collison detected !!!  "+lives);
            lives--;
            if(isGameOver()) {
                log.debug("GAME OVER !!!");
                GameManager.INSTANCE.updateHighScore(score);
            }
            else {
                restart();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() {
        return background;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    private void restart() {
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
        player.setPosition(startPlayerX, startPlayerY);
    }

    private void updateScore(float delta) {
        scoreTimer += delta;

        if(scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score, displayScore + (int)(60*delta));
        }
    }

    private boolean isPlayerColliding() {
        for(Obstacle obstacle : obstacles) {
            if (obstacle.isNotHit() &&  obstacle.isCollidingWithPlayer(player)) {
                hit.play();
                return true;
            }
        }

        return false;
    }

    private void updateObstacles(float delta) {
        for(Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        createNewObstacle(delta);
        removePassedObstacles();
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if(obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            /*float min = GameConfig.PLAYER_SIZE/2f;
            float max = GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE/2f;*/
            float min = 0;
            float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

//            Obstacle obstacle = new Obstacle();
            Obstacle obstacle = obstaclePool.obtain();
            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
        if(obstacles.size >= 50)
            obstacles.removeRange(0,20);
    }

    private void removePassedObstacles() {
        if(obstacles.size <= 0)
            return;

        float minObstacleY = -GameConfig.OBSTACLE_SIZE;
        Obstacle first = obstacles.first();

        if(first.getY() < minObstacleY) {
            obstacles.removeValue(first, true);
            obstaclePool.free(first);
        }
    }

    private void updatePlayer() {
        float xSpeed = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            xSpeed = MAX_PLAYER_X_SPEED;
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            xSpeed = -MAX_PLAYER_X_SPEED;

        player.setX(player.getX() + xSpeed);
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        /*float playerX = MathUtils.clamp(
                player.getX(),
                player.getWidth()/2f,
                GameConfig.WORLD_WIDTH - player.getWidth()/2f
        );*/
        float playerX = MathUtils.clamp(
                player.getX(),
                0,
                GameConfig.WORLD_WIDTH - player.getWidth()
        );
        player.setPosition(playerX, player.getY());
    }

}
