package com.honor.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.honor.entities.Goblin;
import com.honor.entities.HealthLight;
import com.honor.entities.Player;
import com.honor.entities.Projectile;

public class B2dContactListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    Fixture fa = contact.getFixtureA();
    Fixture fb = contact.getFixtureB();

    if (fa == null || fb == null)
      return;
    if (fa.getBody().getUserData() == null || fb.getBody().getUserData() == null)
      return;

    if (projectileAndWallCollision(fa, fb)) {
      getFromContact(fa, fb, Projectile.class).remove();
      return;
    }
    
    if (collisionOfObjects(fa, fb, Projectile.class, Goblin.class)) {
      getFromContact(fa, fb, Goblin.class).takeDamage();
      getFromContact(fa, fb, Projectile.class).remove();
      return;
    }
    
    if (collisionOfObjects(fa, fb, Player.class, Goblin.class)) {
      getFromContact(fa, fb, Player.class).takeDamage();
      getFromContact(fa, fb, Goblin.class).remove();
      return;
    }
    
    if (collisionOfObjects(fa, fb, Player.class, HealthLight.class)) {
      getFromContact(fa, fb, Player.class).heal();
      getFromContact(fa, fb, HealthLight.class).shouldRemove = true;
      return;
    }


  }

  @Override
  public void endContact(Contact contact) {

  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }

  private <A, B extends Object> boolean collisionOfObjects(Fixture fa, Fixture fb, Class<A> typea,
      Class<B> typeb) {

    boolean a = typea.isInstance(fa.getBody().getUserData())
        || typea.isInstance(fb.getBody().getUserData());
    boolean b = typeb.isInstance(fa.getBody().getUserData())
        || typeb.isInstance(fb.getBody().getUserData());

    return a && b;
  }

  private boolean projectileAndWallCollision(Fixture fa, Fixture fb) {
    boolean proj = fa.getBody().getUserData() instanceof Projectile
        || fb.getBody().getUserData() instanceof Projectile;
    boolean wall =
        fa.getBody().getUserData().equals("WALL") || fb.getBody().getUserData().equals("WALL");
    return proj && wall;
  }

  private <T extends Object> T getFromContact(Fixture fa, Fixture fb, Class<T> type) {
    if (type.isInstance(fa.getBody().getUserData()))
      return type.cast(fa.getBody().getUserData());
    if (type.isInstance(fb.getBody().getUserData()))
      return type.cast(fb.getBody().getUserData());
    return null;
  }

}
