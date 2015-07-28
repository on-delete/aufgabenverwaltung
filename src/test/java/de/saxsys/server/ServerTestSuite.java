package de.saxsys.server;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by andre.krause on 27.07.2015.
 */
@RunWith(VertxUnitRunner.class)
public class ServerTestSuite {

	private static Vertx vertx = null;
	private String testString = "{\"teststring\":\"test\"}";

	@Before
	public void beforeEach() {
		vertx = Vertx.vertx();
		vertx.deployVerticle(new Server());
	}

	@After
	public void afterEach() {
		vertx.close();
	}

	@Test
	public void testRoutingAddTask(TestContext context) {
		Async async1 = context.async();

		vertx.eventBus().consumer("addTask", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.post(8080, "localhost", "/addTask");
		req.handler(resp -> {
			Async async2 = context.async();
			resp.bodyHandler(new Handler<Buffer>() {
				@Override
				public void handle(Buffer buffer) {
					String response = buffer.getString(0, buffer.length()).toString();
					context.assertEquals("Task hinzugefügt!", response);
					async2.complete();
				}
			});

			context.assertEquals(200, resp.statusCode());

			async1.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingAddTaskFailure(TestContext context) {
		Async async1 = context.async();

		vertx.eventBus().consumer("addTask", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.post(8080, "localhost", "/addTask");
		req.handler(resp -> {
			Async async2 = context.async();
			resp.bodyHandler(new Handler<Buffer>() {
				@Override
				public void handle(Buffer buffer) {
					String response = buffer.getString(0, buffer.length()).toString();
					context.assertEquals("Interner Fehler!", response);
					async2.complete();
				}
			});

			context.assertEquals(500, resp.statusCode());

			async1.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}
}
