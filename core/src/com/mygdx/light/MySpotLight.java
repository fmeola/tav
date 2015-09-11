package com.mygdx.light;

/**
 * http://www.3dgep.com/transformation-and-lighting-in-cg/#Spotlight_Effects
 */
public class MySpotLight extends MyLight {

    float[] direction; //(float3) The object-space direction of the light.
    float[] color; //(float4) The color of the light.
    float kC; //The constant attenuation factor of the light.
    float kL; //The linear attenuation factor of the light.
    float kQ; //The quadratic attenuation factor of the light.
    float cosInnerCone; //The cosine angle of the spotlight’s inner cone.
    float cosOuterCone; //The cosine angle of the spotlight’s outer cone.

    private static final String vsPath = "spotlight/blinn-phong-vert.glsl";
    private static final String fsPath = "spotlight/blinn-phong-frag.glsl";

    public MySpotLight() {
        super(vsPath, fsPath);
    }

    public void render() {
        super.render();
    }

}