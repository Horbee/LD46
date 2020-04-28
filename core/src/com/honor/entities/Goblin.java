package com.honor.entities;

import static com.honor.utils.Constants.PPM;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.utils.Constants;
import com.honor.utils.b2d.BodyBuilder;

public class Goblin extends Mob {

  private final static String GOBLIN_UP = "goblin_up";
  private final static String GOBLIN_DOWN = "goblin_down";

  private AtlasRegion textureUp, textureDown, currentTexture;

  public Vector2 position;

  private int health = 3;

  public Goblin(LD46Game game, World world, float posX, float posY) {
    this.game = game;
    this.world = world;
    TextureAtlas atlas = game.assetLoader.get(AssetLoader.SPRITES_ATLAS);
    textureUp = atlas.findRegion(GOBLIN_UP);
    textureDown = atlas.findRegion(GOBLIN_DOWN);
    currentTexture = textureDown;

    position = new Vector2(posX, posY);
    width = 64;
    height = 64;

    body = BodyBuilder.createRectangle(world, posX, posY, width, height, false,
        Constants.CATEGORY_GOBLIN, Constants.ENEMY_COLLIDER());
    body.setLinearDamping(20f);
    body.setUserData(this);

    super.initialize(width);
  }

  public Goblin(LD46Game game, World world, Vector2 position) {
    this(game, world, position.x, position.y);
  }

  @Override
  public void render(SpriteBatch batch) {
    batch.draw(currentTexture, position.x, position.y, width, height);
  }

  @Override
  public void update(float delta) {
    // update AI
    super.update(delta);

    position.set(body.getPosition().x * PPM - width / 2, body.getPosition().y * PPM - height / 2);
    if (body.getLinearVelocity().y > 0) {
      currentTexture = textureUp;
    } else {
      currentTexture = textureDown;
    }

    if (health <= 0)
      die();
  }

  private void die() {
    game.entityManager.addHeal(
        new HealthLight(position.x, position.y, world, game.entityManager.getRayHandler()));
    remove();
  }

  public void takeDamage() {
    this.health--;
  }

}
