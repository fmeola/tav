package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MyPointLight;
import com.mygdx.material.Material;
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    
    private Texture img, imgQuad;
    private Mesh spaceshipMesh;
    private Material spaceshipMaterial, quadMaterial;
    private ShaderProgram shaderProgram;

    private int mousePositionX;
    private int mousePositionY;

    private MyCamera camera;
    private MyLight light;

    private List<DisplayableObject> spaceships = new ArrayList();
    
    private Mesh quad;

    @Override
    public void create () {
        quad = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 4, "a_position"), new VertexAttribute(Usage.Normal, 3, "a_normal"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0"));
        //quad = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 4, "a_position"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0"));
        quad.setVertices(new float[] { //position, normal, texCoord
            -4f, 0f, 1f, 1f, 0f, 1f, 0f, 0f, 0f,
            3f, 0f, 1f, 1f, 0f, 1f, 0f, 1f, 0f,
            3f, 0f, -1f, 1f, 0f, 1f, 0f, 1f, 1f,
            -4f, 0f, -1f, 1f, 0f, 1f, 0f, 0f, 1f
        });
        
        quad.setIndices(new short[] {0,1,2,2,3,0});
        quadMaterial = new Material();
        quadMaterial.ambient = new float[]{0.5f,0f,0f,1f};
        quadMaterial.specular = new float[]{0.5f,0f,0f,1f};
        quadMaterial.diffuse = new float[]{0.5f,0f,0.2f,1f};
        quadMaterial.shininess = 0f;
        
        img = new Texture("ship.png");
        imgQuad = new Texture("quad/textureQuad.png");
        light = new MyDirectionalLight();
        shaderProgram = light.getShaderProgram();
        ModelLoader<?> loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));
        spaceshipMesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
                //VertexAttribute.Position(), VertexAttribute.TexCoords(0));
        spaceshipMesh.setVertices(data.meshes.get(0).vertices);
        spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);

        spaceships.add(new DisplayableObject(spaceshipMesh, new Vector3(-1.5f, 0, 0)));
        spaceships.add(new DisplayableObject(spaceshipMesh, new Vector3(0, 0, 0)));
        spaceships.add(new DisplayableObject(spaceshipMesh, new Vector3(1.5f, 0, 0)));

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(Gdx.gl.GL_LESS);
        spaceshipMaterial = new Material();
//        camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 0f, 2f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 300f;

        Gdx.input.setInputProcessor(this);

        float[] position = new float[]{0f, 1f, 0f};
        light.setPosition(position);
        light.setAmbientLight(new float[]{0.5f,0.5f,0.5f,1f});
        light.setSpecularLight(new float[]{0f,1f,0f,1f});
        light.setLightColor(new float[]{0f,0f,1f,1f});
        light.setGlobalAmbientLight(new float[]{0f,0f,0f,1f});
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        shaderProgram.begin();
        shaderProgram.setUniform3fv("cameraPosition", new float[] {camera.position.x, camera.position.y, camera.position.z},0, 3);
        
        //render light
        light.render();
        
        //render quad
        imgQuad.bind();
        shaderProgram.setUniform4fv("matSpecular", quadMaterial.specular, 0, 4);
        shaderProgram.setUniform4fv("matDiffuse", quadMaterial.diffuse, 0, 4);
        shaderProgram.setUniformf("matShininess", quadMaterial.shininess);
        shaderProgram.setUniform4fv("matAmbient", quadMaterial.ambient, 0, 4);
        shaderProgram.setUniformi("u_texture", 0);
        shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(new Matrix4(new float[]{ 1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                0,-0.5f,0,1
        })));
        shaderProgram.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
        quad.render(shaderProgram, GL20.GL_TRIANGLES);
        
        //render spaceships
        img.bind();
        shaderProgram.setUniform4fv("matSpecular", spaceshipMaterial.specular, 0, 4);
        shaderProgram.setUniform4fv("matDiffuse", spaceshipMaterial.diffuse, 0, 4);
        shaderProgram.setUniformf("matShininess", spaceshipMaterial.shininess);
        shaderProgram.setUniform4fv("matAmbient", spaceshipMaterial.ambient, 0, 4);
        for(DisplayableObject spaceship : spaceships) {
            shaderProgram.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(spaceship.getTMatrix()));
            spaceship.getMesh().render(shaderProgram, GL20.GL_TRIANGLES);
        }
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
            value = screenX > mousePositionX ? -0.01f : 0.01f;
            camera.rotX += value;
            mousePositionX = screenX;
        }
        if (screenY != mousePositionY) {
            value = screenY > mousePositionY ? -0.01f : 0.01f;
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
