package com.honor.effects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Light {

  public Vector3 color;
  public Vector2 position;

  public Light(Vector3 color) {
    this.color = color;
    position = new Vector2();
  }
  
  public Light(float red, float green, float blue) {
    this.color = new Vector3(red, green, blue);
    position = new Vector2();
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }
  
  public void setPosition(float x, float y) {
    this.position.set(x, y);
  }

}
