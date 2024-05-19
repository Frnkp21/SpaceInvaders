// SpaceInvaders.java
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SpaceInvaders extends Game {
	SpriteBatch batch;
	Texture playerImg;
	Player player;
	Texture invaderImg;
	List<Invader> invaders;
	int lives = 3;
	BitmapFont font;

	float invaderSpeed = 50f; // Velocidad de los invasores
	float invaderMoveTimer = 0f; // Temporizador para controlar el movimiento de los invasores

	boolean gameOver = false; // Variable para controlar el estado del juego

	@Override
	public void create() {
		batch = new SpriteBatch();
		playerImg = new Texture("nave.png");
		player = new Player(playerImg, Color.WHITE, new Texture("projectile.png")); // Agrega la textura del proyectil aquí

		invaderImg = new Texture("invader.png");
		invaders = new ArrayList<>();
		spawnInvaders();

		font = new BitmapFont();
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
				Vector2 position = new Vector2(startX + j * spacingX, startY - i * spacingY);
				invaders.add(new Invader(invaderImg, Color.WHITE, position));
			}
		}
	}

	@Override
	public void render() {
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!gameOver) {
			handleInput();
			update();
			draw();
		} else {
			goToMainMenu(); // Transición al menú principal cuando el jugador pierde
		}
	}

	private void handleInput() {
		// Disparo del jugador
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			player.Shoot();
		}
	}

	private void update() {
		player.Update(Gdx.graphics.getDeltaTime());

		// Movimiento de los invasores
		invaderMoveTimer += Gdx.graphics.getDeltaTime();
		if (invaderMoveTimer >= 1f) {
			moveInvaders();
			invaderMoveTimer = 0f;
		}

		// Colisión entre invasores y jugador
		checkCollisions();

		// Verificar si todos los invasores han alcanzado la parte inferior de la pantalla
		for (Invader invader : invaders) {
			if (invader.position.y <= 0) {
				gameOver = true;
				break;
			}
		}
	}

	private void draw() {
		batch.begin();
		player.Draw(batch);
		for (Invader invader : invaders) {
			invader.Draw(batch);
		}
		font.draw(batch, "Vidas: " + lives, 20, Gdx.graphics.getHeight() - 20);
		batch.end();
	}

	private void moveInvaders() {
		for (Invader invader : invaders) {
			invader.MoveDown(invaderSpeed * Gdx.graphics.getDeltaTime());
		}
	}

	private void checkCollisions() {
		for (Invader invader : invaders) {
			if (invader.getBounds().overlaps(player.getBounds())) {
				gameOver = true;
				break;
			}
		}
	}

	private void goToMainMenu() {
		// Aquí implementarías la lógica para cambiar a la pantalla del menú principal
	}

	@Override
	public void dispose() {
		batch.dispose();
		playerImg.dispose();
		invaderImg.dispose();
		font.dispose();
	}
}
