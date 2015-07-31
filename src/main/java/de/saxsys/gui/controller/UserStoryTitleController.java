package de.saxsys.gui.controller;

import de.saxsys.gui.view.TaskManagementUserStoryTitleView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class UserStoryTitleController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        if ((event.getEventType().equals(ActionEvent.ACTION)) && (event.getSource() instanceof Hyperlink)) {
            Hyperlink source = (Hyperlink)event.getSource();
            if (source.getParent().getParent() instanceof TaskManagementUserStoryTitleView) {
                TaskManagementUserStoryTitleView userStoryTitleView = (TaskManagementUserStoryTitleView)source.getParent().getParent();
                userStoryTitleView.switchDetailedView();
            }
        }
    }
}
