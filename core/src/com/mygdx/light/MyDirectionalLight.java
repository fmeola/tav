package com.mygdx.light;

public class MyDirectionalLight extends MyLight {

    private static final String vsPath = "directional/blinn-phong-vert.glsl";
    private static final String fsPath = "directional/blinn-phong-frag.glsl";

    public MyDirectionalLight() {
        super(vsPath, fsPath);
    }

    public void render() {
        super.render();

    }

}
