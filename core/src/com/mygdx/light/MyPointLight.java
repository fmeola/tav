package com.mygdx.light;

import com.badlogic.gdx.graphics.Color;

public class MyPointLight extends MyLight {

    //Default values
    private float[] ambientLight = new float[]{1f,1f,1f,1f};
    private float[] specularLight = new float[]{0.2f,0.5f,0.2f,1f};
    private float[] lightColor = new float[]{0f,0f,0f,1f};
    private float[] globalAmbientLight = new float[]{0.7f,0.7f,0.7f,1f};

    private static final String vsPath = "point/blinn-phong-vert.glsl";
    private static final String fsPath = "point/blinn-phong-frag.glsl";

    public MyPointLight() {
        super(vsPath, fsPath);
    }

    public void render() {
        super.render();
        shaderProgram.setUniform4fv("lightAmbient", ambientLight, 0, ambientLight.length);
        shaderProgram.setUniform4fv("lightSpecular", specularLight, 0, specularLight.length);
        shaderProgram.setUniform4fv("globalAmbient", globalAmbientLight, 0, globalAmbientLight.length);
    }

    public void setAmbientLight(Color lightColor) {
        this.ambientLight = new float[]{lightColor.r, lightColor.g, lightColor.b, 1f};
    }

    public void setAmbientLight(Color lightColor, float alpha) {
        this.ambientLight = new float[]{lightColor.r, lightColor.g, lightColor.b, alpha};
    }

    public void setAmbientLight(float[] ambientLight) {
        if(ambientLight.length != 4) {
            throw new IllegalArgumentException();
        }
        this.ambientLight = ambientLight;
    }

    public void setSpecularLight(float[] specularLight) {
        if(specularLight.length != 4) {
            throw new IllegalArgumentException();
        }
        this.specularLight = specularLight;
    }

    public void setSpecularLight(Color specularLight) {
        this.specularLight = new float[]{specularLight.r, specularLight.g, specularLight.b, 1f};
    }

    public void setSpecularLight(Color specularLight, float alpha) {
        this.specularLight = new float[]{specularLight.r, specularLight.g, specularLight.b, alpha};
    }

    public void setGlobalAmbientLight(float[] globalAmbientLight) {
        this.globalAmbientLight = globalAmbientLight;
    }

    public void setGlobalAmbientLight(Color globalAmbientLight) {
        this.globalAmbientLight = new float[]{globalAmbientLight.r, globalAmbientLight.g, globalAmbientLight.b, 1};
    }

    public void setGlobalAmbientLight(Color globalAmbientLight, float alpha) {
        this.globalAmbientLight = new float[]{globalAmbientLight.r, globalAmbientLight.g, globalAmbientLight.b, alpha};
    }

}
