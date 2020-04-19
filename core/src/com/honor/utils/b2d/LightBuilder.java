package com.honor.utils.b2d;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import static com.honor.utils.Constants.PPM;
import com.badlogic.gdx.graphics.Color;

public class LightBuilder {

  public static PointLight createPointLight(RayHandler rayHandler, float x, float y, Color c, float dist) {
    PointLight light = new PointLight(rayHandler, 120, c, dist, x / PPM, y / PPM);
    light.setSoftnessLength(1f);
    light.setXray(false);
    return light;
  }
  
}
