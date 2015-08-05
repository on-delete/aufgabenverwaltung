package de.saxsys.gui.controller;

import de.saxsys.gui.view.TaskManagementAddTaskView;
import de.saxsys.gui.view.TaskManagementEditUserStoryView;
import de.saxsys.gui.view.TaskManagementTaskView;
import de.saxsys.gui.view.TaskManagementUserStoryTitleView;
import de.saxsys.model.UserStory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class UserStoryController implements EventHandler<ActionEvent> {
    private final UserStory userStoryModel;

    public UserStoryController(UserStory userStoryModel) {
        this.userStoryModel = userStoryModel;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            if (event.getSource() instanceof Button) {
                Button source = (Button) event.getSource();
                if ( (source.getParent().getParent() instanceof TaskManagementUserStoryTitleView) && source.getId().contains("_addtask_button") ) {
                    handleAddTask();
                } else if ( (source.getParent().getParent() instanceof TaskManagementTaskView) && !source.getId().contains("_edit_button")) {
                    handleTaskOperations(source);
                } else if ((source.getParent().getParent() instanceof TaskManagementUserStoryTitleView) && source.getId().contains("_edit_button")) {
                    handleEditUserStory();
                } else if ((source.getParent().getParent() instanceof TaskManagementTaskView) && source.getId().contains("_edit_button")) {
                    handleEditTask(source);
                }
            }
        }
    }

    private void handleTaskOperations(Button source) {
        int taskId = getTaskId(source.getParent().getParent().getId());
        switch (getButtonType(source.getId())) {
            case TASK_MOVEUP: userStoryModel.moveTaskUp(taskId); break;
            case TASK_MOVEDOWN: userStoryModel.moveTaskDown(taskId); break;
            case TASK_DELETE: userStoryModel.removeTask(taskId); break;
            case TASK_MOVERIGHT: userStoryModel.getTaskById(taskId).increaseStatus(); userStoryModel.notifyViews(); break;
        }
    }

    private void handleAddTask() {
        AddTaskController addTaskController = new AddTaskController(userStoryModel);
        TaskManagementAddTaskView addView = new TaskManagementAddTaskView(addTaskController);
    }

    private void handleEditUserStory() {
        EditUserStoryController editUserStoryController = new EditUserStoryController(userStoryModel);
        TaskManagementEditUserStoryView editView = new TaskManagementEditUserStoryView(editUserStoryController, userStoryModel);
    }

    private void handleEditTask(Button source) {
        int taskId = getTaskId(source.getParent().getParent().getId());
        EditTaskController editTaskController = new EditTaskController(userStoryModel.getTaskById(taskId));
        TaskManagementEditTaskView editView = new TaskManagementEditTaskView(editTaskController, userStoryModel.getTaskById(taskId));
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
