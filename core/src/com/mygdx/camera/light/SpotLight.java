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
public class SpotLight {
    private float[] position; //vec4
    private float[] intensities; //light color
    private float attenuation;
    private float ambientCoefficient;
    private float coneAngle;
    private float[] coneDirection; //vec3

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

    public float getConeAngle() {
        return coneAngle;
    }

    public float[] getConeDirection() {
        return coneDirection;
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

    public void setConeAngle(float coneAngle) {
        this.coneAngle = coneAngle;
    }

    public void setConeDirection(float[] coneDirection) {
        this.coneDirection = coneDirection;
    }
    
    
}
