package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Player player;
    private Array<Invader> invaders;
    private Array<Projectile> projectiles;
    private Array<Heart> hearts;
    private Texture playerTexture;
    private Texture invaderTexture;
    private Texture projectileTexture;
    private Texture heartTexture;
    private float invaderSpawnTimer;
    private float projectileSize;
    private BitmapFont font;
    private Texture backgroundTexture;
    private float heartSpawnTimer;
    private int invaderCount;

    @Override
    public void show() {
        batch = new SpriteBatch();
        playerTexture = new Texture(Gdx.files.internal("nave.png"));
        invaderTexture = new Texture(Gdx.files.internal("invader.png"));
        projectileTexture = new Texture(Gdx.files.internal("projectile.png"));
        heartTexture = new Texture(Gdx.files.internal("corazon.png"));
        backgroundTexture = new Texture(Gdx.files.internal("spaceInvadersBackground.jpg"));

        float scale = 5.0f;
        projectileSize = 20.0f;
        player = new Player(playerTexture,
                Gdx.graphics.getWidth() / 2 - (playerTexture.getWidth() * scale) / 2,
                20,
                4.0f,
                scale,
                projectileTexture,
                projectileSize);
        invaders = new Array<>();
        projectiles = new Array<>();
        hearts = new Array<>();
        invaderSpawnTimer = 0;
        heartSpawnTimer = 0;
        invaderCount = 0;

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        player.render(batch);
        for (Invader invader : invaders) {
            invader.render(batch);
        }
        for (Projectile projectile : projectiles) {
            projectile.Draw(batch);
        }
        for (Heart heart : hearts) {
            heart.render(batch);
        }
        drawUI();
        batch.end();
    }

    private void update(float delta) {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            if (touchX < Gdx.graphics.getWidth() / 2) {
                player.move(-200 * delta);
            } else {
                player.move(200 * delta);
            }
        }

        invaderSpawnTimer += delta;
        if (invaderSpawnTimer > 1) {
            invaderSpawnTimer = 0;
            float scale = 5.0f;
            invaders.add(new Invader(invaderTexture, (float) Math.random() * (Gdx.graphics.getWidth() - invaderTexture.getWidth() * scale), Gdx.graphics.getHeight(), 100, scale));
        }

        for (int i = invaders.size - 1; i >= 0; i--) {
            Invader invader = invaders.get(i);
            invader.update(delta);

            if (invader.getBounds().overlaps(player.getBounds())) {
                player.loseLife();
                invaders.removeIndex(i);
                if (player.getLives() == 0) {
                    ((SpaceInvadersGame) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                }
            } else if (invader.getBounds().y + invader.getBounds().height < 0) {
                player.loseLife();
                invaders.removeIndex(i);
                if (player.getLives() == 0) {
                    ((SpaceInvadersGame) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                }
            }

            for (int j = projectiles.size - 1; j >= 0; j--) {
                Projectile projectile = projectiles.get(j);
                if (invader.getBounds().overlaps(projectile.getBounds())) {
                    projectiles.removeIndex(j);
                    invaders.removeIndex(i);
                    invaderCount++;
                }
            }
        }

        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.Update(delta);
            if (projectile.position.y >= projectile.maxHeight) {
                projectiles.removeIndex(i);
            }
        }
        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.Update(delta);
            if (projectile.position.y >= projectile.maxHeight) {
                projectiles.removeIndex(i);
            } else {
                for (int j = hearts.size - 1; j >= 0; j--) {
                    Heart heart = hearts.get(j);
                    if (heart.getBounds().overlaps(projectile.getBounds())) {
                        projectiles.removeIndex(i);
                        hearts.removeIndex(j);
                        player.incrementLives();
                        break;
                    }
                }
            }
        }

        spawnHearts(delta);

        for (int i = hearts.size - 1; i >= 0; i--) {
            Heart heart = hearts.get(i);
            heart.getPosition().y -= 200 * delta;
            if (heart.getPosition().y < -heartTexture.getHeight()) {
                hearts.removeIndex(i);
            }
        }

        player.update(delta, projectiles);
    }

    private void spawnHearts(float delta) {
        float heartSpawnInterval = 15.0f;
        heartSpawnTimer += delta;
        if (heartSpawnTimer >= heartSpawnInterval) {
            heartSpawnTimer = 0;
            float x = MathUtils.random(0, Gdx.graphics.getWidth() - heartTexture.getWidth());
            float y = Gdx.graphics.getHeight();
            Heart heart = new Heart(heartTexture, x, y, 80, 80);
            hearts.add(heart);
        }
    }

    private void drawUI() {
        font.draw(batch, "Vidas: " + player.getLives(), 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Invasores eliminados: " + invaderCount, 20, Gdx.graphics.getHeight() - 40);
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        invaderTexture.dispose();
        projectileTexture.dispose();
        heartTexture.dispose();
        font.dispose();
    }
}
