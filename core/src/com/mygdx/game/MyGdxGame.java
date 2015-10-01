package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MyPointLight;
import com.mygdx.light.MySpotLight;
import com.mygdx.material.Material;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

    public static Point mousePosition = new Point(0,0);
    public static MyCamera camera;

    private List<DisplayableObject> objects;
    private List<MyLight> lights;

    private Texture imgQuad;
    private Mesh quad;
    private Material quadMaterial;

    @Override
    public void create () {
        /**
         * Quad Mesh & Materials
         */
        quad = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 4, "a_position"), new VertexAttribute(Usage.Normal, 3, "a_normal"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0"));
        //quad = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 4, "a_position"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0"));
        quad.setVertices(new float[] { //position, normal, texCoord
            -4f, -0.5f, 1f, 1f, 0f, 1f, 0f, 0f, 0f,
            3f, -0.5f, 1f, 1f, 0f, 1f, 0f, 1f, 0f,
            3f, -0.5f, -1f, 1f, 0f, 1f, 0f, 1f, 1f,
            -4f, -0.5f, -1f, 1f, 0f, 1f, 0f, 0f, 1f
        });
        quad.setIndices(new short[] {0,1,2,2,3,0});
        quadMaterial = new Material();
        quadMaterial.ambient = new float[]{0.5f,0f,0f,1f};
        quadMaterial.specular = new float[]{0.5f,0f,0f,1f};
        quadMaterial.diffuse = new float[]{0.5f,0f,0.2f,1f};
        quadMaterial.shininess = 0f;
        imgQuad = new Texture("quad/textureQuad.png");

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
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(0, 0, 0, 0);

        /**
         * Spaceships, lights and camera.
         */
        objects = initSpaceships();
        lights = initLights();
        camera = initCamera();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        /**
         * Blending para varios shaders.
         */
        for(MyLight l: lights){
            ShaderProgram sp = l.getShaderProgram();
            sp.begin();
            sp.setUniform3fv("cameraPosition", new float[] {camera.position.x, camera.position.y, camera.position.z},0, 3);

            /**
             * Light Render
             */
            l.render();

            /**
             * Quad Render
             */
            imgQuad.bind();
            sp.setUniform4fv("matSpecular", quadMaterial.specular, 0, 4);
            sp.setUniform4fv("matDiffuse", quadMaterial.diffuse, 0, 4);
            sp.setUniformf("matShininess", quadMaterial.shininess);
            sp.setUniform4fv("matAmbient", quadMaterial.ambient, 0, 4);
            sp.setUniformi("u_texture", 0);
            sp.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(new Matrix4(new float[]{ 1,0,0,0,
                    0,1,0,0,
                    0,0,1,0,
                    0,-0.5f,0,1
            })));
            sp.setUniformMatrix("u_normalMatrix", camera.getNormalMatrix());
            quad.render(sp, GL20.GL_TRIANGLES);

            /**
             * Spaceship Render
             */
            for(DisplayableObject obj : objects) {
                sp.setUniform4fv("matSpecular", obj.getMaterial().specular, 0, 4);
                sp.setUniform4fv("matDiffuse", obj.getMaterial().diffuse, 0, 4);
                sp.setUniformf("matShininess", obj.getMaterial().shininess);
                sp.setUniform4fv("matAmbient", obj.getMaterial().ambient, 0, 4);
                obj.getTexture().bind();
                sp.setUniformMatrix("u_worldView", camera.getPVMatrix().mul(obj.getTMatrix()));
                obj.getMesh().render(sp, GL20.GL_TRIANGLES);
            }
            sp.end();
        }
    }

    private List<MyLight> initLights() {
        List<MyLight> lights = new ArrayList<>();
        /**
         * Point Light
         */
        MyPointLight pointLight = new MyPointLight();
        float[] pointLightPosition = new float[]{0f, 3f, 0f};
        pointLight.setPosition(pointLightPosition);
        pointLight.setAmbientLight(new float[]{0.5f,0.5f,0.5f,1f});
        pointLight.setSpecularLight(new float[]{0f,1f,0f,1f});
        pointLight.setLightColor(new float[]{0f,0f,1f,1f});
        pointLight.setGlobalAmbientLight(new float[]{0f,0f,0f,1f});
        lights.add(pointLight);
        /**
         * Directional Light
         */
        MyDirectionalLight directionalLight = new MyDirectionalLight();
        float[] directionalLightPosition = new float[]{0f, 3f, 0f};
        directionalLight.setPosition(directionalLightPosition);
        directionalLight.setAmbientLight(new float[]{0.5f,0.5f,0.5f,1f});
        directionalLight.setSpecularLight(new float[]{0f,1f,0f,1f});
        directionalLight.setLightColor(new float[]{0f,0f,1f,1f});
        directionalLight.setGlobalAmbientLight(new float[]{0f,0f,0f,1f});
        lights.add(directionalLight);
        /**
         * Spot Light
         */
//        MySpotLight spotlightLight = new MySpotLight(new float[]{0f,1f,0f,0f}, 20f);
//        float[] spotlightLightPosition = new float[]{0f, 3f, 0f};
//        spotlightLight.setPosition(spotlightLightPosition);
//        spotlightLight.setAmbientLight(new float[]{0.5f,0.5f,0.5f,1f});
//        spotlightLight.setSpecularLight(new float[]{0f,1f,0f,1f});
//        spotlightLight.setLightColor(new float[]{0f,0f,1f,1f});
//        spotlightLight.setGlobalAmbientLight(new float[]{0f,0f,0f,1f});
//        lights.add(spotlightLight);
        return lights;
    }

    private MyCamera initCamera() {
//        MyCamera camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyCamera camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 0f, 2f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

    private List<DisplayableObject> initSpaceships() {
        Mesh spaceshipMesh;
        Texture texture = new Texture("ship.png");
        Material material = new Material();
        List<DisplayableObject> spaceships = new ArrayList<>();
        ModelLoader loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship.obj"));
        spaceshipMesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
        spaceshipMesh.setVertices(data.meshes.get(0).vertices);
        spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);
        spaceships.add(new DisplayableObject(spaceshipMesh, new Vector3(-1.5f, 0, 0), texture, material));
        spaceships.add(new DisplayableObject(spaceshipMesh, new Vector3(0, 0, 0), texture, material));
        spaceships.add(new DisplayableObject(spaceshipMesh, new Vector3(1.5f, 0, 0), texture, material));
        return spaceships;
    }

    @Override
    public void dispose() {
        imgQuad.dispose();
        for(MyLight l : lights) {
            l.getShaderProgram().dispose();
        }
    }

}
