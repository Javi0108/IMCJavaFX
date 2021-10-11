package imcjavafx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMCJavaFX extends Application {

	private Label pesoLabel;
	private TextField pesoText;
	private Label kgLabel;
	private Label alturaLabel;
	private TextField alturaText;
	private Label cmLabel;
	private Label IMCLabel;
	private Label resulIMC;
	
	private StringProperty pesoStringP = new SimpleStringProperty();
	private StringProperty alturaStringP = new SimpleStringProperty();
	private StringProperty IMCStringP = new SimpleStringProperty();

	private DoubleProperty pesoDoubleP = new SimpleDoubleProperty();
	private DoubleProperty alturaDoubleP = new SimpleDoubleProperty();
	private DoubleProperty IMCDoubleP = new SimpleDoubleProperty();
	
	private StringProperty resulStringP = new SimpleStringProperty();
	
	public final double BAJO_PESO = 18.5;
	public final double NORMAL = 25;
	public final double SOBREPESO = 30;
	
	public void calcularIMC() {
		Double IMC = IMCDoubleP.get();
		
		if (IMC == 0d) {
			IMCStringP.set("IMC: (peso / altura ^ 2)");
			resulStringP.set("Bajo peso | Normal | Sobrepeso | Obeso");
		} else {
			IMCStringP.set("IMC: " + IMC);
			if (IMC < BAJO_PESO)
				resulStringP.set("Bajo peso");
			else if (IMC >= BAJO_PESO && IMC < NORMAL)
				resulStringP.set("Normal");
			else if (IMC >= NORMAL && IMC < SOBREPESO)
				resulStringP.set("Sobrepeso");
			else
				resulStringP.set("Obeso");
		}
	}
	
	private void recalcularIMC() {
		if ((pesoDoubleP.get() == 0d) || (alturaDoubleP.get() == 0d))
			IMCDoubleP.set(0d);
		else {
			Double p = pesoDoubleP.get();
			Double a = alturaDoubleP.get();
			Double result = (p / (a * a)) * 10000d;
			IMCDoubleP.set(Math.round(result * 100.0) / 100.0);
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoLabel = new Label("Peso:");
		pesoText = new TextField();
		kgLabel = new Label("kg");
		HBox pesoBox = new HBox(5, pesoLabel, pesoText, kgLabel);
		
		alturaLabel = new Label("Altura:");
		alturaText = new TextField();
		cmLabel = new Label("cm");
		HBox alturaBox = new HBox(5, alturaLabel, alturaText, cmLabel);
		
		IMCLabel = new Label("IMC: (peso / altura^ 2)");
		resulIMC = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		
		VBox root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);
		root.getChildren().addAll(pesoBox, alturaBox, IMCLabel, resulIMC);
		
		Scene scene = new Scene(root, 320, 200);
		primaryStage.setTitle("IMC Calculator");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		pesoStringP.bindBidirectional(pesoText.textProperty());
		Bindings.bindBidirectional(pesoStringP, pesoDoubleP, new NumberStringConverter());
		pesoDoubleP.addListener((o, ov, nv) -> recalcularIMC());
		
		alturaStringP.bindBidirectional(alturaText.textProperty());
		Bindings.bindBidirectional(alturaStringP, alturaDoubleP, new NumberStringConverter());
		alturaDoubleP.addListener((o, ov, nv) -> recalcularIMC());
		
		IMCStringP.bindBidirectional(IMCLabel.textProperty());
		resulStringP.bindBidirectional(resulIMC.textProperty());
		IMCDoubleP.addListener((o, ov, nv) -> calcularIMC());
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
