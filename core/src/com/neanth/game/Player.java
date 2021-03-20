package com.neanth.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class Player extends Sprite {
    public Player(RectangleMapObject object, Texture spritesheet) {
        int width = (int) object.getRectangle().width;
        int height = (int) object.getRectangle().height;
        TextureRegion tr = new TextureRegion(spritesheet, 96, 176, width, height);
        setRegion(tr);
        setSize(width * 3, height * 3);
        setX(object.getRectangle().x * 3);
        setY(object.getRectangle().y * 3);
    }
}
