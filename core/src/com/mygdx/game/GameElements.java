package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.light.*;
import com.mygdx.material.Material;
import sun.jvm.hotspot.debugger.cdbg.LoadObject;

import java.util.ArrayList;
import java.util.List;

public class GameElements {

    private static final float dirDiff = 0.01f;
    private static final float spotDiff = 0.025f;
    private static final int DIR_LIGHT_MOVE_LIMIT = 100;
    private static final int SPOT_LIGHT_MOVE_LIMIT = (int)(2/spotDiff);
    private static int countDirectional = 0;
    private static int countSpotlight = -SPOT_LIGHT_MOVE_LIMIT/2;
    private static boolean rightDirDirectional = true;
    private static boolean rightDirSpotlight = true;

    public static MyCamera initOrthographicCamera() {
        MyCamera camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 0f, 2f);
        camera.near = 0.01f;
        camera.far = 300f;
        return camera;
    }

    public static MyCamera initPerspectiveCameraCamera() {
        MyCamera camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 3.5f);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

    public static MyCamera initAnimationCamera() {
        MyCamera camera = new MyGdxPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        MyCamera camera = new MyGdxOrthographicCamera(3, 3 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0f, 5f, 25f);
        camera.near = 0.1f;
        camera.far = 300f;
        return camera;
    }

    public static List<MyLight> initLights() {
        List<MyLight> lights = new ArrayList();

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
        lights.add(spotlightLight);
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
         * Point Light
         */
        MyPointLight pointLight = new MyPointLight();
        pointLight.setPosition(new float[]{-1.5f, 3f, 0f, 1f}); //position
        pointLight.setAmbientLight(Color.BLACK);
        pointLight.setSpecularLight(Color.BLACK);
        pointLight.setLightColor(Color.GREEN);
        pointLight.setGlobalAmbientLight(Color.BLACK);
        lights.add(pointLight);
        return lights;
    }

    public static List<DisplayableObject> initSpaceships() {
        List<DisplayableObject> spaceships = new ArrayList();
        String objPath = "ship/ship.obj";
        String texturePath = "ship/ship.png";
        spaceships.add(loadModel(objPath, texturePath, new Vector3(-1.5f, 0, 0)));
        spaceships.add(loadModel(objPath, texturePath, new Vector3(0, 0, 0)));
        spaceships.add(loadModel(objPath, texturePath, new Vector3(1.5f, 0, 0)));
        return spaceships;
    }

    public static List<DisplayableObject> initSWSpaceships() {
        List<DisplayableObject> spaceships = new ArrayList();
        Vector3 scaleVector = new Vector3(0.0015f, 0.0015f, 0.0015f);
        String objPath = "swspaceship/ARC170.obj";
        String texturePath = "swspaceship/maps/ARC170_TXT_VERSION_4_D.tga";
        spaceships.add(loadModel(objPath, texturePath, new Vector3(-3f, 0, 0), scaleVector));
        spaceships.add(loadModel(objPath, texturePath, new Vector3(-0.5f, 0, 0), scaleVector));
        spaceships.add(loadModel(objPath, texturePath, new Vector3(2f, 0, 0), scaleVector));
        return spaceships;
    }

    private static DisplayableObject loadModel(String objPath, String texturePath, Vector3 position) {
        return loadModel(objPath, texturePath, new Vector3(1,1,1), position);
    }

    private static DisplayableObject loadModel(String objPath, String texturePath, Vector3 position, Vector3 scale) {
        Texture texture = new Texture(texturePath);
        Material material = new Material();
        ModelLoader loader = new ObjLoader();
        ModelData data = loader.loadModelData(Gdx.files.internal(objPath));
        Mesh mesh = new Mesh(true,
                data.meshes.get(0).vertices.length,
                data.meshes.get(0).parts[0].indices.length,
                VertexAttribute.Position(), VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
        mesh.setVertices(data.meshes.get(0).vertices);
        mesh.setIndices(data.meshes.get(0).parts[0].indices);
        return new DisplayableObject(mesh, position, texture, material, scale);
    }


    public static DisplayableObject initCity() {
        return loadModel("city/Organodron City.obj", "city/maps/cta4.jpg", new Vector3(-50f, 0f, -10f), new Vector3(0.1f, 0.1f, 0.1f));
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
                directionalLight.getCamera().rotY += dirDiff;
            } else {
                --countDirectional;
                lightPosition[0] -= dirDiff;
                directionalLight.getCamera().rotY -= dirDiff;
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

    public static EnvironmentCubemap initEnvironmentCubemap() {
        String cubeMapPath = "cubemap/spaceCubeMap.jpg";
        return new EnvironmentCubemap(new Pixmap(Gdx.files.internal(cubeMapPath)));
    }

}
