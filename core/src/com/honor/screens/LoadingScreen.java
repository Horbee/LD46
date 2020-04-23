package com.honor.screens;

import com.badlogic.gdx.Gdx;
import com.honor.LD46Game;
import com.honor.enums.Screens;

public class LoadingScreen extends LD46GameScreenAdapter {

  public LoadingScreen(LD46Game game) {
    super(game);
  }

  @Override
  public void show() {
    Gdx.app.log("LoadingScreen", "Show");
  }

  @Override
  public void render(float delta) {
    if (game.assetLoader.update()) {
      game.changeScreen(Screens.MENU);
    }

    Gdx.app.log("LoadingScreen", "Loading Assets: " + game.assetLoader.getProgress());
  }


}
