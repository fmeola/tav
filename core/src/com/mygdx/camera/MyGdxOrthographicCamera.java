package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyGdxOrthographicCamera implements MyGdxCamera {

    private float width;
    private float height;
    private float zfar;
    private float znear;

    private Vector3 position;
    private Vector3 target;
    private Vector3 up;

    public MyGdxOrthographicCamera(Vector3 position, Vector3 target, Vector3 up,
                                   float width, float height, float zfar, float znear) {
        this.position = position;
        this.target = target;
        this.up = up;
        this.width = width;
        this.height = height;
        this.zfar = zfar;
        this.znear = znear;
    }

    @Override
    public Matrix4 getPVMatrix() {
        return null;
    }

    private Matrix4 getPMatrix() {
        Matrix4 projectionMatrix = new Matrix4(
                new float[]{
                        1/width,0,0,0,
                        0,1/height,0,0,
                        0,0,-2/(zfar-znear),0,
                        0, 0, -1*(zfar+znear)/(zfar-znear),1});
        return projectionMatrix;
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
