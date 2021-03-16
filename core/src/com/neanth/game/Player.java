package com.neanth.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.TextureMapObject;

public class Player extends Sprite {
    public Player(TextureMapObject object) {
        super(object.getTextureRegion());
        setSize(16*3, 16*3);
        setX(object.getX() * 3);
        setY(object.getY() * 3);
    }
}
