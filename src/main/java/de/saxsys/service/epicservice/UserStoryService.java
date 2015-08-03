package de.saxsys.service.epicservice;

import de.saxsys.model.UserStory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andre.krause on 31.07.2015.
 */
public class UserStoryService extends AbstractVerticle{
	private final static Logger log = Logger.getLogger(UserStoryService.class.getName());

	public void start(){
		final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
				.put("url", "jdbc:hsqldb:file:db/aufgabenDB")
				.put("driver_class", "org.hsqldb.jdbcDriver")
				.put("max_pool_size", 30), "aufgabenDB");

		EventBus eventBus = vertx.eventBus();

		eventBus.consumer("addUserstory", new Handler<Message<JsonObject>>() {
			@Override
			public void handle(Message<JsonObject> msg) {
				try {
					JsonObject transferObject = msg.body();
					UserStory userStory = UserStory.fromJson(transferObject.getValue("request").toString());

					client.getConnection(conn -> {
						if (conn.failed()) {
							log.log(Level.WARNING, "Connection to database failed + " + conn.cause().getMessage());
							msg.fail(500, "");
						}
						else{
							execute(conn.result(), "INSERT INTO userstory VALUES (NEXT VALUE FOR u_id_squence, '" + userStory.getTitle() + "', '" + userStory.getDescription() + "', '" + userStory.getPriority().name() + "', " + Integer.getInteger(transferObject.getValue("order").toString()) + ");", create -> {
								query(conn.result(), "SELECT TOP 1 u_id FROM userstory ORDER BY u_id DESC", rs -> {
									log.info(rs.getResults().get(0).getList().get(0).toString());
									msg.reply(rs.getResults().get(0).getList().get(0).toString());
									client.close();
								});
							});
						}
					});
				} catch (IOException | RuntimeException e) {
					e.printStackTrace();
					msg.fail(500, "");
				}
			}
		});
		eventBus.consumer("updateUserstory", msg -> {

		});
		eventBus.consumer("deleteUserstory", msg -> {

		});
		eventBus.consumer("getUserstory", msg -> {

		});
		eventBus.consumer("getUserstoryWithTasks", msg -> {

		});
		eventBus.consumer("getAllUserstoriesWithTasks", msg -> {

		});
	}

	private void execute(SQLConnection conn, String sql, Handler<Void> done) {
		conn.execute(sql, res -> {
			if (res.failed()) {
				throw new RuntimeException(res.cause());
			}

			done.handle(null);
		});
	}

	private void query(SQLConnection conn, String sql, Handler<ResultSet> done) {
		conn.query(sql, res -> {
			if (res.failed()) {
				throw new RuntimeException(res.cause());
			}

			done.handle(res.result());
		});
	}
}
