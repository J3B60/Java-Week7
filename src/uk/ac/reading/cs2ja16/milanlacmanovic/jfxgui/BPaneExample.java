/**
 * 
 */
package uk.ac.reading.cs2ja16.milanlacmanovic.jfxgui;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BPaneExample extends Application {
	private int canvasSize = 512;				// constants for relevant sizes
	private double earthOrbitSize = canvasSize / 3;
	private double sunSize = 80;
	private double earthSize = 30;
    private GraphicsContext gc; 
    private VBox rtPane;
    private Random rgen = new Random();
    private Image earth = new Image(getClass().getResourceAsStream("earth.png"));
    private Image sun = new Image(getClass().getResourceAsStream("sun.png"));

	 /**
	  * Function to show a message, 
	  * @param TStr		title of message block
	  * @param CStr		content of message
	  */
	private void showMessage(String TStr, String CStr) {
		    Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle(TStr);
		    alert.setHeaderText(null);
		    alert.setContentText(CStr);

		    alert.showAndWait();
	}
    /**
	 * function to show in a box ABout the programme
	 */
	 private void showAbout() {
		 showMessage("About", "RJM's BorderPane Demonstrator");
	 }
	    /**
		 * function to show in a box ABout the programme
		 */
		 private void showHelp() {
			 showMessage("Help", "Press Random Earth to draw an Earth at a random angle, or click on canvas to draw Earth there");
		 }
	 
	/**
	 * Function to set up the menu
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();		// create menu

		Menu mHelp = new Menu("Help");			// have entry for help
				// then add sub menus for About and Help
				// add the item and then the action to perform
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();				// show the about message
            }	
		});
		MenuItem miHelp = new MenuItem("Help");
		miHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showHelp();
            }	
		});
		mHelp.getItems().addAll(mAbout, miHelp); 	// add submenus to Help
		
				// now add File menu, which here only has Exit
		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);						// quit program
		    }
		});
		mFile.getItems().addAll(mExit);
		
		menuBar.getMenus().addAll(mFile, mHelp);	// menu has File and Help
		
		return menuBar;					// return the menu, so can be added
	}

	/**
	 * show where Earth is, in pane on right
	 * @param x		x position 
	 * @param y
	 */
	public void drawStatus(double x, double y) {
		rtPane.getChildren().clear();					// clear rtpane
				// now create label
		Label l = new Label("Earth at " + String.format("%.1f", x) + ", " + String.format("%.1f", y));
		rtPane.getChildren().add(l);				// add label to pane	
	}

	/**
     * drawIt ... draws object defined by given image at position and size
     * @param i		image
     * @param x		xposition
     * @param y
     * @param sz	size
     */
	public void drawIt (Image i, double x, double y, double sz) {
			// to draw centred at x,y, give top left position and x,y size
		gc.drawImage(i, x - sz/2, y - sz/2, sz, sz);
	}

	/**
	 *  draw system, with Earth at x,y
	 * @param x
	 * @param y
	 */
	private void drawSystem(double x, double y) {
		// now clear canvas and draw sun and moon
		gc.clearRect(0,  0,  canvasSize,  canvasSize);		// clear canvas
		drawIt( sun, canvasSize/2, canvasSize/2, sunSize );	// draw Sun
		drawIt( earth, x, y, earthSize );					// draw Earth
		drawStatus(x,y);									// give its position 
	}

	/**
	 * calculate position of Earth at specified angle and then draw system
	 * @param t		angle (time dependent) of Earth
	 */
	private void drawSystem(double t) {
		double x = canvasSize/2 + earthOrbitSize * Math.cos(t);	// calc x coord
		double y = canvasSize/2 + earthOrbitSize * Math.sin(t);	// and y
		drawSystem(x,y);									// and draw system
	}

	/**
	 * set up the mouse event handler, so when click on canvas, draw Earth there
	 * @param canvas
	 */
	private void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   drawSystem(e.getX(), e.getY());	
	    	        	   		// draw system where mouse clicked
	    	           }
	    	       });
	}

	/**
	 * set up the button and return so can add to borderpane
	 * @return
	 */
    private Button setButtons() {
    			// create button
    	Button btnBottom = new Button("Random Earth");
    			// now add handler
    	btnBottom.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			drawSystem(360.0*rgen.nextDouble());
    				// and its action to draw earth at random angle
    		}
    	});
    	return btnBottom;
    }
    	// if wanted multiple buttons, create them and add to a horizontal box
    	// and return that box
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    primaryStage.setTitle("BorderPane Demonstrator");

	    BorderPane bp = new BorderPane();			// create border pane

	    bp.setTop(setMenu());						// create menu, add to top

	    Group root = new Group();					// create group
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    											// and canvas to draw in
	    root.getChildren().add( canvas );			// and add canvas to group
	    gc = canvas.getGraphicsContext2D();			// context for drawing
	    setMouseEvents(canvas);						// set mouse handler
	    bp.setCenter(root);							// put group in centre pane

	    rtPane = new VBox();						// set vBox for listing data
	    bp.setRight(rtPane);						// put in right pane

	    bp.setBottom(setButtons());					/// add button to bottom

	    Scene scene = new Scene(bp, canvasSize*1.4, canvasSize*1.2);
	    								// create scene so bigger than canvas, 

	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	public static void main(String[] args) {
	    Application.launch(args);
	}

}
