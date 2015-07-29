package de.saxsys.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by andre.krause on 23.07.2015.
 */
public class Server extends AbstractVerticle{

	public void start(){
		EventBus eventBus = vertx.eventBus();

		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);
		router.route(HttpMethod.POST, "/addTask").handler(BodyHandler.create());

		router.route(HttpMethod.POST, "/addTask").handler(routingContext -> {
			JsonObject request = routingContext.getBodyAsJson();
			eventBus.send("addTask", request, result -> {
				HttpServerResponse response = routingContext.response();
				response.putHeader("content-type", "text/plain");
				if(result.failed()){
					response.setStatusCode(500);
					response.end("Interner Fehler!");
				}
				else{
					response.setStatusCode(200);
					response.end("Task hinzugefügt!");
				}
			});
		});

		server.requestHandler(router::accept).listen(8080);

		//vertx.deployVerticle(new AddTaskVerticle());
	}
}
