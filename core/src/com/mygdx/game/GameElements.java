package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.math.Vector3;

import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MyPointLight;
import com.mygdx.light.MySpotLight;
import com.mygdx.material.Material;

import java.util.ArrayList;
import java.util.List;

public class GameElements {

    private static final float dirDiff = 0.05f;
    private static final float spotDiff = 0.025f;
    private static final int DIR_LIGHT_MOVE_LIMIT = (int)(2/dirDiff);
    private static final int SPOT_LIGHT_MOVE_LIMIT = (int)(2/spotDiff);
    private static int countDirectional = -DIR_LIGHT_MOVE_LIMIT/2;
    private static int countSpotlight = -SPOT_LIGHT_MOVE_LIMIT/2;
    private static boolean rightDirDirectional = true;
    private static boolean rightDirSpotlight = true;

    public static MyCamera initCamera() {
//        MyCamera camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyCamera camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 0f, 2f);
        //camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

    public static MyCamera initAnimationCamera() {
        MyCamera camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        MyCamera camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 5f, 25f);
//        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

    public static List<MyLight> initLights() {
        List<MyLight> lights = new ArrayList<>();
        /**
         * Directional Light
         */
        MyDirectionalLight directionalLight = new MyDirectionalLight();
        directionalLight.setPosition(new float[]{0f, 1f, 0f, 0f}); //direction
        directionalLight.setAmbientLight(Color.BLACK);
        directionalLight.setSpecularLight(Color.BLACK);
        directionalLight.setLightColor(Color.WHITE);
        directionalLight.setGlobalAmbientLight(Color.BLACK);
        lights.add(directionalLight);
        /**
         * Spot Light
         */
        MySpotLight spotlightLight = new MySpotLight(new float[]{-0.5f,-1.5f,0f,0f}, 30f);
        float[] spotlightLightPosition = new float[]{0f, 1f, 0f, 1f};
        spotlightLight.setPosition(spotlightLightPosition);
        spotlightLight.setAmbientLight(Color.BLACK);
        spotlightLight.setSpecularLight(Color.BLACK);
        spotlightLight.setLightColor(Color.BLUE);
        spotlightLight.setGlobalAmbientLight(Color.BLACK);
//        lights.add(spotlightLight);
        /**
         * Point Light
         */
        MyPointLight pointLight = new MyPointLight();
        pointLight.setPosition(new float[]{-1.5f, 3f, 0f, 1f}); //position
        pointLight.setAmbientLight(Color.BLACK);
        pointLight.setSpecularLight(Color.BLACK);
        pointLight.setLightColor(Color.GREEN);
        pointLight.setGlobalAmbientLight(Color.BLACK);
//        lights.add(pointLight);
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

    public static void moveLight(MyLight light) {
        /**
         * Movimiento de la Directional Light
         */
        if(light instanceof MyDirectionalLight) {
            MyDirectionalLight directionalLight = (MyDirectionalLight) light;
            float lightPosition[] = directionalLight.getPosition();
            if (rightDirDirectional) {
                ++countDirectional;
                lightPosition[0] += dirDiff;
            } else {
                --countDirectional;
                lightPosition[0] -= dirDiff;
            }
            if (countDirectional == DIR_LIGHT_MOVE_LIMIT || countDirectional == -DIR_LIGHT_MOVE_LIMIT) {
                rightDirDirectional = !rightDirDirectional;
            }
            directionalLight.setPosition(lightPosition);
        }
        /**
         * Movimiento de la SpotLight.
         */
        else if (light instanceof MySpotLight) {
            float lightPosition[] = light.getPosition();
            if (rightDirSpotlight) {
                ++countSpotlight;
                lightPosition[0] += spotDiff;
            } else {
                --countSpotlight;
                lightPosition[0] -= spotDiff;
            }
            if (countSpotlight == SPOT_LIGHT_MOVE_LIMIT || countSpotlight == -SPOT_LIGHT_MOVE_LIMIT) {
                rightDirSpotlight = !rightDirSpotlight;
            }
            light.setPosition(lightPosition);
        }
    }

}
