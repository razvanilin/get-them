package com.nightingale.getthemsanta.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nightingale.getthemsanta.models.Clouds;
import com.nightingale.getthemsanta.models.Gifts;
import com.nightingale.getthemsanta.models.Santa;
import com.nightingale.getthemsanta.models.World;
import com.nightingale.getthemsanta.screens.EndingScreen;

public class WorldRenderer {
	
	private static final float CAMERA_WIDTH = 7f;
	private static final float CAMERA_HEIGHT = 10f;
	
	private Game game;

	private TextureRegion santaTexture;
	private TextureRegion cloudTexture;
	private World world;
	private SpriteBatch spriteBatch;
	
	private int width;
	private int height;
	public float ppuX; //pixel per unit on the x axis
	public float ppuY; //pixel per unit on the y axis 
	
	private Clouds cloud;
	private Santa santa;
	private Gifts gift;

	private TextureRegion backgroundTexture;
	private TextureRegion giftTexture;
	boolean gameDone = false;
	private BitmapFont white;
	
	public WorldRenderer(){
		
	}

	public WorldRenderer(World world, Game game){
		this.game = game;
		this.world = world;
		cloud = world.getCloud();
		santa = world.getSanta();
		gift = world.getGift();
		spriteBatch = new SpriteBatch();
		
		loadTextures();
		white = new BitmapFont(Gdx.files.internal("data/fonts/white.fnt"), false);
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		santa.setBounds(ppuX, ppuY);
	}
	
	private void loadTextures()	{
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/santaSprite.pack"));
		santaTexture = atlas.findRegion("Santa");
		cloudTexture = atlas.findRegion("cloud");
		giftTexture = atlas.findRegion("gift");
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")));
	}
	
	float velocity=4f;
	float x;
	boolean cloudHit = false;
	boolean xChanged = false;
	float timePassedCloud;
	float timePassedGift;
	int hitCount=0;
	int score = 0;
	int scoreMultiplier = 1;
	
	public void render(){
		
		hitCount = 0;
		cloudHit=false;
		velocity += Gdx.graphics.getDeltaTime()+(1/2*velocity);
		//System.out.println(velocity);
		
		timePassedCloud += Gdx.graphics.getDeltaTime();
		timePassedGift += Gdx.graphics.getDeltaTime();
		
		/* --- Positioning the models --- */
		if (timePassedCloud > 2+(2/velocity)){
			cloud.add(0 + (int)(Math.random()*(Gdx.graphics.getWidth()-cloud.getBounds().width*ppuX)), -50f, ppuX, ppuY);
			timePassedCloud = 0;
		}
		if (timePassedGift>2f){
			gift.add(0 + (int)(Math.random()*(Gdx.graphics.getWidth()-gift.getBounds().width*ppuX)), -50f, ppuX, ppuY);
			timePassedGift=0;
		}
		/* --- Animate the models --- */
		cloud.animate(velocity);
		gift.animate(velocity);
		
		/* --- Collision detection --- */
		//clouds
		for (int k =0; k< cloud.clouds.size();k++){
			if (santa.getBounds().overlaps(cloud.clouds.get(k))&& hitCount<1){
				//System.out.println(hitCount);
				velocity = 4f;
				hitCount++;
				scoreMultiplier=1;
			}
		}		
		//gifts
		for (int k=0;k<gift.gifts.size();k++)
		{
			if (santa.getBounds().overlaps(gift.gifts.get(k))){
				System.out.println(santa.getBounds().width+ " "+ santa.getBounds().height);
				gift.gifts.remove(k);
				k--;
				score+=scoreMultiplier;
				scoreMultiplier++;
			}
		}
		
		/* --- Drawing the models --- */	
		if (!gameDone){
			spriteBatch.begin();
		
				spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
				drawSanta();
				drawCloud();
				drawGift();
				white.draw(spriteBatch, "Score: "+score, 20, Gdx.graphics.getHeight()-20);

				if (velocity>15f)
					white.draw(spriteBatch, "You're falling too fast!", 20, Gdx.graphics.getHeight()-200);
				if (velocity>25f)
					gameDone = true;
			
				spriteBatch.end();
		}
		else {
			game.setScreen(new EndingScreen(score, game));
			dispose(spriteBatch);
			/*
			spriteBatch.begin();
				spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
				white.draw(spriteBatch, "Santa fainted! He fell too fast", 20, Gdx.graphics.getHeight()-20);
				white.draw(spriteBatch, "Your score: "+score, 20, Gdx.graphics.getHeight()-200);
			spriteBatch.end();
			*/
		}
		
	}
	
	private void drawSanta(){

		spriteBatch.draw(
				santaTexture, 
				//Gdx.graphics.getHeight()/2,
				santa.getPosition().x,
				//Gdx.graphics.getWidth()/2,
				santa.getPosition().y,
				//120,
				santa.getSize() * ppuX, 
				//120
				santa.getSize() * ppuY
				);
		
	}
	
	
	private void drawCloud(){
		for (int k = 0; k< cloud.clouds.size();k++){
			spriteBatch.draw(
					cloudTexture, 
				cloud.clouds.get(k).x, 
				cloud.clouds.get(k).y, 
				cloud.clouds.get(k).width, 
				cloud.clouds.get(k).height
				);
		}
	}
	
	private void drawGift(){
		for (int k=0; k<gift.gifts.size();k++){
			spriteBatch.draw(
				giftTexture, 
				gift.gifts.get(k).x, 
				gift.gifts.get(k).y, 
				gift.gifts.get(k).width, 
				gift.gifts.get(k).height 
				);
		}
	}

	private void dispose(SpriteBatch spriteBatch){
		System.out.println("Sprite Batch disposed");
		spriteBatch.dispose();
		white.dispose();
	}
	
	public int getScore(){
		return score;
	}

}
