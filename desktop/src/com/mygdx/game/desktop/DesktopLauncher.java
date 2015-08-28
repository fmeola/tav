package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.game.*;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGame(), config);
//        new LwjglApplication(new LoadModelsTest(), config);
    }

}
