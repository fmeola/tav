package com.mygdx.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;

public class MyDirectionalLight extends MyLight {

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "directional/direct-blinn-phong-frag.glsl";
    private static final String vsShadowPath = "shadow-light/shadow-vert.glsl";
    private static final String fsShadowPath = "shadow-light/shadow-direct-frag.glsl";

    private MyCamera camera; //para shadow map

    public MyDirectionalLight() {
        super(vsPath, fsPath);
        String vs = Gdx.files.internal(vsShadowPath).readString();
        String fs = Gdx.files.internal(fsShadowPath).readString();
        this.shadowShaderProgram = new ShaderProgram(vs, fs);
        System.out.print(shadowShaderProgram.getLog());
    }

    @Override
    public void render() {
        super.render();
        shaderProgram.setUniform4fv("direction", position, 0, 4);
    }
    
    public MyCamera initCamera() {
        camera = new MyGdxOrthographicCamera(20, 20);
        //camera.position.set(this.position[0]*10f, this.position[1]*10f, this.position[2]*10f);
        camera.position.set(0, 10, 0);
        camera.rotX = -90;
        camera.near = 0.1f;
        camera.far = 30f;
        return camera;
    }

    public MyCamera getCamera() {
        return camera;
    }

}
