package com.honor.screens;

import com.badlogic.gdx.Screen;
import com.honor.LD46Game;

public class LD46GameScreenAdapter implements Screen {

  protected LD46Game game;
  
  public LD46GameScreenAdapter(LD46Game game) {
    this.game = game;
  }
  
  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    dispose();
  }

  @Override
  public void dispose() {
  }

}
