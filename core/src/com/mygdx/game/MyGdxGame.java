package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {

    private Texture img;
    private Mesh spaceshipMesh;
    private ShaderProgram shaderProgram;
    public PerspectiveCamera cam;
    public CameraInputController camController;

    @Override
    public void create () {
        img = new Texture("ship.png");
        String vs = Gdx.files.internal("defaultVS.glsl").readString();
        String fs = Gdx.files.internal("defaultFS.glsl").readString();
        shaderProgram = new ShaderProgram(vs, fs);
        System.out.println(shaderProgram.getLog());
        ModelLoader<?> loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));

        spaceshipMesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.TexCoords(0), VertexAttribute.Normal());
        spaceshipMesh.setVertices(data.meshes.get(0).vertices);
        spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        img.bind();
        shaderProgram.begin();

        shaderProgram.setUniformMatrix("u_worldView", cam.combined); //aca trabajar
        shaderProgram.setUniformi("u_texture", 0);
        spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLE_FAN);
        shaderProgram.end();
    }



}

//public class MyGdxGame extends ApplicationAdapter {
//    SpriteBatch batch;
//    Texture img;
//
//    @Override
//    public void create () {
//        batch = new SpriteBatch();
//        img = new Texture("badlogic.jpg");
//    }
//
//    @Override
//    public void render () {
//        Gdx.gl.glClearColor(1, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();
//    }
//}
