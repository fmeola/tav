package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyPerspectiveCamera extends MyCamera {

    private float fovx;
    private float fovy;
    
    public MyPerspectiveCamera(Vector3 position, Vector3 target, float width, float height, float zfar, float znear,
                               float fovx, float fovy) {
        super(position,target,width,height,zfar,znear);
        this.fovx = fovx;
        this.fovy = fovy;
    }

    @Override
    public Matrix4 getPMatrix() {
        float a = (float) Math.atan(fovx/2f);
        float b = (float) Math.atan(fovy/2f);
        float c = - (zfar + znear) / (zfar - znear);
        float d = - (2f * (znear * zfar)) / (zfar - znear);
        
        Matrix4 projection = new Matrix4(new float[]{a, 0, 0, 0,
                                                    0, b, 0, 0,
                                                    0, 0, c, -1,
                                                    0, 0, d, 0});
        return projection;
    }


}
