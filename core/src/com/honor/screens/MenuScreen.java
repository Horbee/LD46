package com.honor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;

public class MenuScreen extends LD46GameScreenAdapter {

  Texture cover, start;
  float time = 0;
  Music music;
  
  public MenuScreen(LD46Game game) {
    super(game);
    cover = game.assetLoader.get(AssetLoader.COVER_TEX);
    start = game.assetLoader.get(AssetLoader.START_TEX);
    music = game.assetLoader.get(AssetLoader.MUSIC);
  }

  @Override
  public void show() {
    Gdx.app.log("MenuScreen", "Show");
    music.setVolume(0.5f);
    music.play();
  }
  
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    time += delta;
    
    float scale = (float) Math.abs(Math.sin(time)) * 40;
    
    game.batch.begin();
    game.batch.draw(cover, 0, 0);
    game.batch.draw(start, 1280 / 2 - 300, 150f, start.getWidth() + scale, start.getHeight() + scale);
    game.batch.end();

    if (Gdx.input.justTouched()) {
      game.setScreen(new PlayScreen(game));
    }
    
  }
  
}
