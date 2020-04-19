package com.honor.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.honor.utils.Utils;

public class BloomEffect extends Effect {

  private HighlightFilter highlightFilter;
  private HorizontalBlurFilter hBlurFilter;
  private VerticalBlurFilter vBlurFilter;

  public BloomEffect() {
    super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    shaderProgram =
        new ShaderProgram(
            Utils.loadAsString("shaders/simple.vert"),
            Utils.loadAsString("shaders/combine.frag"));

    if (!shaderProgram.isCompiled()) {
      System.out
          .println("BloomEffect ShaderProgram couldn't compile: " + shaderProgram.getLog());
    }

    highlightFilter = new HighlightFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    hBlurFilter = new HorizontalBlurFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    vBlurFilter = new VerticalBlurFilter(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void capture() {
    framebuffer.begin();
  }

  @Override
  public void render(Texture texSrc) {
    // frameBuffer.end();
    highlightFilter.render(texSrc);
    hBlurFilter.render(highlightFilter.getTexture());
    vBlurFilter.render(hBlurFilter.getTexture());

    framebuffer.begin();
    shaderProgram.begin();

    // Screen texture
    texSrc.bind(0);
    shaderProgram.setUniformi("u_texture", 0);

    // Highlight texture
    vBlurFilter.getTexture().bind(1);
    shaderProgram.setUniformi("u_highlightTexture", 1);

    fullscreenQuad.render(shaderProgram, GL20.GL_TRIANGLES);

    shaderProgram.end();
    framebuffer.end();
    // This is required for LibGDX's SpriteBatch since it assumes,
    // that the active texture unit is set back to 0
    Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
  }

  @Override
  public void dispose() {
    highlightFilter.dispose();
    vBlurFilter.dispose();
    hBlurFilter.dispose();
    super.dispose();
  }

}
