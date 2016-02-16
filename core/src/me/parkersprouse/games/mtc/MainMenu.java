package me.parkersprouse.games.mtc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {

	private Game game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private Sprite playBtn, quitBtn, logo;
	private Texture playReg, playHover, quitReg, quitHover;
	
	public MainMenu(Game g) {
		game = g;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
		viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
		batch = new SpriteBatch();
		
		playReg = new Texture(Gdx.files.internal("play.png"));
		playHover = new Texture(Gdx.files.internal("play-hover.png"));
		quitReg = new Texture(Gdx.files.internal("quit.png"));
		quitHover = new Texture(Gdx.files.internal("quit-hover.png"));
		
		playBtn = new Sprite(playReg);
		playBtn.setPosition(100, 300);
		quitBtn = new Sprite(quitReg);
		quitBtn.setPosition(100, 150);
		
		logo = new Sprite(new Texture(Gdx.files.internal("logo.png")));
		logo.setPosition((Main.WIDTH/2) - (logo.getWidth()/2), Main.HEIGHT - logo.getHeight() - 50);
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}
	
	private void update() {
		camera.update();
		
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		viewport.unproject(mousePos);
		
		if (playBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
			playBtn.setTexture(playHover);
			if (Gdx.input.justTouched()) {
				game.setScreen(new DifficultySelectScreen(game));
			}
		}
		else {
			playBtn.setTexture(playReg);
		}
		
		if (quitBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
			quitBtn.setTexture(quitHover);
			if (Gdx.input.justTouched()) {
				Gdx.app.exit();
			}
		}
		else {
			quitBtn.setTexture(quitReg);
		}
	}
	
	private void draw() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		logo.draw(batch);
		playBtn.draw(batch);
		quitBtn.draw(batch);
		batch.end();
	}
	
	@Override
	public void show() {}
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	@Override
	public void dispose() {}
	
}
