package com.mygdx.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloppyBird extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	Texture birdTexture;
	Sprite birdSprite;
	Texture pipeUpTexture;
	Texture pipeDownTexture;
	Array<Sprite> pipeSprites;
	final int gravity = 250;
	final int springHeight = 500;
	float springDuration;
	float pipeHeight;
	float pipeWidth;
	float maxOffset;
	float minOffset;
	Random random;
	Timer timer;
	TimerTask task;
	boolean isPaused;

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		random = new Random();

		birdTexture = new Texture("bird.png");
		birdSprite = new Sprite(birdTexture);
		birdSprite.setSize(56f, 45f);
		birdSprite.setX(camera.viewportWidth / 2 - birdSprite.getWidth() / 2);
		birdSprite.setY(camera.viewportHeight / 2 - birdSprite.getHeight() / 2);

		pipeUpTexture = new Texture("pipe_up.png");
		pipeDownTexture = new Texture("pipe_down.png");
		pipeHeight = camera.viewportHeight;
		pipeWidth = (float) Math.round(pipeHeight / 6.14);
		pipeSprites = new Array<Sprite>();

		maxOffset = -camera.viewportHeight / 3;
		minOffset = camera.viewportHeight / 3;

		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				generatePipes();
			}
		};

		timer.scheduleAtFixedRate(task, 0, 3000);

		isPaused = false;
	}

	@Override
	public void render() {
		if (isPaused) {
			return;
		}
		// drawing
		ScreenUtils.clear(0.6f, 0.6f, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		birdSprite.draw(batch);
		for (Sprite pipe : pipeSprites) {
			pipe.draw(batch);
		}
		batch.end();

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
		for (Sprite pipe : pipeSprites) {
			pipe.setX(pipe.getX() - 3);
		}

		for (Sprite pipeSprite : pipeSprites) {
			if (pipeSprite.getBoundingRectangle().overlaps(birdSprite.getBoundingRectangle())) {
				isPaused = true;
			}
		}

	}

	@Override
	public void dispose() {
		batch.dispose();
		birdTexture.dispose();
		pipeDownTexture.dispose();
		pipeUpTexture.dispose();
		timer.cancel();
		task.cancel();
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
}
