package com.honor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.honor.assets.AssetLoader;
import com.honor.entities.EntityManager;
import com.honor.screens.LoadingScreen;
import com.honor.utils.Utils;

public class LD46Game extends Game {

  public SpriteBatch batch;
  public AssetLoader assetLoader;
  public EntityManager entityManager;

  @Override
  public void create() {
    Gdx.app.log("OpenGL", Utils.getGLVersion());
    Gdx.app.log("Renderer", Utils.getRenderer());
    batch = new SpriteBatch();

    assetLoader = new AssetLoader();
    assetLoader.queueAssets();
    
    entityManager = new EntityManager();
    
    setScreen(new LoadingScreen(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    super.dispose();
    Gdx.app.log("LD46Game", "Cleanup");
    batch.dispose();
    assetLoader.dispose();
  }
}
