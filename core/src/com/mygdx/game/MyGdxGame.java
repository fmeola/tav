package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import com.mygdx.camera.MyCamera;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;

import java.util.List;

public class MyGdxGame extends MyAbstractGameScene implements MyGameScene {

    private List<DisplayableObject> objects;
    private List<MyLight> lights;

    private boolean firstTime;

    private FrameBuffer shadowBuffer;

    @Override
    public void create () {
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
        camera = GameElements.initOrthographicCamera();

        /**
         * ShadowBuffer para el ShadowMap de la Directional Light.
         */
        shadowBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        /**
         * Keyboard & Mouse
         */
        Gdx.input.setInputProcessor(new GameInputProcessor(this));
    }

    @Override
    public void render () {
        firstTime = true;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for(MyLight light: lights){
            if(light.isEnabled()) {
                /**
                 * Blending para varios shaders.
                 */
                if (!firstTime) {
                    Gdx.gl.glEnable(GL20.GL_BLEND);
                    Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
                }

                /**
                 * Movimiento de la luz.
                 */
                GameElements.moveLight(light);

                if (light instanceof MyDirectionalLight) {
                    /**
                     * Render para luces con shadowMap.
                     */

                    /**
                     * Shadow Shader Program
                     */
                    MyDirectionalLight directionalLight = (MyDirectionalLight) light;
                    ShaderProgram shadowShaderProgram = directionalLight.getShadowShaderProgram();
                    MyCamera shadowCamera = directionalLight.getCamera();

                    shadowShaderProgram.begin();

                    shadowBuffer.begin();

                    Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

                    Gdx.gl.glCullFace(GL20.GL_FRONT);

                    for (DisplayableObject obj : objects) {
                        shadowShaderProgram.setUniformMatrix("u_modelViewProjectionMatrix", shadowCamera.getPVMatrix().mul(obj.getTMatrix()));
                        obj.getMesh().render(shadowShaderProgram, GL20.GL_TRIANGLES);
                    }

                    shadowBuffer.end();

                    shadowShaderProgram.end();

                    /**
                     * Shader Program
                     */
                    ShaderProgram shaderProgram = light.getShaderProgram();

                    shaderProgram.begin();

                    shaderProgram.setUniform3fv("cameraPosition", camera.getPosition(), 0, 3);
                    shaderProgram.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
                    shaderProgram.setUniformMatrix("u_viewMatrix", camera.getVMatrix());

                    light.render();

                    Gdx.gl.glCullFace(GL20.GL_BACK);

                    for (DisplayableObject obj : objects) {
                        //                    shaderProgram.setUniformMatrix("u_worldView", shadowCamera.getPVMatrix().mul(obj.getTMatrix()));
                        shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(obj.getTMatrix()));
                        shaderProgram.setUniformMatrix("u_modelViewMatrix", camera.getVMatrix().mul(obj.getTMatrix()));
                        shaderProgram.setUniformMatrix("u_modelViewProjectionMatrixLight", shadowCamera.getPVMatrix().mul(obj.getTMatrix()));
                        obj.getMaterial().render(shaderProgram);
                        obj.getTexture().bind(0);
                        shaderProgram.setUniformi("u_texture", 0);
                        shadowBuffer.getColorBufferTexture().bind(1);
                        shaderProgram.setUniformi("u_shadowMap", 1);
                        obj.getMesh().render(shaderProgram, GL20.GL_TRIANGLES);
                    }

                    shaderProgram.end();
                } else {
                    /**
                     * Render para luces sin shadowMap.
                     */

                    ShaderProgram shaderProgram = light.getShaderProgram();

                    shaderProgram.begin();

                    shaderProgram.setUniform3fv("cameraPosition", camera.getPosition(), 0, 3);
                    shaderProgram.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
                    shaderProgram.setUniformMatrix("u_viewMatrix", camera.getVMatrix());
                    light.render();

                    Gdx.gl.glCullFace(GL20.GL_BACK);

                    for (DisplayableObject obj : objects) {
                        shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(obj.getTMatrix()));
                        shaderProgram.setUniformMatrix("u_modelViewMatrix", camera.getVMatrix().mul(obj.getTMatrix()));
                        obj.getMaterial().render(shaderProgram);
                        obj.getTexture().bind();
                        obj.getMesh().render(shaderProgram, GL20.GL_TRIANGLES);
                    }

                    shaderProgram.end();
                }
                firstTime = false;
            }
        }
    }

    @Override
    public void dispose() {
        for(MyLight l : lights) {
            l.getShaderProgram().dispose();
            if(l instanceof MyDirectionalLight) {
                ((MyDirectionalLight) l).getShadowShaderProgram().dispose();
            }
        }
        shadowBuffer.dispose();
    }

    @Override
    public List<MyLight> getLights() {
        return lights;
    }

}
