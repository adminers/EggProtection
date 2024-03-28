package com.rondeo.pixwarsspace.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.HashMap;
import java.util.Map;

public class BreathingEffect {

    private static final String BREATHING_ACTION_ID = "BreathingAction";
    private static Map<Action, String> actionMap = new HashMap<>();

    public static void applyBreathingEffect(Actor actor, float duration) {
        // 设置初始缩放
        actor.setScale(1.0f);

        // 创建呼吸式抖动效果的动作序列
        Action breathingAction = Actions.forever(
                Actions.sequence(
                        Actions.scaleTo(1.0f, 1.0f, duration / 2),
                        Actions.scaleTo(0.5f, 0.5f, duration / 2)
                )
        );

        // 将动作与标识符关联并添加到Actor
        actor.addAction(breathingAction);
        actionMap.put(breathingAction, BREATHING_ACTION_ID);
    }

    public static void stopBreathingEffect(Actor actor) {

        // 遍历Actor的动作列表，根据标识符移除呼吸式抖动效果动作
        for (Map.Entry<Action, String> entry : actionMap.entrySet()) {
            if (entry.getKey().getActor() == actor && entry.getValue().equals(BREATHING_ACTION_ID)) {
                actor.removeAction(entry.getKey());
                actionMap.remove(entry.getKey());

                // 添加恢复到1比1大小的动作
                actor.addAction(Actions.scaleTo(1.0f, 1.0f, 0.5f));
                break; // 假设每个动作标识符唯一，找到并移除后即可跳出循环
            }
        }
    }
}