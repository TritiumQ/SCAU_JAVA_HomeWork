/***
 * @author TritiumQun
 * @version alpha0.1
 *
 */
package com.demo.imagebrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;

public class HelloApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageBrowser-view.fxml"));
		MainController controller = fxmlLoader.getController();
		Scene scene = new Scene(fxmlLoader.load());
		
		stage.setTitle("Image Browser demoVersion");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}