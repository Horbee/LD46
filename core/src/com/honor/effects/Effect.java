package com.honor.effects;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Effect {

  protected FrameBuffer framebuffer;
  protected Mesh fullscreenQuad;
  protected ShaderProgram shaderProgram;

  protected boolean disabled = false;

  public Effect(int width, int height) {
    framebuffer = new FrameBuffer(Format.RGBA8888, width, height, true);

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
  }

  public Texture getTexture() {
    return framebuffer.getColorBufferTexture();
  }
  
  public void render(Texture texSrc) { }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }
  
  public void dispose() {
    framebuffer.dispose();
    fullscreenQuad.dispose();
    shaderProgram.dispose();
  }
  
  
}
