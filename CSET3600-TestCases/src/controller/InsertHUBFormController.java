package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.PatternConfig;
import application.HUB;
import application.valueChecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InsertHUBFormController implements Initializable {

	@FXML
	VBox formPane;
	
	@FXML
	ChoiceBox<String> cbVlan;
	
	@FXML
	TextField tfName, tfSubnet, tfNetmask;
	
	@FXML
	Button btnFinish, btnCancel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> vlanList = FXCollections.observableArrayList("v2");
		cbVlan.setItems(vlanList);
	}
	
	@FXML
	private void closeForm(){
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void submitForm(){
		//grab all input values
		String hubVlan = cbVlan.getValue();
		String hubName = tfName.getText();
		String hubSubnet = tfSubnet.getText();
		String hubNetmask = tfNetmask.getText();
		
		//check test the values
		boolean checkVlan = valueChecker.checkVlan(hubVlan); 
		boolean checkName = valueChecker.checkName(hubName);
		boolean checkSubnet = valueChecker.checkIp(hubSubnet);
		boolean checkNetmask = valueChecker.checkNetmask(hubNetmask);
		
		if(checkVlan && checkName && checkSubnet && checkNetmask) {
			HUB hubObject = new HUB();
			hubObject.setName(hubName);
			hubObject.setNetmask(hubNetmask);
			hubObject.setSubnet(hubSubnet);
			
			//add it to all the other hubObjects in our hashmap
			PatternConfig.hubMap.put(hubName, hubObject);
			Stage stage = (Stage) btnFinish.getScene().getWindow();
			stage.close();
		}else {
			//create a popup to warn user
			//also highlight the input fields that are invalid
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Input");
			alert.setHeaderText("Error Input");
			alert.setContentText("Please check over your input parameters and resubmit.");
			alert.showAndWait();
			
			if(!checkVlan) {
				cbVlan.getStyleClass().add("hubform-invalid-field");
			}
			
			if(!checkName) {
				tfName.getStyleClass().add("hubform-invalid-field");
			}
			
			if(!checkSubnet) {
				tfSubnet.getStyleClass().add("hubform-invalid-field");
			}
			
			if(!checkNetmask) {
				tfNetmask.getStyleClass().add("hubform-invalid-field");
			}
		}
	}
}
