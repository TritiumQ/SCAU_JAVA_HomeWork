package MIKU.fin.controllers;

import MIKU.fin.utils.FileUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Launcher
{
	public static void launchViewer() throws IOException
	{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(Launcher.class.getResource(FileUtil.PATH_VIEWER_FXML));
		Scene scn = new Scene(loader.load());
		ViewerController controller = loader.getController();
		stage.setScene(scn);
		stage.setTitle("MIKU Viewer");
		stage.getIcons().add(new Image(Launcher.class.getResource(FileUtil.PATH_MAIN_ICON).toExternalForm()));
		
		stage.widthProperty().addListener((observable, oldValue, newValue) -> {
			controller.getCurrentImage().setFitWidth(newValue.doubleValue());
		});
		
		stage.show();
	}
	
	public static void launchSlide() throws IOException
	{
	
	}
}
