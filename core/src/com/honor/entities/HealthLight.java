package com.honor.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.honor.utils.Constants;
import com.honor.utils.b2d.BodyBuilder;
import com.honor.utils.b2d.LightBuilder;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class HealthLight {

  public boolean shouldRemove = false;

  public PointLight light;
  public Body body;
  private float time = 0;
  private float dist = 0;

  public HealthLight(float posX, float posY, World world, RayHandler rayHandler) {
    body = BodyBuilder.createCircle(world, posX, posY, 3, false, Constants.CATEGORY_LIGHT,
        Constants.LIGHT_COLLIDER());
    body.setUserData(this);
    light = LightBuilder.createPointLight(rayHandler, posX, posY,
        new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1.0f), 3);
    light.attachToBody(body);
  }


  public void update(float delta) {
    time += delta;
    dist = (float) Math.abs(Math.sin((double) time));
    light.setDistance(dist);
  }

}
