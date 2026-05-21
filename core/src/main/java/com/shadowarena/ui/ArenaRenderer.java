package com.shadowarena.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shadowarena.config.GameConfig;

public class ArenaRenderer {

    private static final float GRID_SIZE = 40f;

    public void render(ShapeRenderer shapeRenderer) {
        renderArenaBackground(shapeRenderer);
        renderGrid(shapeRenderer);
        renderArenaBorder(shapeRenderer);
    }

    private void renderArenaBackground(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(UiTheme.BACKGROUND);
        shapeRenderer.rect(
            GameConfig.ARENA_X,
            GameConfig.ARENA_Y,
            GameConfig.ARENA_WIDTH,
            GameConfig.ARENA_HEIGHT
        );
    }

    private void renderGrid(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(UiTheme.GRID);

        float startX = GameConfig.ARENA_X;
        float endX = GameConfig.ARENA_X + GameConfig.ARENA_WIDTH;
        float startY = GameConfig.ARENA_Y;
        float endY = GameConfig.ARENA_Y + GameConfig.ARENA_HEIGHT;

        for (float x = startX; x <= endX; x += GRID_SIZE) {
            shapeRenderer.rectLine(x, startY, x, endY, 1f);
        }

        for (float y = startY; y <= endY; y += GRID_SIZE) {
            shapeRenderer.rectLine(startX, y, endX, y, 1f);
        }
    }

    private void renderArenaBorder(ShapeRenderer shapeRenderer) {
        float x = GameConfig.ARENA_X;
        float y = GameConfig.ARENA_Y;
        float w = GameConfig.ARENA_WIDTH;
        float h = GameConfig.ARENA_HEIGHT;

        shapeRenderer.setColor(UiTheme.ARENA_BORDER);
        shapeRenderer.rectLine(x, y, x + w, y, 3f);
        shapeRenderer.rectLine(x, y + h, x + w, y + h, 3f);
        shapeRenderer.rectLine(x, y, x, y + h, 3f);
        shapeRenderer.rectLine(x + w, y, x + w, y + h, 3f);
    }
}
