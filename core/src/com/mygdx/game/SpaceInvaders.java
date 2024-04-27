// SpaceInvaders.java
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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




	@Override
	public void create() {
		batch = new SpriteBatch();
		playerImg = new Texture("nave.png");
		player = new Player(playerImg, Color.WHITE, new Texture("projectile.png")); // Agrega la textura del proyectil aquí

		invaderImg = new Texture("invader.png");
		invaders = new ArrayList<Invader>();
		spawnInvaders();

		font = new BitmapFont();

	}

	private void spawnInvaders() {
		float spacingX = 70;
		float spacingY = 70;

		Random random = new Random();
		float startX = -100; // Cambiado a -100 para que los invasores puedan aparecer en el borde izquierdo
		float startY = Gdx.graphics.getHeight() + 100; // Cambiado a +100 para que los invasores puedan aparecer en la parte superior

		// Generar invasores en el borde izquierdo
		for (int i = 0; i < 5; i++) {
			Vector2 position = new Vector2(startX, startY - i * spacingY); // Ajusta el rango de aparición vertical
			invaders.add(new Invader(invaderImg, Color.WHITE, position));
		}

		// Generar invasores en el borde superior
		for (int i = 0; i < 5; i++) {
			Vector2 position = new Vector2(startX + i * spacingX, startY); // Ajusta el rango de aparición horizontal
			invaders.add(new Invader(invaderImg, Color.WHITE, position));
		}

		// Generar invasores en el borde derecho
		for (int i = 0; i < 5; i++) {
			Vector2 position = new Vector2(Gdx.graphics.getWidth() + 100, startY - i * spacingY); // Ajusta el rango de aparición vertical
			invaders.add(new Invader(invaderImg, Color.WHITE, position));
		}

		// Generar invasores en el borde inferior
		for (int i = 0; i < 5; i++) {
			Vector2 position = new Vector2(startX + i * spacingX, -100); // Ajusta el rango de aparición horizontal
			invaders.add(new Invader(invaderImg, Color.WHITE, position));
		}
	}

	@Override
	public void render() {
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		player.Update(Gdx.graphics.getDeltaTime());

		batch.begin();
		player.Draw(batch);

		boolean invaderReachedPlayer = false; // Variable para detectar si un invasor alcanzó al jugador

		// Iterar sobre los invasores
		for (int i = 0; i < invaders.size(); i++) {
			Invader invader = invaders.get(i);
			invader.MoveRandomly(Gdx.graphics.getDeltaTime());
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
			if (invader.position.y <= player.position.y + playerImg.getHeight()) {
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
				// Implementa la lógica para el fin del juego aquí
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

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		playerImg.dispose();
		invaderImg.dispose();
	}
}