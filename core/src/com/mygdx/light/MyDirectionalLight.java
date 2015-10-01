package com.mygdx.light;

public class MyDirectionalLight extends MyLight {

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "directional/direct-blinn-phong-frag.glsl";

    public MyDirectionalLight() {
        super(vsPath, fsPath);
    }

    public void render() {
        super.render();
        shaderProgram.setUniform3fv("direction", position, 0, 3);
    }

}
