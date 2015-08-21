package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyPerspectiveCamera extends MyCamera {

    public MyPerspectiveCamera(Vector3 position, Vector3 lookAt) {
        super(position, lookAt);
    }

    @Override
    public Matrix4 getPMatrix() {
        return null;
    }

    public Matrix4 getPVMatrix() {
        return getPMatrix().mul(getVMatrix());
    }

}
