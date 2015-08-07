package de.saxsys.gui.controller;

import de.saxsys.gui.view.AddUserStoryView;
import de.saxsys.gui.view.TablePane;
import de.saxsys.gui.view.TitleView;
import de.saxsys.model.UserStoryList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.*;

public class GlobalController implements EventHandler<ActionEvent> {
    UserStoryList globalModelInstance;

    public GlobalController() {
        File saveFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".taskManagement" + System.getProperty("file.separator") + "save.json");
        if (!saveFile.exists()) {
            createNewSaveFile(saveFile);
        }

        globalModelInstance = getGlobalModelFromFile(saveFile);

    }

    private void createNewSaveFile(File saveFile) {
        BufferedWriter writer = null;
        try {
            saveFile.getParentFile().mkdirs();
            saveFile.createNewFile();
            globalModelInstance = new UserStoryList();

            writer = new BufferedWriter(new FileWriter(saveFile));

            writer.write(globalModelInstance.toJson());
        } catch (IOException e) {
            showErrorMessage();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // this exception occurs when writer can't be closed, so there is no step to handle the problem
            }
        }
    }

    public void saveGlobalModel() {
        File saveFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".taskManagement" + System.getProperty("file.separator") + "save.json");
        try (PrintWriter clearWriter = new PrintWriter(saveFile); BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            clearWriter.write("");

            writer.write(globalModelInstance.toJson());
        } catch (IOException e) {
            showErrorMessage();
        }
    }

    private UserStoryList getGlobalModelFromFile(File saveFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            String JsonString = reader.readLine();
            return UserStoryList.fromJson(JsonString);
        } catch (IOException e) {
            showErrorMessage();
            return null;
        }
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
            case USERSTORY_MOVEUP:
                globalModelInstance.moveUserStoryUp(storyId);
                break;
            case USERSTORY_MOVEDOWN:
                globalModelInstance.moveUserStoryDown(storyId);
                break;
            case USERSTORY_DELETE:
                globalModelInstance.removeUserStory(storyId);
                break;
            //ADDTASK is handled by UserStoryController
        }
    }

    private void handleAddUserStory() {
        AddUserStoryController addUserStoryController = new AddUserStoryController(globalModelInstance);
        AddUserStoryView addUserStoryView = new AddUserStoryView(addUserStoryController);
    }

    private void showErrorMessage() {
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setTitle("Save File not accessible");
        warning.setHeaderText("The save file of the application is not accessible");
        warning.setContentText("Make sure the application has the correct access rights in your home directory");

        warning.showAndWait();

        System.exit(1);
    }
}
