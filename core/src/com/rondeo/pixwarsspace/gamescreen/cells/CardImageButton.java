package com.rondeo.pixwarsspace.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.play.PlayShip;
import com.rondeo.pixwarsspace.gamescreen.enums.PlateBlockEnum;
import com.rondeo.pixwarsspace.gamescreen.plate.PlateBlockButton;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class CardImageButton extends ImageButton {

    private PlateBlockEnum plateBlockEnum;

    private ButtonImage buttonImage;

    private final Random random = new Random();

    public CardImageButton(Drawable imageUp, float x, float y, String name, ButtonImage buttonImage) {
        super(imageUp);
        this.setName(name);
        this.buttonImage = buttonImage;
        setBounds(x, y, 25, 40);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Constants.TOTAL_COIN < buttonImage.getPower()) {
                    return;
                }
                super.clicked(event, x, y);
                setTransform(true);
                addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f)); // 在0.2秒内将按钮放大到2.1倍大小
                addAction(Actions.sequence(
                        Actions.delay(1.0f), // 延迟1秒
                        Actions.scaleTo(1.0f, 1.0f, 0.1f) // 将按钮缩放回原始大小，持续0.1秒
                ));
                Random random = new Random();
                int randomNumber = random.nextInt(3) + 1;
                Constants.TOTAL_COIN -= buttonImage.getPower();
                switch (getName()) {
                    case "extend" :

                        show();
                        break;
                    case "attack" :
                        createAttack(PlateBlockEnum.attack);
                        break;
                    case "protect" :
                        createAttack(PlateBlockEnum.protect);
                        break;
                    case "electricShock" :
                        createAttack(PlateBlockEnum.electricShock);
                    default:
                        break;
                }

                // 点击事件处理
//                addAction(Actions.rotateBy(10, 1f)); // 旋转45度，持续1秒

//                setScale(2.1f); // 直接设置按钮的缩放比例
//                addAction(Actions.sequence(
//                        Actions.delay(0.2f), // 延迟0.2秒
//                        Actions.scaleTo(1.0f, 1.0f, 0.2f) // 将按钮缩放回原始大小，持续0.2秒
//                ));
            }
        });
    }

    private void createAttack(PlateBlockEnum plateBlockEnum) {

        Map<Integer, PlayShip> showPlate = LevelManager.getShowPlate();
        Set<Integer> showPlateKey = LevelManager.getShowPlateKey();
        for (Integer key : showPlate.keySet()) {
            if (!showPlateKey.contains(key)) {
                PlayShip playShip = Controllers.getInstance().getPlayController().deploy(key, plateBlockEnum);
                showPlate.put(key, playShip);
                showPlateKey.add(key);
                break;
            }
        }
    }

    private void show() {

        Map<Integer, PlayShip> showPlate = LevelManager.getShowPlate();
        List<Integer> result = Stream.generate(() -> random.nextInt(13))
                .filter(n -> !showPlate.containsKey(n))
                .limit(20)
                .collect(Collectors.toList());
        Collections.shuffle(result);
        showPlate.put(result.get(0), null);
        List<PlateBlockButton> plate = LevelManager.getPlateBlockButtons();
        PlateBlockButton plateBlockButton = plate.get(result.get(0));
        plateBlockButton.setHide(false);
    }

    public ButtonImage getButtonImage() {
        return this.buttonImage;
    }

    public CardImageButton setButtonImage(ButtonImage buttonImage) {
        this.buttonImage = buttonImage;
        return this;
    }
}