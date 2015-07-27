package de.saxsys.gui;

import de.saxsys.server.Server;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import javafx.application.Application;
import javafx.application.Platform;
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
		vertx = Vertx.vertx();

		vertx.deployVerticle(new Server(), res -> {
			if (res.succeeded()) {
				System.out.println("Deployment id is: " + res.result());
				deploymentId = res.result();
			} else {
				System.out.println("Deployment failed!");
			}
		});

		//TODO: Erstellen der GUI
	}

	@Override
	public void stop(){
		vertx.undeploy(deploymentId);
		Platform.exit();
	}
}
