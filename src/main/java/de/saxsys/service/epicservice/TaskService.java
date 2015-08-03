package de.saxsys.service.epicservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

/**
 * Created by andre.krause on 31.07.2015.
 */
public class TaskService extends AbstractVerticle{

	public void start(){
		final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
				.put("url", "jdbc:hsqldb:file:db/aufgabenDB")
				.put("driver_class", "org.hsqldb.jdbcDriver")
				.put("max_pool_size", 30), "aufgabenDB");

		EventBus eventBus = vertx.eventBus();

		eventBus.consumer("addTask", msg -> {

		});
		eventBus.consumer("updateTask", msg -> {

		});
		eventBus.consumer("deleteTask", msg -> {

		});
		eventBus.consumer("getTask", msg -> {

		});
	}
}
