package com.mygdx.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final FloppyBird game;
    final float pipeHeight;
    final float pipeWidth;
    final float maxOffset;
    final float minOffset;
    final int gravity = 250;
    final int springHeight = 500;
    float springDuration;

    OrthographicCamera camera;
    Texture birdTexture;
    Sprite birdSprite;
    Texture pipeUpTexture;
    Texture pipeDownTexture;
    Array<Sprite> pipeSprites;
    Random random;
    Timer timer;
    TimerTask task;

    public GameScreen(final FloppyBird game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        random = new Random();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                generatePipes();
            }
        };

        this.game = game;
        maxOffset = -camera.viewportHeight / 3;
        minOffset = camera.viewportHeight / 3;
        pipeHeight = camera.viewportHeight;
        pipeWidth = (float) Math.round(pipeHeight / 6.14);

        birdTexture = new Texture("bird.png");
        birdSprite = new Sprite(birdTexture);
        birdSprite.setSize(56f, 45f);
        birdSprite.setX(camera.viewportWidth / 2 - birdSprite.getWidth() / 2);
        birdSprite.setY(camera.viewportHeight / 2 - birdSprite.getHeight() / 2);

        pipeUpTexture = new Texture("pipe_up.png");
        pipeDownTexture = new Texture("pipe_down.png");
        pipeSprites = new Array<Sprite>();

        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    @Override
    public void render(float delta) {
        // drawing
        ScreenUtils.clear(0.6f, 0.6f, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        birdSprite.draw(game.batch);
        for (Sprite pipe : pipeSprites) {
            pipe.draw(game.batch);
        }
        game.batch.end();

        // user input
        if (Gdx.input.isKeyJustPressed(Keys.UP)) {
            springDuration = 0.2f;
        }

        // spring
        if (springDuration > 0) {
            float spring = Gdx.graphics.getDeltaTime() * springHeight;
            birdSprite.setY(birdSprite.getY() + spring);
            springDuration -= Gdx.graphics.getDeltaTime();
        }

        birdSprite.setY(birdSprite.getY() - gravity * Gdx.graphics.getDeltaTime());
        if (birdSprite.getY() + birdSprite.getHeight() < 0) {
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }

        for (Sprite pipeSprite : pipeSprites) {
            pipeSprite.setX(pipeSprite.getX() - 3);
            if (pipeSprite.getBoundingRectangle().overlaps(birdSprite.getBoundingRectangle())
                    || birdSprite.getY() > camera.viewportHeight
                            && birdSprite.getX() == pipeSprite.getX()) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    private void generatePipes() {
        float offset = minOffset + random.nextFloat() * (maxOffset - minOffset);

        Sprite topPipe = new Sprite(pipeUpTexture);
        topPipe.setSize(pipeWidth, pipeHeight);
        topPipe.setX(camera.viewportWidth);
        topPipe.setY((float) Math.round(camera.viewportHeight * 1.65) - pipeHeight + offset);
        pipeSprites.add(topPipe);

        Sprite bottomPipe = new Sprite(pipeDownTexture);
        bottomPipe.setSize(pipeWidth, pipeHeight);
        bottomPipe.setX(camera.viewportWidth);
        bottomPipe.setY(-(float) Math.round(camera.viewportHeight * 0.65) + offset);
        pipeSprites.add(bottomPipe);
    }

    @Override
    public void dispose() {
        timer.cancel();
        task.cancel();
        birdTexture.dispose();
        pipeDownTexture.dispose();
        pipeUpTexture.dispose();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }
}
