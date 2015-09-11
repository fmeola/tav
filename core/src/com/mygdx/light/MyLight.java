package com.mygdx.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MyLight {

    private ShaderProgram shaderProgram;

    //Default values
    private float[] ambientLight = new float[]{1f,1f,1f,1f};
    private float[] specularLight = new float[]{0.2f,0.5f,0.2f,1f};
    private float[] diffuseLight = new float[]{0f,0f,0f,1f};
    private float[] globalAmbientLight = new float[]{0.7f,0.7f,0.7f,1f};
    private float[] direction; //L

    public MyLight(ShaderProgram shaderProgram, float[] direction) {
        this.shaderProgram = shaderProgram;
        this.direction = direction;
    }

    /**
     * Render Method.
     */
    public void render() {
        shaderProgram.setUniform4fv("lightAmbient", ambientLight, 0, ambientLight.length);
        shaderProgram.setUniform4fv("lightSpecular", specularLight, 0, specularLight.length);
        shaderProgram.setUniform4fv("lightDiffuse", diffuseLight, 0, diffuseLight.length);
        shaderProgram.setUniform4fv("globalAmbient", globalAmbientLight, 0, globalAmbientLight.length);
        shaderProgram.setUniform3fv("L", direction, 0, direction.length);
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

    public void setDiffuseLight(float[] diffuseLight) {
        this.diffuseLight = diffuseLight;
    }

    public void setDiffuseLight(Color diffuseLight) {
        this.diffuseLight = new float[]{diffuseLight.r, diffuseLight.g, diffuseLight.b, 1f};
    }

    public void setDiffuseLight(Color diffuseLight, float alpha) {
        this.diffuseLight = new float[]{diffuseLight.r, diffuseLight.g, diffuseLight.b, alpha};
    }

    public void setDirection(float[] direction) {
        if(direction.length != 3) {
            throw new IllegalArgumentException();
        }
        this.direction = direction;
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

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

}
