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
	private Stage mainStage;
	private Stage viewerStage;
	
	@Override
	public void init() throws Exception
	{
	
	}
	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FileUtil.PATH_MAIN_FXML));
		Scene scn = new Scene(loader.load());
		
		mainStage = stage;
		mainStage.setMinWidth(640);
		mainStage.setMinHeight(480);
		mainStage.setScene(scn);
		mainStage.setTitle(Resource.PROGRAM_NAME);
		mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(FileUtil.PATH_MAIN_ICON)).toExternalForm()));
		mainStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
