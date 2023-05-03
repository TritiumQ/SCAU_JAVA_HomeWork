package MIKU.fin;

import MIKU.fin.controllers.SlideController;
import MIKU.fin.controllers.ViewerController;
import MIKU.fin.utils.FileUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher
{
	public static void main(String[] args) {
		MainApplication.main(args);
	}
	
	/**
	 * 启动图片查看器
	 * @throws IOException
	 */
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
	
	/**
	 * 启动幻灯片播放器
	 * @throws IOException
	 */
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
		
		stage.show();
		stage.setFullScreen(true);
	}
}
