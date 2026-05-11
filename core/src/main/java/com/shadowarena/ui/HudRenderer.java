package com.shadowarena.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HudRenderer {

    public void renderHealthBar(
        ShapeRenderer shapeRenderer,
        float x,
        float y,
        float width,
        float height,
        int hp,
        int maxHp
    ) {
        shapeRenderer.setColor(UiTheme.BAR_BACKGROUND);
        shapeRenderer.rect(x, y, width, height);

        float hpPercent = Math.max(0f, Math.min(1f, hp / (float) maxHp));
        float currentWidth = width * hpPercent;

        if (hpPercent > 0.35f) {
            shapeRenderer.setColor(UiTheme.HP_GREEN);
        } else {
            shapeRenderer.setColor(UiTheme.HP_RED);
        }

        shapeRenderer.rect(x, y, currentWidth, height);

        shapeRenderer.setColor(UiTheme.PANEL_BORDER);
        shapeRenderer.rectLine(x, y, x + width, y, 2f);
        shapeRenderer.rectLine(x, y + height, x + width, y + height, 2f);
        shapeRenderer.rectLine(x, y, x, y + height, 2f);
        shapeRenderer.rectLine(x + width, y, x + width, y + height, 2f);
    }

    public void renderPanel(
        ShapeRenderer shapeRenderer,
        float x,
        float y,
        float width,
        float height
    ) {
        shapeRenderer.setColor(UiTheme.PANEL);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(UiTheme.PANEL_BORDER);
        shapeRenderer.rectLine(x, y, x + width, y, 2f);
        shapeRenderer.rectLine(x, y + height, x + width, y + height, 2f);
        shapeRenderer.rectLine(x, y, x, y + height, 2f);
        shapeRenderer.rectLine(x + width, y, x + width, y + height, 2f);
    }
}
