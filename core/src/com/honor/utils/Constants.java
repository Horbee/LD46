package com.honor.utils;

public class Constants {

  // Pixel Per Meter for Box2d
  public static final float PPM = 64;

  // Collision Filtering
  public static final short CATEGORY_WALL = 1;
  public static final short CATEGORY_PLAYER = 2;
  public static final short CATEGORY_GOBLIN = 4;
  public static final short CATEGORY_PROJECTILE = 8;
  public static final short CATEGORY_LIGHT = 16;
  
  public static final short PLAYER_COLLIDER() {
    return CATEGORY_WALL | CATEGORY_GOBLIN | CATEGORY_LIGHT;
  }
  
  public static final short ENEMY_COLLIDER() {
    return CATEGORY_WALL | CATEGORY_PLAYER| CATEGORY_PROJECTILE | CATEGORY_GOBLIN;
  }

  public static final short PROJECTILE_COLLIDER() {
    return CATEGORY_WALL | CATEGORY_GOBLIN;
  }
  
  public static final short WALL_COLLIDER() {
    return CATEGORY_PLAYER | CATEGORY_GOBLIN | CATEGORY_PROJECTILE;
  }
  
  public static final short LIGHT_COLLIDER() {
    return CATEGORY_PLAYER;
  }
  
  
}
