package com.rondeo.pixwarsspace.t1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rondeo.pixwarsspace.gamescreen.components.entity.Ship;

public class GridBoard extends Actor {
    private int rows;
    private int cols;
    private float cellSize;
    private Tank[][] tanks;
    private ShapeRenderer shapeRenderer;

    private Ship ship;

    public GridBoard(int rows, int cols, float cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.tanks = new Tank[rows][cols];
        setWidth(cols * cellSize);
        setHeight(rows * cellSize);
        this.shapeRenderer = new ShapeRenderer();
    }

    public GridBoard setShip(Ship ship) {
        this.ship = ship;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                shapeRenderer.setColor(getCellColor(row, col)); // 获取单元格颜色
                shapeRenderer.rect(col * cellSize, (rows - row - 1) * cellSize, cellSize, cellSize);
//                if (tanks[row][col] != null) {
//                    tanks[row][col].draw(batch, parentAlpha); // 绘制坦克
//                }
            }
        }
        shapeRenderer.end();


        /*for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (tanks[row][col] != null) {
                    tanks[row][col].draw(batch, parentAlpha); // 绘制坦克
                }
            }
        }*/

//        if (null != ship) {
//            ship.draw(batch, parentAlpha);
//        }
    }

    public void addTank(int row, int col, Tank tank) {
        tanks[row][col] = tank;
        float x = col * cellSize;
        float y = (rows - row - 1) * cellSize; // 坐标系原点在左下角，需要调整y坐标
        tank.setPosition(x, y);
    }

    private Color getCellColor(int row, int col) {

        // 2行3列和4行5列坐标设定为特定颜色
        if ((row == 1 && col == 2) || (row == 3 && col == 4)) {
            return Color.BLUE;
        } else {
            // 其他单元格随机颜色，您可以根据需要修改
//            return new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
            return Color.WHITE;
        }
    }
}
