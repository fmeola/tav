package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyOrthographicCamera extends MyCamera {

    public MyOrthographicCamera(Vector3 position, Vector3 lookAt) {
        super(position, lookAt);

    }

    @Override
    public Matrix4 getPVMatrix() {
        return super.getVMatrix();
    }

}
