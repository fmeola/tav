package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.mygdx.game.*;

public class DesktopLauncher {

	public static void main (String[] arg) {
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new MyGdxGame(), config);
//		new LwjglApplication(new LoadModelsTest(), config);

        /**
         * Prueba de la cámara artesanal.
         */
        GdxNativesLoader.load(); //Para cargar las librerías sin usar una lwjglApp.
        Vector3 position = new Vector3(1,2,3);
        Vector3 target = new Vector3(4,5,6);
        float width = 100;
        float height = 100;
        float zfar = 90;
        float znear = 10;
//        MyOrthographicCamera camera = new MyOrthographicCamera(position, target, width, height, zfar, znear);
        MyPerspectiveCamera camera = new MyPerspectiveCamera(position, target, width, height, zfar, znear, 90, 90);
        System.out.println("P Matrix\n" + camera.getPMatrix());
        System.out.println("V Matrix\n" + camera.getVMatrix());
        System.out.println("PV Matrix\n" + camera.getPVMatrix());
    }

}
