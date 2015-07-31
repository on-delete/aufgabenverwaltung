package de.saxsys.gui.view;

import de.saxsys.model.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TaskManagementTaskView extends VBox implements ActiveViewElement {
    Task task;

    public TaskManagementTaskView(Task task) {
        this.task = task;


        //build view
        setSimpleView();
    }

    private void setSimpleView() {
        HBox simpleView = new HBox();

        Text taskTitle = new Text(task.getTitle());
        taskTitle.setId("task_" + task.getTitle() + "_title_button");

        Button moveUpButton = new Button("Move Up");
        moveUpButton.setId("task_" + task.getTitle() + "_moveup_button");

        Button moveDownButton = new Button("Move Down");
        moveDownButton.setId("task_" + task.getTitle() + "_movedown_button");


        Button moveRightButton = new Button("Move Right");
        moveRightButton.setId("task_" + task.getTitle() + "_moveright_button");


        Button deleteButton = new Button("Delete");
        deleteButton.setId("task_" + task.getTitle() + "_delete_button");


        simpleView.getChildren().addAll(taskTitle, moveDownButton, moveUpButton, deleteButton, moveRightButton);
        getChildren().add(simpleView);
    }

    public void switchDetailedView() {
        if (getChildren().size() < 2) {
            GridPane detailedView = new GridPane();

            Text priorityText = new Text("Priority");
            priorityText.setId("task_" + task.getTitle() + "_priority_text");
            Text priorityValue = new Text(PriorityNames.PRIORITY_NAMES.get(task.getPriority()));
            priorityText.setId("task_" + task.getTitle() + "_priority_value");
            detailedView.add(priorityText, 0, 0);
            detailedView.add(priorityValue, 1, 0);

            Text descriptionText = new Text("Description");
            descriptionText.setId("task_" + task.getTitle() + "_description_text");
            Text descriptionValue = new Text(task.getDescription());
            descriptionText.setId("task_" + task.getTitle() + "_description_value");
            detailedView.add(descriptionText, 0, 1);
            detailedView.add(descriptionValue, 1, 1);

            Text inChargeText = new Text("Person in Charge");
            inChargeText.setId("task_" + task.getTitle() + "_inCharge_text");
            Text inChargeValue = new Text(task.getInCharge());
            inChargeText.setId("task_" + task.getTitle() + "_inCharge_value");
            detailedView.add(inChargeText, 0, 2);
            detailedView.add(inChargeValue, 1, 2);

            getChildren().add(detailedView);
        } else {
            refresh();
        }
    }

    @Override
    public void refresh() {
        getChildren().clear();
        setSimpleView();
    }
}
