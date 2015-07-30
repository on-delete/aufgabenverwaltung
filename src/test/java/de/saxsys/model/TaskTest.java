package de.saxsys.model;

import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TaskTest {
    private Task task;
    
    @Before
    public void before() {
        task = new Task("Task1", Priority.MIDDLE, "A standard Task", "Person");
    }
    
    //Constructor test
    @Test
    public void testConstructor() {
        Task task2 = new Task("Task2", Priority.MIDDLE);
        
        assertNotNull(task2);
        assertEquals("Task2", task2.getTitle());
        assertEquals(Priority.MIDDLE, task2.getPriority());
        assertEquals(Status.TODO, task2.getStatus());
        assertEquals("", task2.getDescription());
        assertEquals("", task2.getInCharge());
    }
    
    //Equals Test
    @Test
    public void testEquals() {
        Task task1 = task; //same object
        Task task2 = new Task("Task1", Priority.MIDDLE, "A standard Task", "Person"); //object that is equal
        Task task3 = new Task("Task3", Priority.MIDDLE, "A standard Task", "Person"); //object that is different
        Task task4 = new Task("Task4", Priority.MIDDLE); //object that contains null values
        Task task5 = new Task("Task4", Priority.MIDDLE); //object that contains null values which is equal to the other one
        
        assertTrue(task.equals(task1));
        assertTrue(task.equals(task2));
        assertFalse(task.equals(task3));
        assertFalse(task.equals(task4));
        assertFalse(task4.equals(task));
        assertTrue(task4.equals(task5));
    }
    
    //Increase status test
    @Test
    public void testIncreaseStatus() {
        assertEquals(Status.TODO, task.getStatus());
        assertTrue(task.increaseStatus());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
        assertTrue(task.increaseStatus());
        assertEquals(Status.DONE, task.getStatus());
        assertFalse(task.increaseStatus());
        assertEquals(Status.DONE, task.getStatus());
    }
    
    //Json Tests
    @Test
    public void testStaticToJson() {
        try {
            assertEquals("{\"title\":\"Task1\",\"priority\":\"MIDDLE\",\"status\":\"TODO\",\"description\":\"A standard Task\",\"inCharge\":\"Person\"}", Task.toJson(task));
        }
        catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void testToJson() {
        try {
            assertEquals("{\"title\":\"Task1\",\"priority\":\"MIDDLE\",\"status\":\"TODO\",\"description\":\"A standard Task\",\"inCharge\":\"Person\"}", task.toJson());
        }
        catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void testFromJson() throws IOException{
            Task task2 = Task.fromJson("{\"title\":\"Task1\",\"priority\":\"MIDDLE\",\"status\":\"TODO\",\"description\":\"A standard Task\",\"inCharge\":\"Person\"}");
            assertTrue(task.equals(task2));
    }

    @Test(expected = IOException.class)
    public void testStaticToJsonRequiredTitleException() throws IOException{
        Task.fromJson("{\"title\":null,\"priority\":\"MIDDLE\",\"status\":\"TODO\",\"description\":\"A standard Task\",\"inCharge\":\"Person\"}");
    }
    
    @Test(expected = IOException.class)
    public void testStaticToJsonIncorrectMappingException() throws IOException {
        Task.fromJson("{\"test\":\"test\"");
    }
}
