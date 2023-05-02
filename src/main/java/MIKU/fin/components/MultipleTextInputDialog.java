package MIKU.fin.components;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class MultipleTextInputDialog extends Dialog<String[]>
{
	private final ArrayList<TextField> textFields;
	
	public MultipleTextInputDialog(int numTextFields, String[] content) {
		// Create the text fields
		textFields = new ArrayList<>();
		for (int i = 0; i < numTextFields; i++) {
			TextField textField = new TextField();
			textField.setPromptText(content[i].split(":")[0]);
			textFields.add(textField);
		}
		
		// Set the dialog content
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		for (int i = 0; i < numTextFields; i++) {
			gridPane.add(new Label(content[i]), 0, i);
			gridPane.add(textFields.get(i), 1, i);
		}
		getDialogPane().setContent(gridPane);
		
		// Set the result converter
		setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				ArrayList<String> result = new ArrayList<>();
				for (TextField textField : textFields) {
					result.add(textField.getText());
				}
				return result.toArray(new String[0]);
			}
			return null;
		});
		
		// Add the OK and Cancel buttons
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
	}
}
