package com.honor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.effects.HighlightFilter;
import com.honor.effects.HorizontalBlurFilter;
import com.honor.effects.PostProcessor;
import com.honor.effects.ShockwaveEffect;
import com.honor.effects.VerticalBlurFilter;
import com.honor.entities.EntityManager;
import com.honor.entities.Mob;
import com.honor.entities.Player;
import com.honor.levels.Level;
import com.honor.listeners.B2dContactListener;
import com.honor.ui.RectangleActor;
import com.honor.utils.CameraStyles;
import com.honor.utils.Constants;
import com.honor.utils.b2d.BodyBuilder;
import box2dLight.RayHandler;
import static com.honor.utils.Constants.PPM;

public class PlayScreen extends LD46GameScreenAdapter {

  private final int W_WIDTH = 64 * 20;
  private final int W_HEIGHT = 64 * 12;
  
  AtlasRegion hearth, score;

  BitmapFont balooSb;
  Level testLevel;

  OrthographicCamera camera;
  Viewport viewport;

  EntityManager entityManager;
  Player player;

  Matrix4 defaultProjection;

  Stage stage;
  
  RayHandler rayHandler;
  World world;
  Box2DDebugRenderer b2dr;
  private Matrix4 scaledB2dMatrix;

  Mob mob, target;

  PostProcessor postProcessor;
  ShockwaveEffect shockWave;
  HorizontalBlurFilter hBlur;
  VerticalBlurFilter vBlur;
  HighlightFilter highlightFilter;

  float spawnTimer = 0;
  float timer = 0;

  public PlayScreen(LD46Game game) {
    super(game);
    hearth = game.assetLoader.getRegionFromSpriteSheet("hearth");
    score = game.assetLoader.getRegionFromSpriteSheet("score");
    
    defaultProjection = new Matrix4();
    defaultProjection.setToOrtho(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), -1.0f,
        1.0f);

    balooSb = game.assetLoader.get(AssetLoader.BALOO_SB_FONT);

    testLevel = new Level(game, AssetLoader.TEST_LEVEL);

    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    viewport = new FitViewport(W_WIDTH, W_HEIGHT);
    camera.translate(W_WIDTH / 2, W_HEIGHT / 2);

    world = new World(new Vector2(0, 0), false);
    world.setContactListener(new B2dContactListener());
    b2dr = new Box2DDebugRenderer();
    scaledB2dMatrix = camera.combined;

    RayHandler.setGammaCorrection(true);
    rayHandler = new RayHandler(world);
    rayHandler.setAmbientLight(.5f);
    testLevel.createLights(rayHandler);

    for (RectangleMapObject obj : testLevel.walls) {
      Rectangle rect = obj.getRectangle();
      Body b = BodyBuilder.createRectangle(world, rect.x, rect.y, rect.width, rect.getHeight(),
          true, Constants.CATEGORY_WALL, Constants.WALL_COLLIDER());
      b.setUserData("WALL");
    }

    entityManager = game.entityManager;
    entityManager.setWorld(world);
    entityManager.setRayandler(rayHandler);
    player = new Player(game, world, testLevel.playerSpawn.x, testLevel.playerSpawn.y);
    player.setPlayScreen(this);
    // target = new Mob(player.body, 64 / PPM);
    entityManager.addEntity(player);

    postProcessor = new PostProcessor();
    shockWave = new ShockwaveEffect();
    hBlur = new HorizontalBlurFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    vBlur = new VerticalBlurFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    highlightFilter = new HighlightFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    highlightFilter.setDisabled(true);
    hBlur.setDisabled(true);
    vBlur.setDisabled(true);
    postProcessor.addEffect(shockWave).addEffect(highlightFilter).addEffect(hBlur).addEffect(vBlur);
    
    stage = new Stage(viewport);
    RectangleActor rect = new RectangleActor();
    rect.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    rect.setColor(Color.BLACK);
    stage.addActor(rect);
    stage.addAction(Actions.fadeOut(1f));
  }

  @Override
  public void show() {
    Gdx.app.log("PlayScreen", "Show");
  }

  @Override
  public void render(float delta) {
    update(delta);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    postProcessor.capture();

    // render level
    testLevel.render(camera);

    // render entities
    game.batch.begin();
    game.batch.setProjectionMatrix(camera.combined);
    entityManager.render(game.batch);
    game.batch.end();
    postProcessor.render();

    // render lights and box2d
    world.step(1 / 60f, 6, 2);
    scaledB2dMatrix = camera.combined.scl(PPM);
    rayHandler.setCombinedMatrix(scaledB2dMatrix, W_WIDTH / 2, W_HEIGHT / 2, W_WIDTH, W_HEIGHT);
    // b2dr.render(world, scaledB2dMatrix);
    rayHandler.render();
    
    if (player.died) {
      renderGameOverDied(delta);
    } else {
      // UI
      game.batch.begin();
      game.batch.setProjectionMatrix(defaultProjection);
      game.batch.draw(hearth, 10, Gdx.graphics.getHeight() - 60);
      balooSb.draw(game.batch, Integer.toString(player.health), 60, Gdx.graphics.getHeight() - 35);

      game.batch.draw(score, 10, Gdx.graphics.getHeight() - 100);
      balooSb.draw(game.batch, "" + (int) timer, 60, Gdx.graphics.getHeight() - 78);
      
      balooSb.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 120);
      game.batch.end();
    }

    stage.act(delta);
    stage.draw();
  }

  private void renderGameOverDied(float delta) {
    highlightFilter.setDisabled(false);
    hBlur.setDisabled(false);
    vBlur.setDisabled(false);

    game.batch.begin();
    game.batch.setProjectionMatrix(defaultProjection);
    balooSb.draw(game.batch, "Your score: " + (int) timer, Gdx.graphics.getWidth() / 2 - 50,
        350);
    game.batch.end();
  }

  private void update(float delta) {
    spawnTimer += delta;
    if (!player.died) {
      timer += delta;
      float rate = MathUtils.clamp(5.0f - (timer / 10f), 0.2f, 1000f);
      if (spawnTimer >= rate) {
        testLevel.createGoblin();
        spawnTimer -= rate;
      }
    }

    CameraStyles.lerpToTarget(camera, player.position);
    entityManager.update(delta);
    handleInput(delta);
    rayHandler.update();
    shockWave.update(delta);
  }

  private void handleInput(float delta) {

  }

  public void enableShockwave() {
    shockWave.enable(0.55f, 0.55f);
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public void dispose() {
    Gdx.app.log("PlayScreen", "Cleanup");
    testLevel.dispose();
    rayHandler.dispose();
    b2dr.dispose();
    world.dispose();
    hBlur.dispose();
    vBlur.dispose();
    highlightFilter.dispose();
    shockWave.dispose();
  }
}
