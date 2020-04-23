package com.honor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.enums.Musics;
import com.honor.enums.Screens;

public class MenuScreen extends LD46GameScreenAdapter {

  private Texture cover, start;
  private float time = 0;

  private Stage stage;

  public MenuScreen(LD46Game game) {
    super(game);

    stage = new Stage(new ScreenViewport());
    cover = game.assetLoader.get(AssetLoader.COVER_TEX);
    start = game.assetLoader.get(AssetLoader.START_TEX);
    
    game.audioManager.init();
    game.audioManager.play(Musics.MAIN_THEME);
  }

  @Override
  public void show() {
    Gdx.app.log("MenuScreen", "Show");
    
    stage.clear();
    Gdx.input.setInputProcessor(stage);
    
    Table table = new Table();
    table.setFillParent(true);
    table.setDebug(true);
    stage.addActor(table);

    Skin skin = game.assetLoader.get(AssetLoader.UI_SKIN);

    TextButton preferences = new TextButton("Preferences", skin);
    TextButton exit = new TextButton("Exit", skin);

    preferences.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeScreen(Screens.SETTINGS);
      }
    });
    
    exit.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        Gdx.app.exit();
      }
    });
    
    table.add(preferences).fillX().uniformX();
    table.row().pad(10, 0, 0, 0);
    table.add(exit).fillX().uniformX();
  }

  public void update(float delta) {
    time += delta;
    stage.act(delta);
  }

  @Override
  public void render(float delta) {
    update(delta);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    float scale = (float) Math.abs(Math.sin(time)) * 40;

    game.batch.begin();
    game.batch.draw(cover, 0, 0);
    game.batch.draw(start, 1280 / 2 - 300, 150f, start.getWidth() + scale,
        start.getHeight() + scale);
    game.batch.end();

    stage.draw();

    /*if (Gdx.input.justTouched()) {
      game.changeScreen(Screens.PLAY);
    }*/
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void dispose() {
    super.dispose();
    Gdx.app.log("MenuScreen", "Cleanup");
    stage.dispose();
  }
  

}
