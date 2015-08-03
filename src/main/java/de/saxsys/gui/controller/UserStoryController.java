package de.saxsys.gui.controller;

import de.saxsys.gui.view.TaskManagementTaskView;
import de.saxsys.gui.view.TaskManagementUserStoryTitleView;
import de.saxsys.model.UserStory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class UserStoryController implements EventHandler<ActionEvent> {
    private final UserStory storyModel;

    public UserStoryController(UserStory storyModel) {
        this.storyModel = storyModel;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            if (event.getSource() instanceof Button) {
                Button source = (Button) event.getSource();
                if ( (source.getParent().getParent() instanceof TaskManagementUserStoryTitleView) && source.getId().contains("_addview_button") ) {
                    //:TODO implement add Task
                } else if ( (source.getParent().getParent() instanceof TaskManagementTaskView) ) {
                    int taskId = getTaskId(source.getParent().getParent().getId());
                    switch (getButtonType(source.getId())) {
                        case TASK_MOVEUP: storyModel.moveTaskUp(taskId); break;
                        case TASK_MOVEDOWN: storyModel.moveTaskDown(taskId); break;
                        case TASK_DELETE: storyModel.removeTask(taskId); break;
                        case TASK_MOVERIGHT: storyModel.getTaskById(taskId).increaseStatus(); storyModel.notifyViews(); break;
                    }
                }
            }
        }
    }

    private ButtonType getButtonType(String buttonId) {
        if (buttonId.contains("_moveup_button")) {
            return ButtonType.TASK_MOVEUP;
        } else if (buttonId.contains("_movedown_button")) {
            return ButtonType.TASK_MOVEDOWN;
        } else if (buttonId.contains("_moveright_button")) {
            return ButtonType.TASK_MOVERIGHT;
        } else if (buttonId.contains("_delete_button")) {
            return ButtonType.TASK_DELETE;
        } else {
            throw new IllegalArgumentException("Incorrect Button");
        }
    }

    private int getTaskId(String viewId) {
        return Integer.valueOf(viewId.substring(viewId.indexOf("task-")+"task-".length(), viewId.indexOf("_view")));
    }
}
