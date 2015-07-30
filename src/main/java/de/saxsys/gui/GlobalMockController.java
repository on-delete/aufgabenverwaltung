package de.saxsys.gui;

import de.saxsys.model.Priority;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import de.saxsys.model.UserStoryList;

import java.util.ArrayList;
import java.util.List;

public class GlobalMockController implements GlobalController {
    @Override
    public UserStoryList getGlobalModelInstance() {
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(new Task("Task1 ", Priority.HIGH));
        tasks1.add(new Task("Task2 ", Priority.HIGH));
        Task task3 = new Task("Task3 ", Priority.HIGH);
        task3.increaseStatus();
        tasks1.add(task3);
        Task task4 = new Task("Task4 ", Priority.HIGH);
        task4.increaseStatus();
        task4.increaseStatus();
        tasks1.add(task4);
        UserStory story1 = new UserStory("Story1", Priority.HIGH, tasks1);

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(new Task("Task1 ", Priority.HIGH));
        tasks2.add(new Task("Task2 ", Priority.HIGH));
        tasks2.add(new Task("Task3 ", Priority.HIGH));
        tasks2.add(new Task("Task4 ", Priority.HIGH));
        UserStory story2 = new UserStory("Story2", Priority.HIGH, tasks2);

        UserStoryList list = new UserStoryList();
        list.addUserStory(story1);
        list.addUserStory(story2);

        return list;
    }
}
