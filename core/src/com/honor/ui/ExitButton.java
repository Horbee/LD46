package com.honor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class ExitButton extends ImageButton {

  public ExitButton(Drawable imageUp) {
    super(imageUp);
    setTransform(true);
    setOrigin(Align.center);
    
    this.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        getStage().addAction(
            Actions.sequence(
                Actions.fadeOut(1f),
                Actions.run(() -> Gdx.app.exit())));
      }
    });
    
    addListener(new InputListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        clearActions();
        addAction(Actions.scaleTo(1.1f, 1.1f, 0.5f, Interpolation.sine));
      }

      @Override
      public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
        clearActions();
        addAction(Actions.scaleTo(1f, 1f, 1f));
      }

    });
    
  }
  
}
