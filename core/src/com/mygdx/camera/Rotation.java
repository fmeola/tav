package com.mygdx.camera;

import com.badlogic.gdx.math.Matrix4;

public class Rotation {

    public static Matrix4 getXRotationMatrix(float theta) {
        Matrix4 mat = new Matrix4(new float[]{
                1,0,0,0,
                0, (float)Math.cos(theta), (float)Math.sin(theta),0,
                0, (float)-Math.sin(theta), (float)Math.cos(theta),0,
                0,0,0,1
        });
        return mat;
    }
    
    public static Matrix4 getYRotationMatrix(float theta) {
        Matrix4 mat = new Matrix4(new float[] {
            (float)Math.cos(theta), 0, (float)-Math.sin(theta), 0,
            0, 1, 0, 0,
            (float)Math.sin(theta), 0, (float)Math.cos(theta), 0,
            0, 0, 0, 1
        });
        return mat;
    }

    public static Matrix4 getZRotationMatrix(float theta) {
        Matrix4 mat = new Matrix4(new float[] {
            (float)Math.cos(theta), (float)Math.sin(theta), 0, 0,
            -(float)Math.sin(theta), (float)Math.cos(theta), 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        });
        return mat;
    }

}
