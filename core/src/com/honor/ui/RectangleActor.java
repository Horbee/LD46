package com.honor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RectangleActor extends Actor {

  private ShapeRenderer renderer;
  private boolean projectionMatrixSet;
  
  public RectangleActor() {
    renderer = new ShapeRenderer();
    projectionMatrixSet = false;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.end();
    if(!projectionMatrixSet){
      renderer.setProjectionMatrix(batch.getProjectionMatrix());
    }
    
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    renderer.begin(ShapeType.Filled);
    Color c = getColor();
    renderer.setColor(c.r, c.g, c.b, c.a * parentAlpha);
    renderer.rect(getX(), getY(), getWidth(), getHeight());
    renderer.end();
    Gdx.gl.glDisable(GL20.GL_BLEND);

    batch.begin();
  }
}
