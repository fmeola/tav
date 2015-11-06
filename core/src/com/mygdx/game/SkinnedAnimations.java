package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.UBJsonReader;
import com.mygdx.camera.MyCamera;

import java.awt.*;

/**
 * Inspiración:
 * 1) http://cgi.tutsplus.com/tutorials/create-an-animation-walk-cycle-in-blender-using-rigify--cg-17812
 * 2) https://github.com/libgdx/fbx-conv
 * 3) http://dagger.se/?p=71
 */
public class SkinnedAnimations implements ApplicationListener {

    private ShaderProgram shaderProgram;
    private MyCamera camera;
    private AssetManager assets = new AssetManager();
    private ModelInstance characterInstance;
    private AnimationController animationController;

    private static final String VS_PATH = "animation/animation-vs.glsl";
    private static final String FS_PATH = "animation/animation-fs.glsl";
    private static final String ANIMATION_FILE_PATH = "animation/Dave.g3db";
    private static final int BONE_MATRIX_COUNT = 32;
    private static final int MATRIX_SIZE = 16;

    Texture texture;
    public static Point mousePosition = new Point(0,0);

    @Override
    public void create() {
        /**
         * Creación del Shader.
         */
        String vs = Gdx.files.internal(VS_PATH).readString();
        String fs = Gdx.files.internal(FS_PATH).readString();
        shaderProgram = new ShaderProgram(vs, fs);
        System.out.println(shaderProgram.getLog());

        /**
         * Creación de la cámara.
         */
        camera = GameElements.initAnimationCamera();
        camera.setPosition(new Vector3(0f, 5f, 25f));
        assets.load(ANIMATION_FILE_PATH, Model.class);

        texture = new Texture("animation/uv_dave_mapeo.jpg");

        /**
         * Creación del Modelo.
         */
        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        Model characterModel = modelLoader.loadModel(Gdx.files.getFileHandle(ANIMATION_FILE_PATH, Files.FileType.Internal));
        characterInstance = new ModelInstance(characterModel);

        /**
         * Creación de la animación.
         */
        animationController = new AnimationController(characterInstance);
        animationController.animate(characterInstance.animations.get(0).id, -1, 1f, null, 0.2f); // Starts the animaton

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.input.setInputProcessor(new GameInputProcessor(camera, mousePosition));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        animationController.update(Gdx.graphics.getDeltaTime());

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
//        System.out.println("#Renderables: " + renderables.size);
        Matrix4 idtMatrix = new Matrix4().idt();
        float[] bones = new float[BONE_MATRIX_COUNT * MATRIX_SIZE];
        for (int i = 0; i < bones.length; i++) {
            bones[i] = idtMatrix.val[i % MATRIX_SIZE];
        }

        Matrix4 mvpMatrix = new Matrix4();
        Matrix4 nMatrix = new Matrix4();

        for (Renderable render : renderables) {
            mvpMatrix.set(camera.getPVMatrix());
            mvpMatrix.mul(render.worldTransform);
            shaderProgram.setUniformMatrix("u_mvpMatrix", mvpMatrix);
//            nMatrix.set(camera.getVMatrix());
//            nMatrix.mul(render.worldTransform);
//            shaderProgram.setUniformMatrix("u_modelViewMatrix", nMatrix);
//            nMatrix.inv();
//            nMatrix.tra();
//            shaderProgram.setUniformMatrix("u_normalMatrix", nMatrix);
            for (int i = 0; i < bones.length; i++) {
                final int idx = i/MATRIX_SIZE;
                bones[i] = (render.bones == null || idx >= render.bones.length || render.bones[idx] == null) ?
                        idtMatrix.val[i%MATRIX_SIZE] : render.bones[idx].val[i%MATRIX_SIZE];
            }
            shaderProgram.setUniformMatrix4fv("u_bones", bones, 0, bones.length);
            render.mesh.render(shaderProgram, render.primitiveType, render.meshPartOffset, render.meshPartSize);
            texture.bind();
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
