package de.saxsys.model;


import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserStoryTest { 
    private UserStory userstory;

    @Before
    public void before() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "Task1", Priority.HIGH));
        tasks.add(new Task(1, "Task2", Priority.HIGH));
        tasks.add(new Task(2, "Task3", Priority.MIDDLE));
        tasks.add(new Task(3, "Task4", Priority.LOW));
        tasks.add(new Task(4, "Task5", Priority.VERY_LOW));

        userstory = new UserStory(0, "Story1", Priority.HIGH, tasks, "An userstory");
    }

    @Test
    public void testConstructor() {
        // Also tests addTask and addAllTasks hbecause they are used by the constructor

        UserStory userstory2 = new UserStory(0, "Story2", Priority.MIDDLE);

        assertEquals("Story2", userstory2.getTitle());
        assertEquals(Priority.MIDDLE, userstory2.getPriority());
        assertEquals("",userstory2.getDescription());

        assertEquals("Story1", userstory.getTitle());
        assertEquals(Priority.HIGH, userstory.getPriority());
        assertEquals("An userstory", userstory.getDescription());
    }

    public void testSetTitle() {
        userstory.setTitle("Story2");
        assertEquals("Story2", userstory.getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTitleException() {
        userstory.setTitle(null);
    }

    public void testSetPriority() {
        userstory.setPriority(Priority.MIDDLE);
        assertEquals(Priority.MIDDLE, userstory.getPriority());
        
        userstory.setPriority(null);
        assertEquals(Priority.MIDDLE, userstory.getPriority());
    }

    @Test
    public void testGetTask() {
        Task testTask = new Task(1, "Task2", Priority.HIGH);

        assertTrue(testTask.equals(userstory.getTaskByTitle("Task2")));
    }
    
    @Test
    public void testAddTask() {
        assertTrue(userstory.addTask(new Task(5, "Task6", Priority.HIGH))); //adding new task
        assertNotNull(userstory.getTaskByTitle("Task6"));
        
        assertFalse(userstory.addTask(new Task(5, "Task6", Priority.HIGH))); //adding task that allready exists
    } 
    
    @Test
    public void testAddAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(2, "Task3", Priority.MIDDLE));  //adding task that allready exists
        tasks.add(new Task(5, "Task6", Priority.LOW)); //adding new task
        tasks.add(new Task(6, "Task7", Priority.VERY_LOW)); //adding new task
        
        assertEquals(2, userstory.addAllTasks(tasks));
        assertNotNull(userstory.getTaskByTitle("Task6"));
        assertNotNull(userstory.getTaskByTitle("Task7"));
    }
    
    @Test
    public void testRemoveTask() {
        assertTrue(userstory.removeTask("Task4"));

        assertNull(userstory.getTaskByTitle("Task4"));
    }

    @Test
    public void testToJSON() throws IOException{
        //Json representation of the userstory object
        String jsonString = "{\"id\":0,\"title\":\"Story1\",\"description\":\"An userstory\",\"tasks\":[{\"id\":0,\"title\":\"Task1\",\"priority\":\"HIGH\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":1,\"title\":\"Task2\",\"priority\":\"HIGH\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":2,\"title\":\"Task3\",\"priority\":\"MIDDLE\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":3,\"title\":\"Task4\",\"priority\":\"LOW\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":4,\"title\":\"Task5\",\"priority\":\"VERY_LOW\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"}],\"priority\":\"HIGH\"}";
        assertEquals(jsonString, userstory.toJson());
        assertEquals(jsonString, UserStory.toJson(userstory));
    }
    
    @Test
    public void testFromJSON() throws IOException{
        //Json representation of an UserStory object, which equals userstory
        String jsonString = "{\"id\":0,\"title\":\"Story1\",\"description\":\"An userstory\",\"tasks\":[{\"id\":0,\"title\":\"Task1\",\"priority\":\"HIGH\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":1,\"title\":\"Task2\",\"priority\":\"HIGH\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":2,\"title\":\"Task3\",\"priority\":\"MIDDLE\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":3,\"title\":\"Task4\",\"priority\":\"LOW\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"},{\"id\":4,\"title\":\"Task5\",\"priority\":\"VERY_LOW\",\"status\":\"TODO\",\"description\":\"\",\"inCharge\":\"\"}],\"priority\":\"HIGH\"}";
        
        UserStory userstoryFromJson = UserStory.fromJson(jsonString);

        assertTrue(userstory.equals(userstoryFromJson));
    }
    
    @Test(expected=JsonMappingException.class)
    public void testFromJSONException() throws IOException{
        UserStory userstoryFromJson = UserStory.fromJson("{\"test\":\"test\"}");
    }
    
    @Test
    public void testHashCode() {
        UserStory userstory2 = new UserStory(0, "Story2", Priority.MIDDLE);
        UserStory userstory3 = new UserStory(0, "Story2", Priority.MIDDLE);
        UserStory userstory4= new UserStory(1, "Story4", Priority.LOW);
        
        assertTrue(userstory2.hashCode() == userstory3.hashCode());
        assertFalse(userstory2.hashCode() == userstory4.hashCode());
    }
    
    @Test
    public void testMoveTaskUp() {
        assertFalse(userstory.moveTaskUp("Task1"));
        assertTrue(userstory.moveTaskUp("Task5"));
        
        assertEquals("Task5", userstory.getTasks().get(3).getTitle());
        assertEquals("Task4", userstory.getTasks().get(4).getTitle());
    }
    
    @Test
    public void testMoveTaskDown() {
        assertFalse(userstory.moveTaskDown("Task5"));
        assertTrue(userstory.moveTaskDown("Task4"));
        
        assertEquals("Task4", userstory.getTasks().get(4).getTitle());
        assertEquals("Task5", userstory.getTasks().get(3).getTitle());
    }
    
    @Test
    public void testEquals() throws IOException{
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "Task1", Priority.HIGH));
        tasks.add(new Task(1, "Task2", Priority.HIGH));
        tasks.add(new Task(2, "Task3", Priority.MIDDLE));
        tasks.add(new Task(3, "Task4", Priority.LOW));
        UserStory userstory2 = new UserStory(0, "Story1", Priority.HIGH, tasks, "An userstory");
        
        assertFalse(userstory.equals(userstory2));
        
        userstory2.addTask(new Task(4, "Task5", Priority.VERY_LOW));

        assertTrue(userstory.equals(userstory2));
        
        userstory2.setTitle("Story2");
        assertFalse(userstory.equals(userstory2));
        
        assertTrue(userstory.equals(userstory));
    }
}
