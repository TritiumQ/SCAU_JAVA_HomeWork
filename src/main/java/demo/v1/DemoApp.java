package demo.v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DemoApp extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("demo-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		
		stage.getIcons().add(new Image("file:" + StaticResources.path_MainIcon));
		stage.setTitle("demo");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
