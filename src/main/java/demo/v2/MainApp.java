package demo.v2;

import demo.v2.util.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application
{
	Scene mainScene;
	Stage mainStage;
	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Resource.path_MainWindowFXML));
		mainScene = new Scene(loader.load());
		
		mainStage = stage;
		mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Resource.path_MainIcon).toExternalForm())));
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	public static void main(String[] args) {launch(args);}
}
