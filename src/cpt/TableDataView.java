package cpt;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;

/**
* TableDataView class file
* @author: D. Gu
* 
*/ 

public class TableDataView implements IDataView {
	// Initialize class variables
	private DatePicker datePicker = new DatePicker();
	private DataSet dataSet = null;
	private BorderPane border = null;
	private Comparator<CovidRecord> comparator = Comparator.comparing(CovidRecord::getLocation);
	private CheckBox descending = new CheckBox("Descending");

	/**
    * Constructor - Creates a table of the Covid-19 data
    *
    * @param border - The layout container
    */	
	TableDataView(BorderPane border){
		this.border = border;
	}

	/**
    * A method that creates a table with all the data from the csv file 
	*
    * @param dataset - The data from the csv file
	* 
	* @return The data table with all of the data
    */	
	@Override
	public Node display(DataSet dataSet) {
		this.dataSet = dataSet;
		List<CovidRecord> data = new ArrayList<>();
		LocalDate localDate = datePicker.getValue();
		// Set the default date
		if (localDate == null) {
			localDate = LocalDate.of(2022, 12, 26);
		}
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		// Add data of selected countries to table 
		for (int i = 0; i < dataSet.getSelectedCountries().size(); i++) {
			String country = dataSet.getSelectedCountries().get(i);
			List<CovidRecord> countryRecords = dataSet.getCountryRecords(country);
			for (int j = 0; j < countryRecords.size(); j++) {
				CovidRecord record = countryRecords.get(j);
				// Check if selected date matches with records
				if (date.equals(record.getDate())) {
					data.add(record);
				}
			}
		}
		// Built in sorting disabled due to requirements
		data = Sort.mergeSort(data, this.comparator);
		// Reverse the list if in descending order
		if (descending.isSelected()) {
			Collections.reverse(data);	
		}
		
		// Create a column for countries
		TableColumn<CovidRecord, String> countryName = new TableColumn<CovidRecord, String>();
		countryName.setText("Location");
		countryName.setCellValueFactory(new PropertyValueFactory<CovidRecord, String>("location"));
		countryName.setSortable(false);

		// Create a column for total cases
		TableColumn<CovidRecord, Long> totalCases = new TableColumn<CovidRecord, Long>();
		totalCases.setText("TotalCases");
		totalCases.setCellValueFactory(new PropertyValueFactory<CovidRecord, Long>("totalCases"));
		totalCases.setSortable(false);

		// Create a column for total cases per million
		TableColumn<CovidRecord, Double> totalCasesPerMillion = new TableColumn<CovidRecord, Double>();
		totalCasesPerMillion.setText("TotalCasesPerMillion");
		totalCasesPerMillion.setCellValueFactory(new PropertyValueFactory<CovidRecord, Double>("totalCasesPerMillion"));
		totalCasesPerMillion.setSortable(false);

		// Create a column for total deaths
		TableColumn<CovidRecord, Integer> totalDeaths = new TableColumn<CovidRecord, Integer>();
		totalDeaths.setText("TotalDeaths");
		totalDeaths.setCellValueFactory(new PropertyValueFactory<CovidRecord, Integer>("totalDeaths"));
		totalDeaths.setSortable(false);

		// Create a column for total deaths per million
		TableColumn<CovidRecord, Double> totalDeathsPerMillion = new TableColumn<CovidRecord, Double>();
		totalDeathsPerMillion.setText("TotalDeathsPerMillion");
		totalDeathsPerMillion.setCellValueFactory(new PropertyValueFactory<CovidRecord, Double>("totalDeathsPerMillion"));
		totalDeathsPerMillion.setSortable(false);

		// Create a column for population
		TableColumn<CovidRecord, Long> population = new TableColumn<CovidRecord, Long>();
		population.setText("Population");
		population.setCellValueFactory(new PropertyValueFactory<CovidRecord, Long>("population"));
		population.setSortable(false);

		// Create the table
		final TableView<CovidRecord> tableView = new TableView<CovidRecord>();
		tableView.setItems(FXCollections.observableArrayList(data));
		tableView.getColumns().addAll(countryName, totalCases, totalCasesPerMillion, totalDeaths, totalDeathsPerMillion, population);
		return tableView;
	}

	@Override
	/**
    * A method that creates buttons on the top of the screen
	*
	* @param - datePicker, a pop-up calender that lets the user pick a date
	* 
	* @return the layout container with all of the buttons
    */	
	public HBox updateTopButtons(DatePicker datePicker) {
		// Initialize the layout container
		this.datePicker = datePicker;
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #AABBCC;");
		Text text = new Text("Select date");
		datePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setDisable(item.isBefore(LocalDate.of(2020, 1, 22)) || item.isAfter(LocalDate.of(2022, 12, 26)));
			}	
		});
	    // Add an event listener to allow the user to change the date 
	    datePicker.valueProperty().addListener((ChangeListener<? super LocalDate>) new ChangeListener<LocalDate>() {
	      @Override
	      public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
	    	  sortTable(comparator);
	      }
	    });		
		
		// Create a button for sorting location
		Button buttonSortLocation  = new Button("Sort location");
		buttonSortLocation.setPrefSize(100, 20);
		buttonSortLocation.setOnAction(event -> sortTable(Comparator.comparing(CovidRecord::getLocation)));		
			
		// Create a button for sorting total cases
		Button buttonSortTotalCases  = new Button("Sort TotalCases");
		buttonSortTotalCases.setPrefSize(100, 20);
		buttonSortTotalCases.setOnAction(event -> sortTable(Comparator.comparing(CovidRecord::getTotalCases)));
		
		// Create a button for sorting total cases per million
		Button buttonTotalCasesPerMillion  = new Button("Sort TotalCasesPerMillion");
		buttonTotalCasesPerMillion.setPrefSize(150, 20);
		buttonTotalCasesPerMillion.setOnAction(event -> sortTable(Comparator.comparing(CovidRecord::getTotalCasesPerMillion)));
		
		hbox.getChildren().addAll(text, datePicker, buttonSortLocation, buttonSortTotalCases, buttonTotalCasesPerMillion, descending);
		return hbox;
	}
	
	/**
 	* This method sorts the table using the provided comparator.
 	*
	* @param comparator - A comparator object used to sort the table
 	*
	* @return nothing
    */	
	private void sortTable(Comparator<CovidRecord> comparator) {
		this.comparator = comparator;
		border.setCenter(display(dataSet));
	}
}
