package com.honor.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.enums.Sounds;
import com.honor.screens.PlayScreen;
import com.honor.utils.Constants;
import com.honor.utils.b2d.BodyBuilder;
import static com.honor.utils.Constants.PPM;

public class Player extends Mob {

  private final static String PLAYER_UP = "player_up";
  private final static String PLAYER_DOWN = "player_down";

  private PlayScreen playScreen;
  private AtlasRegion textureUp, textureDown, currentTexture;

  public int health = 100;

  private float timeCounter = 0;
  
  public boolean died = false;

  public Player(LD46Game game, World world, float posX, float posY) {
    this.game = game;
    this.world = world;

    TextureAtlas atlas = game.assetLoader.get(AssetLoader.SPRITES_ATLAS);
    textureUp = atlas.findRegion(PLAYER_UP);
    textureDown = atlas.findRegion(PLAYER_DOWN);
    currentTexture = textureDown;
    
    position = new Vector2(posX, posY);
    width = 64;
    height = 64;

    body = BodyBuilder.createRectangle(world, posX, posY, width, height, false,
        Constants.CATEGORY_PLAYER, Constants.PLAYER_COLLIDER());
    body.setLinearDamping(20f);
    body.setUserData(this);
  }

  public Player(LD46Game game, World world, Vector2 position) {
    this(game, world, position.x, position.y);
  }

  public void render(SpriteBatch batch) {
    batch.draw(currentTexture, position.x, position.y, width, height);
  }

  public void update(float delta) {
    timeCounter += delta;
    position.set(body.getPosition().x * PPM - width / 2, body.getPosition().y * PPM - height / 2);
    inputUpdate(delta);

    if (timeCounter >= 1.0) {
      health--;
      timeCounter -= 1.0;
    }
    
    if (health <= 0) {
      health = 0;
      die();
    }
  }

  private void inputUpdate(float delta) {
    float x = 0, y = 0;
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      currentTexture = textureUp;
      y += 1;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      currentTexture = textureDown;
      y -= 1;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      x -= 1;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      x += 1;
    }

    // Dampening check
    if (x != 0) {
      body.setLinearVelocity(x * 350 * delta, body.getLinearVelocity().y);
    }
    if (y != 0) {
      body.setLinearVelocity(body.getLinearVelocity().x, y * 350 * delta);
    }

    if (Gdx.input.justTouched()) {
      float dx = (Gdx.graphics.getWidth() / 2) - Gdx.input.getX();
      float dy = (Gdx.graphics.getHeight() / 2) - Gdx.input.getY();

      float angle = (float) Math.atan2(dy, -dx);

      Projectile p = new Projectile(game, world, position.x + 16, position.y + 16, angle);
      game.entityManager.addProjectile(p);
      game.audioManager.play(Sounds.SHOOT);
    }
  }

  private void die() {
    if (!died) {
      game.entityManager.stop = true;
      game.audioManager.play(Sounds.EXPLODE, 2f);
      playScreen.enableShockwave();
      died = true;
    }
  }
  
  public void takeDamage() {
    this.health -= 5;
    game.audioManager.play(Sounds.HURT);
  }
  
  public void heal() {
    this.health += 5;
    if (health > 100) {
      health = 100;
    }
    game.audioManager.play(Sounds.HEALTH, 0.3f);
  }
  
  public void setPlayScreen(PlayScreen playScreen) {
    this.playScreen = playScreen;
  }
  

}
