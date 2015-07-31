package de.saxsys.server;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;


import java.util.concurrent.TimeoutException;

import org.apache.derby.tools.sysinfo;
import org.apache.derby.vti.AwareVTI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by andre.krause on 30.07.2015.
 */
@RunWith(VertxUnitRunner.class)
public class SQLStatementTest {

	private Vertx vertx = null;
	private JDBCClient client = null;

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();

		client = JDBCClient.createShared(vertx, new JsonObject()
				.put("url", "jdbc:hsqldb:mem:test?shutdown=true")
				.put("driver_class", "org.hsqldb.jdbcDriver")
				.put("max_pool_size", 30));
	}

	@After
	public void tearDown(TestContext context) {
		client.close();
	}
	
	@After
	public void tearDown2(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}
	
	
/*
 * Alle Tests funktionieren EINZELN, aber nicht, wenn alle Tests auf einmal ausgeführt werden.
 */
	
	//u_id; u_title; u_description; u_prority; u_order
	@Test
	public void testInsertUserstory(TestContext context) {
		Async async1 = context.async();

		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);

			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', '', 'HIGH', 0);", res2 -> {
			});

			connection.query("SELECT * FROM userstory", res1 -> {
				context.assertEquals("[0,\"userstory 1\",\"\",\"HIGH\",0]", res1.result().getResults().get(0).toString());

				async1.complete();
			});
		});
	}
	
	@Test
	public void testUpdateUserstory(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1 FG', '', 'HIGH', 0);", res2 -> {
			});
			connection.execute("UPDATE userstory SET u_title='manipulated userstory', u_description='manipulated description', u_priority='MIDDLE';", res3 -> {			
			});
			connection.query("SELECT * FROM userstory", res1 -> {
				context.assertEquals("[0,\"manipulated userstory\",\"manipulated description\",\"MIDDLE\",0]", res1.result().getResults().get(0).toString());
				
				async1.complete();
			});
			
			connection.execute("DELETE * FROM userstory", res6 -> {
			});
		});
	}
	
	@Test
	public void testSelectUserstory(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory0', 'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory1', 'MIDDLE', 1);", res22 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 3', 'An Userstory2', 'LOW', 	2);", res23 -> {
			});
			
			connection.query("SELECT userstory.u_title FROM userstory WHERE u_title='userstory 1'", res11 -> {
				context.assertEquals("[\"userstory 1\"]", res11.result().getResults().get(0).toString());
			});
			connection.query("SELECT userstory.u_title FROM userstory WHERE u_priority='LOW'", res12 -> {
				System.out.println(res12.result().getResults().get(2).toString());
				context.assertEquals("[\"userstory 3\"]", res12.result().getResults().get(0).toString());
			});
			connection.query("SELECT userstory.u_description FROM userstory WHERE u_order=1", res13 -> {
				context.assertEquals("[\"An Userstory1\"]", res13.result().getResults().get(0).toString());
			
				async1.complete();
			});
		});
	}
	
	@Test
	public void testSelectAllUserstories(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'First', 	'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'Second',	'MIDDLE', 	1);", res22 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 3', 'Third', 	'LOW', 		2);", res23 -> {
			});
			
			connection.query("SELECT * FROM userstory", res1 -> {
				context.assertEquals("[[0,\"userstory 1\",\"First\",\"HIGH\",0], [1,\"userstory 2\",\"Second\",\"MIDDLE\",1], [2,\"userstory 3\",\"Third\",\"LOW\",2]]", res1.result().getResults().toString());
				
				async1.complete();
			});
		});	
	}
	
	@Test
	
	public void testDeleteUserstory(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'First', 	'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'Second',	'MIDDLE', 	1);", res22 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 3', 'Third', 	'LOW', 		2);", res23 -> {
			});
			
			connection.execute("DELETE FROM userstory WHERE u_title='userstory 1';", res24 -> {
			});
			
			connection.query("SELECT * FROM userstory", res1 -> {
				context.assertEquals("[[1,\"userstory 2\",\"Second\",\"MIDDLE\",1], [2,\"userstory 3\",\"Third\",\"LOW\",2]]", res1.result().getResults().toString());
				
				async1.complete();
			});
		});
	}
	
	//t_id; t_title; t_description; t_priority; t_inCharge; t_order; u_id
	@Test
	public void testInsertTask(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("DELETE * FROM task", res3 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory0', 'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory1', 'MIDDLE', 1);", res22 -> {
			});
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task', 'HIGH', '', 0, 1);", res2 -> {
			});
			
			connection.query("SELECT * FROM task", res1 -> {
				context.assertEquals("[0,\"task 1\",\"A task\",\"HIGH\",\"\",0,1]", res1.result().getResults().get(0).toString());
				
				async1.complete();
			});
		});
	}
	
	@Test
	public void testUpdateTask(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory0', 'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory1', 'MIDDLE', 1);", res22 -> {
			});
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task', 'HIGH', '', 0, 1);", res2 -> {
			});
			connection.execute("UPDATE task SET t_title='manipulated task', t_description='manipulated description', t_priority='MIDDLE', t_inCharge='';", res2 -> {
			});
		
			connection.query("SELECT * FROM task", res1 -> {
				context.assertEquals("[0,\"manipulated task\",\"manipulated description\",\"MIDDLE\",\"\",0,1]", res1.result().getResults().get(0).toString());
				
				async1.complete();
			});
		});
	}
	
	@Test
	public void testSelectTask(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory0', 'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory1', 'MIDDLE', 1);", res22 -> {
			});
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task', 'HIGH', '', 0, 1);", res21 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 2', 'An other task', 'MIDDLE', '', 1, 1);", res22 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 3', 'An other task, too', 'LOW', '', 2, 1);", res23 -> {
			});
			
			connection.query("SELECT task.t_title FROM task WHERE t_title='task 1'", res11 -> {
				context.assertEquals("[\"task 1\"]", res11.result().getResults().get(0).toString());
			});	
			connection.query("SELECT task.t_priority FROM task WHERE t_priority='LOW'", res12 -> {
				context.assertEquals("[\"LOW\"]", res12.result().getResults().get(0).toString());
					
				async1.complete();
			});
		});
	}
	
	@Test
	public void testDeleteTask(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory0', 'HIGH', 	0);", res21 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory1', 'MIDDLE', 1);", res22 -> {
			});
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task1', 'HIGH', '', 0, 1);", res2 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 2', 'A task2', 'MIDDLE', '', 0, 1);", res2 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 3', 'A task3', 'LOW', '', 0, 1);", res2 -> {
			});
			
			connection.execute("DELETE FROM task WHERE t_title='task 1';", res24 -> {
			});
			
			connection.query("SELECT * FROM task", res1 -> {
				System.out.println(res1.result().getResults().toString());
				context.assertEquals("[[1,\"task 2\",\"A task2\",\"MIDDLE\",\"\",0,1], [2,\"task 3\",\"A task3\",\"LOW\",\"\",0,1]]", res1.result().getResults().toString());
				
				async1.complete();
			});
		});
	}
	
	@Test
	public void testSelectAllTasksOfOneUserstory(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory0', 'HIGH', 	0);", res211 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory1', 'MIDDLE', 1);", res212 -> {
			});
			connection.execute("INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, 'userstory 3', 'An Userstory2', 'MIDDLE', 2);", res212 -> {
			});
		
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task1', 'HIGH', 		'', 0, 1);", res221 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 2', 'A task2', 'MIDDLE', 	'', 0, 1);", res222 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 3', 'A task3', 'LOW', 		'', 0, 2);", res223 -> {
			});
			
			connection.query("SELECT * FROM task WHERE u_id=1", res11 -> {
				context.assertEquals("[[0,\"task 1\",\"A task1\",\"HIGH\",\"\",0,1], [1,\"task 2\",\"A task2\",\"MIDDLE\",\"\",0,1]]", res11.result().getResults().toString());
			});	
			connection.query("SELECT * FROM task WHERE u_id=2", res12 -> {
				context.assertEquals("[[2,\"task 3\",\"A task3\",\"LOW\",\"\",0,2]]", res12.result().getResults().toString());
			
				async1.complete();
			});
			
			connection.execute("DELETE * FROM userstory", res6 -> {
			});
		});
	}
	

	private void initDatabase(SQLConnection conn){
		conn.execute("create sequence u_id_squence", voidAsyncResult -> {
		});
		conn.execute("create table userstory(u_id bigint GENERATED BY DEFAULT AS SEQUENCE u_id_squence PRIMARY KEY, u_title varchar(255) NOT NULL, u_description varchar(255), u_priority varchar(255) NOT NULL, u_order int);", res -> {
			if (!res.succeeded())
				System.out.println(res.cause().getMessage());
		});
		conn.execute("create sequence t_id_squence", voidAsyncResult -> {
		});
		conn.execute("create table task(t_id bigint GENERATED BY DEFAULT AS SEQUENCE t_id_squence PRIMARY KEY, t_title varchar(255) NOT NULL, t_description varchar(255), t_priority varchar(255) NOT NULL, t_inCharge varchar(255), t_order int, u_id bigint NOT NULL, FOREIGN KEY(u_id) REFERENCES userstory(u_id) ON DELETE CASCADE);", res -> {
			if (!res.succeeded()) {
				System.out.println(res.cause().getMessage());
			}
		});
	}
}
