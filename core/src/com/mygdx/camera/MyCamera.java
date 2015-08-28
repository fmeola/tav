package com.mygdx.camera;

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

    public MyCamera(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void lookAt(float x, float y, float z) {
        this.target = new Vector3(z,y,z);
    }

    public Matrix4 getVMatrix() {
        Vector3 currentPosition = new Vector3(position);
        Vector3 currentTarget = new Vector3(target);
        Vector3 currentUp = new Vector3(up);
        Vector3 zaxis = currentPosition.sub(currentTarget).nor();  // The "forward" vector.
        Vector3 xaxis = currentUp.crs(zaxis).nor(); // The "right" vector.
        Vector3 currentZAxis = new Vector3(zaxis);
        Vector3 yaxis = currentZAxis.crs(xaxis); // The "up" vector.
        Matrix4 wMatrix = new Matrix4(new float[]{
                xaxis.x, xaxis.y, xaxis.z, 0,
                yaxis.x, yaxis.y, yaxis.z, 0,
                zaxis.x, zaxis.y, zaxis.z, 0,
                position.x, position.y, position.z, 1
        });
        return wMatrix.inv();
    }

    public abstract Matrix4 getPMatrix();

    public Matrix4 getPVMatrix() {
        return getPMatrix().mul(getVMatrix());
    }

}
