package de.saxsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.saxsys.gui.view.ActiveViewElement;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for storing information about a task. Includes task title, priority, description (optional) and name of th
 * person in charge for this task (optional).
 */
public class Task implements Model {

    @JsonProperty(required = true)
    private String title;
    @JsonProperty(required = true)
    private Priority priority;
    private Status status;
    private String description;
    private String inCharge;

    // list for registered views
    @JsonIgnore
    private Set<ActiveViewElement> registeredViews;

    private Task() {
        this.registeredViews = new HashSet<ActiveViewElement>();
    }

    /**
     * Create a Task without description and person in charge
     * 
     * @param title
     *            Title of the new Task object
     * @param priority
     *            Priority of the new Task object
     */
    public Task(String title, Priority priority) {
        this(title, priority, null);
    }

    /**
     * Create a Task without person in charge
     * 
     * @param title
     *            Title of the new Task object
     * @param priority
     *            Priority of the new Task object
     * @param description
     *            description of the new Task object
     */
    public Task(String title, Priority priority, String description) {
        this(title, priority, description, null);
    }

    /**
     * Create a Task with all information
     * 
     * @param title
     *            Title of the new Task object
     * @param priority
     *            Priority of the new Task object
     * @param description
     *            description of the new Task object
     * @param inCharge
     *            person in charge of the new Task object
     * @throws IllegalArgumentException
     *             If title is null
     * @throws IllegalArgumentException
     *             If priority is 0
     */
    public Task(String title, Priority priority, String description, String inCharge) {
        this.registeredViews = new HashSet<ActiveViewElement>();
        setTitle(title);
        setPriority(priority);
        setDescription(description);
        setInCharge(inCharge);
        this.status = Status.TODO;
    }

    public boolean equals(Task comp) {
        if (this == comp) {
            return true;
        } else if (this.getTitle().equals(comp.getTitle()) && this.getPriority().equals(comp.getPriority())
                && this.getDescription().equals(comp.getDescription()) && this.getInCharge().equals(comp.getInCharge())
                && this.getStatus().equals(comp.getStatus())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set a new task title in the Task object
     * 
     * @param title
     *            new title for the Task object; null is not allowed
     * @throws IllegalArgumentException
     *             If title is null
     */
    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
            notifyViews();
        } else {
            throw new IllegalArgumentException("Title musn't be null");
        }
    }

    /**
     * Set a new task priority in the Task object
     * 
     * @param priority
     *            new priority for the Task object; Default: MIDDLE
     */
    public void setPriority(Priority priority) {
        if (priority != null) {
            this.priority = priority;
            notifyViews();
        } else {
            this.priority = Priority.MIDDLE;
        }
    }

    /**
     * Increase task status in the Task object
     */
    public boolean increaseStatus() {
        if (this.status.ordinal() < Status.values().length-1) {
            this.status = Status.values()[this.status.ordinal()+1];
            notifyViews();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Increase task status in the Task object
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
            notifyViews();
        } else {
            this.description = "";
        }
    }

    /**
     * Set a new person in charge for the task in the Task object
     * 
     * @param inCharge
     *            new name of person in charge for the Task object
     */
    public void setInCharge(String inCharge) {
        if (inCharge != null) {
            this.inCharge = inCharge;
            notifyViews();
        } else {
            this.inCharge = "";
        }
    }

    /**
     * Gets the title of the task
     * 
     * @return The title as an String
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the priority of the task
     * 
     * @return The priority of the task
     */
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * Gets the status of the task
     * 
     * @return The status of the task
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Gets the description of the task
     * 
     * @return The description as an String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the person in charge of the task
     * 
     * @return The name of the person in charge as an String
     */
    public String getInCharge() {
        return this.inCharge;
    }

    /**
     * Creates an JSON Serialization of the given Task object
     * 
     * @param inputTask
     *            The task objhect to be serialized
     * @return The JSON String
     */
    public static String toJson(Task inputTask) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(inputTask);
    }

    /**
     * Creates a task object from an JSON serialization
     * 
     * @param jsonString
     *            The JSON serialization to be nmarshalled
     * @return The created Task object
     * @throws JsonParseException
     *             if the data couldn't be parsed
     * @throws IOException
     *             shouldn't occur
     */
    public static Task fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Task.class);
    }

    /**
     * Creates an JSON Serialization from the calling object object
     * 
     * @return The JSON String
     */
    public String toJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
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
