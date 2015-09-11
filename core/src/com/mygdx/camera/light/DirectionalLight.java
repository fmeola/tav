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
public class DirectionalLight {
    private float[] direction; //vec4, direction from an "infinite" point. -direction == light direction from an "infinite" point.
                               // direction[3] = 0.
    private float[] intensities; //light color

    public float[] getDirection() {
        return direction;
    }

    public float[] getIntensities() {
        return intensities;
    }

    public void setDirection(float[] direction) {
        this.direction = direction;
    }

    public void setIntensities(float[] intensities) {
        this.intensities = intensities;
    }
    
    
}
