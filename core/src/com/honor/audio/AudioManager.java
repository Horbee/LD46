package com.honor.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.honor.LD46Game;
import com.honor.assets.AssetLoader;
import com.honor.enums.Musics;
import com.honor.enums.Sounds;

public class AudioManager {

  private LD46Game game;

  private boolean soundsEnabled;
  private boolean musicEnabled;
  private float soundsVolume;
  private float musicVolume;

  private Sound hurt;
  private Sound shoot;
  private Sound explode;
  private Sound health;

  private Music mainTheme;

  public AudioManager(LD46Game game) {
    this.game = game;
    applyMusicPreferences();
    applySoundsPreferences();
  }

  public void applyMusicPreferences() {
    this.musicVolume = game.getPreferences().getMusicVolume();
    this.musicEnabled = game.getPreferences().isMusicEnabled();

    if (mainTheme != null) {
      if (musicEnabled == true) {
        play(Musics.MAIN_THEME);
      } else {
        mainTheme.stop();
      }
    }
  }
  
  public void applySoundsPreferences() {
    this.soundsVolume = game.getPreferences().getSoundVolume();
    this.soundsEnabled = game.getPreferences().isSoundEffectsEnabled();
  }

  public void init() {
    this.hurt = game.assetLoader.get(AssetLoader.HURT_SOUND);
    this.shoot = game.assetLoader.get(AssetLoader.SHOOT_SOUND);
    this.explode = game.assetLoader.get(AssetLoader.EXPLODE_SOUND);
    this.health = game.assetLoader.get(AssetLoader.HEALTH_SOUND);
    this.health = game.assetLoader.get(AssetLoader.HEALTH_SOUND);

    this.mainTheme = game.assetLoader.get(AssetLoader.MUSIC);
  }

  public void play(Sounds sound) {
    play(sound, 1.0f);
  }

  public void play(Sounds sound, float volume) {
    if (!soundsEnabled)
      return;
    float finalVolume = soundsVolume * volume;

    switch (sound) {
      case HURT:
        hurt.play(finalVolume);
        break;
      case SHOOT:
        shoot.play(finalVolume);
        break;
      case EXPLODE:
        explode.play(finalVolume);
        break;
      case HEALTH:
        health.play(finalVolume);
        break;
    }
  }

  public void play(Musics music) {
    if (!musicEnabled)
      return;

    switch (music) {
      case MAIN_THEME:
        mainTheme.stop();
        mainTheme.setVolume(musicVolume);
        mainTheme.play();
        break;
    }

  }

}
