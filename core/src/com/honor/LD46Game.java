package com.honor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.honor.assets.AssetLoader;
import com.honor.audio.AudioManager;
import com.honor.entities.EntityManager;
import com.honor.enums.Screens;
import com.honor.preferences.GamePreferences;
import com.honor.screens.LoadingScreen;
import com.honor.screens.MenuScreen;
import com.honor.screens.PlayScreen;
import com.honor.screens.SettingsScreen;
import com.honor.utils.Utils;

public class LD46Game extends Game {

  private MenuScreen menuScreen;
  private LoadingScreen loadingScreen;
  private SettingsScreen settingsScreen;
  private PlayScreen playScreen;
  private GamePreferences preferences;

  public SpriteBatch batch;
  public AssetLoader assetLoader;
  public EntityManager entityManager;
  public AudioManager audioManager;

  @Override
  public void create() {
    Gdx.app.log("OpenGL", Utils.getGLVersion());
    Gdx.app.log("Renderer", Utils.getRenderer());
    preferences = new GamePreferences(this);
    
    DisplayMode dm = preferences.getSavedDisplayMode();
    if (preferences.isFullscreenEnabled()) {
      if (dm != null) {
        Gdx.graphics.setFullscreenMode(dm);
      }
    } else {
      if (dm != null) {
        Gdx.graphics.setWindowedMode(dm.width, dm.height);
      }
    }
    
    batch = new SpriteBatch();

    assetLoader = new AssetLoader();
    assetLoader.queueAssets();
    
    audioManager = new AudioManager(this);

    entityManager = new EntityManager();

    loadingScreen = new LoadingScreen(this);
    setScreen(loadingScreen);
  }

  @Override
  public void render() {
    super.render();
  }

  public void changeScreen(Screens screen) {
    switch (screen) {
      case MENU:
        if (menuScreen == null)
          menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen);
        break;
      case LOADING:
        if (loadingScreen == null)
          loadingScreen = new LoadingScreen(this);
        this.setScreen(loadingScreen);
        break;
      case SETTINGS:
        if (settingsScreen == null)
          settingsScreen = new SettingsScreen(this);
        this.setScreen(settingsScreen);
        break;
      case PLAY:
        if (playScreen == null)
          playScreen = new PlayScreen(this);
        this.setScreen(playScreen);
        break;
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    Gdx.app.log("LD46Game", "Cleanup");
    if (menuScreen != null) menuScreen.dispose();
    if (settingsScreen != null) settingsScreen.dispose();
    if (playScreen != null) playScreen.dispose();
    
    batch.dispose();
    assetLoader.dispose();
  }
  
  public GamePreferences getPreferences() {
    return preferences;
  }
}
