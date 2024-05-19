package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 600;
    public float maxHeight;
    public float size;

    public Projectile(Texture img, float positionX, float positionY, float maxHeight, float size) {
        sprite = new Sprite(img);
        this.position = new Vector2(positionX, positionY);
        this.maxHeight = maxHeight;
        this.size = size;
    }

    public void Update(float deltaTime) {
        position.y += deltaTime * speed;
        if (position.y >= maxHeight) {
            position.y = maxHeight;
        }
    }

    public void Draw(SpriteBatch batch) {
        sprite.setSize(size, size);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }


}
