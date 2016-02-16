package me.parkersprouse.games.mtc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Card {

	private float x, y;
	private String color;
	private Sprite sprite;
	private boolean flipped;

	public Card(float x, float y, String c) {
		this.x = x;
		this.y = y;
		color = c;
		flipped = false;
		sprite = new Sprite(new Texture("card_unknown.png"));
		sprite.setPosition(x, y);
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public void flipCard() {
		if (flipped) {
			sprite = new Sprite(new Texture("card_unknown.png"));
		}
		else {
			switch(color) {
				case "red":
					sprite = new Sprite(new Texture("card_red.png"));
					break;
				case "blue":
					sprite = new Sprite(new Texture("card_blue.png"));
					break;
				case "yellow":
					sprite = new Sprite(new Texture("card_yellow.png"));
					break;
				case "green":
					sprite = new Sprite(new Texture("card_green.png"));
					break;
				case "purple":
					sprite = new Sprite(new Texture("card_purple.png"));
					break;
				case "orange":
					sprite = new Sprite(new Texture("card_orange.png"));
					break;
			}
		}
		
		sprite.setPosition(x, y);

		flipped = !flipped;
	}
	
	public Rectangle getBounds() {
		return sprite.getBoundingRectangle();
	}
	
	public boolean isFlipped() {
		return flipped;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public String getColor() {
		return color;
	}

}
