package de.saxsys.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TaskTests {
    private Task task;
    
    @Before
    public void before() {
        task = new Task("Task1", 1, "A standard Task", "Person");
    }
    
    //Equals Test
    @Test
    public void testEquals() {
        Task task1 = task; //same object
        Task task2 = new Task("Task1", 1, "A standard Task", "Person"); //object that is equal
        Task task3 = new Task("Task3", 1, "A standard Task", "Person"); //object that is different
        
        assertTrue(task.equals(task1));
        assertTrue(task.equals(task2));
        assertFalse(task.equals(task3));
    }
    
    //Get Tests
    @Test
    public void testGetTitle() {
        assertEquals("Task1", task.getTitle());
    }
    
    @Test
    public void testGetPriority() {
        assertEquals(1, task.getPriority());
    }
    
    @Test
    public void testGetDescription() {
        assertEquals("A standard Task", task.getDescription());
    }
    
    @Test
    public void testGetInCharge() {
        assertEquals("Person", task.getInCharge());
    }
    
    //Set Tests
    @Test
    public void testSetTitle() {
        task.setTitle("Task2");
        assertEquals("Task2", task.getTitle());
    }
    
    @Test
    public void testSetPriority() {
        task.setPriority(2);
        assertEquals(2, task.getPriority());
    }
    
    @Test
    public void testSetDescription() {
        task.setDescription("Changed Description");
        assertEquals("Changed Description", task.getDescription());
    }
    
    @Test
    public void testSetInCharge() {
        task.setInCharge("Person2");
        assertEquals("Person2", task.getInCharge());
    }
    
    //Json Tests
    @Test
    public void testStaticToJson() {
        try {
            assertEquals("{\"title\":\"Task1\",\"priority\":1,\"description\":\"A standard Task\",\"inCharge\":\"Person\"}", Task.toJson(task));
        }
        catch (Exception e) {
            fail();
        }
    }
    
    //Json Tests
    @Test
    public void testToJson() {
        try {
            assertEquals("{\"title\":\"Task1\",\"priority\":1,\"description\":\"A standard Task\",\"inCharge\":\"Person\"}", task.toJson());
        }
        catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void testFromJson() {
        Task task2 = Task.fromJson("{\"title\":\"Task1\",\"priority\":1,\"description\":\"A standard Task\",\"inCharge\":\"Person\"}");
        assertTrue(task.equals(task2));
    }
}
