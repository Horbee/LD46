package com.honor.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {

  public static final String COVER_TEX = "cover.png";
  public static final String START_TEX = "start.png";
  public static final String EXIT_TEX = "sprites/exit.png";
  public static final String SPRITES_ATLAS = "sprites/sprites.atlas";
  public static final String TEST_LEVEL = "levels/testlevel.tmx";
  public static final String BALOO_SB_FONT = "fonts/BalooBhaina2-SemiBold.ttf";

  public static final String HURT_SOUND = "hurt.wav";
  public static final String SHOOT_SOUND = "shoot.wav";
  public static final String EXPLODE_SOUND = "explode.mp3";
  public static final String HEALTH_SOUND = "health.wav";
  public static final String MUSIC = "music.mp3";
  public static final String UI_SKIN = "skin/comic-ui.json";

  private AssetManager assetManager;

  public AssetLoader() {
    assetManager = new AssetManager();
  }

  public void queueAssets() {
    // Loading UI Skin
    assetManager.load(UI_SKIN, Skin.class);

    // Loading textures
    TextureParameter textureParam = new TextureParameter();
    textureParam.magFilter = TextureFilter.Linear;
    textureParam.minFilter = TextureFilter.Linear;

    assetManager.load(COVER_TEX, Texture.class, textureParam);
    assetManager.load(START_TEX, Texture.class, textureParam);
    assetManager.load(EXIT_TEX, Texture.class, textureParam);

    // Texture Atlasses
    assetManager.load(SPRITES_ATLAS, TextureAtlas.class);

    // Loading Sounds and Music
    assetManager.load(HURT_SOUND, Sound.class);
    assetManager.load(SHOOT_SOUND, Sound.class);
    assetManager.load(EXPLODE_SOUND, Sound.class);
    assetManager.load(HEALTH_SOUND, Sound.class);
    assetManager.load(MUSIC, Music.class);

    // Loading TMX levels
    assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    assetManager.load(TEST_LEVEL, TiledMap.class);

    // Loading Fonts
    FileHandleResolver resolver = new InternalFileHandleResolver();
    assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
    assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

    FreeTypeFontLoaderParameter balooFont = new FreeTypeFontLoaderParameter();
    balooFont.fontFileName = BALOO_SB_FONT;
    balooFont.fontParameters.size = 24;
    assetManager.load(BALOO_SB_FONT, BitmapFont.class, balooFont);
  }

  public AtlasRegion getRegionFromSpriteSheet(String filePath) {
    return get(AssetLoader.SPRITES_ATLAS, TextureAtlas.class).findRegion(filePath);
  }

  public boolean update() {
    return assetManager.update();
  }

  public float getProgress() {
    return assetManager.getProgress();
  }

  public <T> T get(String fileName) {
    return assetManager.get(fileName);
  }
  
  public <T> T get(String fileName, Class<T> type) {
    return assetManager.get(fileName, type);
  }

  public void dispose() {
    Gdx.app.log("AssetLoader", "Cleanup");
    assetManager.dispose();
  }

}
