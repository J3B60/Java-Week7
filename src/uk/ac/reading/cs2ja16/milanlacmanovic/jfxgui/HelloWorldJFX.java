/**
 * 
 */
package uk.ac.reading.cs2ja16.milanlacmanovic.jfxgui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author milan
 *
 */
public class HelloWorldJFX extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Create a scene for the window with the text inside it
		Scene scene = new Scene(new Group(new Text(25, 25, "Hello World! This is the Stage")));
		
		//Set title of the window
		primaryStage.setTitle("Hello World! This is the Title");
		
		//Add scene to the window (underneath the window bar)
		primaryStage.setScene(scene);
		
		//Change size of window to fit text
//		primaryStage.sizeToScene();
		//Change size of window to custom set
		primaryStage.setWidth(400);
		primaryStage.setHeight(250);
		
		//Show the window
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
