package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;

public class LoadModelsTest extends ApplicationAdapter {

    private PerspectiveCamera cam;
    private CameraInputController camController;
    private ModelBatch modelBatch;
    private AssetManager assets;
    private Array<ModelInstance> instances = new Array<>();
    private Environment environment;
    private boolean loading;

    @Override
    public void create () {
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        /**
         * Con esto hago que con el mouse mueva la cámara.
         */
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        assets = new AssetManager();
//        assets.load("ship.obj", Model.class);
        assets.load("ship.g3db", Model.class);
        loading = true;
    }

    /**
     * Es mejor usar g3db que obj. Se puede convertir de obj a g3db con una cmdlinetool.
     */

    private void doneLoading() {
//        Model ship = assets.get("ship.obj", Model.class);
        Model ship = assets.get("ship.g3db", Model.class);
        ModelInstance shipInstance = new ModelInstance(ship);
        instances.add(shipInstance);

        /**
         * Añado otras instancias de la misma nave en otra posición.
         */

        ModelInstance shipInstance2 = new ModelInstance(ship);
        shipInstance2.transform.setToTranslation(1,0,1);
        instances.add(shipInstance2);

        ModelInstance shipInstance3 = new ModelInstance(ship);
        shipInstance3.transform.setToTranslation(-1,0,1);
        instances.add(shipInstance3);

        loading = false;
    }

    @Override
    public void render () {
        if (loading && assets.update()) {
            doneLoading();
        }

        /**
         * Con esto hago que con el mouse se mueva el objeto al moverse la cámara.
         */
        camController.update();
//        System.out.println(camController.camera.combined);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

}
