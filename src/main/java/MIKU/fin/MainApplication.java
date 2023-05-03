package MIKU.fin;

import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApplication extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FileUtil.PATH_MAIN_FXML));
		Scene scn = new Scene(loader.load());
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.setScene(scn);
		stage.setTitle(Resource.PROGRAM_NAME);
		stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(FileUtil.PATH_MAIN_ICON)).toExternalForm()));
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
