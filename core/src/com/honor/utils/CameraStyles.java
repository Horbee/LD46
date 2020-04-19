package com.honor.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraStyles {

  public static void lockOnTarget(OrthographicCamera camera, Vector2 target) {
    Vector3 position = camera.position;
    position.x = target.x;
    position.y = target.y;
    camera.position.set(position);
    camera.update();
  }

  public static void lerpToTarget(OrthographicCamera camera, Vector2 target) {
    Vector3 position = camera.position;
    position.x = camera.position.x + (target.x - camera.position.x) * .1f;
    position.y = camera.position.y + (target.y - camera.position.y) * .1f;
    camera.position.set(position);
    camera.update();
  }

}
