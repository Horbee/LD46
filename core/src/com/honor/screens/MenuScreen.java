package com.honor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.enums.Musics;
import com.honor.enums.Screens;
import com.honor.ui.ExitButton;
import com.honor.ui.PreferencesButton;

public class MenuScreen extends LD46GameScreenAdapter {

  private Texture coverTex, start, exit;
  private TextureRegionDrawable cover, preferencesUp, preferencesDown, exitUp;

  private Stage stage;

  public MenuScreen(LD46Game game) {
    super(game);

    stage = new Stage(new ScreenViewport());
    start = game.assetLoader.get(AssetLoader.START_TEX);
    coverTex = game.assetLoader.get(AssetLoader.COVER_TEX);
    exit = game.assetLoader.get(AssetLoader.EXIT_TEX);

    game.audioManager.init();
    game.audioManager.play(Musics.MAIN_THEME);

    preferencesUp = new TextureRegionDrawable(game.assetLoader.getRegionFromSpriteSheet("cog"));
    preferencesDown =
        new TextureRegionDrawable(game.assetLoader.getRegionFromSpriteSheet("cogDown"));

    exitUp = new TextureRegionDrawable(exit);
    cover = new TextureRegionDrawable(coverTex);
  }

  @Override
  public void show() {
    Gdx.app.log("MenuScreen", "Show");

    stage.clear();
    Gdx.input.setInputProcessor(stage);

    Table table = new Table();
    table.setFillParent(true);
    // table.setDebug(true);
    table.setBackground(cover);
    table.setTouchable(Touchable.enabled);
    table.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(() -> game.changeScreen(Screens.PLAY))));
      }
    });
    stage.addActor(table);

    Image startImage = new Image(start);
    startImage.setPosition(Gdx.graphics.getWidth() / 2, 200, Align.center);
    startImage.addAction(
        Actions.forever(
          Actions.sequence(
            Actions.scaleTo(1.1f, 1.1f, 0.7f, Interpolation.sine),
            Actions.scaleTo(1.0f, 1.0f, 0.7f, Interpolation.sine))
    ));
    stage.addActor(startImage);
    
    PreferencesButton preferences = new PreferencesButton(preferencesUp, preferencesDown, game);
    ExitButton exit = new ExitButton(exitUp);
    
    table.add(exit).expand().top().right().pad(25);
    table.row();
    table.add(preferences).bottom().left().pad(25);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    stage.act(delta);
    stage.draw();
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
