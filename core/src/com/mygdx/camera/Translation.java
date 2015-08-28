package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;

public class Translation {

    public static Matrix4 getTranslationMatrix(float x, float y, float z) {
        Matrix4 mat = new Matrix4(new float[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                x, y, z, 1
        });
        return mat;
    }

}
