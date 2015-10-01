package com.mygdx.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.material.Material;

public class DisplayableObject {

    private Vector3 position = new Vector3(0,0,0);
    private Mesh mesh;
    private Texture texture;
    private Material material;

    public DisplayableObject(Mesh mesh, Vector3 position, Texture texture, Material material){
        this.mesh = mesh;
        this.position = position;
        this.texture = texture;
        this.material = material;
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

    public Texture getTexture() {
        return texture;
    }

    public Material getMaterial() {
        return material;
    }
}
