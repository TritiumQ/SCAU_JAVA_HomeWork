package demo.v2;

import demo.v2.component.FileTreeItem;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Objects;

public class TestApp extends Application {
	@Override
	public void init()
	{
	
	}
	@Override
	public void start(Stage stage) throws Exception {
		FileTreeItem item = new FileTreeItem(Objects.requireNonNull(FileSystemView.getFileSystemView().getHomeDirectory().listFiles())[0]);
		
		TreeView<String> treeView = new TreeView<>();
		treeView.setRoot(item);
		treeView.setShowRoot(false);
		treeView.setMinWidth(250);
		
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().add(treeView);
		
		Scene scn = new Scene(vb, 320, 480);
		stage.setScene(scn);
		stage.show();
	}
	
	public static void main(String[] args) { launch(args); }
}
