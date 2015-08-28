package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyGdxOrthographicCamera extends MyCamera {

    public MyGdxOrthographicCamera(float width, float height) {
        super(width, height);
    }

    public Matrix4 getPMatrix() {
        Matrix4 projectionMatrix = new Matrix4(
                new float[]{
                        1/width,0,0,0,
                        0,1/height,0,0,
                        0,0,-2/(far-near),0,
                        0, 0, -1*(far+near)/(far-near),1});
        return projectionMatrix;
    }


}
