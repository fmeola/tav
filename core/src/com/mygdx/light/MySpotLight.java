package com.mygdx.light;

/**
 * http://www.3dgep.com/transformation-and-lighting-in-cg/#Spotlight_Effects
 */
public class MySpotLight extends MyLight {

    private float[] direction; //(float4) The world-space direction of the light.
    private float cutOff; //in degrees

    private static final String vsPath = "blinn-phong-vert.glsl";
    private static final String fsPath = "spot/spot-blinn-phong-frag.glsl";

    public MySpotLight(float[] direction, float cutOff) {
        super(vsPath, fsPath);
        this.direction = direction;
        this.cutOff = cutOff;
    }

    public void render() {
        super.render();
        shaderProgram.setUniform4fv("lightPosition", position, 0, position.length);
        shaderProgram.setUniform4fv("lightDirection", direction, 0, direction.length);
        shaderProgram.setUniformf("spotCutOff", cutOff);
    }

    public float[] getDirection() {
        return direction;
    }

    public float getCutOff() {
        return cutOff;
    }
    
}