package com.shadowarena.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ArenaRenderer {

    private static final float PADDING = 18f;
    private static final float GRID_SIZE = 40f;

    public void render(ShapeRenderer shapeRenderer) {
        renderBackground(shapeRenderer);
        renderGrid(shapeRenderer);
        renderArenaBorder(shapeRenderer);
    }

    private void renderBackground(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(UiTheme.BACKGROUND);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void renderGrid(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(UiTheme.GRID);

        for (float x = PADDING; x < Gdx.graphics.getWidth() - PADDING; x += GRID_SIZE) {
            shapeRenderer.rectLine(x, PADDING, x, Gdx.graphics.getHeight() - PADDING, 1f);
        }

        for (float y = PADDING; y < Gdx.graphics.getHeight() - PADDING; y += GRID_SIZE) {
            shapeRenderer.rectLine(PADDING, y, Gdx.graphics.getWidth() - PADDING, y, 1f);
        }
    }

    private void renderArenaBorder(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(UiTheme.ARENA_BORDER);

        float width = Gdx.graphics.getWidth() - PADDING * 2;
        float height = Gdx.graphics.getHeight() - PADDING * 2;

        shapeRenderer.rectLine(PADDING, PADDING, PADDING + width, PADDING, 3f);
        shapeRenderer.rectLine(PADDING, PADDING + height, PADDING + width, PADDING + height, 3f);
        shapeRenderer.rectLine(PADDING, PADDING, PADDING, PADDING + height, 3f);
        shapeRenderer.rectLine(PADDING + width, PADDING, PADDING + width, PADDING + height, 3f);
    }
}
