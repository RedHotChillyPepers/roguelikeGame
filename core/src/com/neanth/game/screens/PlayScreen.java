package com.neanth.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neanth.game.Config;
import com.neanth.game.Player;

public class PlayScreen implements Screen {
    Viewport viewport;
    OrthographicCamera camera;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    Player player;
    Texture spritesheet;

    private World world;
    private Box2DDebugRenderer b2dr;

    @Override
    public void show() {
        spritesheet = new Texture("spritesheet.png");
        tiledMap = new TmxMapLoader().load("level1.tmx");
        camera = new OrthographicCamera(Config.V_WIDTH / 2f, Config.V_HEIGHT / 2f);
        viewport = new FitViewport(Config.V_WIDTH / Config.PPM, Config.V_HEIGHT / Config.PPM, camera);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        MapLayer gameobjects = tiledMap.getLayers().get("player");
        for (MapObject object : gameobjects.getObjects()) {
            switch(object.getName()) {
                case "Player":
                    player = new Player((RectangleMapObject) object, spritesheet, world);
                    break;
//				case "Polyline":
//					PolylineMapObject polyline = (PolylineMapObject) object;
//
//					break;
                default:
                    break;
            }
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Config.SCALE / Config.PPM);
        tiledMapRenderer.setView(camera);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) && player.body.getLinearVelocity().x >= -2) {
            player.body.applyLinearImpulse(new Vector2(-Config.PLAYER_SPEED, 0), player.body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && player.body.getLinearVelocity().x <= 2) {
            player.body.applyLinearImpulse(new Vector2(Config.PLAYER_SPEED, 0), player.body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && player.body.getLinearVelocity().y <= 2) {
            player.body.applyLinearImpulse(new Vector2(0, Config.PLAYER_SPEED), player.body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && player.body.getLinearVelocity().y >= -2) {
            player.body.applyLinearImpulse(new Vector2(0, -Config.PLAYER_SPEED), player.body.getWorldCenter(), true);
        }

        camera.position.set(player.body.getPosition(), 0);
        camera.update();
        tiledMapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        handleInput(delta);

        world.step(1/60f, 6, 2);

        player.update();

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our world
        tiledMapRenderer.render();

        //render our Box2DDebugLines
        b2dr.render(world, camera.combined);

        Batch batch = tiledMapRenderer.getBatch();
        batch.begin();
        player.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }
}
