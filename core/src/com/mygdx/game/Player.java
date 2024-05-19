package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    private Texture texture;
    private Rectangle bounds;
    private int lives;
    private float speed;
    private float scale;
    private float shootTimer;
    private Texture projectileTexture;
    private float projectileSize;

    public Player(Texture texture, float x, float y, float speed, float scale, Texture projectileTexture, float projectileSize) {
        this.texture = texture;
        this.bounds = new Rectangle(x, y, texture.getWidth() * scale, texture.getHeight() * scale);
        this.lives = 3;
        this.speed = speed;
        this.scale = scale;
        this.shootTimer = 0;
        this.projectileTexture = projectileTexture;
        this.projectileSize = projectileSize;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void move(float x) {
        bounds.x += x * speed;
        // Control de límites
        if (bounds.x < 0) {
            bounds.x = 0;
        }
        if (bounds.x > Gdx.graphics.getWidth() - bounds.width) {
            bounds.x = Gdx.graphics.getWidth() - bounds.width;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        lives--;
    }

    public void incrementLives() {
        lives++;
    }

    public void update(float deltaTime, Array<Projectile> projectiles) {
        shootTimer += deltaTime;
        if (shootTimer >= 0.5f) {
            shoot(projectiles);
            shootTimer = 0;
        }
    }

    private void shoot(Array<Projectile> projectiles) {
        projectiles.add(new Projectile(projectileTexture, bounds.x + bounds.width / 2, bounds.y + bounds.height, Gdx.graphics.getHeight(), projectileSize));
    }
}
