/***
 * @author TritiumQun
 * @version alpha0.1
 *
 */
package demo.v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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