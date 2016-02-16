package me.parkersprouse.games.mtc;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

public class HardPlayScreen extends PlayScreen {

	public HardPlayScreen(Game g, int lives, boolean twoP) {
		super(g, lives, twoP);
		createCards();
	}

	protected void update(float delta) {
		camera.update();
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		viewport.unproject(mousePos);

		if (!gameOver) {

			// IF THE GAME IS STILL GOING

			// If we have less than all three cards flipped
			if (numCardsFlipped < 3) {
				if (numCardsFlipped == 2) {
					if (c1.getColor().equals(c2.getColor())) {
						if (Gdx.input.justTouched()) {
							for (Card c : cards) {
								if (c.getBounds().contains(mousePos.x, mousePos.y) && !c.isFlipped()) {
									c.flipCard();
									numCardsFlipped++;
									c3 = c;
								}
							}
						}
					} 
					else {
						counter += delta;
						if (counter > time) {
							counter -= time;
							c1.flipCard();
							c2.flipCard();
							if (!twoPlayer) lives--;
							numCardsFlipped = 0;
							
							if (playerTurn == 1) playerTurn = 2;
							else playerTurn = 1;
						}
					}
				}
				else {
					if (Gdx.input.justTouched()) {
						for (Card c : cards) {
							if (c.getBounds().contains(mousePos.x, mousePos.y) && !c.isFlipped()) {
								c.flipCard();
								numCardsFlipped++;
								if (numCardsFlipped == 1)
									c1 = c;
								else if (numCardsFlipped == 2)
									c2 = c;
							}
						}
					}
				}
			}

			// If the previous two cards match, then we check for a third
			else if (numCardsFlipped == 3) {
				if (c2.getColor().equals(c3.getColor())) {
					numCardsFlipped = 0;
					completedTiles += 3;
					if (!twoPlayer) lives++;
					
					if (playerTurn == 1) p1solves++;
					else p2solves++;
				}
				else {
					counter += delta;
					if (counter > time) {
						counter -= time;
						c1.flipCard();
						c2.flipCard();
						c3.flipCard();
						if (!twoPlayer) lives--;
						numCardsFlipped = 0;
						
						if (playerTurn == 1) playerTurn = 2;
						else playerTurn = 1;
					}
				}
			}

			if (numCardsFlipped == 0) {
				int cardsDone = 0;
				for (Card c : cards) {
					if (c.isFlipped()) {
						cardsDone++;
					}
					if (cardsDone == cards.length) {
						gameOver = true;
						if (twoPlayer) {
							p1scoreGlyph.setText(font, "Player 1 score: " + p1solves);
							p2scoreGlyph.setText(font, "Player 2 score: " + p2solves);

							if (p1solves > p2solves) {
								winnerGlyph.setText(font, "Player 1 Wins");
							}
							else if (p2solves > p1solves) {
								winnerGlyph.setText(font, "Player 2 Wins");
							}
							else {
								winnerGlyph.setText(font, "Tied!");
							}
						}
					}
				}
			}

			if (!twoPlayer) {
				if (lives < 1) {
					loser = true;
					gameOver = true;
					tilePostGame.setText(font, "Tiles matched: " + completedTiles + " / 18");
				}
			}

			// END GAME IS STILL GOING

		}

		// If the game is over
		else {
			if (loser) {
				for (Card c : cards) {
					if (!c.isFlipped()) {
						c.flipCard();
					}
				}
			}

			if (replayBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
				replayBtn.setTexture(replayBtnHover);
				if (Gdx.input.justTouched()) {
					/*if (!loser) {
						game.setScreen(new HardPlayScreen(game, lives));
					}
					else {
						game.setScreen(new HardPlayScreen(game, 20));
					}*/
					game.setScreen(new HardPlayScreen(game, 20, twoPlayer));
				}
			}
			else {
				replayBtn.setTexture(replayBtnReg);
			}

			if (mainBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
				mainBtn.setTexture(mainBtnHover);
				if (Gdx.input.justTouched()) {
					game.setScreen(new MainMenu(game));
				}
			}
			else {
				mainBtn.setTexture(mainBtnReg);
			}

			if (quitBtn.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
				quitBtn.setTexture(quitBtnHover);
				if (Gdx.input.justTouched()) {
					Gdx.app.exit();
				}
			}
			else {
				quitBtn.setTexture(quitBtnReg);
			}
		}

	}

	protected void draw() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Card c : cards)
			c.draw(batch);

		if (!gameOver) {
			if (!twoPlayer)
				uiFont.draw(batch, "[Tries | " + lives + " ]", 25, Main.HEIGHT - 25);
			else 
				uiFont.draw(batch, "[Player  " + playerTurn + "]", 25, Main.HEIGHT - 25);
		}
		else if (gameOver) {
			gameOverPanelBg.draw(batch);
			replayBtn.draw(batch);
			mainBtn.draw(batch);
			quitBtn.draw(batch);
			if (loser) {
				lose.draw(batch);
				font.setColor(Color.WHITE);
				font.draw(batch, "Tiles matched: " + completedTiles + " / 12", (Main.WIDTH / 2) - (tilePostGame.width / 2), lose.getY() - 75);
			}
			else {
				if (twoPlayer) {
					font.setColor(Color.WHITE);
					if (p1solves > p2solves) {
						font.draw(batch, "Player 1 Wins", (Main.WIDTH / 2) - (winnerGlyph.width / 2), (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 25);
					}
					else if (p2solves > p1solves) {
						font.draw(batch, "Player 2 Wins", (Main.WIDTH / 2) - (winnerGlyph.width / 2), (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 25);
					}
					else {
						font.draw(batch, "Tied!", (Main.WIDTH / 2) - (winnerGlyph.width / 2), (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 25);
					}

					font.draw(batch, "Player 1 score: " + p1solves, gameOverPanelBg.getX() + 25, (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 150);
					font.draw(batch, "Player 2 score: " + p2solves, gameOverPanelBg.getX() + 25, (gameOverPanelBg.getY() + gameOverPanelBg.getHeight()) - 210);
				}
				else {
					win.draw(batch);
				}
			}
		}
		batch.end();
	}

	protected void createCards() {
		cards = new Card[18];

		int numRed = 0;
		int numYellow = 0;
		int numBlue = 0;
		int numGreen = 0;
		int numOrange = 0;
		int numPurple = 0;

		String cardColor = "";
		Random rand = new Random();

		for (int i = 0; i < 18; i++) {

			while (true) {
				int c = rand.nextInt(6);
				switch(c) {
				case 0:
					cardColor = "red";
					numRed++;
					break;
				case 1:
					cardColor = "yellow";
					numYellow++;
					break;
				case 2:
					cardColor = "blue";
					numBlue++;
					break;
				case 3:
					cardColor = "green";
					numGreen++;
					break;
				case 4:
					cardColor = "orange";
					numOrange++;
					break;
				case 5:
					cardColor = "purple";
					numPurple++;
					break;
				}

				if (
						(c == 0 && numRed < 4) ||
						(c == 1 && numYellow < 4) ||
						(c == 2 && numBlue < 4) ||
						(c == 3 && numGreen < 4) ||
						(c == 4 && numOrange < 4) ||
						(c == 5 && numPurple < 4)) {
					break;
				}
			}

			if (i < 6)
				cards[i] = new Card(65 + (i * 153), 473, cardColor);
			else if (i >= 6 && i < 12)
				cards[i] = new Card(65 + ((i - 6) * 153), 320, cardColor);
			else
				cards[i] = new Card(65 + ((i - 12) * 153), 167, cardColor);
		}
	}

}
