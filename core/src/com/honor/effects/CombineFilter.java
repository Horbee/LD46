package com.honor.effects;

import static com.badlogic.gdx.graphics.GL20.GL_TEXTURE0;
import static com.badlogic.gdx.graphics.GL20.GL_TEXTURE1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.honor.utils.Utils;

public class CombineFilter extends Effect {

  public CombineFilter(int width, int height) {
    super(width, height);

    shaderProgram =
        new ShaderProgram(
            Utils.loadAsString("shaders/simple.vert"),
            Utils.loadAsString("shaders/combine.frag"));

    if (!shaderProgram.isCompiled()) {
      System.out
          .println("Combine ShaderProgram couldn't compile: " + shaderProgram.getLog());
    }
  }

  public void bindSecondTexture(TextureRegion textureRegion, String shaderUniformTextureName) {
    shaderProgram.begin();
    shaderProgram.setUniformi(shaderUniformTextureName, 1);
    Gdx.gl.glActiveTexture(GL_TEXTURE1);
    textureRegion.flip(false, true);
    textureRegion.getTexture().bind();
    System.out.println(
        "U: " + textureRegion.getU() + ", " +
        "V: " + textureRegion.getV() + ", " +
        "U2: " + textureRegion.getU2() + ", " +
        "V2: " + textureRegion.getV2());
    Gdx.gl.glActiveTexture(GL_TEXTURE0);
    shaderProgram.end();
  }

}
