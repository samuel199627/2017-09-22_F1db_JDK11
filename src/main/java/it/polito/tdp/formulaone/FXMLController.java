package it.polito.tdp.formulaone;

import java.net.URL;

import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private Button btnSelezionaStagione;

    @FXML
    private ComboBox<Race> boxGara;

    @FXML
    private Button btnSimulaGara;

    @FXML
    private TextField textInputK;

    @FXML
    private TextField textInputK1;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	txtResult.clear();
    	
    	Season stagioneSelezionata=boxAnno.getValue();
    	if(stagioneSelezionata==null) {
    		txtResult.appendText("SELEZIONA UNA STAGIONE PRIMA DI PROCEDERE");
    		return;
    	}
    	
    	String ritornata=model.creaGrafo(stagioneSelezionata);
    	txtResult.appendText(ritornata);
    	
    	boxGara.getItems().clear();
    	boxGara.getItems().addAll(model.ritornaVertici());
    	
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	txtResult.clear();
    	Race garaSelezionata=boxGara.getValue();
    	if(garaSelezionata==null) {
    		txtResult.appendText("DEVI SELEZIONARE UNA GARA!");
    		return;
    	}
    	
    	double probabilita=0.0;
    	int tempoPausaSecondi=0;
    	try {
    		probabilita=Double.parseDouble(textInputK.getText());
    		tempoPausaSecondi=Integer.parseInt(textInputK1.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("PARAMETRI NON VALIDI!");
    		return;
    	}
    	
    	//controllare parametri >0
    	
    	txtResult.appendText(model.simula(garaSelezionata,probabilita,tempoPausaSecondi));
    	

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		boxAnno.getItems().clear();
		boxAnno.getItems().addAll(model.importaStagioni());
	}
}
