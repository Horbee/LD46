package com.honor.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.honor.utils.Utils;

public class ShockwaveEffect extends Effect {

  private float time;
  private float posX, posY;

  public ShockwaveEffect() {
    super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    shaderProgram =
        new ShaderProgram(
            Utils.loadAsString("shaders/simple.vert"),
            Utils.loadAsString("shaders/shockwave.frag"));

    if (!shaderProgram.isCompiled()) {
      System.out
          .println("Shockwave ShaderProgram couldn't compile: " + shaderProgram.getLog());
    }

    time = 0f;
    disabled = true;
    posX = 0f;
    posY = 0f;
  }

  public void capture() {
    if (disabled) {
      return;
    }

    framebuffer.begin();
  }

  @Override
  public void render(Texture texSrc) {
    if(disabled) {
      return;
    }

//    frameBuffer.end();
    framebuffer.begin();
    shaderProgram.begin();

    texSrc.bind();
    shaderProgram.setUniformi("u_texture", 0);

    shaderProgram.setUniformf("u_time", time);
    shaderProgram.setUniformf("u_center", posX, posY);

    fullscreenQuad.render(shaderProgram, GL20.GL_TRIANGLES);

    shaderProgram.end();
    framebuffer.end();
  }

  public void update(float deltaTime) {
    if(disabled) {
      return;
    }

    time += .3f * deltaTime;
    if (time > 1f) {
      System.out.println("disable shockwave effect");
      time = 0;
      disabled = true;
    }
  }


  public void enable(float x, float y) {
    this.posX = x;
    this.posY = y;
    this.disabled = false;
  }

}
