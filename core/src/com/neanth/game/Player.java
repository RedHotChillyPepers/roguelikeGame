package com.neanth.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {
    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    public Body body;

    public Player(RectangleMapObject object, Texture spritesheet, World world) {
        int trWidth = (int) object.getRectangle().width;
        int trHeight = (int) object.getRectangle().height;
        TextureRegion tr = new TextureRegion(spritesheet, 96, 176, trWidth, trHeight);
        float width = (trWidth / Config.PPM) * Config.SCALE;
        float height = (trHeight / Config.PPM) * Config.SCALE;
        float x = (object.getRectangle().x / Config.PPM) * Config.SCALE;
        float y = (object.getRectangle().y / Config.PPM) * Config.SCALE;
        setRegion(tr);
        setSize(width, height);
        setPosition(x, y);
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        float centerX = getX() + getWidth() / 2f;
        float centerY = getY() + getHeight() / 2f;
        bdef.position.set(centerX, centerY);
        bdef.linearDamping = 4f;

        body = world.createBody(bdef);

        shape.setAsBox(getWidth() / 2f, getHeight() / 2f);
        fdef.shape = shape;
        body.createFixture(fdef);
        shape.dispose();
    }

    public void update() {
        float posX = body.getWorldCenter().x - getWidth() / 2f;
        float posY = body.getWorldCenter().y - getHeight() / 2f;
        setPosition(posX, posY);
    }
}
