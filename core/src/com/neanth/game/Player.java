package com.neanth.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Sprite {
    public Player(MapObject object, TextureRegion texture) {
        super(texture);
//        setScale(3f);
        setSize(16*3, 16*3);
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        setX(rect.x * 3);
        setY(rect.y * 3);
    }
}
