package com.honor.levels;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.honor.LD46Game;
import com.honor.entities.Goblin;
import com.honor.utils.b2d.LightBuilder;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Level {

  LD46Game game;

  TiledMap tiledMap;
  OrthogonalTiledMapRenderer tileMapRenderer;

  public Vector2 playerSpawn = new Vector2();
  public List<Vector2> goblinSpawns = new ArrayList<>();
  public List<Vector2> greenLightPositions = new ArrayList<>();
  public List<Vector2> blueLightPositions = new ArrayList<>();
  public List<Vector2> orangeLightsPositions = new ArrayList<>();
  public Array<RectangleMapObject> walls = new Array<>();

  public List<PointLight> lights;

  public Level(LD46Game game, String levelPath) {
    this.game = game;
    tiledMap = game.assetLoader.get(levelPath);
    tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    for (RectangleMapObject obj : tiledMap.getLayers().get("entities").getObjects()
        .getByType(RectangleMapObject.class)) {

      if (obj.getName().equals("playerSpawn")) {
        float x = obj.getRectangle().getX();
        float y = obj.getRectangle().getY();
        playerSpawn.set(x, y);
        Gdx.app.log("Found", "SpawnPoint: " + x + ", " + y);
      }

      if (obj.getName().equals("light_green")) {
        float x = obj.getRectangle().getX();
        float y = obj.getRectangle().getY();
        greenLightPositions.add(new Vector2(x, y));
        Gdx.app.log("Found", "Green Light: " + x + ", " + y);
      }

      if (obj.getName().equals("light_blue")) {
        float x = obj.getRectangle().getX();
        float y = obj.getRectangle().getY();
        blueLightPositions.add(new Vector2(x, y));
        Gdx.app.log("Found", "Blue Light: " + x + ", " + y);
      }

      if (obj.getName().equals("light_orange")) {
        float x = obj.getRectangle().getX();
        float y = obj.getRectangle().getY();
        orangeLightsPositions.add(new Vector2(x, y));
        Gdx.app.log("Found", "Orange Light: " + x + ", " + y);
      }

      if (obj.getName().equals("goblinSpawn")) {
        float x = obj.getRectangle().getX();
        float y = obj.getRectangle().getY();
        goblinSpawns.add(new Vector2(x, y));
        Gdx.app.log("Found", "GoblinSpawn: " + x + ", " + y);
      }
    }


    walls = tiledMap.getLayers().get("collision").getObjects().getByType(RectangleMapObject.class);

    Gdx.app.log("Found", "Walls: " + walls.size);
  }

  public void createLights(RayHandler rayHandler) {
    lights = new ArrayList<PointLight>();

    greenLightPositions.forEach(lightPos -> lights
        .add(LightBuilder.createPointLight(rayHandler, lightPos.x, lightPos.y, Color.GREEN, 10)));

    blueLightPositions.forEach(lightPos -> lights
        .add(LightBuilder.createPointLight(rayHandler, lightPos.x, lightPos.y, Color.BLUE, 10)));

    orangeLightsPositions.forEach(lightPos -> lights
        .add(LightBuilder.createPointLight(rayHandler, lightPos.x, lightPos.y, Color.ORANGE, 10)));
  }

  public void render(OrthographicCamera camera) {
    tileMapRenderer.setView(camera);
    tileMapRenderer.render();
  }

  public void createGoblin() {
    Goblin goblin = new Goblin(game, game.entityManager.getWorld(),
        goblinSpawns.get(MathUtils.random(goblinSpawns.size() - 1)));
    game.entityManager.addEntity(goblin);

    Arrive<Vector2> arriveSB = new Arrive<Vector2>(goblin, game.entityManager.getPlayer())
        .setTimeToTarget(0.5f).setArrivalTolerance(1.0f).setDecelerationRadius(10);

    goblin.setBehaviur(arriveSB);
  }

  public void dispose() {
    Gdx.app.log("Level", "Cleanup");
    tileMapRenderer.dispose();
  }
}
