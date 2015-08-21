package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class MyCamera  {

    private Vector3 position;
    private Vector3 target;
    private Vector3 up = new Vector3(0,1,0);

    public MyCamera(Vector3 position, Vector3 target) {
        this.target = target;
        this.position = position;
    }

    public abstract Matrix4 getPVMatrix();

    public Matrix4 getVMatrix() {
        Vector3 zAxis = position.sub(target).nor(); // The "forward" vector.
        Vector3 xAxis = up.crs(zAxis).nor(); // The "right" vector.
        Vector3 yAxis = zAxis.crs(xAxis); // The "up" vector.

        Matrix4 orientation = new Matrix4().set(xAxis, yAxis, zAxis, new Vector3(0, 0, 0));
        System.out.println("Orientation\n" + orientation);

        //Ojo que el orden del array de float es antinatura.
        Matrix4 translation = new Matrix4(
                new float[]{
                        1,0,0,-position.x,
                        0,1,0,-position.y,
                        0,0,1,-position.z,
                        0, 0, 0,1});
        System.out.println("Translation\n" + translation);

        return orientation.mul(translation);
    }

}
