package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloppyBird extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	Texture birdTexture;
	Sprite birdSprite;

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		birdTexture = new Texture("bird.png");
		birdSprite = new Sprite(birdTexture);
		birdSprite.setPosition(10, 10);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		birdSprite.setSize(56f, 45f);
		birdSprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		birdTexture.dispose();
	}
}
