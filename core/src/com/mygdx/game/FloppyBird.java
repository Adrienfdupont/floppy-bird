package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	final int gravity = 300;
	int springTime;
	final int springSpeed = 600;

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		birdTexture = new Texture("bird.png");
		birdSprite = new Sprite(birdTexture);
		birdSprite.setSize(56f, 45f);
		birdSprite.setX(camera.viewportWidth / 2 - birdSprite.getWidth() / 2);
		birdSprite.setY(camera.viewportHeight / 2 - birdSprite.getHeight() / 2);
	}

	@Override
	public void render() {
		// drawing
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		birdSprite.draw(batch);
		batch.end();

		// user input
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			springTime = 15;
		}

		birdSprite.setY(birdSprite.getY() - gravity * Gdx.graphics.getDeltaTime());

		// spring
		if (springTime > 0) {
			birdSprite.setY(birdSprite.getY() + springSpeed * Gdx.graphics.getDeltaTime());
			springTime -= 0.1;
		}

	}

	@Override
	public void dispose() {
		batch.dispose();
		birdTexture.dispose();
	}
}
