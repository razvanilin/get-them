package com.nightingale.getthemsanta.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nightingale.getthemsanta.models.Clouds;
import com.nightingale.getthemsanta.models.Gifts;
import com.nightingale.getthemsanta.models.Santa;
import com.nightingale.getthemsanta.models.World;
import com.nightingale.getthemsanta.screens.LoseScreen;
import com.nightingale.getthemsanta.screens.WinScreen;

public class WorldRenderer {
	
	private static final float CAMERA_WIDTH = 7f;
	private static final float CAMERA_HEIGHT = 10f;
	
	private Game game;

	private TextureRegion santaTexture;
	private TextureRegion cloudTexture;
	private TextureRegion backgroundTexture;
	private World world;
	private SpriteBatch spriteBatch;
	
	private int width;
	private int height;
	public float ppuX; //pixel per unit on the x axis
	public float ppuY; //pixel per unit on the y axis 
	
	private Clouds cloud;
	private Santa santa;
	private Gifts gift;

	private TextureRegion giftTexture;
	boolean gameDone = false;
	private BitmapFont gameFont;
	
	private float level;
	private float defaultSantaPositionY;
	
	public WorldRenderer(){
		
	}

	public WorldRenderer(World world, Game game, float level){
		this.game = game;
		this.world = world;
		this.level = level;
		cloud = world.getCloud();
		santa = world.getSanta();
		gift = world.getGift();
		spriteBatch = new SpriteBatch();
		
		defaultSantaPositionY = santa.getPosition().y;
		
		loadTextures();
		gameFont = new BitmapFont(Gdx.files.internal("data/fonts/game.fnt"), false);
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		santa.setBounds(ppuX, ppuY);
	}
	
	private void loadTextures()	{
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")),-20 , -20, Gdx.graphics.getWidth()+20, Gdx.graphics.getHeight()+20);
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/santaSprite.pack"));
		santaTexture = atlas.findRegion("Santa");
		cloudTexture = atlas.findRegion("cloud");
		giftTexture = atlas.findRegion("gift");
	}
	
	//render fields
	public float velocity=6f;
	float x;
	boolean cloudHit = false;
	boolean xChanged = false;
	float timePassedCloud;
	float timePassedGift;
	int hitCount=0;
	int score = 0;
	int scoreMultiplier = 1;
	
	
	public void render(){
		
		if (level != -10){
			level -=Gdx.graphics.getDeltaTime();
			//			System.out.println(level);
			if (level < 0)
			{
				game.setScreen(new WinScreen(game, score));
			}
		}
//		System.out.println(Gdx.graphics.getDeltaTime());
		hitCount = 0;
		cloudHit=false;
		
		if (velocity < 15f)
			velocity += 2*Gdx.graphics.getDeltaTime()+(1/2*velocity);
		else if (velocity>=15f && velocity<=23)
			velocity += Gdx.graphics.getDeltaTime()+(1/2*velocity);
		else 
			velocity += .5f*Gdx.graphics.getDeltaTime()+(1/2*velocity);
		
		//System.out.println(velocity);

		timePassedCloud += Gdx.graphics.getDeltaTime();
		timePassedGift += Gdx.graphics.getDeltaTime();

		/* --- Positioning the models --- */
		if (timePassedCloud > (10/velocity)){
			cloud.add(0 + (int)(Math.random()*(Gdx.graphics.getWidth()-cloud.getBounds().width*ppuX)), -50f, ppuX, ppuY);
			timePassedCloud = 0;
		}
		if (timePassedGift>5/velocity){
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
				if (velocity > 6)
					velocity -= 4*Gdx.graphics.getDeltaTime();
				hitCount++;
				if (scoreMultiplier >2)
					scoreMultiplier -= 2;
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
				scoreMultiplier+=20;
			}
		}
		
		/* --- Animate to show the velocity --- */
//		System.out.println(ppuY*(velocity-6f));
		santa.setPosition(new Vector2(santa.getPosition().x, defaultSantaPositionY-(ppuY*(velocity-6f))/8));
		cloud.animate(-(velocity-6f)/8);
		gift.animate(-(velocity-6f)/8);

		/* --- Drawing the models --- */	
		if (!gameDone){
			spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			if (velocity>23) shakeBackground(velocity);
			drawSanta();
			drawCloud();
			drawGift();
			gameFont.draw(spriteBatch, "Score: "+score, 20, Gdx.graphics.getHeight()-20);
			gameFont.draw(spriteBatch, "+"+scoreMultiplier, 20, Gdx.graphics.getHeight()-80);
			if (level != -10)
				gameFont.draw(spriteBatch, ""+(int)level, 20, Gdx.graphics.getHeight()-120);
			if (velocity>23f)
				gameFont.draw(spriteBatch, "You're falling too fast!", 20, Gdx.graphics.getHeight()-200);
			if (velocity>30f)
				gameDone = true;
			spriteBatch.end();
		}
		else {
			game.setScreen(new LoseScreen(score, game));
			dispose();
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
	
	private void shakeBackground(float speed){
//		Min + (int)(Math.random() * ((Max - Min) + 1))
		spriteBatch.draw(
				backgroundTexture, 
				-.1f*speed + (int)(Math.random() * ((-.1f*speed + 0) + 1)), 
				-.1f*speed + (int)(Math.random() * ((-.1f*speed + 0) + 1)), 
				Gdx.graphics.getWidth() + (int)(Math.random() * ((Gdx.graphics.getWidth()+10 - Gdx.graphics.getWidth()) + 1)), 
				Gdx.graphics.getHeight() + (int)(Math.random() * ((Gdx.graphics.getHeight()+10 - Gdx.graphics.getHeight()) + 1)) 
				);
	}

	private void dispose(){
		System.out.println("Sprite Batch disposed");
		spriteBatch.dispose();
		gameFont.dispose();
	}
	
	public int getScore(){
		return score;
	}

}
