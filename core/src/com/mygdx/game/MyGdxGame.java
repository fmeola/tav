package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;

public class MyGdxGame extends ApplicationAdapter {

    private Texture img;
    private Mesh spaceshipMesh;
    private ShaderProgram shaderProgram;

    private MyCamera camera;
    private Camera cam;

    @Override
    public void create () {
        img = new Texture("ship.png");
        String vs = Gdx.files.internal("defaultVS.glsl").readString();
        String fs = Gdx.files.internal("defaultFS.glsl").readString();
        shaderProgram = new ShaderProgram(vs, fs);
        ModelLoader<?> loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));
        spaceshipMesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.TexCoords(0), VertexAttribute.Normal());
        spaceshipMesh.setVertices(data.meshes.get(0).vertices);
        spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);

        // Cámaras de libdx
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        cam = new OrthographicCamera(3, 3 * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        // Nuestras Cámaras
        camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera = new MyGdxOrthographicCamera(3, 3 * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        camera.position.set(1f, 1f, 1f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        img.bind();
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix());
//        shaderProgram.setUniformMatrix("u_worldView", cam.combined);
//        System.out.println(camera.getPVMatrix());
//        System.out.println(cam.combined);
        shaderProgram.setUniformi("u_texture", 0);
        spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLE_FAN);
        shaderProgram.end();

        /**
         * Mover la cámara con el teclado.
         */
        float moveAmount = 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= moveAmount;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += moveAmount;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += moveAmount;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= moveAmount;
        }

        /**
         * Mover la cámara con el mouse.
         */
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            camera.position.x -= moveAmount;
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            camera.position.x += moveAmount;
        }

    }

}
