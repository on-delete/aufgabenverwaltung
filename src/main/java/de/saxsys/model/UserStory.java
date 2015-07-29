package de.saxsys.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fasterxml.jackson.core.JsonGenerationException;

import de.saxsys.gui.Model;
import de.saxsys.gui.ActiveViewElement;

/**
 * Class for storing information about a userstory. Includes userstory title, priority, description (optional) and a
 * list of associated tasks. The task list is allways sorted by the priority of the task and priorities are without gap.
 */
public class UserStory implements Model {
    private String title;
    private String description = "";
    private List<Task> tasks = new ArrayList<>();
    private Priority priority;

    // list vor registerd views
    private Set<ActiveViewElement> registeredViews;

    
    //constructor for Jackson, shouldn't be used
    private UserStory(){}
    
    /**
     * Create a UserStory object without tasks and description
     * 
     * @param title
     *            title of the new userstory
     * @param priority
     *            priority of the new userstory
     * @param description
     *            description of the new userstory
     */
    public UserStory(String title, Priority priority) {
        this(title, priority, (String) null);
    }

    /**
     * Create a UserStory object without tasks
     * 
     * @param title
     *            title of the new userstory
     * @param priority
     *            priority of the new userstory
     * @param description
     *            description of the new userstory
     */
    public UserStory(String title, Priority priority, String description) {
        this(title, priority, null, description);
    }

    /**
     * Create a UserStory object without a description
     * 
     * @param title
     *            title of the new userstory
     * @param priority
     *            priority of the new userstory
     * @param tasks
     *            list of tasks associated with the new userstory
     */
    public UserStory(String title, Priority priority, List<Task> tasks) {
        this(title, priority, tasks, null);
    }

    /**
     * Create a UserStory object with all information
     * 
     * @param title
     *            title of the new userstory
     * @param priority
     *            priority of the new userstory
     * @param tasks
     *            list of tasks associated with the new userstory
     * @param description
     *            description of the new userstory
     */
    public UserStory(String title, Priority priority, List<Task> tasks, String description) {
        this.tasks = new ArrayList<Task>();
        this.registeredViews = new HashSet<ActiveViewElement>();
        setTitle(title);
        setPriority(priority);
        addAllTasks(tasks);
        setDescription(description);
    }

