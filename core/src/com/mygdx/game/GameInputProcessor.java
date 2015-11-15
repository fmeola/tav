package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.camera.MyCamera;

import java.awt.*;

public class GameInputProcessor implements InputProcessor {

    private MyCamera camera;
    private Point mousePosition;

    public GameInputProcessor(MyCamera camera, Point mousePosition) {
        this.camera = camera;
        this.mousePosition = mousePosition;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        float moveAmount = 0.1f;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= moveAmount;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        float value;
        if (screenX != MyGdxGame.mousePosition.x) {
            value = screenX > MyGdxGame.mousePosition.x ? -0.01f : 0.01f;
            MyGdxGame.camera.rotY += value;
            //MyGdxGame.shadowCamera.rotY += value;
            MyGdxGame.mousePosition.x = screenX;
        }
        if (screenY != MyGdxGame.mousePosition.y) {
            value = screenY > MyGdxGame.mousePosition.y ? -0.01f : 0.01f;
            MyGdxGame.camera.rotX += value;
//            MyGdxGame.shadowCamera.rotX += value;
            MyGdxGame.mousePosition.y = screenY;
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
