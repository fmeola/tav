package com.mygdx.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.material.Material;

public class DisplayableObject implements Disposable {

    private Vector3 position = new Vector3(0,0,0);
    private Mesh mesh;
    private Texture texture;
    private Material material;
    private Vector3 scale = new Vector3(1,1,1);

    public DisplayableObject(Mesh mesh, Vector3 position, Texture texture, Material material){
        this.mesh = mesh;
        this.position = position;
        this.texture = texture;
        this.material = material;
    }

    public DisplayableObject(Mesh mesh, Vector3 position, Texture texture, Material material, Vector3 scale){
        this.mesh = mesh;
        this.position = position;
        this.texture = texture;
        this.material = material;
        this.scale = scale;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Matrix4 getTMatrix(){
        float[] values = { scale.x,0,0,0,
                0,scale.y,0,0,
                0,0,scale.z,0,
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

    @Override
    public void dispose() {
        texture.dispose();
    }

}
