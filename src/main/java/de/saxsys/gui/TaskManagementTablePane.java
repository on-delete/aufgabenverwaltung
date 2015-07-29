package de.saxsys.gui;

import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.Node;

public class TaskManagementTablePane extends GridPane {
    public TaskManagementTablePane() {
        setHeadings();        
        initLogic();
    }

    private void setHeadings() {
        int columnNumber = 0;
        
        for (RowTitles title: RowTitles.values()) {
            getColumnConstraints().add(new ColumnConstraints(1500/RowTitles.values().length));
            
            Text rowTitle = new Text(title.getTitle());
            GridPane.setHalignment(rowTitle, HPos.CENTER);
            rowTitle.setId(title.getTitle() + "_text");
            
            add(rowTitle, columnNumber, 0);
            
            columnNumber++;
        }
    }
    
    /*
    private void initLogic() {
        TaskManagementUserStoryViewContainer userStoryView = new TaskManagementUserStoryViewContainer();
        

        int rowNumber = getNumberOfRows()+1;
        
        TaskManagerTaskListView todoView = userStoryView.getTodoView();
        todoView.setId("todo_view");
        TaskManagerTaskListView inProgressView = userStoryView.getInProgressView();
        todoView.setId("in_progress_view");
        TaskManagerTaskListView doneView = userStoryView.getDoneView();
        todoView.setId("done_view");
        
        add(todoView, 0, rowNumber);
        add(inProgressView, 1, rowNumber);
        add(doneView, 2, rowNumber);
        
    }
    
    */
    public int getMaxRowNumber(){
        int numberOfRows = 0;
        for (Node element : getChildren()) {
            numberOfRows = Math.max(numberOfRows, getRowIndex(element));
        }
        return numberOfRows;
    }
}
