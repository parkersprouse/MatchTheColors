package me.parkersprouse.games.mtc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DifficultySelectScreen implements Screen {

	private Game game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private Sprite easyBtn, hardBtn, twoPlayer;
	private Texture easyReg, easyHover, hardReg, hardHover, twoUnchecked, twoChecked;
	
	private BitmapFont font;
	private GlyphLayout easyLayout, hardLayout;
	private boolean showEasyText, showHardText, twoPlayerOn;
	
	public DifficultySelectScreen(Game g) {
		game = g;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
		viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
		batch = new SpriteBatch();

		font = new BitmapFont(Gdx.files.internal("font-small.fnt"));
		font.setColor(Color.BLACK);
		
		easyLayout = new GlyphLayout(font, "Match two cards of the same color");
		hardLayout = new GlyphLayout(font, "Match three cards of the same color");
		
		showEasyText = false;
		showHardText = false;
		twoPlayerOn = false;
		
		easyReg = new Texture(Gdx.files.internal("easy.png"));
		easyHover = new Texture(Gdx.files.internal("easy-hover.png"));
		hardReg = new Texture(Gdx.files.internal("hard.png"));
		hardHover = new Texture(Gdx.files.internal("hard-hover.png"));
		twoUnchecked = new Texture(Gdx.files.internal("two.png"));
		twoChecked = new Texture(Gdx.files.internal("twochecked.png"));
		
		easyBtn = new Sprite(easyReg);
		easyBtn.setPosition((Main.WIDTH / 2) - (easyBtn.getWidth() / 2), (Main.HEIGHT / 2) - (easyBtn.getHeight() / 2) + 150);
		hardBtn = new Sprite(hardReg);
		hardBtn.setPosition((Main.WIDTH / 2) - (hardBtn.getWidth() / 2), (Main.HEIGHT / 2) - (hardBtn.getHeight() / 2) - 150);
		twoPlayer = new Sprite(twoUnchecked);
		twoPlayer.setPosition(Main.WIDTH - twoPlayer.getWidth() - 25, Main.HEIGHT - twoPlayer.getHeight() - 25);
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
		
		// Easy button
		if (easyBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
			easyBtn.setTexture(easyHover);
			showEasyText = true;
			if (Gdx.input.justTouched()) {
				game.setScreen(new EasyPlayScreen(game, 20, twoPlayerOn));
			}
		}
		else {
			easyBtn.setTexture(easyReg);
			showEasyText = false;
		}
		
		// Hard button
		if (hardBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
			hardBtn.setTexture(hardHover);
			showHardText = true;
			if (Gdx.input.justTouched()) {
				game.setScreen(new HardPlayScreen(game, 20, twoPlayerOn));
			}
		}
		else {
			hardBtn.setTexture(hardReg);
			showHardText = false;
		}
		
		if (twoPlayer.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
			if (Gdx.input.justTouched()) {
				twoPlayerOn = !twoPlayerOn;
				if (twoPlayerOn)
					twoPlayer.setTexture(twoChecked);
				else
					twoPlayer.setTexture(twoUnchecked);
			}
		}
	}
	
	private void draw() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		easyBtn.draw(batch);
		hardBtn.draw(batch);
		twoPlayer.draw(batch);
		
		if (showEasyText)
			font.draw(batch, "Match two cards of the same color", (Main.WIDTH / 2) - (easyLayout.width / 2), (Main.HEIGHT / 2) + (easyLayout.height / 2));
		else if (showHardText)
			font.draw(batch, "Match three cards of the same color", (Main.WIDTH / 2) - (hardLayout.width / 2), (Main.HEIGHT / 2) + (hardLayout.height / 2));
		
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
