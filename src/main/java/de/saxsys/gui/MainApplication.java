package de.saxsys.gui;


import de.saxsys.gui.controller.GlobalController;
import de.saxsys.gui.view.RootPane;
import io.vertx.core.Vertx;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application{

	private GlobalController globalController;

	public MainApplication() {
		this.globalController = new GlobalController();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//GUI
	    RootPane root = new RootPane(globalController); //create the top pane of the application
	    root.setId("root");
	    
	    Scene primaryScene = new Scene(root);
	    primaryStage.setScene(primaryScene);
		primaryStage.setTitle("Task Management");
	    primaryStage.show();


	}

	@Override
	public void stop(){
		globalController.saveGlobalModel();
		Platform.exit();
	}
}
