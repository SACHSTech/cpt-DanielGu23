package cpt;

import java.text.SimpleDateFormat;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;


/**
 * LineChartView class file
 * @author: D. Gu
 * 
 */    
public class LineChartView implements IDataView {

	 /**
     * A method that displays the line chart for selected countries
     * 
     * @param - dataSet, the data 
     * 
     * @return a linechart as a node object
     */ 
	@Override
	public Node display(DataSet dataSet) {
		// Initialize the axes
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		// Set labels for the axes (x-axis =  date, y-axis = deaths per million)
		xAxis.setLabel("Date");
		yAxis.setLabel("Deaths per million people");
		// Create a linechart with these axes
		LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);
		// Show data on line chart for all selected countries
		for (int i = 0; i < dataSet.getSelectedCountries().size(); i++) {
			String country = dataSet.getSelectedCountries().get(i);
			List<CovidRecord> countryRecords = dataSet.getCountryRecords(country);
			XYChart.Series series = new XYChart.Series();
			for (int j = 0; j < countryRecords.size(); j++) {
				// Fetch records for the selected country
				CovidRecord record = countryRecords.get(j);
				// Format the date
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				String date = formatter.format(record.getDate());
				// Add the data point to the series
				series.getData().add(new XYChart.Data(date, record.getTotalDeathsPerMillion()));
			}
			// Set each series name as its country name
			series.setName(country);
			linechart.getData().add(series);
		}
		linechart.setCreateSymbols(false);
		return linechart;
	}

	 /**
     * A method that creates a layout container at the top of the screen
     * 
     * @return Hbox layout container
     */ 
	@Override
	public HBox updateTopButtons() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #AABBCC;");
		return hbox;
	}
}
