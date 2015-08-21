package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyOrthographicCamera extends MyCamera {

    public MyOrthographicCamera(Vector3 position, Vector3 target, float width, float height, float zfar, float znear) {
        super(position,target,width,height,zfar,znear);
    }

    @Override
    public Matrix4 getPMatrix() {
        Matrix4 projectionMatrix = new Matrix4(
                new float[]{
                        1/width,0,0,0,
                        0,1/height,0,0,
                        0,0,-2/(zfar-znear),0,
                        0, 0, -1*(zfar+znear)/(zfar-znear),1});
        return projectionMatrix;
    }

    public Matrix4 getPVMatrix() {
        return getPMatrix().mul(getVMatrix());
    }

}
