package com.honor.utils.b2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static com.honor.utils.Constants.PPM;

public class BodyBuilder {

  public static Body createRectangle(World world, float posX, float posY, float width, float height,
      boolean isStatic, short cBits, short mBits) {
    BodyDef bodyDef = new BodyDef();

    bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

    bodyDef.position.set((posX + width / 2) / PPM, (posY + height / 2) / PPM);
    bodyDef.fixedRotation = true;

    Body body = world.createBody(bodyDef);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

    FixtureDef fixture = new FixtureDef();
    fixture.shape = shape;
    fixture.density = 1.0f;
    fixture.filter.categoryBits = cBits;
    fixture.filter.maskBits = mBits;
    fixture.filter.groupIndex = 0;
    body.createFixture(fixture);
    shape.dispose();
    return body;
  }

  public static Body createCircle(World world, float posX, float posY, float radius,
      boolean isStatic, short cBits, short mBits) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.position.set((posX + radius) / PPM, (posY + radius) / PPM);
    bodyDef.fixedRotation = true;

    bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

    Body body = world.createBody(bodyDef);
    CircleShape shape = new CircleShape();
    shape.setRadius(radius / PPM);

    FixtureDef fixture = new FixtureDef();
    fixture.shape = shape;
    fixture.density = 1.0f;
    fixture.filter.categoryBits = cBits; 
    fixture.filter.maskBits = mBits;
    fixture.filter.groupIndex = 0;

    body.createFixture(fixture);
    shape.dispose();
    
    return body;
  }

}
