package com.honor.entities;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import box2dLight.RayHandler;

public class EntityManager {

  private List<Entity> entities;
  private List<Projectile> projectiles;
  private List<HealthLight> heals;
  private World world;
  private RayHandler rayHandler;
  
  public boolean stop = false;
  
  public EntityManager() {
    entities = new ArrayList<>();
    projectiles = new ArrayList<>();
    heals = new ArrayList<>();
  }

  public void setWorld(World world) {
    this.world = world;
  }
  
  public void setRayandler(RayHandler rayHandler) {
    this.rayHandler = rayHandler;
  }

  public void addEntity(Entity e) {
    entities.add(e);
  }

  public void addProjectile(Projectile p) {
    projectiles.add(p);
  }
  
  public void addHeal(HealthLight healthLight) {
    heals.add(healthLight);
  }

  public void removeEntity(Entity e) {
    entities.remove(e);
    if (e instanceof Goblin) {
      world.destroyBody(((Goblin) e).body);
    }
  }

  public void removeProjectile(Projectile p) {
    projectiles.remove(p);
    world.destroyBody(p.body);
  }
  
  private void removeHeal(HealthLight healthLight) {
    heals.remove(healthLight);
    world.destroyBody(healthLight.body);
    healthLight.light.remove();
  }

  public void render(SpriteBatch batch) {
    entities.forEach(e -> e.render(batch));
    projectiles.forEach(p -> p.render(batch));
  }

  public void update(float delta) {
    if (stop) return;
    
    for (int i = 0; i < entities.size(); i++) {
      if (entities.get(i).shouldRemove) {
        removeEntity(entities.get(i));
      } else {
        entities.get(i).update(delta);
      }
    }

    for (int i = 0; i < projectiles.size(); i++) {
      if (projectiles.get(i).shouldRemove) {
        removeProjectile(projectiles.get(i));
      } else {
        projectiles.get(i).update(delta);
      }
    }
    
    for (int i = 0; i < heals.size(); i++) {
      if (heals.get(i).shouldRemove) {
        removeHeal(heals.get(i));
      } else {
        heals.get(i).update(delta);
      }
    }
  }

  public World getWorld() {
    return world;
  }
  
  public RayHandler getRayHandler() {
    return rayHandler;
  }
  
  public Player getPlayer() {
    for (Entity e : entities) {
      if (e instanceof Player) {
        return (Player) e;
      }
    }
    return null;
  }
}
