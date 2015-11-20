package com.mygdx.game;

import com.mygdx.camera.MyCamera;
import com.mygdx.light.MyLight;

import java.awt.*;
import java.util.List;

public interface MyGameScene {

    MyCamera getCamera();

    Point getMousePosition();

    List<MyLight> getLights();

    void changeCamera();

}
