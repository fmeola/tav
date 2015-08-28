package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;

public class MyGdxPerspectiveCamera extends MyCamera {

    public MyGdxPerspectiveCamera(float width, float height) {
        super(width, height);
    }

    public Matrix4 getPMatrix() {
        float fovx = 90f; //TODO
        float fovy = 67f; //TODO
        float a = (float) (1f / Math.tan((fovx * (Math.PI / 180))/2f));
        float b = (float) (1f / Math.tan((fovy * (Math.PI / 180))/2f));
        float c = (-1f * (far + near)) / (far - near);
        float d = (-2f * (near * far)) / (far - near);
        Matrix4 projection = new Matrix4(new float[]{
                a, 0, 0, 0,
                0, b, 0, 0,
                0, 0, c, -1,
                0, 0, d, 0});
        return projection;
    }

}
