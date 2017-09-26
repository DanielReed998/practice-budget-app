package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	//Login Window
	private LoginModel loginModel = new LoginModel();
	
	@FXML private CheckBox stayLoggedIn;	
	@FXML private DatePicker datePicker = new DatePicker();	
	@FXML private Button login;	
	
	@FXML private Label isConnected;
	@FXML private TextField userName;
	@FXML private TextField password;


	
	public void login(ActionEvent e) throws IOException {
		
		try {
			if (loginModel.isLogin(userName.getText(), password.getText())) {
				((Node)(e.getSource())).getScene().getWindow().hide();
				
				Parent root=FXMLLoader.load(getClass().getResource("Main.fxml")); 
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage primaryStage = new Stage();
				primaryStage.setTitle("BudgetFX");
				primaryStage.setScene(scene);
				primaryStage.show();	
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Error");
				alert.setHeaderText(null);
				alert.setContentText("Username or Password incorrect.");

				alert.showAndWait();
			}
		} catch (SQLException e1) {
			isConnected.setText("Username and password is not correct");
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (loginModel.isDbConnected()) {
			isConnected.setText("Connected");
		} else {
			isConnected.setText("Not Connected");
		}
	}
}
