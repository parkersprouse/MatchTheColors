package me.parkersprouse.games.mtc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class PlayScreen implements Screen {

	protected Game game;
	protected OrthographicCamera camera;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected Card[] cards;
	protected int numCardsFlipped, completedTiles;
	protected Card c1, c2, c3;
	protected boolean gameOver, twoPlayer;

	protected Sprite gameOverPanelBg, replayBtn, mainBtn, quitBtn, win, lose;
	protected Texture replayBtnReg, replayBtnHover, mainBtnReg, mainBtnHover, quitBtnReg, quitBtnHover;	

	// Timer used to determine when to flip unmatched cards back over
	protected float time = 1;
	protected float counter = 0;

	// Number of lives ("tries") the player has, if single player
	protected int lives;
	
	protected int playerTurn, p1solves, p2solves;

	// Flag to determine if the player lost or won
	protected boolean loser = false;
	
	// Font drawing stuff
	protected BitmapFont font, uiFont;
	protected GlyphLayout tilePostGame, p1scoreGlyph, p2scoreGlyph, winnerGlyph;

	public PlayScreen(Game g, int lives, boolean twoP) {
		game = g;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
		viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
		batch = new SpriteBatch();
		numCardsFlipped = 0;
		gameOver = false;
		twoPlayer = twoP;

		playerTurn = 1;
		p1solves = 0;
		p2solves = 0;
		
		this.lives = lives;

		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		
		uiFont = new BitmapFont(Gdx.files.internal("ui-font.fnt"));
		uiFont.setColor(Color.BLACK);
		
		tilePostGame = new GlyphLayout();
		p1scoreGlyph = new GlyphLayout();
		p2scoreGlyph = new GlyphLayout();
		winnerGlyph = new GlyphLayout();

		replayBtnReg = new Texture(Gdx.files.internal("replayBtn.png"));
		replayBtnHover = new Texture(Gdx.files.internal("replayBtnHover.png"));
		mainBtnReg = new Texture(Gdx.files.internal("menuBtn.png"));
		mainBtnHover = new Texture(Gdx.files.internal("menuBtnHover.png"));
		quitBtnReg = new Texture(Gdx.files.internal("quitBtn.png"));
		quitBtnHover = new Texture(Gdx.files.internal("quitBtnHover.png"));

		gameOverPanelBg = new Sprite(new Texture(Gdx.files.internal("gameOverBg.png")));
		replayBtn = new Sprite(replayBtnReg);
		mainBtn = new Sprite(mainBtnReg);
		quitBtn = new Sprite(quitBtnReg);
		win = new Sprite(new Texture(Gdx.files.internal("win.png")));
		lose = new Sprite(new Texture(Gdx.files.internal("loser.png")));

		gameOverPanelBg.setPosition((Main.WIDTH / 2) - (gameOverPanelBg.getWidth() / 2), (Main.HEIGHT / 2) - (gameOverPanelBg.getHeight() / 2));
		quitBtn.setPosition(gameOverPanelBg.getX() + 25, gameOverPanelBg.getY() + 25);
		mainBtn.setPosition(gameOverPanelBg.getX() + 25, quitBtn.getY() + quitBtn.getHeight() + 25);
		replayBtn.setPosition(gameOverPanelBg.getX() + 25, mainBtn.getY() + mainBtn.getHeight() + 25);
		win.setPosition((Main.WIDTH / 2) - (win.getWidth() / 2), (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 100);
		lose.setPosition((Main.WIDTH / 2) - (lose.getWidth() / 2), (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 100);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	protected abstract void update(float delta);

	protected abstract void draw();

	protected abstract void createCards();

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
