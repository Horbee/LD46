package com.honor.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.honor.LD46Game;

public abstract class Entity {

  protected LD46Game game;
  protected World world;
  
  public Vector2 position;
  public float width, height;
  public boolean shouldRemove = false;
  
  
  public void render(SpriteBatch batch) {

  }

  public void update(float delta) {

  }
  
  public void remove() {
    shouldRemove = true;
  }


}
