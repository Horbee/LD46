package com.honor.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.honor.utils.Utils;

public class HighlightFilter extends Effect {

  public HighlightFilter(int width, int height) {
    super(width, height);

    shaderProgram = new ShaderProgram(Utils.loadAsString("shaders/simple.vert"),
        Utils.loadAsString("shaders/highlightFilter.frag"));

    if (shaderProgram.isCompiled() == false) {
      Gdx.app.log("HighlightFilter", "ShaderProgram couldn't compile: " + shaderProgram.getLog());
    }

  }


  public void render(Texture texSrc) {
    framebuffer.begin();

    shaderProgram.begin();
    shaderProgram.setUniformf("u_texture", 0);

    texSrc.bind();

    fullscreenQuad.render(shaderProgram, GL20.GL_TRIANGLES);

    shaderProgram.end();

    framebuffer.end();
  }

}
