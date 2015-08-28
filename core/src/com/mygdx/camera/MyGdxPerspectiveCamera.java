package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyGdxPerspectiveCamera implements MyCamera {

    private float width;
    private float height;

    public float far;
    public float near;

    public Vector3 position = new Vector3(0f,0f,0f);

    private Vector3 target;

    private Vector3 up = new Vector3(0f, 1f, 0f);

    public MyGdxPerspectiveCamera(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void lookAt(float x, float y, float z) {
        this.target = new Vector3(z,y,z);
    }

    @Override
    public Matrix4 getPVMatrix() {
        return getPMatrix().mul(getVMatrix());
    }

    public Matrix4 getPMatrix() {
        float fovx = 50f; //TODO
        float fovy = 100f; //TODO

        float a = (float) Math.atan((fovx * (Math.PI / 180))/2f);
        float b = (float) Math.atan((fovy * (Math.PI / 180))/2f);
        float c = - (far + near) / (far - near);
        float d = - (2f * (near * far)) / (far - near);
        Matrix4 projection = new Matrix4(new float[]{
                a, 0, 0, 0,
                0, b, 0, 0,
                0, 0, c, -1,
                0, 0, d, 0});
        return projection;
    }

    public Matrix4 getVMatrix() {
        Vector3 currentPosition = new Vector3(position.x, position.y, position.z);
        Vector3 currentTarget = new Vector3(target.x, target.y, target.z);
        Vector3 currentUp = new Vector3(up.x, up.y, up.z);
        Vector3 zaxis = currentPosition.sub(currentTarget).nor();  // The "forward" vector.
        Vector3 xaxis = currentUp.crs(zaxis).nor(); // The "right" vector.
        Vector3 currentZAxis = new Vector3(zaxis.x, zaxis.y, zaxis.z);
        Vector3 yaxis = currentZAxis.crs(xaxis); // The "up" vector.
        Matrix4 wMatrix = new Matrix4(new float[]{
                xaxis.x, xaxis.y, xaxis.z, 0,
                yaxis.x, yaxis.y, yaxis.z, 0,
                zaxis.x, zaxis.y, zaxis.z, 0,
                position.x, position.y, position.z, 1
        });
        return wMatrix.inv();
    }

}
