package de.saxsys.model;

/**
 * Class for storing information about a task.
 * Includes task title, priority, description (optional) and name of th person in charge for this task (optional).
 */

public class Task {
    private String title;
    private int priority;
    private String description;
    private String inCharge;
    
    /** 
     * Create a Task without description and person in charge
     *  @param title Title of the new Task object
     *  @param priority Priority of the new Task object
     */
    public Task(String title, int priority) {
        this(title, priority, null);       
    }
    
    /** 
     * Create a Task without person in charge
     *  @param title Title of the new Task object
     *  @param priority Priority of the new Task object
     *  @param description description of the new Task object
     */
    public Task(String title, int priority, String description) {
       this(title, priority, description, null);      
    }
    
    /** 
     * Create a Task without person in charge
     *  @param title Title of the new Task object
     *  @param priority Priority of the new Task object
     *  @param description description of the new Task object
     *  @param inCharge person in charge of the new Task object
     *  @throws IllegalArgumentException If title is null
     *  @throws IllegalArgumentException If priority is 0
     */
    public Task(String title, int priority, String description, String inCharge) {
        setTitle(title);
        setPriority(priority);
        setDescription(description);
        setInCharge(inCharge);
    }
    
    
    /**
     * Set a new task title in the Task object
     * @param title new title for the Task object; null is not allowed
     * @throws IllegalArgumentException If title is null
     */
    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
        else {
            throw new IllegalArgumentException("Title musn't be null");
        }
    }
    
    /**
     * Set a new task priority in the Task object
     * @param priority new priority for the Task object; 0 is not allowed
     * @throws IllegalArgumentException If priority is 0
     */
    public void setPriority(int priority) {
        if (priority > 0) {
            this.priority = priority;
        }
        else {
            throw new IllegalArgumentException("Priority must be bigger than 0");
        }
    }
    
    /**
     * Set a new task description in the Task object
     * @param description new priority for the Task object
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Set a new person in charge for the task in the Task object
     * @param inCharge new name of person in charge for the Task object
     */
    public void setInCharge(String inCharge) {
        this.inCharge = inCharge;
    }
    
    
    /**
     * Gets the title of the task
     * @return The title as an String
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Gets the priority of the task
     * @return The priority as an int
     */
    public int getPriority() {
        return this.priority;
    }
    
    /**
     * Gets the description of the task
     * @return The description as an String
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Gets the person in charge of the task
     * @return The name of the person in charge as an String
     */
    public String getInCharge() {
        return this.inCharge;
    }
}
