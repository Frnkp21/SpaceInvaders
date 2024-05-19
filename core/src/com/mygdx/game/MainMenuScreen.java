package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Space Invaders", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 100);
        font.draw(batch, "Toca para empezar", Gdx.graphics.getWidth() / 2 - 180, Gdx.graphics.getHeight() / 2);
        batch.end();

        if (Gdx.input.justTouched()) {
            ((SpaceInvadersGame) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
