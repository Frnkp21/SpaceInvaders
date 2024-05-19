package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 600;
    public float maxHeight; // Nueva variable para almacenar la altura máxima
    public float size; // Nuevo atributo para el tamaño del proyectil

    public Projectile(Texture img, Color color, Vector2 position, float maxHeight, float size) {
        sprite = new Sprite(img);
        sprite.setColor(color);
        this.position = position;
        this.maxHeight = maxHeight;
        this.size = size; // Establecer el tamaño del proyectil
    }

    public void Update(float deltaTime) {
        position.y += deltaTime * speed;
        // Verificar si el proyectil ha alcanzado la altura máxima
        if (position.y >= maxHeight) {
            position.y = maxHeight; // Ajustar la posición al límite máximo
        }
    }

    public void Draw(SpriteBatch batch) {
        // Ajustar el tamaño del proyectil
        sprite.setSize(size, size);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
