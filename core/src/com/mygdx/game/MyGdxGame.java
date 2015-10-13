package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import com.mygdx.camera.MyCamera;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MySpotLight;

import java.awt.Point;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

    public static Point mousePosition = new Point(0,0);
    public static MyCamera camera;

    private List<DisplayableObject> objects;
    private List<MyLight> lights;

    private static final float diff = 0.05f;
    private static final int LIGHT_MOVE_LIMIT = (int)(2/diff);
    private int countDirectional = -LIGHT_MOVE_LIMIT/2;
    private int countSpotlight = -LIGHT_MOVE_LIMIT/2;
    private boolean rightDirDirectional = true;
    private boolean rightDirSpotlight = true;
    private boolean firstTime = true;

    @Override
    public void create () {
        /**
         * Keyboard & Mouse
         */
        Gdx.input.setInputProcessor(new GameInputProcessor());

        /**
         * OpenGL.
         */
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        /**
         * Spaceships, quad, lights and camera.
         */
        objects = GameElements.initSpaceships();
        objects.add(GameElements.initQuad());
        lights = GameElements.initLights();
        camera = GameElements.initCamera();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        /**
         * Blending para varios shaders.
         */
        for(MyLight light: lights){
            if(!firstTime){
                Gdx.gl.glBlendFunc(GL20.GL_ONE,GL20.GL_ONE);
            }
            ShaderProgram shaderProgram = light.getShaderProgram();
            shaderProgram.begin();

            shaderProgram.setUniform3fv("cameraPosition", camera.getPosition(), 0, 3);
            shaderProgram.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
            shaderProgram.setUniformMatrix("u_viewMatrix", camera.getVMatrix());

            /**
             * Movimiento de la Directional Light
             */
            if(light instanceof MyDirectionalLight) {
                float lightPosition[] = light.getPosition();
                if (rightDirDirectional) {
                    ++countDirectional;
                    lightPosition[0] += diff;
                } else {
                    --countDirectional;
                    lightPosition[0] -= diff;
                }
                if (countDirectional == LIGHT_MOVE_LIMIT || countDirectional == -LIGHT_MOVE_LIMIT) {
                    rightDirDirectional = !rightDirDirectional;
                }
                light.setPosition(new float[]{lightPosition[0], lightPosition[1], lightPosition[2]});
            }
            
            /**
             * Movimiento de la SpotLight.
             */
            /*else if (light instanceof MySpotLight) {
                //System.out.println(light.getPosition()[0]);
                float lightPosition[] = light.getPosition();
                if (rightDirSpotlight) {
                    ++countSpotlight;
                    lightPosition[0] += diff;
                } else {
                    --countSpotlight;
                    lightPosition[0] -= diff;
                }
                System.out.println(countSpotlight);
                if (countSpotlight == LIGHT_MOVE_LIMIT || countSpotlight == -LIGHT_MOVE_LIMIT) {
                    rightDirSpotlight = !rightDirSpotlight;
                }
                //System.out.println(lightPosition[0]);
                light.setPosition(new float[]{lightPosition[0], lightPosition[1], lightPosition[2]});
            }

            /**
             * Light Render
             */
            light.render();

            /**
             * Objects Render
             */
            for(DisplayableObject obj : objects) {
                shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(obj.getTMatrix()));
                shaderProgram.setUniformMatrix("u_modelViewMatrix", camera.getVMatrix().mul(obj.getTMatrix()));
                obj.getMaterial().render(shaderProgram);
                obj.getTexture().bind();
                obj.getMesh().render(shaderProgram, GL20.GL_TRIANGLES);
            }

            shaderProgram.end();
            firstTime = false;
        }
    }

    @Override
    public void dispose() {
        for(MyLight l : lights) {
            l.getShaderProgram().dispose();
        }
    }

}
