package com.mygdx.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;

public class MyDirectionalLight extends MyLight implements Disposable {

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "directional/direct-blinn-phong-frag.glsl";
    private static final String vsShadowPath = "shadow-light/shadow-vert.glsl";
    private static final String fsShadowPath = "shadow-light/shadow-direct-frag.glsl";

    /**
     * ShadowMap
     */
    private ShaderProgram shadowShaderProgram;
    private MyCamera camera;

    public MyDirectionalLight() {
        super(vsPath, fsPath);
        String vs = Gdx.files.internal(vsShadowPath).readString();
        String fs = Gdx.files.internal(fsShadowPath).readString();
        this.shadowShaderProgram = new ShaderProgram(vs, fs);
        System.out.print(shadowShaderProgram.getLog());
        initCamera();
    }

    @Override
    public void render() {
        super.render();
        shaderProgram.setUniform4fv("direction", position, 0, 4);
    }
    
    private void initCamera() {
        camera = new MyGdxOrthographicCamera(20, 20);
        camera.rotX = -90;
        camera.near = 0.1f;
        camera.far = 30f;
    }

    public MyCamera getCamera() {
        return camera;
    }
    
    public void setCameraPosition(float[] position) {
        this.camera.setPosition(new Vector3(position));
    }
    
    public float[] getCameraPosition() {
        return this.camera.getPosition();
    }

    @Override
    public void setPosition(float[] position) {
        super.setPosition(position);
        setCameraPosition(new float[]{position[0]*5f, position[1]*5f, position[2]*5f});
    }

    public ShaderProgram getShadowShaderProgram() {
        return shadowShaderProgram;
    }

    @Override
    public void dispose() {
        super.dispose();
        shadowShaderProgram.dispose();
    }

}
