package com.mygdx.light;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MyPointLight extends MyLight {

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "point/point-blinn-phong-frag.glsl";
    private static final String shadowFsPath = "shadow-light/shadow-point-frag.glsl";
    private static final String vsShadowPath = "shadow-light/shadow-vert.glsl";

    public MyPointLight() {
        super(vsPath, fsPath, shadowFsPath, vsShadowPath);
    }

    public void render() {
        super.render();
        shaderProgram.setUniform4fv("lightPosition", position, 0, position.length);
    }
    
}
