package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class MyCamera {

    protected float width;
    protected float height;

    public float far;
    public float near;

    public Vector3 position = new Vector3(0f,0f,0f);

    protected Vector3 target;

    protected Vector3 up = new Vector3(0f, 1f, 0f);

    public float rotX = 0;
    public float rotY = 0;

    public MyCamera(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void lookAt(float x, float y, float z) {
        this.target = new Vector3(x,y,z);
    }

    public Matrix4 getVMatrix() {
        return Translation.getTranslationMatrix(position.x, position.y, position.z).
                mul(Rotation.getYRotationMatrix(rotX)).
                mul(Rotation.getXRotationMatrix(rotY)).inv();
    }

    public abstract Matrix4 getPMatrix();

    public Matrix4 getPVMatrix() {
        return getPMatrix().mul(getVMatrix());
    }
    
    public Matrix4 getNormalMatrix() {
        return this.getVMatrix().inv().tra();
    }

    public float[] getPosition() {
        return new float[] {this.position.x, this.position.y, this.position.z};
    }

}
