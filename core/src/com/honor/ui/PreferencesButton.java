package com.honor.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.honor.LD46Game;
import com.honor.enums.Screens;

public class PreferencesButton extends ImageButton {

  public RotateByAction rotateAction;
  public ScaleToAction scaleAction, scaleToOriginal;
  public SequenceAction scaleSequenceAction;
  public ParallelAction paraAction;
  public RepeatAction repeat;
  
  public PreferencesButton(Drawable imageUp, Drawable imageDown, LD46Game game) {
    super(imageUp, imageDown);

    addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeScreen(Screens.SETTINGS);
      }
    });

    setOrigin(Align.center);
    setTransform(true);

    scaleToOriginal = Actions.scaleTo(1f, 1f, 0.5f, Interpolation.sine);
    rotateAction = Actions.rotateBy(90, 1f);
    scaleSequenceAction = new SequenceAction(
        Actions.scaleTo(1.1f, 1.1f, 0.5f, Interpolation.sine),
        Actions.scaleTo(1.0f, 1.0f, 0.5f, Interpolation.sine)
    );
    paraAction = new ParallelAction(rotateAction, scaleSequenceAction); 
        
    repeat = Actions.forever(paraAction);
    
    addListener(new InputListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);

        if (getActions().contains(repeat, true)) {
          repeat.restart();
        } else {
          repeat.reset();
          repeat.setAction(paraAction);
          addAction(repeat);
        }
      }

      @Override
      public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
        if (getActions().contains(repeat, true)) {
          repeat.finish();
          removeAction(repeat);
        }
        if (getActions().contains(scaleToOriginal, true) == false) {
          scaleToOriginal.restart();
          addAction(scaleToOriginal);
        }
      }

    });
    
  }

}
