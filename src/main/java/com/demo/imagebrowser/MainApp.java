/***
 * @author TritiumQun
 * @version alpha0.1
 *
 */
package com.demo.imagebrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;

public class MainApp extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageManager-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		
		stage.getIcons().add(new Image("file:" + StaticResources.path_MainIcon));
		stage.setTitle("ImageManager - alphaVersion");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}