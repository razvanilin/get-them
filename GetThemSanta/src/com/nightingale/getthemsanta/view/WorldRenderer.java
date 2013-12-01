package com.nightingale.getthemsanta.view;

import java.util.HashMap;

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

public class WorldRenderer {
	
	private static final float CAMERA_WIDTH = 7f;
	private static final float CAMERA_HEIGHT = 10f;
	private static final float ACCELERATION = 1f;

	private TextureRegion santaTexture;
	private TextureRegion cloudTexture;
	private World world = new World();
	private SpriteBatch spriteBatch;
	
	private int width;
	private int height;
	public float ppuX;
	public float ppuY;
	
	private Clouds cloud;
	private Santa santa;
	private Gifts gift;

	private TextureRegion backgroundTexture;
	private TextureRegion giftTexture;
	boolean gameDone = false;
	private BitmapFont white;
private SpriteBatch batch;
	
	private HashMap<TextureRegion, Boolean> cloudChecker = new HashMap<TextureRegion, Boolean>();

	
	public WorldRenderer(World world){
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
	}
	
	private void loadTextures()	{
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/santaSprite.pack"));
		santaTexture = atlas.findRegion("Santa");
		cloudTexture = atlas.findRegion("cloud");
		giftTexture = atlas.findRegion("gift");
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")));
	}
	
	float velocity=1f;
	float x;
	boolean cloudHit = false;
	boolean xChanged = false;
	float timePassedCloud;
	float timePassedGift;
	int hitCount=0;
	int score = 0;
	
	public void render(){
		hitCount = 0;
		cloudHit=false;
		velocity += Gdx.graphics.getDeltaTime();
		System.out.println(velocity);
		timePassedCloud += Gdx.graphics.getDeltaTime();
		timePassedGift += Gdx.graphics.getDeltaTime();
		if (timePassedCloud > 2.5f){
			cloud.add(0 + (int)(Math.random()*(Gdx.graphics.getWidth()-cloud.getBounds().x)), -50f, ppuX, ppuY);
			timePassedCloud = 0;
		}
		if (timePassedGift>2f){
			gift.add(0 + (int)(Math.random()*(Gdx.graphics.getWidth()-gift.getBounds().x)), -50f, ppuX, ppuY);
			timePassedGift=0;
		}
		cloud.animate(velocity);
		gift.animate(velocity);
		/*cloud.setPosition(new Vector2(cloud.getPosition().x, cloud.getPosition().y + ACCELERATION*velocity));
		if (cloud.getPosition().y > Gdx.graphics.getHeight()){
			x = 0 + (int)(Math.random()*Gdx.graphics.getWidth());
			cloud.setPosition(new Vector2(x, -50f));
			cloudHit = false;
		}*/
		if (!gameDone){
		spriteBatch.begin();
		spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			drawSanta();
			drawCloud();
			drawGift();
			if (velocity>15f)
				white.draw(spriteBatch, "You're falling too fast!", 20, Gdx.graphics.getHeight()-20);

			white.draw(spriteBatch, "Score: "+score, 20, Gdx.graphics.getHeight()-200);
			if (velocity>25f)
				gameDone = true;
		spriteBatch.end();
		}
		else {
			batch = new SpriteBatch();
		batch.begin();
			batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			white.draw(batch, "Santa fainted! He fell too fast", 20, Gdx.graphics.getHeight()-20);
			white.draw(batch, "Your score: "+score, 20, Gdx.graphics.getHeight()-200);

		batch.end();
		}
		for (int k =0; k< cloud.clouds.size();k++){
		if (santa.getBounds().overlaps(cloud.clouds.get(k))&& hitCount<1){
			System.out.println(hitCount);
			velocity = 2f;
			hitCount++;
		
		}
		}
		
		for (int k=0;k<gift.gifts.size();k++)
		{
			if (santa.getBounds().overlaps(gift.gifts.get(k))){
				gift.gifts.remove(k);
				k--;
				score++;
			}
		}
		
	}
	
	private void drawSanta(){
		
		//if (santa.getState().equals(State.LEFT))
		//if (santa.getState().equals(State.RIGHT))
			//santa.setPosition(new Vector2(santa.getPosition().x + 20, santa.getPosition().y));

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
					cloud.texture, 
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
				gift.texture, 
				gift.gifts.get(k).x, 
				gift.gifts.get(k).y, 
				gift.gifts.get(k).width, 
				gift.gifts.get(k).height 
				);
		}
	}

	private void dispose(SpriteBatch spriteBatch){
		batch = new SpriteBatch();
		spriteBatch.dispose();
		batch.begin();
			batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			white.draw(batch, "Santa fainted! He fell too fast", 20, Gdx.graphics.getHeight()-20);
		batch.end();
		gameDone = true;
	}

}
