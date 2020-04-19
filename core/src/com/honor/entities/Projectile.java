package com.honor.entities;

import static com.honor.utils.Constants.PPM;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.utils.Constants;
import com.honor.utils.b2d.BodyBuilder;
import com.honor.utils.b2d.LightBuilder;
import box2dLight.PointLight;

public class Projectile extends Entity {
  private final static String PROJECTILE = "projectile";

  private AtlasRegion texture;

  public Body body;

  public float range, speed;
  public float speedX, speedY;
  public float originX, originY;
  public float radius;
  
  public PointLight light;
  private boolean lightRemoved = false;

  public Projectile(LD46Game game, World world, float posX, float posY, float angle) {
    TextureAtlas atlas = game.assetLoader.get(AssetLoader.SPRITES_ATLAS);
    texture = atlas.findRegion(PROJECTILE);

    position = new Vector2(posX, posY);
    originX = posX;
    originY = posY;
    width = 32;
    height = 32;
    radius = 16;
    speed = 5f;
    range = 1000f;
    
    speedX = (float) (speed * Math.cos(angle));
    speedY = (float) (speed * Math.sin(angle));

    body = BodyBuilder.createCircle(world, posX, posY, radius, false,
        Constants.CATEGORY_PROJECTILE, Constants.PROJECTILE_COLLIDER());
    body.setUserData(this);
    
    light = LightBuilder.createPointLight(game.entityManager.getRayHandler(), posX, posY, Color.RED, 1);
    light.attachToBody(body);
  }

  @Override
  public void render(SpriteBatch batch) {
    batch.draw(texture, position.x, position.y, width, height);
  }

  @Override
  public void update(float delta) {
    move(delta);
    position.set(body.getPosition().x * PPM - radius, body.getPosition().y * PPM - radius);
  }

  private float distance() {
    float dx = originX - position.x;
    float dy = originY - position.y;
    return (float) Math.sqrt(dx * dx + dy * dy);
  }

  private void move(float delta) {
    body.setLinearVelocity(speedX * 350 * delta, speedY * 350 * delta);
    
    if (distance() > range)
      remove();
  }
  
  @Override
  public void remove() {
    super.remove();
    if (lightRemoved == false) {
      light.remove();
      lightRemoved = true;
    }
  }

}
