package de.saxsys.service;

import de.saxsys.service.Server;
import io.vertx.core.Vertx;
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
public class ServerTest {

	private Vertx vertx = null;
	private final String testString = "{\"teststring\":\"test\"}";

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();
		vertx.deployVerticle(new Server(), context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void testRoutingAddTask(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("addTask", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.post(8080, "localhost", "/epicservice/addTask");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Success!", response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingAddTaskFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("addTask", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.post(8080, "localhost", "/epicservice/addTask");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingUpdateTask(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("updateTask", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.put(8080, "localhost", "/epicservice/updateTask");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Success!", response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingUpdateTaskFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("updateTask", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.put(8080, "localhost", "/epicservice/updateTask");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingDeleteTask(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("deleteTask", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.delete(8080, "localhost", "/epicservice/deleteTask/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Success!", response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingDeleteTaskFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("deleteTask", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.delete(8080, "localhost", "/epicservice/deleteTask/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingGetTask(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getTask", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.reply(testString);
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getTask/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals(testString, response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingGetTaskFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getTask", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getTask/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingAddUserstory(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("addUserstory", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.post(8080, "localhost", "/epicservice/addUserstory");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Success!", response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingAddUserstoryFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("addUserstory", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.post(8080, "localhost", "/epicservice/addUserstory");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingUpdateUserstory(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("updateUserstory", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.put(8080, "localhost", "/epicservice/updateUserstory");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Success!", response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingUpdateUserstoryFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("updateUserstory", msg -> {
			context.assertEquals(msg.body().toString(), testString);
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.put(8080, "localhost", "/epicservice/updateUserstory");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingDeleteUserstory(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("deleteUserstory", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.reply("Success!");
		});
		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.delete(8080, "localhost", "/epicservice/deleteUserstory/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Success!", response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingDeleteUserstoryFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("deleteUserstory", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.delete(8080, "localhost", "/epicservice/deleteUserstory/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.setChunked(true);
		req.write(testString);
		req.end();
	}

	@Test
	public void testRoutingGetUserstory(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getUserstory", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.reply(testString);
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getUserstory/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals(testString, response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingGetUserstoryFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getUserstory", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getUserstory/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingGetUserstoryWithTasks(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getUserstoryWithTasks", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.reply(testString);
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getUserstoryWithTasks/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals(testString, response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingGetUserstoryWithTasksFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getUserstoryWithTasks", msg -> {
			context.assertEquals(msg.body().toString(), "1");
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getUserstoryWithTasks/1");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingGetAllUserstoriesWithTasks(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getAllUserstoriesWithTasks", msg -> {
			msg.reply(testString);
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getAllUserstoriesWithTasks");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals(testString, response);
				asyncInner.complete();
			});

			context.assertEquals(200, resp.statusCode());

			async.complete();
		});
		req.end();
	}

	@Test
	public void testRoutingGetAllUserstoriesWithTasksFailure(TestContext context) {
		Async async = context.async();

		vertx.eventBus().consumer("getAllUserstoriesWithTasks", msg -> {
			msg.fail(500, "Fail");
		});

		HttpClient client = vertx.createHttpClient();

		HttpClientRequest req = client.get(8080, "localhost", "/epicservice/getAllUserstoriesWithTasks");
		req.handler(resp -> {
			Async asyncInner = context.async();
			resp.bodyHandler(buffer -> {
				String response = buffer.getString(0, buffer.length());
				context.assertEquals("Internal Error!", response);
				asyncInner.complete();
			});

			context.assertEquals(500, resp.statusCode());

			async.complete();
		});
		req.end();
	}
}
