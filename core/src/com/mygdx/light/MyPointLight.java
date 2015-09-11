package com.mygdx.light;

import com.badlogic.gdx.graphics.Color;

public class MyPointLight extends MyLight {

    private static final String vsPath = "point/blinn-phong-vert.glsl";
    private static final String fsPath = "point/blinn-phong-frag.glsl";

    public MyPointLight() {
        super(vsPath, fsPath);
    }

    public void render() {
        super.render();
        shaderProgram.setUniform3fv("lightPosition", position, 0, position.length);
    }

    
}
