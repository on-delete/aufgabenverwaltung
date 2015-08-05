package de.saxsys.gui.controller;

import de.saxsys.gui.view.AddUserStoryView;
import de.saxsys.gui.view.TablePane;
import de.saxsys.gui.view.TitleView;
import de.saxsys.model.Priority;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import de.saxsys.model.UserStoryList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GlobalController implements EventHandler<ActionEvent> {
    UserStoryList globalModelInstance;

    public GlobalController() {
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(new Task(0, "Task1 ", Priority.HIGH));
        tasks1.add(new Task(1, "Task2 ", Priority.HIGH));
        Task task3 = new Task(2, "Task3 ", Priority.HIGH);
        task3.increaseStatus();
        tasks1.add(task3);
        Task task4 = new Task(3, "Task4 ", Priority.HIGH);
        task4.increaseStatus();
        task4.increaseStatus();
        tasks1.add(task4);
        UserStory story1 = new UserStory(0, "Story1", Priority.HIGH, tasks1);

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(new Task(0, "Task1 ", Priority.HIGH));
        tasks2.add(new Task(1, "Task2 ", Priority.HIGH));
        tasks2.add(new Task(2, "Task3 ", Priority.HIGH));
        tasks2.add(new Task(3, "Task4 ", Priority.HIGH));
        UserStory story2 = new UserStory(1, "Story2", Priority.HIGH, tasks2);

        globalModelInstance = new UserStoryList();
        globalModelInstance.addUserStory(story1);
        globalModelInstance.addUserStory(story2);
    }

    public UserStoryList getGlobalModelInstance() {
        return globalModelInstance;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION) && (event.getSource() instanceof Button)) {
            Button source = (Button) event.getSource();
            if (source.getParent().getParent() instanceof TitleView) {
                handleUserStoryOperations(source);
            } else if (source.getParent() instanceof TablePane && source.getId().equals("add_userstory_button")) {
                handleAddUserStory();
            }
        }
    }

    private int getUserStoryId(String viewId) {
        return Integer.valueOf(viewId.substring(viewId.indexOf("story-") + "story-".length(), viewId.indexOf("_title")));
    }

    private ButtonType getButtonType(String buttonId) {
        if (buttonId.contains("_moveup_button")) {
            return ButtonType.USERSTORY_MOVEUP;
        } else if (buttonId.contains("_movedown_button")) {
            return ButtonType.USERSTORY_MOVEDOWN;
        } else if (buttonId.contains("_delete_button")) {
            return ButtonType.USERSTORY_DELETE;
        } else if (buttonId.contains("_addtask_button")) {
            return ButtonType.USERSTORY_ADDTASK;
        } else {
            throw new IllegalArgumentException("Incorrect source button");
        }
    }

    private void handleUserStoryOperations(Button source) {
        int storyId = getUserStoryId(source.getParent().getParent().getId());

        switch (getButtonType(source.getId())) {
            case USERSTORY_MOVEUP: globalModelInstance.moveUserStoryUp(storyId); break;
            case USERSTORY_MOVEDOWN: globalModelInstance.moveUserStoryDown(storyId); break;
            case USERSTORY_DELETE: globalModelInstance.removeUserStory(storyId); break;
            //ADDTASK is handled by UserStoryController
        }
    }

    private void handleAddUserStory() {
        AddUserStoryController addUserStoryController = new AddUserStoryController(globalModelInstance);
        AddUserStoryView addUserStoryView = new AddUserStoryView(addUserStoryController);
    }
}
