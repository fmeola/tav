package com.mygdx.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.camera.MyCamera;

public abstract class MyLight {
    
    protected ShaderProgram shaderProgram, shadowShaderProgram;

    protected float[] position; //en DirectionalLight: vector direccion
    
    //Default values
    protected float[] ambientLight = new float[]{1f,1f,1f,1f};
    protected float[] specularLight = new float[]{0.2f,0.5f,0.2f,1f};
    protected float[] lightColor = new float[]{1f,1f,1f,1f};
    protected float[] globalAmbientLight = new float[]{0.7f,0.7f,0.7f,1f};
    
    protected MyCamera camera; //para shadow map

    public MyLight(String vsPath, String fsPath, String shadowFsPath, String shadowVsPath) {
        String vs = Gdx.files.internal(vsPath).readString();
        String fs = Gdx.files.internal(fsPath).readString();
        this.shaderProgram = new ShaderProgram(vs, fs);
        System.out.print(shaderProgram.getLog());

        fs = Gdx.files.internal(shadowFsPath).readString();
        vs = Gdx.files.internal(shadowVsPath).readString();
        this.shadowShaderProgram = new ShaderProgram(vs, fs);
        System.out.print(shadowShaderProgram.getLog());
    }

    public void render() {
        shaderProgram.setUniform4fv("lightColor", lightColor, 0, lightColor.length);
        shaderProgram.setUniform4fv("lightAmbient", ambientLight, 0, ambientLight.length);
        shaderProgram.setUniform4fv("lightSpecular", specularLight, 0, specularLight.length);
        shaderProgram.setUniform4fv("globalAmbient", globalAmbientLight, 0, globalAmbientLight.length);
    }

    public MyLight() {
    }

    public MyLight(float[] position) {
        this.position = position;
    }
    
    public void initCamera() {}

    public void setPosition(float[] position) {
        /*if(position.length != 3) {
            throw new IllegalArgumentException();
        }*/
        this.position = position;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }
    
    public ShaderProgram getShadowShaderProgram() {
        return shadowShaderProgram;
    }

    public void setLightColor(float[] lightColor) {
        this.lightColor = lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = new float[]{lightColor.r, lightColor.g, lightColor.b, 1f};
    }

    public void setLightColor(Color lightColor, float alpha) {
        this.lightColor = new float[]{lightColor.r, lightColor.g, lightColor.b, alpha};
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

    public float[] getPosition() {
        return position;
    }
    
    public MyCamera getCamera() {
        return camera;
    }
}
