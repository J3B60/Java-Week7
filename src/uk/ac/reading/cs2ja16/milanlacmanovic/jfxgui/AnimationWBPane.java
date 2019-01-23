/**
 * 
 */
package uk.ac.reading.cs2ja16.milanlacmanovic.jfxgui;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationWBPane extends Application {
	int canvasSize = 512;				// constants for relevant sizes
	double earthOrbitSize = canvasSize /6;
	double marsOrbitSize = canvasSize/3;//ADDED
	double sunSize = 30;
	double earthSize = 20;
	double marsSize = 10;//ADDED
	double earthx = 0;
	double earthy = 0;
	double marsx = 0;
	double marsy = 0;
	double t;
    GraphicsContext gc;
    private VBox rtPane;
    private HBox btPane;
    private boolean SetAnimationRun = true;
    private Random rgen = new Random();
    Image earth = new Image(getClass().getResourceAsStream("earth.png"));
    Image sun = new Image(getClass().getResourceAsStream("sun.png"));
    Image mars = new Image(getClass().getResourceAsStream("mars.png"));
    double sunPosx = canvasSize/2;
    double sunPosy = canvasSize/2;
    long startNanoTime = System.nanoTime();
    /**
     * drawIt ... draws object defined by given image at position and size
     * @param i
     * @param x
     * @param y
     * @param sz
     */
	public void drawIt (Image i, double x, double y, double sz) {
		gc.drawImage(i, x - sz/2, y - sz/2, sz, sz );
	}
	
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
	 showMessage("About", "Solar System with Mars and Earth");
 }
    /**
	 * function to show in a box ABout the programme
	 */
	 private void showHelp() {
		 showMessage("Help", "Start to start animation, Pause to pause animation");
	 }
 
	
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
	
	public void drawStatus(double sx, double sy, double ex, double ey, double mx, double my) {
		rtPane.getChildren().clear();					// clear rtpane
				// now create label
		Label l = new Label("Sun at " + String.format("%.1f", sx) + ", " + String.format("%.1f", sy) + "\n" + "Earth at " + String.format("%.1f", ex) + ", " + String.format("%.1f", ey) + "\n" + "Mars at " + String.format("%.1f", mx) + ", " + String.format("%.1f", my));
		rtPane.getChildren().add(l);				// add label to pane	
	}
	
	private void SystemPosSet(double x, double y) {
		// now clear canvas and draw sun and moon
		gc.clearRect(0,  0,  canvasSize,  canvasSize);		// clear canvas
		sunPosx = x;
		sunPosy = y;	// draw Sun									// give its position 
	}
	
	/**
	 * calculate position of Earth at specified angle and then draw system
	 * @param t		angle (time dependent) of Earth
	 */
	private void drawSystem (double t) {
		earthx = sunPosx + earthOrbitSize * Math.cos(t);	// calculate coordinates of earth
		earthy = sunPosy + earthOrbitSize * Math.sin(t);
		marsx= sunPosx + marsOrbitSize * Math.cos(t*0.5);
		marsy= sunPosy + marsOrbitSize * Math.sin(t*0.5);
			
			// now clear canvas and draw earth and sun
		gc.clearRect(0,  0,  canvasSize,  canvasSize);
		drawIt( earth, earthx, earthy, earthSize );
		drawIt( mars, marsx, marsy, marsSize );//Test
		drawIt( sun, sunPosx, sunPosy, sunSize );
		drawStatus(sunPosx, sunPosy, earthx, earthy, marsx, marsy);

	}
	
	private void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   SystemPosSet(e.getX(), e.getY());	
	    	        	   		// draw system where mouse clicked
	    	           }
	    	       });
	}
	
	private Button setStartButton() {
			// create button
		Button btnBottom = new Button("Start");
				// now add handler
		btnBottom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(SetAnimationRun == true) {
					startNanoTime = System.nanoTime();
					SetAnimationRun = true;
				}
				else {
					SetAnimationRun = true;
				}
				setBottomButtons();
					// and its action to draw earth at random angle
			}
		});
		return btnBottom;
	}
	
	private Button setPauseButton() {
		// create button
		Button btnBottom = new Button("ERROR");
		if(SetAnimationRun == true) {
			btnBottom = new Button("Pause");
		}
		else {
			btnBottom = new Button("Stop");
		}
			// now add handler
		btnBottom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (SetAnimationRun == false) {
					startNanoTime = System.nanoTime();
					SetAnimationRun = false;
					drawSystem(t);
				}
				else {
					SetAnimationRun = false;
				}
				setBottomButtons();
					// and its action to draw earth at random angle
			}
		});
		return btnBottom;
		}
	private void setBottomButtons(){
		btPane.getChildren().clear();
		btPane.getChildren().add(setStartButton());
		btPane.getChildren().add(setPauseButton());
		
	}
	/**
	 * main function ... sets up canvas, menu, buttons and timer
	 */
	@Override
	public void start(Stage stagePrimary) throws Exception {
		StackPane holder = new StackPane();
		stagePrimary.setTitle("Solar System");
		BorderPane bp = new BorderPane();
		
		bp.setTop(setMenu());
		
		
	    Group root = new Group();					// for group of what is shown			// put it in a scene				// apply the scene to the stage
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    							// create canvas onto which animation shown
	    holder.getChildren().add(canvas);
	    root.getChildren().add( holder );			// add to root and hence stage
	   
	    holder.setStyle("-fx-background-color: black");
	    gc = canvas.getGraphicsContext2D();
	    setMouseEvents(canvas);
	    bp.setCenter(root);
	    								// get context on canvas onto which images put
		// now load images of earth and sun
		// note these should be in package
	    rtPane = new VBox();
	    bp.setRight(rtPane);
	    
	    btPane = new HBox();
	    bp.setBottom(btPane);
	    setBottomButtons();
	    Scene scene = new Scene(bp, canvasSize*1.4, canvasSize*1.2);
	    stagePrimary.setScene( scene );
	    stagePrimary.show();
//	    final long startNanoTime = System.nanoTime();
		// for animation, note start time

	    new AnimationTimer()			// create timer
	    	{
	    		public void handle(long currentNanoTime) {
	    				// define handle for what do at this time
	    			if (SetAnimationRun == true){
	    				t = (currentNanoTime - startNanoTime) / 1000000000.0;
	    				drawSystem(t);	
	    			}
	    			else{
	    				drawSystem(t);
	    			}
	    		}
	    	}.start();					// start it
	    	
		stagePrimary.show();
		
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent event) {
		        System.out.println("this is called every 5 seconds on UI thread");
		    }
		}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();
	}
	
	public static void main(String[] args) {
		Application.launch(args);			// launch the GUI
	}

}
