package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.UBJsonReader;
import com.mygdx.camera.MyCamera;

/**
 * Inspiración:
 * 1) http://dagger.se/?p=71
 */
public class SkinnedAnimations implements ApplicationListener {

    private ShaderProgram shaderProgram;
    private MyCamera camera;
    private AssetManager assets = new AssetManager();
    private ModelInstance characterInstance;
    private AnimationController animationController;

    private static final String vsPath = "animation/animation-vs.glsl";
    private static final String fsPath = "animation/animation-fs.glsl";
    private static final float DELTA_TIME = 60;
    private static final int BONE_MATRIX_COUNT = 12;
    private static final int MATRIX_SIZE = 16;

    @Override
    public void create() {
        /**
         * Creación del Shader.
         */
        String vs = Gdx.files.internal(vsPath).readString();
        String fs = Gdx.files.internal(fsPath).readString();
        shaderProgram = new ShaderProgram(vs, fs);

        /**
         * Creación de la cámara.
         */
        camera = GameElements.initCamera();

        assets.load("animation/complete_rig_finish.g3db", Model.class);

        /**
         * Creación del Modelo.
         */
        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        Model characterModel = modelLoader.loadModel(Gdx.files.getFileHandle("animation/complete_rig_finish.g3db", Files.FileType.Internal));
        characterInstance = new ModelInstance(characterModel);
        System.out.println("#Animaciones: " + characterInstance.animations.size);

        /**
         * Creación de la animación.
         */
        animationController = new AnimationController(characterInstance);
        animationController.animate(characterInstance.animations.get(0).id, -1, 1f, null, 0.2f); // Starts the animaton
        animationController.update(DELTA_TIME);
    }

    @Override
    public void render() {
        shaderProgram.begin();
        Array<Renderable> renderables = new Array<>();
        final Pool<Renderable> pool = new Pool<Renderable>() {
            @Override
            protected Renderable newObject () {
                return new Renderable();
            }
            @Override
            public Renderable obtain () {
                Renderable renderable = super.obtain();
                renderable.material = null;
                renderable.mesh = null;
                renderable.shader = null;
                return renderable;
            }
        };
        characterInstance.getRenderables(renderables, pool);
        Matrix4 idtMatrix = new Matrix4().idt();
        float[] bones = new float[BONE_MATRIX_COUNT*MATRIX_SIZE];
        for (int i = 0; i < bones.length; i++) {
            bones[i] = idtMatrix.val[i % MATRIX_SIZE];
        }

        Matrix4 mvpMatrix = new Matrix4();
        Matrix4 nMatrix = new Matrix4();

        for (Renderable render : renderables) {
            mvpMatrix.set(camera.getPVMatrix());
            mvpMatrix.mul(render.worldTransform);
            shaderProgram.setUniformMatrix("u_mvpMatrix", mvpMatrix);
            nMatrix.set(camera.getVMatrix());
            nMatrix.mul(render.worldTransform);
            shaderProgram.setUniformMatrix("u_modelViewMatrix", nMatrix);
            nMatrix.inv();
            nMatrix.tra();
            shaderProgram.setUniformMatrix("u_normalMatrix", nMatrix);
            for (int i = 0; i < bones.length; i++) {
                final int idx = i/MATRIX_SIZE;
                bones[i] = (render.bones == null || idx >= render.bones.length || render.bones[idx] == null) ?
                        idtMatrix.val[i%MATRIX_SIZE] : render.bones[idx].val[i%MATRIX_SIZE];
            }
            shaderProgram.setUniformMatrix4fv("u_bones", bones, 0, bones.length);
            render.mesh.render(shaderProgram, render.primitiveType, render.meshPartOffset, render.meshPartSize);
        }
        shaderProgram.end();
    }

    @Override
    public void dispose() {
        assets.dispose();
        shaderProgram.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
