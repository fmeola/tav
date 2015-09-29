package com.mygdx.light;

import com.badlogic.gdx.graphics.Color;

/**
 * http://www.3dgep.com/transformation-and-lighting-in-cg/#Spotlight_Effects
 */
public class MySpotLight extends MyLight {

    private float[] direction; //(float3) The object-space direction of the light.
    private float cutOff; //in degrees

    private static final String vsPath = "spot/spot-blinn-phong-vert.glsl";
    private static final String fsPath = "spot/spot-blinn-phong-frag.glsl";

    public MySpotLight(float[] direction, float cutOff) {
        super(vsPath, fsPath);
        this.direction = direction;
        this.cutOff = cutOff;
    }

    public void render() {
        super.render();
        shaderProgram.setUniform3fv("lightPosition", position, 0, position.length);
        shaderProgram.setUniform3fv("lightDirection", direction, 0, direction.length);
        shaderProgram.setUniformf("spotCutOff", cutOff);
    }

    
    
}