package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.mygdx.game.LoadModelsTest;
import com.mygdx.game.MyCamera;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new MyGdxGame(), config);
		new LwjglApplication(new LoadModelsTest(), config);

        /**
         * Prueba de la cámara artesanal.
         */
        /*GdxNativesLoader.load(); //Para cargar las librerías sin usar una lwjglApp.
        Vector3 position = new Vector3(1,2,3);
        Vector3 target = new Vector3(4,5,6);
        Matrix4 camera = new MyCamera(position, target) {
            @Override
            public Matrix4 getPMatrix() {
                return null;
            }

            public Matrix4 getPVMatrix() {
                return super.getVMatrix();}
        }.getPVMatrix();
        System.out.println("PV Matrix\n" + camera);*/
    }

}
