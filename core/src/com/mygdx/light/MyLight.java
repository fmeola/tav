package com.mygdx.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class MyLight {

    protected ShaderProgram shaderProgram;

    protected float[] position; //L
    protected float[] lightColor;

    public MyLight(String vsPath, String fsPath) {
        String vs = Gdx.files.internal(vsPath).readString();
        String fs = Gdx.files.internal(fsPath).readString();
        this.shaderProgram = new ShaderProgram(vs, fs);
        System.out.print(shaderProgram.getLog());
    }

    public void render() {
        shaderProgram.setUniform3fv("L", position, 0, position.length);
        shaderProgram.setUniform4fv("lightColor", lightColor, 0, lightColor.length);
    }

    public MyLight() {
    }

    public MyLight(float[] position) {
        this.position = position;
    }

    public void setPosition(float[] position) {
        if(position.length != 3) {
            throw new IllegalArgumentException();
        }
        this.position = position;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
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
}
