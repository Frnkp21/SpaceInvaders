package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Invader {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 50; // Velocidad de movimiento de los invasores
    public Rectangle bounds; // Rectángulo de colisión

    public Invader(Texture img, Color color, Vector2 position) {
        sprite = new Sprite(img);
        sprite.setScale(3); // Ajusta la escala según lo necesites
        sprite.setColor(color);
        this.position = position;
        this.bounds = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }


    public void MoveDown(float delta) {
        position.y -= delta * speed;
        bounds.setPosition(position.x, position.y);
    }

    public void Draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}