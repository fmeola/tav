package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.camera.Rotation;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MyPointLight;
import com.mygdx.material.Material;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

    private Texture img;
    private Mesh spaceshipMesh;
    private Material spaceshipMaterial;
    private ShaderProgram shaderProgram;
//    private Array<ModelMaterial> spaceshipMaterials;

    private MyCamera camera;
    private Camera cam;
    
    private int mousePositionX;
    private int mousePositionY;

    private MyLight light; 
    

    @Override
    public void create () {
        img = new Texture("ship.png");
        light = new MyPointLight();
        shaderProgram = light.getShaderProgram();
//        String vs = Gdx.files.internal("blinn-phong-vert.glsl").readString();
//        String fs = Gdx.files.internal("blinn-phong-frag.glsl").readString();
//        shaderProgram = new ShaderProgram(vs, fs);
//        System.out.print(shaderProgram.getLog());
        ModelLoader<?> loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));
//        spaceshipMaterials = data.materials;
        spaceshipMesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
        spaceshipMesh.setVertices(data.meshes.get(0).vertices);
        spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(Gdx.gl.GL_LESS);
        
        spaceshipMaterial = new Material();

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
        camera.position.set(0f, 0f, 2f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 300f;

        Gdx.input.setInputProcessor(this);

        float[] position = new float[]{0f, 2f, 0f};
        light.setPosition(position);
        light.setAmbientLight(Color.GREEN);
        light.setSpecularLight(Color.LIGHT_GRAY);
        light.setLightColor(Color.GREEN);
        light.setGlobalAmbientLight(Color.YELLOW);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        img.bind();
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix());
        shaderProgram.setUniform3fv("cameraPosition", new float[] {camera.position.x, camera.position.y, camera.position.z},0, 3);
        shaderProgram.setUniform4fv("matSpecular", spaceshipMaterial.specular, 0, 4);
        //shaderProgram.setUniform4fv("matAmbient", spaceshipMaterial.ambient, 0, 4);
        shaderProgram.setUniform4fv("matDiffuse", spaceshipMaterial.diffuse, 0, 4);
        shaderProgram.setUniformf("matShininess", spaceshipMaterial.shininess);
//        shaderProgram.setUniform4fv("lightSpecular", new float[]{0.2f,0.5f,0.2f,1f}, 0, 4);
//        shaderProgram.setUniform4fv("lightAmbient", new float[]{0f,0.9f,0f,1f}, 0, 4);
//        shaderProgram.setUniform4fv("lightDiffuse", new float[]{0f,0f,0f,1f}, 0, 4);
//        shaderProgram.setUniform4fv("globalAmbient", new float[]{0.7f,0.7f,0.7f,1f}, 0, 4);
//        shaderProgram.setUniform3fv("L", new float[]{1f,1f,1f}, 0, 3);
        light.render();
//        shaderProgram.setUniformMatrix("u_worldView", cam.combined);
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
