package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.light.MyDirectionalLight;
import com.mygdx.light.MyLight;
import com.mygdx.light.MyPointLight;
import com.mygdx.light.MySpotLight;

public class GameInputProcessor implements InputProcessor {

    private MyGameScene game;

    public GameInputProcessor(MyGameScene game) {
        this.game = game;
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
            game.getCamera().position.x -= moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.getCamera().position.x += moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            game.getCamera().position.y += moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            game.getCamera().position.y -= moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            game.getCamera().position.z -= moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            game.getCamera().position.z += moveAmount;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyPressed(Input.Keys.P)) {
            if(game.getLights() != null) {
                for (MyLight l : game.getLights()) {
                    if (l instanceof MyPointLight) {
                        l.changeState();
                    }
                }
            }
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(game.getLights() != null) {
                for (MyLight l : game.getLights()) {
                    if (l instanceof MySpotLight) {
                        l.changeState();
                    }
                }
            }
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(game.getLights() != null) {
                for (MyLight l : game.getLights()) {
                    if (l instanceof MyDirectionalLight) {
                        l.changeState();
                    }
                }
            }
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C) || Gdx.input.isKeyPressed(Input.Keys.C)) {
            game.changeCamera();
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
        if (screenX != game.getMousePosition().x) {
            value = screenX > game.getMousePosition().x ? -0.01f : 0.01f;
            game.getCamera().rotY += value;
            game.getMousePosition().x = screenX;
        }
        if (screenY != game.getMousePosition().y) {
            value = screenY > game.getMousePosition().y ? -0.01f : 0.01f;
            game.getCamera().rotX += value;
            game.getMousePosition().y = screenY;
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
