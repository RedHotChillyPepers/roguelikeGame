package com.neanth.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Game extends ApplicationAdapter {
	OrthographicCamera camera;
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	Player player;
	Texture spritesheet;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		spritesheet = new Texture("spritesheet.png");
		tiledMap = new TmxMapLoader().load("level1.tmx");
		MapLayer gameobjects = tiledMap.getLayers().get("gameobjects");
		for (MapObject object : gameobjects.getObjects()) {
			switch(object.getName()) {
				case "Player":
					player = new Player((RectangleMapObject) object, spritesheet);
					break;
				case "Polyline":
					PolylineMapObject polyline = (PolylineMapObject) object;

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
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.setX(player.getX() - 16 * 3);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.setX(player.getX() + 16 * 3);
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.setY(player.getY() + 16 * 3);
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.setY(player.getY() - 16 * 3);
		}
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
		tiledMap.dispose();
		tiledMapRenderer.dispose();
	}
}
