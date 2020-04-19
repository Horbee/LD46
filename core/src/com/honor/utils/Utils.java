package com.honor.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;

public class Utils {

  public static String loadAsString(String fileName) {
    return Gdx.files.local(fileName).readString();
  }

  public static String getGLVersion() {
    return Gdx.graphics.getGLVersion().getMajorVersion() + "."
        + Gdx.graphics.getGLVersion().getMinorVersion();
  }

  public static String getRenderer() {
    return Gdx.graphics.getGLVersion().getRendererString();
  }
  
  public static Mesh createFullScreenQuad() {
    Mesh fullscreenQuad = new Mesh(true, 4, 6, VertexAttribute.Position(),
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
    
    return fullscreenQuad;
  }
  
  public static Mesh createQuad(float width, float height) {
    Mesh fullscreenQuad = new Mesh(true, 4, 6, VertexAttribute.Position(),
        VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));

    // Texcoords are flipped vertically
    fullscreenQuad.setVertices(new float[] {
       -width / 2f, -height / 2f, 0, 1, 1, 1, 1, 0, 0, // 3 position, 4 color, 2 texCoords
        width / 2f, -height / 2f, 0, 1, 1, 1, 1, 1, 0,
        width / 2f,  height / 2f, 0, 1, 1, 1, 1, 1, 1,
       -width / 2f,  height / 2f, 0, 1, 1, 1, 1, 0, 1
    });
    
    fullscreenQuad.setIndices(new short[] {
        0, 1, 2, 2, 3, 0
    });
    
    return fullscreenQuad;
  }

}
