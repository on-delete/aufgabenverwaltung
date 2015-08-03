package de.saxsys.model;

import com.fasterxml.jackson.core.JsonGenerationException;
import de.saxsys.gui.view.ActiveViewElement;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;


public class UserStoryList implements Model {

    private List<UserStory> userStories;

    private Set<ActiveViewElement> registeredViews;

// UserStoryList definition: without parameters, parameter userStories

    /**
     * Create a UserStoryList without parameters
     */
    public UserStoryList() {
        this.registeredViews = new HashSet<>();
        this.userStories = new ArrayList<>();
    }

    /**
     * Create a UserStoryList
     *
     * @param userStories UserStories of the new Column
     */
    public UserStoryList(List<UserStory> userStories) {
        this.registeredViews = new HashSet<>();
        this.userStories = userStories;
    }

    @Override
    public boolean registerView(ActiveViewElement view) {
        return registeredViews.add(view);
    }

    @Override
    public void notifyViews() {
        for (ActiveViewElement view : registeredViews) {
            view.refresh();
        }
    }

    // Commands: add, remove, removeAll

    /**
     * Adds a new UserStory to the UserStoryList
     *
     * @param userStory the UserStory object to be added
     * @return True if adding was successful
     */
    public boolean addUserStory(UserStory userStory) {
        if (userStory != null && getUserStoryByTitle(userStory.getTitle()) == null) {
            this.userStories.add(userStory);
            notifyViews();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Removes one specified UserStory from the UserStoryList
     *
     * @param id the id of the UserStory to be deleted
     * @return True if removing was successful
     */
    public boolean removeUserStory(int id) {
        UserStory target = getUserStoryById(id);
        if (target != null) {
                userStories.remove(target);
                notifyViews();
                return true;
        } else {
            return false;
        }
    }

    /**
     * Removes all UserStories from the respective UserStoryList
     */
    public void removeAllUserStories() {
        this.userStories = new ArrayList<>();
        notifyViews();
    }


    /**
     * Increases the position of a task by 1
     *
     * @param id the id of the target to be moved
     * @return true if the movement was successful
     */
    public boolean moveUserStoryUp(int id) {
        UserStory target = getUserStoryById(id);

        if (target != null) {
            int oldIndex = userStories.indexOf(target);

            if (oldIndex > 0) {
                userStories.remove(target);
                userStories.add(oldIndex - 1, target);
                notifyViews();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * Decreases the position of a UserStory by 1
     *
     * @param id the id of the target to be moved
     * @return true if the movement was successful
     */
    public boolean moveUserStoryDown(int id) {
        UserStory target = getUserStoryById(id);

        if (target != null) {
            int oldIndex = userStories.indexOf(target);

            if (oldIndex < (userStories.size() - 1)) {
                userStories.remove(target);
                userStories.add(oldIndex + 1, target);
                notifyViews();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * Defines the equals method for the UserStoryList-Class
     *
     * @param comp the UserStoryList to compare to
     * @return True if both objects are equal
     */
    public boolean equals(UserStoryList comp) {
        if (this == comp) {
            return true;
        } else if (this.getUserStories().size() != comp.getUserStories().size()) {
            return false;
        } else {
            for (int i = 0; i < this.getUserStories().size(); i++) {
                if (!this.getUserStories().get(i).equals(comp.getUserStories().get(i))) {
                    return false;
                }
            }
            return true;
        }
    }


    // For Commands
    public UserStory getUserStoryById(int id) {
        // find first element which title property matches the input
        Optional<UserStory> result = userStories.stream().filter((element) -> (
                (element.getId() == id)
        )).findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    // For Commands
    public UserStory getUserStoryByTitle(String title) {
        // find first element which title property matches the input
        Optional<UserStory> result = userStories.stream().filter((element) -> (
                (element.getTitle().equals(title))
        )).findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }


// JSON

    /**
     * Generates a hashCode of the object
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return this.userStories.toString().hashCode();
    }

    /**
     * @param inputUserStoryList the user story to be serialized
     * @return Json String
     * @throws IOException
     */
    public static String toJson(UserStoryList inputUserStoryList) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(inputUserStoryList);
    }

    /**
     * Creates a task object from an JSON serialization
     *
     * @param jsonString The JSON serialization to be marshaled
     * @return The created Task object
     * @throws IOException shouldn't occur
     */
    public static UserStoryList fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, UserStoryList.class);
    }


    /**
     * Creates an JSON Serialization from the calling object object
     *
     * @return Json String
     * @throws JsonGenerationException
     * @throws IOException
     */
    public String toJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    /**
     * gets the userstory list
     *
     * @return Userstories as unmodifiable list
     */
    public List<UserStory> getUserStories() {
        return Collections.unmodifiableList(this.userStories);
    }
}
