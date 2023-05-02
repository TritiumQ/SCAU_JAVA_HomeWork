package MIKU.fin.controllers;

import MIKU.fin.utils.FileUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
		stage.setMinHeight(600);
		stage.setMinWidth(800);
		
		stage.show();
	}
	
	public static void launchSlide() throws IOException
	{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(Launcher.class.getResource(FileUtil.PATH_FULLSCREENVIEWER_FXML));
		Scene scn = new Scene(loader.load());
		SlideController controller = loader.getController();
		stage.setScene(scn);
		stage.setTitle("MIKU Slide");
		stage.getIcons().add(new Image(Launcher.class.getResource(FileUtil.PATH_MAIN_ICON).toExternalForm()));
		stage.setMinHeight(600);
		stage.setMinWidth(800);
		stage.heightProperty().addListener((observable, oldValue, newValue) -> {
			controller.getCurrentImage().setFitHeight(newValue.doubleValue()*0.9);
			System.out.println("height: " + newValue.doubleValue());
		});
		
		stage.widthProperty().addListener((observable, oldValue, newValue) -> {
			controller.getCurrentImage().setFitWidth(newValue.doubleValue()*0.9);
			System.out.println("width: " + newValue.doubleValue());
		});
		
		stage.show();
		controller.onFullScreen();
	}
}
