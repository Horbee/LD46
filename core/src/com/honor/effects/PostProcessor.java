package com.honor.effects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.honor.utils.Utils;

public class PostProcessor {

  private List<Effect> effects;
  private FrameBuffer framebuffer;
  private Mesh fullscreenQuad;
  protected ShaderProgram shaderProgram;
  
  public PostProcessor() {
    effects = new ArrayList<>();

    framebuffer =
        new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    
    fullscreenQuad = new Mesh(true, 4, 6, VertexAttribute.Position(),
        VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));

    // Texcoords are flipped vertically
    fullscreenQuad.setVertices(new float[] {
       -1f, -1f, 0, 1, 1, 1, 1, 0, 0, // 3 position, 4 color, 2 texCoords
        1f, -1f, 0, 1, 1, 1, 1, 1, 0,
        1f,  1f, 0, 1, 1, 1, 1, 1, 1,
       -1f,  1f, 0, 1, 1, 1, 1, 0, 1
    });
    
    fullscreenQuad.setIndices(new short[] {
        0, 1, 2, 2, 3, 0
    });
    
    shaderProgram = new ShaderProgram(
        Utils.loadAsString("shaders/simple.vert"),
        Utils.loadAsString("shaders/simple.frag"));

    if (shaderProgram.isCompiled() == false) {
      Gdx.app.log("PostProcessor", "ShaderProgram couldn't compile: " + shaderProgram.getLog());
    }
    
  }
  
  public PostProcessor addEffect(Effect effect) {
    this.effects.add(effect);
    return this;
  }
  
  public void capture() {
    if (getEnabledEffects().isEmpty()) return;
    
    
    framebuffer.begin();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }
  
  public void render() {
    if (getEnabledEffects().isEmpty()) return;

    framebuffer.end();
    
    List<Effect> enabledEffects = getEnabledEffects();
    for (int i = 0; i < enabledEffects.size(); i++) {
      Effect e = enabledEffects.get(i);
      
      if (i == 0) {
        e.render(framebuffer.getColorBufferTexture());
      } else {
        e.render(enabledEffects.get(i - 1).getTexture());
      }
    }
    
    shaderProgram.begin();
    // TODO: handle if enabledEffect.size is 0
    enabledEffects.get(enabledEffects.size() - 1).getTexture().bind();
    shaderProgram.setUniformf("u_texture", 0);
    fullscreenQuad.render(shaderProgram, GL20.GL_TRIANGLES);
    shaderProgram.end();
    
  }
  
  private List<Effect> getEnabledEffects() {
    return this.effects.stream().filter(e -> !e.disabled).collect(Collectors.toList());
  }
  
  public void dispose() {
    framebuffer.dispose();
    fullscreenQuad.dispose();
    shaderProgram.dispose();
  }

}