    /**
     * Set the title of the userstory
     * 
     * @param title
     *            new title of the userstory (musn't be null)
     */
    public void setTitle(String title) {
        if (title != null && title.length() > 0) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("Title musn't be null or empty");
        }
    }

    /**
     * Set the priority of the userstory
     * 
     * @param priority
     *            new priority of the userstory (must be bigger than 0)
     */
    public void setPriority(Priority priority) {
        if (priority != null) {
            this.priority = priority;
        } else {
            this.priority = Priority.MIDDLE;
        }
    }

    /**
     * Set the description of the userstory
     * 
     * @param description
     *            new description of the userstory
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        } else {
            this.description = "";
        }
    }

    /**
     * Get the title of the UserStory object
     * 
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the priority of the UserStory object
     * 
     * @return the priority
     */
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * Get the description of the UserStory object
     * 
     * @return the description, returns empty string if not existing
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Gets the task list
     * @return the task list
     */
    public List<Task> getTasks() {
        return this.tasks;
    }
    
    /**
     * Removes all tasks in the list
     */
    public void removeAllTasks() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Deletes the Task object with the given title from the task list
     * 
     * @param title
     *            the title of the task to be deleted
     * @return true if task was found and deleted, fasle if title wasn't found
     */
    public boolean removeTask(String title) {
        Task target = getTaskByTitle(title);
        return tasks.remove(target);
    }

    /**
     * Adding a new task to the task list, if no task with the same title or priority is existing
     * 
     * @param input
     *            the task object to be added
     * @return true if task was added, false if task was not added (allready existing)
     */
    public boolean addTask(Task input) {
        if (input != null && getTaskByTitle(input.getTitle()) == null) {
            this.tasks.add(input);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adding a list of tasks to the task list (only the task that don't allready exist (title and priority))
     * 
     * @param input
     *            the list of tasks
     * @return the number of inserted tasks
     */
    public int addAllTasks(List<Task> input) {
        int added = 0;
        if (input != null) {
            for (int i = 0; i <= input.size() - 1; i++) {
                if (addTask(input.get(i))) {
                    added++;
                }
            }
        }
        return added;
    }

    /**
     * get a Task object from the tasks list of the userstory by it's title
     * 
     * @param title
     *            the title of the task
     * @return the Task Object; returns null if task object is not existing
     */
    public Task getTaskByTitle(String title) {
        // find first element which title property matches the input
        Optional<Task> result = tasks.stream().filter((element) -> {
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
     * @param index
     *            the index of the desired Task object
     * @return the Task object; null if index doesn't exist
     */
    public Task getTaskByIndex(int index) {
        if (index < 0 || index > tasks.size() - 1) {
            return null;
        } else {
            return tasks.get(index);
        }
    }

    /**
     * Gets the number of tasks in the user story
     * 
     * @return the number of tasks
     */
    public int numberOfTasks() {
        return tasks.size();
    }

    /**
     * gets the Stream of the task list
     * 
     * @return the stream
     */
    public Stream<Task> stream() {
        return tasks.stream();
    }

    /**
     * Increases the position of a task by 1
     * 
     * @param title
     *            the title of the target to be moved
     * @return true if the movement was successsfull
     */
    public boolean moveTaskUp(String title) {
        Task target = getTaskByTitle(title);

        if (target != null) {
            int oldIndex = tasks.indexOf(target);

            if (oldIndex > 0) {
                tasks.remove(target);
                tasks.add(oldIndex - 1, target);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Decreases the position of a task by 1
     * 
     * @param title
     *            the title of the target to be moved
     * @return true if the movement was successsfull
     */
    public boolean moveTaskDown(String title) {
        Task target = getTaskByTitle(title);

        if (target != null) {
            int oldIndex = tasks.indexOf(target);

            if (oldIndex < (tasks.size() - 1)) {
                tasks.remove(target);
                tasks.add(oldIndex + 1, target);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * compares the UserStory object with another one
     * 
     * @param comp
     *            the other UserStory object to compare with
     * @return true if objects are equal, false if not
     */
    public boolean equals(UserStory comp) {
        if (this == comp) {
            return true;
        } else if (this.getTitle().equals(comp.getTitle()) && this.getPriority() == comp.getPriority()
                && this.getDescription().equals(comp.getDescription()) && this.tasksEquals(comp)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates a String representation of the object (Json representation)
     * 
     * @return the string representation in Json
     */
    @Override
    public String toString() {
        return "{\"title\":\""+title+"\",\"description\":\""+description+"\",\"tasks\":\""+tasks.toString()+"\",\"priority\":\""+priority.toString()+"\"}";
    }

    /**
     * Generates a hashCode of the object
     * 
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Creates an JSON Serialization of the given Task object
     * 
     * @param inputTask
     *            The task objhect to be serialized
     * @return The JSON String
     * @throws JsonGenerationException 
     * @throws IOException 
     */
    public static String toJson(UserStory inputUserStory) throws IOException  {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(inputUserStory);
    }

    /**
     * Creates a task object from an JSON serialization
     * 
     * @param jsonString
     *            The JSON serialization to be nmarshalled
     * @return The created Task object
     * @throws JsonSyntaxException
     *             if the parsed data were incorrect Json
     * @throws IllegalArgumentsException
     *             if the Json String couldn't be mapped to the object
     */
    public static UserStory fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, UserStory.class);
    }

    /**
     * Creates an JSON Serialization from the calling UserStory object
     * 
     * @return The JSON String
     * @throws IOException 
     * @throws JsonGenerationException 
     */
    public String toJson() throws JsonGenerationException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    // checks if the task lists of this and of another UserStory object are equal
    private boolean tasksEquals(UserStory comp) {
        if (this.numberOfTasks() != comp.numberOfTasks()) {
            return false;
        } else {
            for (int i = 0; i <= this.numberOfTasks() - 1; i++) {
                if (!this.getTaskByIndex(i).equals(comp.getTaskByIndex(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    // Model methods

    /**
     * Adds en view Element, that will be notified whenever a property is changed
     * 
     * @param view
     *            the view Element
     * @return True if element was added
     */
    @Override
    public boolean registerView(ActiveViewElement view) {
        return registeredViews.add(view);
    }

    /**
     * Refresh all registered views
     */
    @Override
    public void notifyViews() {
        for (ActiveViewElement element : registeredViews) {
            element.refresh();
        }
    }
}
