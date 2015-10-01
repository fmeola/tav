package com.mygdx.material;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Material {

    public float[] diffuse = {0.5f,0f,0f,1f};
    public float[] specular = {0.5f,0.5f,0f,1f};
    public float[] ambient = {0.5f,0f,0f,1f};
    public float shininess = 0.2f;

    public Material() {
    }

    public void render(ShaderProgram sp) {
        sp.setUniform4fv("matSpecular", this.specular, 0, 4);
        sp.setUniform4fv("matDiffuse", this.diffuse, 0, 4);
        sp.setUniformf("matShininess", this.shininess);
        sp.setUniform4fv("matAmbient", this.ambient, 0, 4);
    }


    public void setAmbient(float[] ambient) {
        this.ambient = ambient;
    }

    public void setDiffuse(float[] diffuse) {
        this.diffuse = diffuse;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public void setSpecular(float[] specular) {
        this.specular = specular;
    }

}
