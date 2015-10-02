package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.math.Vector3;

import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MyPointLight;
import com.mygdx.material.Material;

import java.util.ArrayList;
import java.util.List;

public class GameElements {

    public static MyCamera initCamera() {
//        MyCamera camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyCamera camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 0f, 2f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

    public static List<MyLight> initLights() {
        List<MyLight> lights = new ArrayList<>();
        /**
         * Point Light
         */
        MyPointLight pointLight = new MyPointLight();
        pointLight.setPosition(new float[]{-1.5f, 3f, 0f});
        pointLight.setAmbientLight(Color.WHITE);
        pointLight.setSpecularLight(Color.GREEN);
        pointLight.setLightColor(Color.BLUE);
        pointLight.setGlobalAmbientLight(Color.BLACK);
        lights.add(pointLight);
        /**
         * Directional Light
         */
        MyDirectionalLight directionalLight = new MyDirectionalLight();
        directionalLight.setPosition(new float[]{1.5f, -3f, 0f});
        directionalLight.setAmbientLight(Color.BLACK);
        directionalLight.setSpecularLight(Color.WHITE);
        directionalLight.setLightColor(Color.WHITE);
        directionalLight.setGlobalAmbientLight(Color.BLACK);
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

    public static List<DisplayableObject> initSpaceships() {
        List<DisplayableObject> spaceships = new ArrayList<>();
        Texture texture = new Texture("ship/ship.png");
        Material material = new Material();
        ModelLoader loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal("ship/ship.obj"));
        Mesh mesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
        mesh.setVertices(data.meshes.get(0).vertices);
        mesh.setIndices(data.meshes.get(0).parts[0].indices);
        spaceships.add(new DisplayableObject(mesh, new Vector3(-1.5f, 0, 0), texture, material));
        spaceships.add(new DisplayableObject(mesh, new Vector3(0, 0, 0), texture, material));
        spaceships.add(new DisplayableObject(mesh, new Vector3(1.5f, 0, 0), texture, material));
        return spaceships;
    }

    public static DisplayableObject initQuad() {
        Texture texture = new Texture("quad/track.jpg");
        Material material = new Material();
        Mesh mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 4, "a_position"), new VertexAttribute(VertexAttributes.Usage.Normal, 3, "a_normal"), new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));
        mesh.setVertices(new float[] { //position, normal, texCoord
                -4f, -0.5f, 1f, 1f, 0f, 1f, 0f, 0f, 0f,
                3f, -0.5f, 1f, 1f, 0f, 1f, 0f, 1f, 0f,
                3f, -0.5f, -1f, 1f, 0f, 1f, 0f, 1f, 1f,
                -4f, -0.5f, -1f, 1f, 0f, 1f, 0f, 0f, 1f
        });
        mesh.setIndices(new short[] {0,1,2,2,3,0});
        material.setAmbient(new float[]{0.5f,0f,0f,1f});
        material.setSpecular(new float[]{0.5f,0f,0f,1f});
        material.setDiffuse(new float[]{0.5f,0f,0.2f,1f});
        material.setShininess(0.5f);
        return new DisplayableObject(mesh, new Vector3(0,0,0), texture, material);
    }

}
