package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import com.mygdx.camera.MyCamera;
import com.mygdx.light.MyLight;
import com.mygdx.material.Material;

import java.awt.Point;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

    public static Point mousePosition = new Point(0,0);
    public static MyCamera camera;
    public static MyCamera shadowCamera;

    private List<DisplayableObject> objects;
    private List<MyLight> lights;

    private boolean firstTime;

    //The maximum number of triangles our mesh will hold
    public static final int MAX_TRIS = 1;

    @Override
    public void create () {
        /**
         * OpenGL.
         */
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
        Gdx.gl.glClearColor(1, 1, 1, 1);

        /**
         * Spaceships, quad, lights and camera.
         */
        objects = GameElements.initSpaceships();
        objects.add(GameElements.initQuad());
        lights = GameElements.initLights();
        camera = GameElements.initCamera();
        
        /*
        * Init light cameras.
        */
        for (MyLight light : lights) 
            light.initCamera();
        
    }

    @Override
    public void render () {
        firstTime = true;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        /**
         * Blending para varios shaders.
         */
        for(MyLight light: lights){
            /*
             * Generar Shadow Map
             */
            MyCamera lightCamera = light.initCamera();
            ShaderProgram shadowShaderProgram = light.getShadowShaderProgram();

            shadowShaderProgram.begin();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            Gdx.gl.glClearColor(0,0,0,0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            for(DisplayableObject obj : objects) {
                shadowShaderProgram.setUniformMatrix("u_worldView", lightCamera.getPVMatrix().mul(obj.getTMatrix()));
//                shadowShaderProgram.setUniformMatrix("u_modelViewMatrix", lightCamera.getVMatrix().mul(obj.getTMatrix()));
//                obj.getMaterial().render(shadowShaderProgram);
                obj.getTexture().bind();
                obj.getMesh().render(shadowShaderProgram, GL20.GL_TRIANGLES);
            }
            shadowShaderProgram.end();
            Gdx.gl.glEnable(GL20.GL_BLEND);

            if(!firstTime){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_ONE,GL20.GL_ONE);
            }
            
            /*
             * Generar Shadow Map
            */
            
            
            shadowCamera = light.getCamera();
            ShaderProgram shadowShaderProgram = light.getShadowShaderProgram();
            
            FrameBuffer shadowBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            shadowBuffer.begin();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            shadowShaderProgram.begin();
            for(DisplayableObject obj : objects) {
                shadowShaderProgram.setUniformMatrix("u_modelViewProjectionMatrix", shadowCamera.getPVMatrix().mul(obj.getTMatrix()));
                shadowShaderProgram.setUniformMatrix("u_modelViewMatrix", shadowCamera.getVMatrix().mul(obj.getTMatrix()));
                obj.getTexture().bind();
                obj.getMesh().render(shadowShaderProgram, GL20.GL_TRIANGLES);
            }
            shadowShaderProgram.end();
            shadowBuffer.end();
//            
            Texture shadowMap = shadowBuffer.getColorBufferTexture();
            
//            dibujar shadow en pantalla
//            
//            Mesh mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 4, "a_position"), new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));
//            mesh.setVertices(new float[] { //position, texCoord
//                -1f, -1f, 0f, 1f, 0f, 0f,
//                1f, -1f, 0f, 1f, 1f, 0f,
//                1f, 1f, 0f, 1f, 1f, 1f,
//                -1f, 1f, 0f, 1f, 0f, 1f
//       });
//            mesh.setIndices(new short[]{0,1,2,2,3,0});
//            String vs = Gdx.files.internal("auxShaderVert.glsl").readString();
//            String fs = Gdx.files.internal("auxShaderFrag.glsl").readString();
//            ShaderProgram auxShader = new ShaderProgram(vs,fs);
//            auxShader.begin();
//            shadowMap.bind();
//            auxShader.setUniformi("shadowMap", 0);
//            mesh.render(auxShader,GL20.GL_TRIANGLES);
//            auxShader.end();

            
            /*
             * Pintar
            */
            ShaderProgram shaderProgram = light.getShaderProgram();
            shaderProgram.begin();
            shaderProgram.setUniform3fv("cameraPosition", camera.getPosition(), 0, 3);
            shaderProgram.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
            shaderProgram.setUniformMatrix("u_viewMatrix", camera.getVMatrix());

            /**
             * Movimiento de la luz.
             */
            //GameElements.moveLight(light);

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
                shaderProgram.setUniformMatrix("u_modelViewMatrixLight", shadowCamera.getVMatrix().mul(obj.getTMatrix()));
                obj.getMaterial().render(shaderProgram);
                obj.getTexture().bind(0);
                shadowMap.bind(1);
                shaderProgram.setUniformi("u_texture", 0);
                shaderProgram.setUniformi("u_shadowMap", 1);
                obj.getMesh().render(shaderProgram, GL20.GL_TRIANGLES);
            }

            shaderProgram.end();
            
            shadowBuffer.dispose();
            
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
