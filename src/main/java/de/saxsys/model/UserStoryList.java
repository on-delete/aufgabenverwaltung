package de.saxsys.model;

import java.io.IOException;
import java.util.*;

import de.saxsys.gui.ActiveViewElement;
import de.saxsys.gui.Model;
import org.codehaus.jackson.map.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;


public class UserStoryList implements Model {

    private List<UserStory> userStories;

    private Set<ActiveViewElement> registeredViews;

// UserStoryList definition: without parameters, parameter userStories

    /**
     * Create a UserStoryList without parameters
     */
    public UserStoryList() {
        this.registeredViews = new HashSet<>();
        this.userStories = new ArrayList<UserStory>();
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
     * @param userStory
     * @return
     */
    public boolean addUserStory(UserStory userStory) {
        if (userStory != null && getUserStory(userStory.getTitle()) == null) {
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
     * @param title title of the UserStory to be deleted
     * @return
     */
    public boolean removeUserStory(String title) {
        if (title != null) {
            UserStory target = getUserStory(title);
            if (target != null) {
                userStories.remove(target);
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
     * Removes all UserStories from the respective UserStoryList
     */
    public void removeAllUserStories() {
        this.userStories = new ArrayList<UserStory>();
        notifyViews();
    }


    /**
     * Increases the position of a task by 1
     *
     * @param title the title of the target to be moved
     * @return true if the movement was successful
     */
    public boolean moveUserStoryUp(String title) {
        UserStory target = getUserStory(title);

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
     * @param title title the title of the target to be moved
     * @return true if the movement was successful
     */
    public boolean moveUserStoryDown(String title) {
        UserStory target = getUserStory(title);

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
     * @param comp
     * @return
     */
    public boolean equals(UserStoryList comp) {
        if (this == comp) {
            return true;
        } else if (this.numberOfUserStories() != comp.numberOfUserStories()) {
            return false;
        } else {
            for (int i = 0; i < this.numberOfUserStories(); i++) {
                if (!this.getUserStory(i).equals(comp.getUserStory(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Returns the size of the ArrayList userStories
     *
     * @return
     */
    public int numberOfUserStories() {
        return userStories.size();
    }

    // For Commands
    public UserStory getUserStory(String title) {
        // find first element which title property matches the input
        Optional<UserStory> result = userStories.stream().filter((element) -> {
            if (element.getTitle().equals(title)) {
                return true;
            } else {
                return false;
            }
        }).findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    /**
     * Get an Task object by its index in the task list
     *
     * @param index the index of the desired Task object
     * @return the Task object; null if index doesn't exist
     */
    public UserStory getUserStory(int index) {
        if (index < 0 || index > userStories.size() - 1) {
            return null;
        } else {
            return userStories.get(index);
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
     * @param inputUserStoryList
     * @return
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
     * @throws JsonMappingException if the parsed data couldn't be mapped to the object
     * @throws JsonParseException   if the data couldn't be parsed
     * @throws IOException          shouldn't occur
     */
    public static UserStoryList fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, UserStoryList.class);
    }


    /**
     * Creates an JSON Serialization from the calling object object
     *
     * @return
     * @throws JsonGenerationException
     * @throws IOException
     */
    public String toJson() throws JsonGenerationException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public List<UserStory> getUserStories() {
        return this.userStories;
    }
}
