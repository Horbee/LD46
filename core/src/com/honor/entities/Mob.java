package com.honor.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Mob extends Entity implements Steerable<Vector2> {

  public Body body;

  protected float boundingRadius;
  protected boolean tagged;
  protected float maxLinearSpeed, maxLinearAcc;
  protected float maxAngularSpeed, maxAngularAcc;

  SteeringBehavior<Vector2> behavior;
  SteeringAcceleration<Vector2> steeringOutput;

  public void initialize(float width) {
    this.boundingRadius = width;

    this.maxLinearSpeed = 50;
    this.maxLinearAcc = 100;
    this.maxAngularAcc = 5;
    this.maxAngularSpeed = 30;

    this.tagged = false;

    this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    this.body.setUserData(this);
  }

  @Override
  public Vector2 getPosition() {
    return body.getPosition();
  }

  @Override
  public float getOrientation() {
    return body.getAngle();
  }

  @Override
  public void setOrientation(float orientation) {
    // TODO Auto-generated method stub
  }

  @Override
  public float vectorToAngle(Vector2 vector) {
    return (float) Math.atan2(-vector.x, vector.y);
  }

  @Override
  public Vector2 angleToVector(Vector2 outVector, float angle) {
    outVector.x = -(float) Math.sin(angle);
    outVector.y = (float) Math.cos(angle);
    return outVector;
  }

  @Override
  public Location<Vector2> newLocation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public float getZeroLinearSpeedThreshold() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setZeroLinearSpeedThreshold(float value) {
    // TODO Auto-generated method stub

  }

  @Override
  public float getMaxLinearSpeed() {
    return maxLinearSpeed;
  }

  @Override
  public void setMaxLinearSpeed(float maxLinearSpeed) {
    this.maxLinearSpeed = maxLinearSpeed;
  }

  @Override
  public float getMaxLinearAcceleration() {
    return this.maxLinearAcc;
  }

  @Override
  public void setMaxLinearAcceleration(float maxLinearAcceleration) {
    this.maxLinearAcc = maxLinearAcceleration;
  }

  @Override
  public float getMaxAngularSpeed() {
    return this.maxAngularSpeed;
  }

  @Override
  public void setMaxAngularSpeed(float maxAngularSpeed) {
    this.maxAngularSpeed = maxAngularSpeed;
  }

  @Override
  public float getMaxAngularAcceleration() {
    return this.maxAngularAcc;
  }

  @Override
  public void setMaxAngularAcceleration(float maxAngularAcceleration) {
    this.maxAngularAcc = maxAngularAcceleration;
  }

  @Override
  public Vector2 getLinearVelocity() {
    return body.getLinearVelocity();
  }

  @Override
  public float getAngularVelocity() {
    return body.getAngularVelocity();
  }

  @Override
  public float getBoundingRadius() {
    return boundingRadius;
  }

  @Override
  public boolean isTagged() {
    return tagged;
  }

  @Override
  public void setTagged(boolean tagged) {
    this.tagged = tagged;
  }

  public Body getBody() {
    return body;
  }

  public void setBehavior(SteeringBehavior<Vector2> behavior) {
    this.behavior = behavior;
  }

  public SteeringBehavior<Vector2> getBehavior() {
    return behavior;
  }

  @Override
  public void update(float delta) {
    if (behavior != null) {
      behavior.calculateSteering(steeringOutput);
      applySteering(steeringOutput, delta);
    }

  }

  private void applySteering(SteeringAcceleration<Vector2> steering, float time) {
    boolean anyAccelerations = false;

    if (!steeringOutput.linear.isZero()) {
      // this method internally scales the force by deltaTime
      body.applyForceToCenter(steeringOutput.linear, true);
      anyAccelerations = true;
    }

    if (anyAccelerations) {
      Vector2 velocity = body.getLinearVelocity();
      float currentSpeedSquare = velocity.len2();
      float maxLinearSpeed = getMaxLinearSpeed();
      if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
        body.setLinearVelocity(
            velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
      }


    }

  }

}
