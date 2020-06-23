/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.StateAndNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="btnSelezionaAnno"
	private Button btnSelezionaAnno; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxForma"
	private ComboBox<String> cmbBoxForma; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtAlfa"
	private TextField txtAlfa; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML
	void doCreaGrafo(ActionEvent event) {

		txtResult.clear();
		
		//il controllo su anni l'ho fatto prima che tanto non potrei arrivare qui diversamente
		
		if(this.cmbBoxForma.getValue()==null) {
			txtResult.appendText("ERRORE : Selezionare una Shape!\n");
			return; 
		}
		
		model.creaGrafo(Integer.parseInt(txtAnno.getText()), cmbBoxForma.getValue());
		
		for (StateAndNumber s : this.model.getSommaPesiAdiacentiPerStato()) {
			txtResult.appendText(s+"\n");
		}
		
		
		
	}

	@FXML
	void doSelezionaAnno(ActionEvent event) {

		txtResult.clear();
		
		int year=-1; 
		if (this.txtAnno.getText().length()==0) {
			txtResult.appendText("ERRORE : Inserire anno compreso fra 1910-2014 !\n");
			return; 
		}
		
		try {
			year= Integer.parseInt(this.txtAnno.getText());
		}catch(NumberFormatException nfe) {
			txtResult.appendText("ERRORE : Inserire anno in valori numerici !\n");
			return; 
		}
		if(year<1910 || year >2014) {
			txtResult.appendText("ERRORE : Inserire anno compreso fra 1910-2014 !\n");
			return; 
		}
		
		this.cmbBoxForma.getItems().removeAll(this.cmbBoxForma.getItems());
		this.cmbBoxForma.getItems().addAll(this.model.getShapeByYear(year)); 
		
		this.btnCreaGrafo.setDisable(false);
	}

	@FXML
	void doSimula(ActionEvent event) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		this.btnCreaGrafo.setDisable(true);

	}
}
