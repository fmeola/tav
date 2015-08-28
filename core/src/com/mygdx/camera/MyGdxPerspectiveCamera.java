package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyGdxPerspectiveCamera extends MyCamera {

    public MyGdxPerspectiveCamera(float width, float height) {
        super(width, height);
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

}
