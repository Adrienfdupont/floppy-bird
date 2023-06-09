package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final FloppyBird game;
    String text;

    OrthographicCamera camera;

    public MainMenuScreen(final FloppyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        text = "Press the space bar to play !";
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.6f, 0.6f, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, text, camera.viewportWidth / 2 - 100, camera.viewportHeight / 2);
        game.font.setColor(0, 0, 0, 1);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
