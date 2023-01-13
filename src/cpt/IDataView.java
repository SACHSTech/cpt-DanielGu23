package cpt;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * IDataView class file
 *
 */    

public interface IDataView {
	// Create abstract methods
	public Node display(DataSet dataSet);
	public HBox updateTopButtons();
}
