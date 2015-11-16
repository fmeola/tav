package com.mygdx.light;

import com.mygdx.camera.MyCamera;

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

    @Override
    public void setCameraPosition(float[] position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] getCameraPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
