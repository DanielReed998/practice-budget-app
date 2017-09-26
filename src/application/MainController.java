package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {
	
	//Data Tab
	@FXML private TableView<DataPiece> table;
	@FXML private TableColumn<DataPiece, String> category;
	@FXML private TableColumn<DataPiece, Double> amount;
	@FXML private TableColumn<DataPiece, String> note;
	@FXML private Button addData;
	@FXML private Button showData;
	@FXML private TextField datepicker;
	@FXML private TextField amountEntry;
	@FXML private TextField gasAmountEntry;
	@FXML private TextField notesBox;
	@FXML private Label dbLabel;
	@FXML private TextField newCategory;
	@FXML private Button addNewCategory;
	
	//Graph Tab
	@FXML LineChart<String, Number> lineChart;
	@FXML private Button button;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		//date.setValue(LocalDate.parse(formatter.format(LocalDate.now())));
		datepicker.setText(LocalDate.now().toString());
			
		//Initial creation of DB table. This is only relevant when opening program for the first time.
		String sql = "CREATE TABLE IF NOT EXISTS budget " +
				"(id			INTEGER		PRIMARY KEY, " + 	
				"date 			TEXT, " +
				"notes			TEXT, " +
				"food			TEXT, " +
				"gas			TEXT);";
		new DBInteraction().createTable(sql);
			
		//Initialize table to Food = 0. If values have been updated for today, table will reflect them.
		List<String> todayDataPull = new DBInteraction().pullData(datepicker.getText());
		ObservableList<DataPiece> oList = FXCollections.observableArrayList();
		if (todayDataPull.isEmpty()) {
			oList.add(new DataPiece("Food","0"));
		} else {
			oList.add(new DataPiece(todayDataPull.get(0), todayDataPull.get(1)));
		}
		category.setCellValueFactory(new PropertyValueFactory<DataPiece, String>("category"));
		amount.setCellValueFactory(new PropertyValueFactory<DataPiece, Double>("amount"));
		note.setCellValueFactory(new PropertyValueFactory<DataPiece, String>("note"));
		table.getItems().setAll(oList);
		
		
		//Line graph - beginning work on 7/12
		List<String> months = Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		/*series.getData().add(new XYChart.Data<String,Number>("Jan", 200.50));
		series.getData().add(new XYChart.Data<String,Number>("Feb", 300));
		series.getData().add(new XYChart.Data<String,Number>("Mar", 250));
		series.getData().add(new XYChart.Data<String,Number>("Apr", 200));
		series.getData().add(new XYChart.Data<String,Number>("May", 500));
		series.getData().add(new XYChart.Data<String,Number>("Jun", 600));
		series.getData().add(new XYChart.Data<String,Number>("Jul", 550));
		series.getData().add(new XYChart.Data<String,Number>("Aug", 700));*/
		for (String month : months) {
			series.getData().add(new XYChart.Data<String,Number>(month, Math.floor(Math.random()*800)));
		}
		series.setName("Pay1");
		lineChart.getData().add(series);
		
		//Attempting to show line graph values on hover. Takes too long to appear.
		for (XYChart.Series<String,Number> s : lineChart.getData()) {
			for (XYChart.Data<String, Number> data : series.getData()) {
				Tooltip.install(data.getNode(), new Tooltip(
						data.getYValue().toString()
						));
	            data.getNode().setOnMouseEntered(event -> data.getNode().getStyleClass().add("onHover"));
	            data.getNode().setOnMouseExited(event -> data.getNode().getStyleClass().remove("onHover"));
			}
		}
	}
	
	public void showData(ActionEvent e) {
		List<String> list = new ArrayList<String>();
		list = new DBInteraction().pullData(datepicker.getText());
		ObservableList<DataPiece> oList = FXCollections.observableArrayList();
		
		if (list.isEmpty()){
			oList.add(new DataPiece("Food","0.0"));
			oList.add(new DataPiece("Gas","0.0"));
		} else {
			for (int i = 0; i < list.size(); i += 2) {
				oList.add(new DataPiece(list.get(i), list.get(i+1)));
			}
		}
		
		table.getItems().setAll(oList);

	}
	
	public void addData(ActionEvent e) {
		new DBInteraction().addData(datepicker.getText(), 
				Double.parseDouble(amountEntry.getText()), 
				Double.parseDouble(gasAmountEntry.getText()), 
				notesBox.getText());
	}
	
	public void addNewCategory(ActionEvent e) {
		new DBInteraction().addColumn(newCategory.getText());
	}
	
}