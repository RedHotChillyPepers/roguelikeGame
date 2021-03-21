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
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.V_WIDTH / 2f, Config.V_HEIGHT / 2f);
        camera.update();
        viewport = new FitViewport(Config.V_WIDTH / Config.PPM, Config.V_HEIGHT / Config.PPM, camera);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        MapLayer gameobjects = tiledMap.getLayers().get("player");
        for (MapObject object : gameobjects.getObjects()) {
            switch(object.getName()) {
                case "Player":
                    player = new Player((RectangleMapObject) object, spritesheet, world);
                    break;
                default:
                    break;
            }
        }
        MapLayer collisions = tiledMap.getLayers().get("collisions");
        for (MapObject object : collisions.getObjects()) {
            if (object instanceof PolylineMapObject) {
                PolylineMapObject polyline = (PolylineMapObject) object;
                float objectX = object.getProperties().get("x", Float.class);
                float objectY = object.getProperties().get("y", Float.class);
                float[] vertices = polyline.getPolyline().getVertices();
                Vector2[] worldVertices = new Vector2[vertices.length / 2];
                for (int i = 0; i < worldVertices.length; i++) {
                    float worldX = vertices[i * 2] + objectX;
                    float worldY = vertices[i * 2 + 1] + objectY;
                    worldVertices[i] = new Vector2(worldX, worldY).scl(Config.SCALE / Config.PPM);
                }
                ChainShape shape = new ChainShape();
                shape.createChain(worldVertices);
                Body body;
                BodyDef bdef = new BodyDef();
                bdef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bdef);
                body.createFixture(shape, 1f);
                shape.dispose();
            }
        }
        MapLayer torches = tiledMap.getLayers().get("torches");
        for (MapObject object : torches.getObjects()) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            PolygonShape shape = new PolygonShape();
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            Rectangle rect = rectangleMapObject.getRectangle();
            float width = (rect.width * Config.SCALE) / Config.PPM;
            float height = (rect.height * Config.SCALE) / Config.PPM;
            float x = (rect.x * Config.SCALE) / Config.PPM;
            float y = (rect.y * Config.SCALE) / Config.PPM;
            float centerX = x + width / 2f;
            float centerY = y + height / 2f;
            bdef.position.set(centerX, centerY);

            Body body = world.createBody(bdef);

            shape.setAsBox(width / 2f, height / 2f);
            body.createFixture(shape, 1f);
            shape.dispose();
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Config.SCALE / Config.PPM);
        tiledMapRenderer.setView(camera);
    }

    private void handleInput() {
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
        handleInput();

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
        world.dispose();
        spritesheet.dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }
}
