package de.saxsys.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserStoryListTest {
	
	private UserStoryList userStoryList;
	
	List<UserStory> userStories = new ArrayList<>();
	
	
	
	@Before
	public void before()
	{
		List<UserStory> userStories = new ArrayList<>();
		List<Task> tasks = null;
		userStories.add(new UserStory("US1", Priority.HIGH));
		userStories.add(new UserStory("US2", Priority.MIDDLE, tasks));
		userStories.add(new UserStory("US3", Priority.LOW, "Third Userstory"));
		userStories.add(new UserStory("US4", Priority.VERY_LOW, tasks, "Fourth Userstory"));
		
		userStoryList = new UserStoryList(userStories);
	}
	
	
	
	// I do not test the constructors ...
	// ... but the commands
	@Test
	public void testAddAndRemoveUserStory()
	{
		UserStory myUserStory1 = new UserStory("myUserStory1", Priority.MIDDLE);
		UserStory myUserStory2 = new UserStory("myUserStory2", Priority.LOW);
		UserStory myUserStory3 = new UserStory("myUserStory3", Priority.HIGH);
		UserStory myUserStory4 = new UserStory("myUserStory4", Priority.VERY_LOW);
		
		assertTrue(userStoryList.addUserStory(myUserStory1));
		assertTrue(userStoryList.addUserStory(myUserStory2));
		assertTrue(userStoryList.addUserStory(myUserStory3));
		assertTrue(userStoryList.addUserStory(myUserStory4));
		assertFalse(userStoryList.addUserStory(null));
		
		assertTrue(userStoryList.removeUserStory("myUserStory1"));
		assertFalse(userStoryList.removeUserStory(""));		
	}

	
	@Test
	public void testRemoveAllUserStories()
	{
		List<UserStory> compareList = new ArrayList<UserStory>();
		UserStoryList compareUserStoryList 	= new UserStoryList(compareList);
		userStoryList.removeAllUserStories(); 

		assertTrue(compareUserStoryList.equals(userStoryList));
	}
	
	@Test
	public void testGetNumberOfUserStories()
	{
		UserStoryList test = new UserStoryList(userStories);
		UserStory myUserStory1 = new UserStory("myUserStory1", Priority.MIDDLE);
		UserStory myUserStory2 = new UserStory("myUserStory2", Priority.LOW);
		test.addUserStory(myUserStory1);
		test.addUserStory(myUserStory2);
		
		assertEquals(test.getUserStories().size(), 2);

	}
	
	@Test
	public void testMoveUserStoryUp()
	{
		assertFalse(userStoryList.moveUserStoryUp("US1"));
		assertTrue(userStoryList.moveUserStoryUp("US3"));
		assertTrue(userStoryList.moveUserStoryUp("US4"));
		assertFalse(userStoryList.moveUserStoryUp(null));
	}
	
	@Test
	public void testMoveUserStoryDown()
	{
		assertFalse(userStoryList.moveUserStoryDown("US4"));
		assertTrue(userStoryList.moveUserStoryDown("US1"));
		assertTrue(userStoryList.moveUserStoryDown("US3"));
		assertFalse(userStoryList.moveUserStoryDown(null));
	}
	
// Successfully tested yet
	
	
// Now JSON tests
    @Test
    public void testFromJSON() throws IOException
    {
    	List<UserStory> jsonUserStories = new ArrayList<>();
    	jsonUserStories.add(new UserStory("TestUS1", Priority.MIDDLE));
    	UserStoryList jsonList = new UserStoryList(jsonUserStories);
    	
    	String jsonString = "{\"userStories\":[{\"title\":\"TestUS1\",\"priority\":\"MIDDLE\"}]}";
    	UserStoryList generated = UserStoryList.fromJson(jsonString);
    	assertTrue(jsonList.equals(generated));
    }
    
    @Test
    public void testToJSON() throws IOException
    {
    	List<UserStory> jsonUserStories = new ArrayList<>();
    	jsonUserStories.add(new UserStory("TestUS1", Priority.MIDDLE));
    	UserStoryList jsonList = new UserStoryList(jsonUserStories);
    	
    	String jsonString = "{\"userStories\":[{\"title\":\"TestUS1\",\"description\":\"\",\"tasks\":[],\"priority\":\"MIDDLE\"}]}";
    	String generated = new String(jsonList.toJson());
    	assertEquals(jsonString, generated);
    }
	
    @Test
    public void testHashCode()
    {
    	List<UserStory> list1 = new ArrayList<>();
    	List<UserStory> list2 = new ArrayList<>();
    	UserStory userStory = new UserStory("UserStoryTitle", Priority.MIDDLE);
    	list2.add(userStory);
    	UserStoryList userStoryList1 = new UserStoryList(list1);
    	UserStoryList userStoryList2 = new UserStoryList(list1);
    	UserStoryList userStoryList3 = new UserStoryList(list2);
    	
    	assertTrue(userStoryList1.hashCode() == userStoryList2.hashCode());
    	assertFalse(userStoryList1.hashCode() == userStoryList3.hashCode());
    }
	
}
