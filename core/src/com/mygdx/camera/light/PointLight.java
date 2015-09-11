/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.camera.light;

/**
 *
 * @author picosat
 */
public class PointLight {
    private float[] position; //vec3
    private float[] intensities; //light color
    private float attenuation;
    private float ambientCoefficient;

    public PointLight(float[] position, float[] intensities) {
        this.position = position;
        this.intensities = intensities;
    }

    public PointLight(float[] position, float[] intensities, float attenuation, float ambientCoefficient) {
        this.position = position;
        this.intensities = intensities;
        this.attenuation = attenuation;
        this.ambientCoefficient = ambientCoefficient;
    }

    
    
    public float[] getPosition() {
        return position;
    }

    public float[] getIntensities() {
        return intensities;
    }

    public float getAttenuation() {
        return attenuation;
    }

    public float getAmbientCoefficient() {
        return ambientCoefficient;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public void setIntensities(float[] intensities) {
        this.intensities = intensities;
    }

    public void setAttenuation(float attenuation) {
        this.attenuation = attenuation;
    }

    public void setAmbientCoefficient(float ambientCoefficient) {
        this.ambientCoefficient = ambientCoefficient;
    }
    
    
    
}
