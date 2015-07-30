package de.saxsys.server;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.model.Priority;

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

	@Test
	public void testInsertUserstory(TestContext context) {
		Async async1 = context.async();

		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);

			connection.execute("insert into userstory values (NEXT VALUE FOR u_id_squence, 'userstory 1', '', 'HIGH');", res2 -> {
			});

			connection.query("SELECT * FROM userstory", res1 -> {
				context.assertEquals("[0,\"userstory 1\",\"\",\"HIGH\"]", res1.result().getResults().get(0).toString());

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
			
			connection.execute("UPDATE userstory SET u_title='manipulated userstory', u_description='manipulated description', t_priority='MIDDLE',);", res2 -> {
			});
			
			connection.query("SELECT * FROM userstory", res1 -> {
				context.assertEquals("[0,\"manipulated userstory\",\"manipulated description\",\"MIDDLE\"]", res1.result().getResults().get(0).toString());
				
				async1.complete();
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
			
			connection.execute("insert into userstory values (NEXT VALUE FOR u_id_squence, 'userstory 1', 'An Userstory', 'HIGH');", res21 -> {
			});
			connection.execute("insert into userstory values (NEXT VALUE FOR u_id_squence, 'userstory 2', 'An Userstory', 'MIDDLE');", res22 -> {
			});
			connection.execute("insert into userstory values (NEXT VALUE FOR u_id_squence, 'userstory 3', 'An Userstory', 'LOW');", res23 -> {
			});
			
			connection.query("SELECT userstory.title FROM userstory WHERE title='userstory 1'", res11 -> {
				context.assertEquals("[0,\"userstory 1\",\"An Uuserstory\",\"HIGH\",\"\"]", res11.result().getResults().get(0).toString());
				
			connection.query("SELECT userstory.prority FROM userstory WHERE priority='LOW'", res12 -> {
				context.assertEquals("[0,\"userstory 3\",\"An Userstory\",\"LOW\",\"\"]", res12.result().getResults().get(0).toString());
					
				async1.complete();
			});
		});});
	}
	
	@Test
	public void testInsertTask(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task', 'HIGH', '', );", res2 -> {
			});
			
			connection.query("SELECT * FROM task", res1 -> {
				context.assertEquals("[0,\"task 1\",\"A task\",\"HIGH\",\"\"]", res1.result().getResults().get(0).toString());
				
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
			
			connection.execute("UPDATE task SET t_title='manipulated task', t_description='manipulated description', t_priority='MIDDLE', t_inCharge='', );", res2 -> {
			});
		
			connection.query("SELECT * FROM task", res1 -> {
				context.assertEquals("[0,\"manipulated task\",\"manipulated description\",\"MIDDLE	\",\"\"]", res1.result().getResults().get(0).toString());
				
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
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 1', 'A task', 'HIGH', '', );", res21 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 2', 'An other task', 'MIDDLE', '', );", res22 -> {
			});
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, 'task 3', 'An other task, too', 'LOW', '', );", res23 -> {
			});
			
			connection.query("SELECT task.title FROM task WHERE title='task 1'", res11 -> {
				context.assertEquals("[0,\"task 1\",\"A task\",\"HIGH\",\"\"]", res11.result().getResults().get(0).toString());
				
			connection.query("SELECT task.prority FROM task WHERE priority='LOW'", res12 -> {
				context.assertEquals("[0,\"task 3\",\"An other task, too\",\"LOW\",\"\"]", res12.result().getResults().get(0).toString());
					
				async1.complete();
			});
		});});
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
	
	
	/*
	@Test
	public void testInsertTask2(TestContext context)
	{
		Async async1 = context.async();
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
		
		SQLStatement test = new SQLStatement();
		test.InsertTask("task1", "A task", Priority.HIGH, "");
		
		connection.query("SELECT * FROM task", res1 -> {
			context.assertEquals("[0,\"task 1\",\"A task\",\"HIGH\",\"\"]", res1.result().getResults().get(0).toString());
			
			async1.complete();
		});
		});
	}
	*/
}
