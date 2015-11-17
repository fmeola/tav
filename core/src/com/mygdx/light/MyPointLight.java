package com.mygdx.light;

public class MyPointLight extends MyLight {

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "point/point-blinn-phong-frag.glsl";

    public MyPointLight() {
        super(vsPath, fsPath);
    }

    public void render() {
        super.render();
        shaderProgram.setUniform4fv("lightPosition", position, 0, position.length);
    }

}
