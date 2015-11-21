package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.mygdx.camera.MyCamera;
import com.mygdx.camera.MyGdxOrthographicCamera;
import com.mygdx.camera.MyGdxPerspectiveCamera;
import com.mygdx.light.MyLight;

import java.awt.*;
import java.util.List;

public abstract class MyAbstractGameScene extends ApplicationAdapter implements MyGameScene {

    protected MyCamera camera;
    protected Point mousePosition = new Point(0,0);

    @Override
    public MyCamera getCamera() {
        return camera;
    }

    @Override
    public Point getMousePosition() {
        return mousePosition;
    }

    public abstract List<MyLight> getLights();

    @Override
    public void changeCamera() {
        if(camera instanceof MyGdxOrthographicCamera) {
            camera = GameElements.initPerspectiveCameraCamera();
        } else if (camera instanceof MyGdxPerspectiveCamera) {
            camera = GameElements.initOrthographicCamera();
        }
    }

}
