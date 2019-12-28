package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MyPlayer extends GameObject
{
	Rectangle top, bottom, left, right, full;
	Sprite sprite;
	Texture texture;
	int action;
	float velocityY, velocityX;
	
	//Player constructor
	public MyPlayer()
	{
		/*
		 * Rectangle(int x, int y, int width, int height)
		 * Constructs a new Rectangle whose upper-left corner is specified as (x,y) 
		 * and whose width and height are specified by the arguments of the same name.
		 */
		
		full = new Rectangle(0f,0f,128f,128f);
		bottom = new Rectangle(0f, 0f, 128f, 16f);
		left = new Rectangle(0f, 16f, 64f, 96f);
		right = new Rectangle(64f, 16f, 64f, 96f);
		top = new Rectangle(0f, 112f, 64f, 16f);
		
		
		
		texture = new Texture("squirtle_128x128.png");
		sprite = new Sprite(texture, 0, 0, 128, 128);
		this.setPosition(0, 0);
		velocityY = 0;
		velocityX = 0;
	}
	
	//Collision checker, if collision return number representing which side the collision occurred
	public int collisionWith(Rectangle r)
	{
		if (left.overlaps(r))
			return 2;
		if (right.overlaps(r))
			return 3;
		if (bottom.overlaps(r))
			return 1;
		if (top.overlaps(r))
			return 4;
		
		return -1;
	}
	
	//Multipurpose method that performs a certain action based on whats passed in as type
	public void action(int type, float x, float y)
	{
		//type == 1: Collision with ground, occurs after jumping
		//reset player's y velocity to 0, and reset the player to be above the ground
		if (type == 1 || type == 4)
		{
			velocityY = 0;
			setPosition(bottom.x, y);
		}
		
		if (type == 2 || type == 3)
		{
			velocityY = 0;
			setPosition(x, bottom.y);
		}
			
	}
	
	//Updates position for every tick when not grounded
	public void update(float delta)
	{
		velocityY -= 20 * delta;
		
		//left.y += velocityY;
		//right.y += velocityY;
		bottom.y += velocityY;
		top.y += velocityY;
		//full.y += velocityY;
		
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	public void setPosition(float x, float y)
	{
		//sets hitbox
		full.x = x;
		full.y = y;
		
		bottom.x = x;
		bottom.y = y;
		
		left.x = x;
		left.y = y + 16;
		
		right.x = x+64;
		right.y = y+16;
		
		top.x = x;
		top.y = y+ 112;
		//sets character image
		sprite.setPosition(x, y);
	}
	
	public void moveLeft(float delta)
	{
		//move hitbox pos to the left
		bottom.x -= (200*delta);
		
		//update pos on sprite
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	public void moveRight(float delta)
	{
		//move hitbox pos to the left
		bottom.x += (200*delta);
		
		
		//update pos on sprite
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	public void accelerateToPoint(float delta, int cursorX, int cursorY)
	{
		velocityY = 10;
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	public void jump()
	{
		//if statement to only let the player jump if grounded
		if (velocityY == 0)
			velocityY = 10;
	}

	@Override
	public Rectangle getHitbox() {
		// TODO Auto-generated method stub
		return full;
	}
}
