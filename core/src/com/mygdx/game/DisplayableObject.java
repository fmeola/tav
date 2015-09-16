package com.mygdx.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class DisplayableObject {

    private Vector3 position = new Vector3(0,0,0);
    private Mesh mesh;

    public DisplayableObject(Mesh mesh, Vector3 position){
        this.mesh = mesh;
        this.position = position;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Matrix4 getTMatrix(){
        float[] values = { 1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                position.x,position.y,position.z,1
        };
        return new Matrix4(values);
    }

}
