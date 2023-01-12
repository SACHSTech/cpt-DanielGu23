package cpt;

import java.io.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

/**
* The main page of the covid-19 data explorer
*
* @author: Daniel Gu
*/

	public class Main extends Application {
		private DataSet dataSet;
		private BorderPane border;
		
		public static Scene mainScene;
	    
	    /**
	     * Main method that launches Javafx
	     * @param - args, defult main method parameter
	     */
	    public static void main(String[] args) throws IOException {
	        launch(args);
	    }

	    
	    /**
	     * A method that creates the home page
	     * 
	     * @param - primaryStage,  The main stage where all the scenes are shown
	     */
	    @Override
	    public void start(Stage primaryStage) throws IOException {	    	
	    	primaryStage.setTitle("COVID-19 Data Explorer");	  
	    	dataSet = new DataSet();
            border = new BorderPane();            	    	
	        // Set the main scene
	        mainScene = new Scene(border, 954, 600);
	        primaryStage.setScene(mainScene);
	        primaryStage.show();
	    }
}
