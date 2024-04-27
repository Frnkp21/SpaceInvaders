package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Invader {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 70; // Velocidad de movimiento de los invasores
    boolean reachedBottom; // Nuevo campo para indicar si el invasor ha llegado abajo del todo


    public Invader(Texture img, Color color, Vector2 position) {
        sprite = new Sprite(img);
        sprite.setScale(3); // Ajusta la escala según lo necesites
        sprite.setColor(color);
        this.position = position;
        this.reachedBottom = false; // Inicialmente establecemos que no ha llegado abajo del todo

    }

    public void MoveRandomly(float deltaTime) {
        // Mueve el invasor hacia abajo
        position.y -= deltaTime * speed;

        // Verifica los límites de la pantalla
        if (position.y + sprite.getHeight() <= 0) {
            // Reinicia la posición en la parte superior de la pantalla de forma aleatoria
            position.y = Gdx.graphics.getHeight() + MathUtils.random(200, 400); // Ajusta el rango de reinicio vertical
            position.x = MathUtils.random(0, Gdx.graphics.getWidth() - sprite.getWidth()); // Ajusta el rango de reinicio horizontal
        }
    }

    public void Draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}