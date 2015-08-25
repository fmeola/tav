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
    public MyCamera cam;
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

        /*cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();*/
        
        Vector3 position = new Vector3(1,2,3);
        Vector3 target = new Vector3(4,5,6);
        float width = 100;
        float height = 100;
        float zfar = 90;
        float znear = 10;
        float fovx = 90;
        float fovy = 90;
        
        //        MyOrthographicCamera camera = new MyOrthographicCamera(position, target, width, height, zfar, znear);
        initPersCam(position, target, width, height, zfar, znear, fovx, fovy);
        System.out.println("P Matrix\n" + cam.getPMatrix());
        System.out.println("V Matrix\n" + cam.getVMatrix());
        System.out.println("PV Matrix\n" + cam.getPVMatrix());

        //camController = new CameraInputController(cam);
        //Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        img.bind();
        shaderProgram.begin();
        //System.out.println("rendering");
        shaderProgram.setUniformMatrix("u_worldView", cam.getVMatrix()); //aca trabajar
        
        shaderProgram.setUniformi("u_texture", 0);  
        spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLE_FAN);
        shaderProgram.end();
    }
    
    private void initPersCam(Vector3 position, Vector3 target, float width, float height, float zfar, float znear, float fovx, float fovy) {
        cam = new MyPerspectiveCamera(position, target, width, height, zfar, znear, fovx, fovy);
    }
    
    private void initOrthoCam(Vector3 position, Vector3 target, float width, float height, float zfar, float znear) {
        cam = new MyOrthographicCamera(position, target, width, height, zfar, znear);
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
