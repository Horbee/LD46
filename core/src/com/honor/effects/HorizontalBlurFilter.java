package com.honor.effects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.honor.utils.Utils;

public class HorizontalBlurFilter extends Effect {

  public HorizontalBlurFilter(int width, int height) {
    super(width, height);

    shaderProgram =
        new ShaderProgram(
            Utils.loadAsString("shaders/horizontalBlur.vert"),
            Utils.loadAsString("shaders/blur.frag"));

    if (!shaderProgram.isCompiled()) {
      System.out.println(
          "HorizontalBlur ShaderProgram couldn't compile: " + shaderProgram.getLog());
    }
  }

  public void render(Texture texSrc) {
    framebuffer.begin();
    shaderProgram.begin();
    texSrc.bind();
    shaderProgram.setUniformi("u_texture", 0);
    shaderProgram.setUniformf("u_targetWidth", 300);
    fullscreenQuad.render(shaderProgram, GL20.GL_TRIANGLES);
    framebuffer.end();
  }
}
