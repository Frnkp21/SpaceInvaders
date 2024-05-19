package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Invader {
    private Texture texture;
    private Rectangle bounds;
    private float speed;
    private float scale;

    public Invader(Texture texture, float x, float y, float speed, float scale) {
        this.texture = texture;
        this.speed = speed;
        this.scale = scale;
        this.bounds = new Rectangle(x, y, texture.getWidth() * scale, texture.getHeight() * scale);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void update(float deltaTime) {
        bounds.y -= speed * deltaTime;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
