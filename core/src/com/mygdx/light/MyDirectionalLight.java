package com.mygdx.light;

import com.badlogic.gdx.Gdx;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;

public class MyDirectionalLight extends MyLight {

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "directional/direct-blinn-phong-frag.glsl";
    private static final String fsShadowPath = "shadow-light/shadow-direct-frag.glsl";
    private static final String vsShadowPath = "shadow-light/shadow-vert.glsl";

    public MyDirectionalLight() {
        super(vsPath, fsPath, fsShadowPath, vsShadowPath);
    }

    @Override
    public void render() {
        super.render();
        shaderProgram.setUniform4fv("direction", position, 0, 4);
    }
    
    public MyCamera initCamera() {
        camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(this.position[0]*10f, this.position[1]*10f, this.position[2]*10f);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

}
