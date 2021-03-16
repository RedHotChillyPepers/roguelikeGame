package com.neanth.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		tiledMap = new TmxMapLoader().load("level1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 3f);
		tiledMapRenderer.setView(camera);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiledMapRenderer.dispose();
	}
}
