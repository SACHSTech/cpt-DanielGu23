package cpt;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.control.DatePicker;

/**
 * IDataView class file
 * @author: D. Gu
 * 
 */    

public interface IDataView {
	// Create abstract methods
	public Node display(DataSet dataSet);
	public HBox updateTopButtons(DatePicker datePicker);
}
