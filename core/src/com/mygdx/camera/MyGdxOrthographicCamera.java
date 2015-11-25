package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;

public class MyGdxOrthographicCamera extends MyCamera {

    public MyGdxOrthographicCamera(float width, float height) {
        super(width, height);
    }

    public Matrix4 getPMatrix() {
        Matrix4 projectionMatrix = new Matrix4(
                new float[]{
                        1f/width,0,0,0,
                        0,1f/height,0,0,
                        0,0,-2f/(far-near),0,
                        0, 0, -(far+near)/(far-near),1f});
        return projectionMatrix;
    }

}
