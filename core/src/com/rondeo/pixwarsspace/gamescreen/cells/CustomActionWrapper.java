package com.rondeo.pixwarsspace.gamescreen.cells;

import com.badlogic.gdx.scenes.scene2d.Action;

public class CustomActionWrapper {

    private Action action;
    private String actionId;

    public CustomActionWrapper(Action action, String actionId) {
        this.action = action;
        this.actionId = actionId;
    }

    public Action getAction() {
        return action;
    }

    public String getActionId() {
        return actionId;
    }
}

