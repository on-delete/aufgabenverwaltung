package de.saxsys.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by andre.krause on 23.07.2015.
 */
public class Server extends AbstractVerticle{

	private Router router;
	private EventBus eventBus;

	public void start(){
		eventBus = vertx.eventBus();
		HttpServer server = vertx.createHttpServer();

		router = Router.router(vertx);
		router.route().method(HttpMethod.POST).method(HttpMethod.PUT).handler(BodyHandler.create());

		epicserviceInit();

		server.requestHandler(router::accept).listen(8080);
	}

	private void epicserviceInit(){
		router.route(HttpMethod.POST, "/epicservice/addTask/:userstoryid/:order").handler(routingContext -> {
			String userstoryId = routingContext.request().getParam("userstoryid");
			String order = routingContext.request().getParam("order");
			String request = routingContext.getBodyAsString();
			String requestString = "{\"request\":{"+request+"}, \"userstoryid\":{"+userstoryId+", \"order\":{"+order+"}}";
			eventBus.send("addTask", requestString, result -> sendResponseWithBody(result, routingContext));
		});
		router.route(HttpMethod.PUT, "/epicservice/updateTask").handler(routingContext -> {
			JsonObject request = routingContext.getBodyAsJson();
			eventBus.send("updateTask", request, result -> sendResponseWithoutBody(result, routingContext));
		});
		router.route(HttpMethod.DELETE, "/epicservice/deleteTask/:taskid").handler(routingContext -> {
			String taskid = routingContext.request().getParam("taskid");
			eventBus.send("deleteTask", taskid, result -> sendResponseWithoutBody(result, routingContext));
		});
		router.route(HttpMethod.GET, "/epicservice/getTask/:taskid").handler(routingContext -> {
			String taskid = routingContext.request().getParam("taskid");
			eventBus.send("getTask", taskid, result -> sendResponseWithBody(result, routingContext));
		});

		router.route(HttpMethod.POST, "/epicservice/addUserstory/:order").handler(routingContext -> {
			String order = routingContext.request().getParam("order");
			String request = routingContext.getBodyAsString();
			String requestString = "{\"request\":{"+request+"}, \"order\":{"+order+"}}";
			eventBus.send("addUserstory", requestString, result -> sendResponseWithBody(result, routingContext));
		});
		router.route(HttpMethod.PUT, "/epicservice/updateUserstory").handler(routingContext -> {
			JsonObject request = routingContext.getBodyAsJson();
			eventBus.send("updateUserstory", request, result -> sendResponseWithoutBody(result, routingContext));
		});
		router.route(HttpMethod.DELETE, "/epicservice/deleteUserstory/:userstoryid").handler(routingContext -> {
			String userstoryid = routingContext.request().getParam("userstoryid");
			eventBus.send("deleteUserstory", userstoryid, result -> sendResponseWithoutBody(result, routingContext));
		});
		router.route(HttpMethod.GET, "/epicservice/getUserstory/:userstoryid").handler(routingContext -> {
			String userstoryid = routingContext.request().getParam("userstoryid");
			eventBus.send("getUserstory", userstoryid, result -> sendResponseWithBody(result, routingContext));
		});
		router.route(HttpMethod.GET, "/epicservice/getUserstoryWithTasks/:userstoryid").handler(routingContext -> {
			String userstoryid = routingContext.request().getParam("userstoryid");
			eventBus.send("getUserstoryWithTasks", userstoryid, result -> sendResponseWithBody(result, routingContext));
		});
		router.route(HttpMethod.GET, "/epicservice/getAllUserstoriesWithTasks").handler(routingContext -> {
			eventBus.send("getAllUserstoriesWithTasks", "", result -> sendResponseWithBody(result, routingContext));
		});
	}

	private void sendResponseWithoutBody(AsyncResult<Message<Object>> result, RoutingContext routingContext){
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "text/plain");
		if (result.failed()) {
			response.setStatusCode(500);
			response.end("Internal Error!");
		} else {
			response.setStatusCode(200);
			response.end("Success!");
		}
	}

	private void sendResponseWithBody(AsyncResult<Message<Object>> result, RoutingContext routingContext){
		HttpServerResponse response = routingContext.response();
		if (result.failed()) {
			response.putHeader("content-type", "text/plain");
			response.setStatusCode(500);
			response.end("Internal Error!");
		} else {
			String resultBody = result.result().body().toString();
			response.putHeader("content-type", "application/json");
			response.setStatusCode(200);
			response.end(resultBody);
		}
	}
}
