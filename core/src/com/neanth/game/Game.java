package com.neanth.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Game extends ApplicationAdapter implements InputProcessor {
	OrthographicCamera camera;
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	Player player;
	
	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		tiledMap = new TmxMapLoader().load("level1.tmx");
		MapLayer gameobjects = tiledMap.getLayers().get("gameobjects");
		for (MapObject object : gameobjects.getObjects()) {
			switch(object.getName()) {
				case "Player":
					Texture texture = new Texture("spritesheet.png");
					TextureRegion tr = new TextureRegion(texture, 64, 128, 16, 16);
					player = new Player(object, tr);
					break;
				default:
					break;
			}
		}
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 3f);
		tiledMapRenderer.setView(camera);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.render();
		Batch batch = tiledMapRenderer.getBatch();
		batch.begin();
		player.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		tiledMapRenderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.A) {
			player.setX(player.getX() - 16 * 3);
			return true;
		} else if (keycode == Input.Keys.D) {
			player.setX(player.getX() + 16 * 3);
		} else if (keycode == Input.Keys.W) {
			player.setY(player.getY() + 16 * 3);
		} else if (keycode == Input.Keys.S) {
			player.setY(player.getY() - 16 * 3);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
