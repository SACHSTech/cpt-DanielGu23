package cpt;

import java.io.*;


import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



/**
* The main page of the covid-19 data explorer
*
* @author: Daniel Gu
*/

public class Main extends Application {
	private DataSet dataSet;
	private BorderPane border;
	private IDataView centerView;
	public static Scene mainScene;

	/**
 	 * Main method that launches Javafx
	 * 
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
		// Add the title and a list of countries    	
		primaryStage.setTitle("COVID-19 Data Explorer");	  
		dataSet = new DataSet();
		border = new BorderPane();
		border.setLeft(addLeftBox(dataSet));
		border.setBottom(addBottomBox());
		centerView = new LineChartView();
		updateCenterView();

		// Set the main scene
		mainScene = new Scene(border, 954, 600);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * A method that adds a box at the left of the Javafx screen
	 * 
	 * @param - dataSet, a set of data
	 * 
	 * @return A vertical column of child nodes
	 */
	private Node addLeftBox(DataSet dataSet) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		Text title = new Text("Countries");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);
		
		// Add selected countries to list
		ListView<String> listView = new ListView<>();
		for (int i = 0; i < dataSet.getSelectedCountries().size(); i++) {
			String strCountry = dataSet.getSelectedCountries().get(i);
			listView.getItems().add(strCountry);
			listView.getSelectionModel().select(i);
		}
		// Add unselected countries to list
		for (int i = 0; i < dataSet.getUnselectedCountries().size(); i++) {
			String strCountry = dataSet.getUnselectedCountries().get(i);
			listView.getItems().add(strCountry);
		}
		listView.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(String item) {
				// Select and unselect a country using checkboxes
				BooleanProperty observable = new SimpleBooleanProperty(dataSet.getSelectedCountries().contains(item));
				observable.addListener((obs, wasSelected, isNowSelected) -> {
					if (isNowSelected) {
						dataSet.getUnselectedCountries().remove(item);
						dataSet.getSelectedCountries().add(item);
					}else {
						dataSet.getSelectedCountries().remove(item);
						dataSet.getUnselectedCountries().add(item);	                		
					}
					updateCenterView();
					// Check that it is working
					System.out.println("Check box for "+item+" changed from "+wasSelected+" to "+isNowSelected);
					}
				);
				return observable ;
			}
		}));
		vbox.getChildren().add(listView);
		VBox.setVgrow(listView, javafx.scene.layout.Priority.ALWAYS);
		return vbox;
	}		

	/**
     * A method that creates buttons on the bottom of the screen
     * 
     * @return Hbox layout container
     */ 
	private HBox addBottomBox() {
		HBox bottomPane = new HBox();
		bottomPane.setPadding(new Insets(15, 12, 15, 12));
		bottomPane.setSpacing(10);
		bottomPane.setStyle("-fx-background-color: #AABBCC;");
		// Create button for line chart
		Button buttonLineChart = new Button("Line chart");
		buttonLineChart.setPrefSize(100, 20);
		buttonLineChart.setOnAction(event -> {centerView = new LineChartView(); updateCenterView();});
		// Create button for pie chart
		Button buttonPieChart = new Button("Pie chart");
		buttonLineChart.setPrefSize(100, 20);
		buttonLineChart.setOnAction(event -> {});		    
		// Create button for table
		Button buttonTable = new Button("Table");
		buttonTable.setPrefSize(100, 20);		    		    
		buttonTable.setOnAction(event -> {});
		
		bottomPane.getChildren().addAll(buttonLineChart, buttonPieChart, buttonTable);
		return bottomPane;			
	}

    /**
     * A method that organizes the layout of the program 
     * 
     * @return nothing
     */ 
	private void updateCenterView() {
		border.setTop(centerView.updateTopButtons());
		border.setCenter(centerView.display(dataSet));
	}

}
