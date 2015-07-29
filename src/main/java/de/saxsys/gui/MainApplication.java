package de.saxsys.gui;

import de.saxsys.server.AddTaskVerticle;
import de.saxsys.server.InitDatabaseVerticle;
import de.saxsys.server.Server;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.beans.VetoableChangeListener;

/**
 * Created by andre.krause on 23.07.2015.
 */
public class MainApplication extends Application{

	private String deploymentId = null;
	private Vertx vertx = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*Erzeugen der Vertx-Umgebung und deployen der einzelnen Verticle*/
		vertx = Vertx.vertx();

		vertx.deployVerticle(new Server());
		vertx.deployVerticle(new AddTaskVerticle());
		vertx.deployVerticle(new InitDatabaseVerticle());

		//TODO: Erstellen der GUI
	}

	@Override
	public void stop(){
		vertx.undeploy(deploymentId);
		Platform.exit();
	}
}
