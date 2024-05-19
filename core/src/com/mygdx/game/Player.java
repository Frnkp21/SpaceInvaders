package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 400;
    public float shootCooldown = 0.1f; // Tiempo de espera entre disparos
    public float timeSinceLastShot = 0; // Tiempo transcurrido desde el último disparo
    public Projectile projectile; // Declara la variable projectile aquí
    private Texture projectileImg; // Textura del proyectil
    private float projectileSize = 25; // Tamaño del proyectil

    public Player(Texture img, Color color, Texture projectileTexture){
        sprite = new Sprite(img);
        sprite.setScale(4);
        sprite.setColor(color);
        position = new Vector2(Gdx.graphics.getWidth()/2,sprite.getScaleY()*sprite.getHeight()/2);
        projectileImg = projectileTexture;
    }

    public void Update(float deltaTime) {
        // Mover jugador
        if(Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float screenWidth = Gdx.graphics.getWidth();
            if (touchX < screenWidth / 2) {
                position.x -= deltaTime * speed;
            } else {
                position.x += deltaTime * speed;
            }
        }

        if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x = (sprite.getWidth()*sprite.getScaleX()/2);
        if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>= Gdx.graphics.getWidth()) position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);

        timeSinceLastShot += deltaTime;
        if (timeSinceLastShot >= shootCooldown) {
            timeSinceLastShot = 0;
            // Disparar automáticamente sin necesidad de pulsar un botón
            continuousShoot();
        }
    }

    public void Draw(SpriteBatch batch){
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void continuousShoot() {
        // Verificar si el proyectil ya existe antes de crear uno nuevo
        if (projectile == null || projectile.position.y >= position.y + Gdx.graphics.getHeight()) {
            projectile = new Projectile(projectileImg, Color.WHITE, new Vector2(position.x, position.y + sprite.getHeight() * sprite.getScaleY()), position.y + Gdx.graphics.getHeight(), projectileSize);
        }
    }

    public void Shoot() {
        if (sprite != null) {
            projectile = new Projectile(projectileImg, Color.WHITE, new Vector2(position.x, position.y + sprite.getHeight() * sprite.getScaleY()), position.y + Gdx.graphics.getHeight(), projectileSize);
        }
    }
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

}
