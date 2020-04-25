package com.honor.preferences;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.honor.LD46Game;
import com.honor.utils.ListUtils;

public class GamePreferences {

  private LD46Game game;

  private static final String PREFS_NAME = "ld46game";
  private static final String PREF_MUSIC_VOLUME = "volume";
  private static final String PREF_SOUND_VOL = "sound";
  private static final String PREF_MUSIC_ENABLED = "music.enabled";
  private static final String PREF_SOUND_ENABLED = "sound.enabled";
  private static final String PREF_RESOLUTION = "resolution";
  private static final String PREF_REFRESH_RATE = "refresh";
  private static final String PREF_FULLSCREEN = "fullscreen";

  private List<DisplayMode> displayModes;

  public GamePreferences(LD46Game game) {
    this.game = game;
    displayModes = Arrays.asList(Gdx.graphics.getDisplayModes());
  }

  protected Preferences getPrefs() {
    return Gdx.app.getPreferences(PREFS_NAME);
  }

  public DisplayMode getSavedDisplayMode() {
    return getDisplayMode(getResolution(), getRefreshRate());
  }

  public DisplayMode getDisplayMode(String resolution, int refreshRate) {
    int width = Integer.parseInt(resolution.split("x")[0]);
    int height = Integer.parseInt(resolution.split("x")[1]);
    return displayModes.stream()
        .filter(dm -> dm.width == width && dm.height == height && dm.refreshRate == refreshRate)
        .findFirst().orElse(null);
  }

  public float getMusicVolume() {
    return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
  }

  public void setMusicVolume(float volume) {
    getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
  }

  public boolean isSoundEffectsEnabled() {
    return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
  }

  public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
    getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
  }

  public boolean isMusicEnabled() {
    return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
  }

  public void setMusicEnabled(boolean musicEnabled) {
    getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
  }

  public boolean isFullscreenEnabled() {
    return getPrefs().getBoolean(PREF_FULLSCREEN, false);
  }

  public void setFullscreenEnabled(boolean fullscreenEnabled) {
    getPrefs().putBoolean(PREF_FULLSCREEN, fullscreenEnabled);
  }

  public float getSoundVolume() {
    return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
  }

  public void setSoundVolume(float volume) {
    getPrefs().putFloat(PREF_SOUND_VOL, volume);
  }

  public String getResolution() {
    return getPrefs().getString(PREF_RESOLUTION, "1280x768");
  }

  public void setResolution(String resolution) {
    getPrefs().putString(PREF_RESOLUTION, resolution);
  }

  public int getRefreshRate() {
    return getPrefs().getInteger(PREF_REFRESH_RATE, 72);
  }

  public void setRefreshRate(int rate) {
    getPrefs().putInteger(PREF_REFRESH_RATE, rate);
  }

  public Array<String> getAvailableResolutions() {
    List<String> javaList = displayModes.stream().map(dm -> dm.width + "x" + dm.height).distinct()
        .collect(Collectors.toList());
    return ListUtils.toArray(javaList);
  }

  public Array<Integer> getAvailableRefreshRates() {
    return ListUtils.toArray(
        displayModes.stream().map(dm -> dm.refreshRate).distinct().collect(Collectors.toList()));
  }

  public Array<Integer> getAvailableRefreshRates(String resolution) {
    int width = Integer.parseInt(resolution.split("x")[0]);
    int height = Integer.parseInt(resolution.split("x")[1]);
    return ListUtils
        .toArray(displayModes.stream().filter(dm -> dm.width == width && dm.height == height)
            .map(dm -> dm.refreshRate).distinct().collect(Collectors.toList()));
  }

  public void applyAudioPreferences(boolean musicEnabled, boolean soundEffectsEnabled,
      float musicVolume, float soundVolume) {
    boolean isMusicEnabledChanged = isMusicEnabled() != musicEnabled;
    boolean isSoundsEnabledChanged = isSoundEffectsEnabled() != soundEffectsEnabled;
    boolean isMusicVolumeChanged = getMusicVolume() != musicVolume;
    boolean isSoundsVolumeChanged = getSoundVolume() != soundVolume;

    if (isMusicEnabledChanged || isMusicVolumeChanged) {
      setMusicEnabled(musicEnabled);
      setMusicVolume(musicVolume);
      getPrefs().flush();
      game.audioManager.applyMusicPreferences();
    }

    if (isSoundsEnabledChanged || isSoundsVolumeChanged) {
      setSoundEffectsEnabled(soundEffectsEnabled);
      setSoundVolume(soundVolume);
      getPrefs().flush();
      game.audioManager.applySoundsPreferences();
    }

  }

  public void applyGraphicsSettings(boolean fullscreen, String resolution, int refreshRate) {
    boolean isFullscreenChanged = isFullscreenEnabled() != fullscreen;
    boolean isResolutionChanged = !getResolution().equals(resolution);
    boolean isRefreshRateChanged = getRefreshRate() != refreshRate;

    if (isFullscreenChanged || isResolutionChanged || isRefreshRateChanged) {
      if (fullscreen) {
        Gdx.graphics.setFullscreenMode(getDisplayMode(resolution, refreshRate));
        Gdx.graphics.setVSync(true);
      } else {
        int width = Integer.parseInt(resolution.split("x")[0]);
        int height = Integer.parseInt(resolution.split("x")[1]);
        Gdx.graphics.setWindowedMode(width, height);
      }
      setFullscreenEnabled(fullscreen);
      setResolution(resolution);
      setRefreshRate(refreshRate);
      getPrefs().flush();
    }
  }

}
