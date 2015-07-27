package de.saxsys.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStoryTest { 
    private UserStory userstory;

    @Before
    public void before() {
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("Task1", Priority.HIGH));
        tasks.add(new Task("Task2", Priority.HIGH));
        tasks.add(new Task("Task3", Priority.MIDDLE));
        tasks.add(new Task("Task4", Priority.LOW));
        tasks.add(new Task("Task5", Priority.VERY_LOW));

        userstory = new UserStory("Story1", Priority.HIGH, tasks, "An userstory");
    }

    @Test
    public void testConstructor() {
        // Also tests addTask and addAllTasks hbecause they are used by the constructor

        UserStory userstory2 = new UserStory("Story2", Priority.MIDDLE);

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
        Task testTask = new Task("Task2", Priority.HIGH);

        assertTrue(testTask.equals(userstory.getTask("Task2")));
    }

    @Test
    public void testRemoveTask() {
        assertTrue(userstory.removeTask("Task4"));

        assertNull(userstory.getTask("Task4"));
    }

    @Test
    public void testToJSON() {
        //Json representation of the userstory object
        String jsonString = "{\"title\":\"Story1\",\"description\":\"An userstory\",\"tasks\":[{\"title\":\"Task1\",\"priority\":\"HIGH\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task2\",\"priority\":\"HIGH\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task3\",\"priority\":\"MIDDLE\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task4\",\"priority\":\"LOW\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task5\",\"priority\":\"VERY_LOW\",\"description\":\"\",\"inCharge\":\"\"}],\"priority\":\"HIGH\"}";
        assertEquals(jsonString, userstory.toJson());
        assertEquals(jsonString, UserStory.toJson(userstory));
    }
    
    @Test
    public void testFromJSON() {
        //Json representation of an UserStory object, which equals userstory
        String jsonString = "{\"title\":\"Story1\",\"description\":\"An userstory\",\"tasks\":[{\"title\":\"Task1\",\"priority\":\"HIGH\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task2\",\"priority\":\"HIGH\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task3\",\"priority\":\"MIDDLE\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task4\",\"priority\":\"LOW\",\"description\":\"\",\"inCharge\":\"\"},{\"title\":\"Task5\",\"priority\":\"VERY_LOW\",\"description\":\"\",\"inCharge\":\"\"}],\"priority\":\"HIGH\"}";
        
        UserStory userstoryFromJson = UserStory.fromJson(jsonString);
        
        assertEquals(userstory, userstory);
    }
    
    @Test
    public void testHashCode() {
        UserStory userstory2 = new UserStory("Story2", Priority.MIDDLE);
        UserStory userstory3 = new UserStory("Story2", Priority.MIDDLE);
        UserStory userstory4= new UserStory("Story4", Priority.LOW);
        
        assertTrue(userstory2.hashCode() == userstory3.hashCode());
        assertFalse(userstory2.hashCode() == userstory4.hashCode());
    }
    
    @Test
    public void testMoveTaskUp() {
        assertFalse(userstory.moveTaskUp("Task1"));
        assertTrue(userstory.moveTaskUp("Task5"));
        
        assertEquals("Task5", userstory.getTask(3).getTitle());
        assertEquals("Task4", userstory.getTask(4).getTitle());
    }
    
    @Test
    public void testMoveTaskDown() {
        assertFalse(userstory.moveTaskDown("Task5"));
        assertTrue(userstory.moveTaskDown("Task4"));
        
        assertEquals("Task4", userstory.getTask(4).getTitle());
        assertEquals("Task5", userstory.getTask(3).getTitle());
    }
}
