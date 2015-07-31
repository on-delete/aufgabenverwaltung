package de.saxsys.server;

import de.saxsys.model.Priority;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.unit.TestContext;

public class SQLStatement {
	
	private Vertx vertx = null;
	private JDBCClient client = null;
	
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();

		client = JDBCClient.createShared(vertx, new JsonObject()
				.put("url", "jdbc:hsqldb:mem:test?shutdown=true")
				.put("driver_class", "org.hsqldb.jdbcDriver")
				.put("max_pool_size", 30));
	}
	
	
	
	public void InsertTask(String title, String description, Priority priority, String inCharge)
	{
		//Async async1 = context.async();
		
		
		
		client.getConnection(res -> {
			SQLConnection connection = res.result();
			initDatabase(connection);
			
			connection.execute("INSERT INTO task VALUES (NEXT VALUE FOR t_id_squence, '" +title +"', '"+description +"', '"+priority +"', '"+inCharge +"', )", res2 -> {
			});
			
		//	connection.query("SELECT * FROM task", res1 -> {
		//		context.assertEquals("[0,\"task 1\",\"A task\",\"HIGH\",\"\"]", res1.result().getResults().get(0).toString());
				
				//async1.complete();
			});
		//});
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