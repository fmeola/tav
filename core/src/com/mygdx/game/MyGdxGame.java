package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import com.mygdx.camera.MyCamera;
import com.mygdx.light.MyLight;

import java.awt.Point;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

    public static Point mousePosition = new Point(0,0);
    public static MyCamera camera;

    private List<DisplayableObject> objects;
    private List<MyLight> lights;

    private boolean firstTime;

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
        firstTime = true;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        /**
         * Blending para varios shaders.
         */
        for(MyLight light: lights){
            if(!firstTime){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_ONE,GL20.GL_ONE);
            }
            ShaderProgram shaderProgram = light.getShaderProgram();
            shaderProgram.begin();

            shaderProgram.setUniform3fv("cameraPosition", camera.getPosition(), 0, 3);
            shaderProgram.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
            shaderProgram.setUniformMatrix("u_viewMatrix", camera.getVMatrix());

            /**
             * Movimiento de la luz.
             */
            GameElements.moveLight(light);

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
