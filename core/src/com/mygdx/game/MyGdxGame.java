package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.camera.Rotation;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

    private Texture img;
    private Mesh spaceshipMesh;
    private ShaderProgram shaderProgram;

    private MyCamera camera;
    private Camera cam;
    
    private int mousePositionX;
    private int mousePositionY;

    @Override
    public void create () {
        img = new Texture("ship.png");
        String vs = Gdx.files.internal("blinn-phong-vert.glsl").readString();
        String fs = Gdx.files.internal("blinn-phong-frag.glsl").readString();
        shaderProgram = new ShaderProgram(vs, fs);
        System.out.print(shaderProgram.getAttributes());
        ModelLoader<?> loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));
        spaceshipMesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
        spaceshipMesh.setVertices(data.meshes.get(0).vertices);
        spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(Gdx.gl.GL_LESS);

        // Cámaras de libdx
//        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        cam = new OrthographicCamera(3, 3 * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
//        cam.position.set(1f, 1f, 1f);
//        cam.lookAt(0,0,0);
//        cam.near = 1f;
//        cam.far = 300f;
//        cam.update();

        // Nuestras Cámaras
//        camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new MyGdxOrthographicCamera(3, 3 * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        camera.position.set(0f, 0f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 0.1f;
        camera.far = 300f;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        img.bind();
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix());
        shaderProgram.setUniformMatrix("u_modelView", camera.getVMatrix());
        shaderProgram.setUniform4fv("matSpecular", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniform4fv("matAmbient", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniform4fv("matDiffuse", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniformf("matShininess", 3f);
        shaderProgram.setUniform4fv("lightSpecular", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniform4fv("lightAmbient", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniform4fv("lightDiffuse", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniform4fv("globalAmbient", new float[]{1f,1f,1f,1f}, 0, 4);
        shaderProgram.setUniform3fv("normal", new float[]{1f,1f,1f}, 0, 3);
        shaderProgram.setUniform3fv("L", new float[]{1f,1f,1f}, 0, 3);

//        shaderProgram.setUniformMatrix("u_worldView", cam.combined);
//        System.out.println(camera.getPVMatrix());
//        System.out.println(cam.combined);
        shaderProgram.setUniformi("u_texture", 0);
        spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLES);
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

//        /**
//         * Mover la cámara con el mouse.
//         */
//        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
//            camera.rotX -= 0.01f;
//        }
//        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
//            camera.rotX += 0.01f;
//        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        float value;
        if (screenX != mousePositionX) {
            value = screenX > mousePositionX ? -0.005f : 0.005f;
            camera.rotX += value;
            mousePositionX = screenX;
        }
        if (screenY != mousePositionY) {
            value = screenY > mousePositionY ? -0.005f : 0.005f;
            camera.rotY += value;
            mousePositionY = screenY;
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
