package com.honor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.effects.HighlightFilter;
import com.honor.effects.HorizontalBlurFilter;
import com.honor.effects.PostProcessor;
import com.honor.effects.VerticalBlurFilter;
import com.honor.enums.Screens;

public class SettingsScreen extends LD46GameScreenAdapter {

  private Stage stage;

  private Label titleLabel;
  private Label fullscreenLabel;
  private Label resolutionLabel;
  private Label resfreshRateLabel;
  private Label volumeMusicLabel;
  private Label volumeSoundLabel;
  private Label musicOnOffLabel;
  private Label soundOnOffLabel;
  
  private Texture cover;
  private PostProcessor postProcessor;
  private HorizontalBlurFilter hBlur;
  private VerticalBlurFilter vBlur;
  private HighlightFilter highlightFilter;
  
  public SettingsScreen(LD46Game game) {
    super(game);

    stage = new Stage(new ScreenViewport());
    cover = game.assetLoader.get(AssetLoader.COVER_TEX);
    postProcessor = new PostProcessor();
    hBlur = new HorizontalBlurFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    vBlur = new VerticalBlurFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    highlightFilter = new HighlightFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    postProcessor.addEffect(highlightFilter).addEffect(hBlur).addEffect(vBlur);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
    
    stage.clear();
    
    Table table = new Table();
    table.setFillParent(true);
    table.setDebug(true);
    stage.addActor(table);

    Skin skin = game.assetLoader.get(AssetLoader.UI_SKIN);
    
    final CheckBox fullscreenCheckbox = new CheckBox(null, skin);
    fullscreenCheckbox.setChecked(game.getPreferences().isFullscreenEnabled());
    
    // Resolutions
    final SelectBox<String> resolutions = new SelectBox<>(skin);
    resolutions.setItems(game.getPreferences().getAvailableResolutions());
    resolutions.setSelected(game.getPreferences().getResolution());

    // Refresh Rates
    final SelectBox<Integer> refreshRates = new SelectBox<>(skin);
    refreshRates.setItems(game.getPreferences().getAvailableRefreshRates());
    refreshRates.setSelected(game.getPreferences().getRefreshRate());  
    
    resolutions.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        int selectedRefreshRate = refreshRates.getSelected();
        refreshRates.setItems(game.getPreferences().getAvailableRefreshRates(resolutions.getSelected()));
        if (refreshRates.getItems().contains(selectedRefreshRate, true))
          refreshRates.setSelected(selectedRefreshRate);
        else
          refreshRates.setSelectedIndex(0);
      }
    });

    
    final Slider volumeSoundsSlider = new Slider(0f, 1f, 0.1f, false, skin);
    volumeSoundsSlider.setValue(game.getPreferences().getSoundVolume());

    final CheckBox soundsCheckbox = new CheckBox(null, skin);
    soundsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());

    final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
    volumeMusicSlider.setValue(game.getPreferences().getMusicVolume());

    final CheckBox musicCheckbox = new CheckBox(null, skin);
    musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());

    final TextButton applyButton = new TextButton("Apply", skin);
    applyButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.getPreferences().applyAudioPreferences(
            musicCheckbox.isChecked(),
            soundsCheckbox.isChecked(),
            volumeMusicSlider.getValue(),
            volumeSoundsSlider.getValue()
        );
        game.getPreferences().applyGraphicsSettings(
            fullscreenCheckbox.isChecked(),
            resolutions.getSelected(),
            refreshRates.getSelected()
        );
      }
    });

    
    final TextButton backButton = new TextButton("Back", skin);
    backButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeScreen(Screens.MENU);
      }
    });

    
    skin.getFont("font").getData().setScale(1.5f, 1.5f);
    
    titleLabel = new Label("Preferences", skin, "title");
    fullscreenLabel = new Label("Fullscreen", skin);
    resolutionLabel = new Label("Resolution", skin);
    resfreshRateLabel = new Label("Refresh Rate", skin);
    volumeMusicLabel = new Label("Music volume", skin);
    volumeSoundLabel = new Label("Sound volume", skin);
    musicOnOffLabel = new Label("Music", skin);
    soundOnOffLabel = new Label("Sound", skin);

    table.add(titleLabel).colspan(2);
    table.row().pad(10, 0, 0, 40);
    table.add(fullscreenLabel).left();
    table.add(fullscreenCheckbox);
    table.row().pad(10, 0, 0, 40);
    table.add(resolutionLabel).left();
    table.add(resolutions);
    table.row().pad(10, 0, 0, 40);
    table.add(resfreshRateLabel).left();
    table.add(refreshRates);
    table.row().pad(10, 0, 0, 40);
    table.add(volumeMusicLabel).left();
    table.add(volumeMusicSlider);
    table.row().pad(10, 0, 0, 40);
    table.add(musicOnOffLabel).left();
    table.add(musicCheckbox);
    table.row().pad(10, 0, 0, 40);
    table.add(volumeSoundLabel).left();
    table.add(volumeSoundsSlider);
    table.row().pad(10, 0, 0, 40);
    table.add(soundOnOffLabel).left();
    table.add(soundsCheckbox);
    table.row().pad(10, 0, 0, 40);
    table.add(backButton).left();
    table.add(applyButton).right();

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    postProcessor.capture();
    game.batch.begin();
    game.batch.draw(cover, 0, 0);
    game.batch.end();
    postProcessor.render();
    
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void dispose() {
    super.dispose();
    Gdx.app.log("SettingsScreen", "Cleanup");
    stage.dispose();
    hBlur.dispose();
    vBlur.dispose();
    highlightFilter.dispose();
    postProcessor.dispose();
  }
  
  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

}
