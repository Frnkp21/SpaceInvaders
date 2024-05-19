package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene implements Screen {

    private Player player; // Declarar una instancia de Player
    private SpriteBatch batch; // Declarar una instancia de SpriteBatch
    private List<Invader> invaders; // Declarar una lista de invasores
    private Texture invaderImg; // Textura de los invasores

    private int lives = 3; // Declarar el número inicial de vidas
    private BitmapFont font; // Declarar la variable font
    private boolean gameOver; // Variable para controlar el estado del juego

    public GameScene(Texture invaderImg) {
        player = new Player(new Texture("nave.png"), Color.WHITE, new Texture("projectile.png"));
        batch = new SpriteBatch();
        invaders = new ArrayList<>();
        this.invaderImg = invaderImg; // Asigna la textura del invasor
        spawnInvaders();
        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.Update(Gdx.graphics.getDeltaTime());

        batch.begin();
        player.Draw(batch);

        boolean invaderReachedPlayer = false; // Variable para detectar si un invasor alcanzó al jugador

        // Iterar sobre los invasores
        for (int i = 0; i < invaders.size(); i++) {
            Invader invader = invaders.get(i);
            invader.MoveDown(Gdx.graphics.getDeltaTime());
            invader.Draw(batch);

            // Colisión entre proyectil y invader
            if (player.projectile != null) {
                if (player.projectile.position.dst(invader.position) < 40) {
                    invaders.remove(invader);
                    player.projectile = null;
                    i--; // Ajustar el índice después de la eliminación
                }
            }

            // Verificar si el invasor llega al jugador
            if (invader.position.y <= player.position.y + player.sprite.getHeight()) {
                invaderReachedPlayer = true;
            }

            // Eliminar invasores que pasan por debajo de la pantalla
            if (invader.position.y <= 0) {
                invaders.remove(invader); // Elimina el invasor de la lista
                i--; // Ajusta el índice después de la eliminación
            }
        }

        // Restar una vida si un invasor alcanza al jugador
        if (invaderReachedPlayer) {
            lives--; // Resta una vida
            if (lives <= 0) {
                gameOver = true; // El juego termina si las vidas llegan a 0
            }
        }

        // Actualizar y dibujar proyectil del jugador (si está en vuelo)
        if (player.projectile != null) {
            player.projectile.Update(Gdx.graphics.getDeltaTime());
            player.projectile.Draw(batch);

            // Destruir proyectil si llega al techo
            if (player.projectile.position.y >= Gdx.graphics.getHeight()) {
                player.projectile = null;
            }
        }

        // Verificar si todos los invasores han sido eliminados
        if (invaders.isEmpty()) {
            spawnInvaders(); // Generar nuevos invasores
        }

        font.draw(batch, "Vidas: " + lives, 20, Gdx.graphics.getHeight() - 20);

        // Verificar si el juego ha terminado
        if (gameOver) {
            // Aquí puedes agregar código para manejar el final del juego
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private void spawnInvaders() {
        float spacingX = 70;
        float spacingY = 70;

        Random random = new Random();
        float startX = Gdx.graphics.getWidth() / 2 - 2 * spacingX; // Centrado
        float startY = Gdx.graphics.getHeight() + 100; // En la parte superior de la pantalla

        // Generar invasores
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // Ajustar la posición de cada invasor de manera aleatoria
                float randomX = startX + (random.nextInt(5) - 2) * spacingX;
                float randomY = startY - (random.nextInt(5) + i) * spacingY;
                Vector2 position = new Vector2(randomX, randomY);
                invaders.add(new Invader(invaderImg, Color.WHITE, position));
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        // Dispose textures for invaders
        for (Invader invader : invaders) {
            invader.sprite.getTexture().dispose();
        }
    }
}