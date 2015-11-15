package com.mygdx.light;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.camera.MyCamera;

/**
 * http://www.3dgep.com/transformation-and-lighting-in-cg/#Spotlight_Effects
 */
public class MySpotLight extends MyLight {

    private float[] direction; //(float4) The world-space direction of the light.
    private float cutOff; //in degrees

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "spot/spot-blinn-phong-frag.glsl";
    private static final String shadowFsPath = "shadow-light/shadow-spot-frag.glsl";
    private static final String vsShadowPath = "shadow-light/shadow-vert.glsl";

    public MySpotLight(float[] direction, float cutOff) {
        super(vsPath, fsPath, shadowFsPath, vsShadowPath);
        this.direction = direction;
        this.cutOff = cutOff;
    }

    public void render() {
        super.render();
        shaderProgram.setUniform4fv("lightPosition", position, 0, position.length);
        shaderProgram.setUniform4fv("lightDirection", direction, 0, direction.length);
        shaderProgram.setUniformf("spotCutOff", cutOff);
    }

    @Override
    public MyCamera initCamera() {
        return null;
    }

    public float[] getDirection() {
        return direction;
    }

    public float getCutOff() {
        return cutOff;
    }
    
    
    
}