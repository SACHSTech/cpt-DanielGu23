package cpt;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
* PieChartView class file
*
* @author: D.Gu
*/

public class PieChartView implements IDataView {
	private DatePicker datePicker = null;
	private DataSet dataSet = null;
	private BorderPane border = null;
	
	/**
	 * Constructor - loads the pie chart
	 *   
	 */ 
	PieChartView(BorderPane border){
		this.border = border;   
	}	

	/**
     * A method that displays the pie chart for selected countries
     * 
     * @param - dataSet, the data 
     * 
     * @return a pie chart as a node object
     */ 
	@Override
	public Node display(DataSet dataSet) {
		this.dataSet = dataSet;
		LocalDate localDate = datePicker.getValue();
		if (localDate == null) {
			localDate = LocalDate.of(2022, 12, 26);
		}
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();	
		// Adding data to the pie chart
		for (int i = 0; i < dataSet.getSelectedCountries().size(); i++) {
			String country = dataSet.getSelectedCountries().get(i);
			List<CovidRecord> countryRecords = dataSet.getCountryRecords(country);
			for (int j = 0; j < countryRecords.size(); j++) {
				CovidRecord record = countryRecords.get(j);
				if (date.equals(record.getDate())) {
					pieChartData.add(new PieChart.Data(record.getLocation(), record.getTotalCases()));
				}
			}
		}
        final PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Total cases");
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                     }
                });
        }                
		return pieChart;
	}

	

}
