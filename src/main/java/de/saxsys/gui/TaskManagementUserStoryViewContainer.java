package de.saxsys.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import de.saxsys.model.Status;
import de.saxsys.model.UserStory;
import de.saxsys.model.Task;

public class TaskManagementUserStoryViewContainer {
    private final UserStory userstory;

    public TaskManagementUserStoryViewContainer() {
        this.userstory = ModelContext.getInstance().getUserStory();
    }

    public TaskManagerTaskListView getTodoView() {
        List<Task> todoTasks = userstory.getStream().filter((task) -> {
            return (task.getStatus().equals(Status.TODO)) ? true : false;
        }).collect(Collectors.toCollection(() -> {
            return new ArrayList<Task>();
        }));
        
        return new TaskManagerTaskListView(todoTasks);
    }

    public TaskManagerTaskListView getInProgressView() {
        List<Task> inProgressTasks = userstory.getStream().filter((task) -> {
            return (task.getStatus().equals(Status.IN_PROGRESS)) ? true : false;
        }).collect(Collectors.toCollection(() -> {
            return new ArrayList<Task>();
        }));

        return new TaskManagerTaskListView(inProgressTasks);
    }

    public TaskManagerTaskListView getDoneView() {
        List<Task> doneTasks = userstory.getStream().filter((task) -> {
            return (task.getStatus().equals(Status.DONE)) ? true : false;
        }).collect(Collectors.toCollection(() -> {
            return new ArrayList<Task>();
        }));

        return new TaskManagerTaskListView(doneTasks);
    }
}
