package com.rondeo.pixwarsspace.gamescreen.components.entity.apper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LightningLineRenderer {
    private Texture circleTexture;
    private List<Vector2> points;
    private float alpha;
    private List<Float[]> cachePoints;

    private boolean isComplete;

    private long time;

    private int i;

    public LightningLineRenderer(Texture circleTexture) {
        this.circleTexture = circleTexture;
        this.points = new ArrayList<>();

        // 初始透明度为1
        this.alpha = 1.0f;
    }

    public void init(List<Float[]> points) {
        this.points.clear();
        this.alpha = 1.0f;
        this.cachePoints = points;
        this.isComplete = false;
        this.time = 0;
        this.i = 0;
    }

    public void addPoint(float x, float y) {
        points.add(new Vector2(x, y));
    }

    public void putPoint(List<Float[]> points) {
        cachePoints = points;
    }

}