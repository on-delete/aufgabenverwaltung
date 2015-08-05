package de.saxsys.gui;

//import de.saxsys.server.AddTaskVerticle;

import de.saxsys.gui.view.RootPane;
import io.vertx.core.Vertx;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
/*
        vertx = Vertx.vertx();
        
        vertx.deployVerticle(new Server());
        vertx.deployVerticle(new AddTaskVerticle());
        vertx.deployVerticle(new InitDatabaseVerticle());*/
        
		//GUI
	    RootPane root = new RootPane(); //create the top pane of the application
	    root.setId("root");
	    
	    Scene primaryScene = new Scene(root);
	    primaryStage.setScene(primaryScene);
		primaryStage.setTitle("Task Management");
	    primaryStage.show();


	}

	@Override
	public void stop(){
		vertx.undeploy(deploymentId);
		Platform.exit();
	}
}
