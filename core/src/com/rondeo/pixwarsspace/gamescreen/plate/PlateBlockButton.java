package com.rondeo.pixwarsspace.gamescreen.plate;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * 关于libgdx中UI控件的旋转和缩放的备忘
 * <p>
 * 最近遇到这样一个问题,定义了一个ImageButton后,想对按钮进行下旋转,结果setRotation(-90f),不起作用.后来在官网上找到了原因
 * <p>
 * <p>
 * 关于UI控件的旋转 缩放官网上有这样一段话(链接:http://code.google.com/p/libgdx/wiki/scene2dui):
 * <p>
 * Flushing for each group would severely limit performance, so most scene2d.ui groups have transform set to false by default. Rotation and scale is ignored when the group's transform is disabled.
 * <p>
 * <p>
 * 在刷每个Group的时候会严格限制性能消耗,所以大多数的scene2d.ui中的group的矩阵变换默认设置为不启用.这样当group的矩阵变换设置为不起用时,系统会忽略group的旋转和缩放.
 * <p>
 * <p>
 * 我在查看了Group源码后,果然发现了有setTransform(boolean transform)这个函数.
 * <p>
 * 代码中,在bt.setRotation(-90f)前设置了bt.setTransform(true),按钮就被转过来了。
 * <p>
 * // 在其他事件触发后恢复按钮到原始状态
 * // 假设在某个事件触发后调用以下代码
 * button.addAction(Actions.sequence(
 * Actions.delay(1f), // 延迟1秒
 * Actions.rotateTo(0, 0.5f) // 将按钮旋转到0度，持续0.5秒
 * ));
 */
public class PlateBlockButton extends ImageButton {

    /**
     * 是否隐藏
     */
    private boolean isHide;

    public PlateBlockButton(Drawable imageUp, float x, float y, boolean isHide) {
        super(imageUp);
        this.isHide = isHide;
        setBounds(x, y, 22, 19.13f);
        System.out.println("我方地图：(" + x + ", " + y + ")");

        this.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setTransform(true);
                addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f)); // 在0.2秒内将按钮放大到2.1倍大小
                addAction(Actions.sequence(
                        Actions.delay(1.0f), // 延迟1秒
                        Actions.scaleTo(1.0f, 1.0f, 0.1f) // 将按钮缩放回原始大小，持续0.1秒
                ));
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (isHide) {
            return;
        }
        super.draw(batch, parentAlpha);
    }

    public PlateBlockButton setHide(boolean isHide) {
        this.isHide = isHide;
        return this;
    }
}