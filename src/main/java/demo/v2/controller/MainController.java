package demo.v2.controller;

import demo.v2.component.FileTreeItem;
import demo.v2.util.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主页面控制器类
 * @author TritiumQ
 */
public class MainController implements Initializable
{
	@FXML
	private TreeView<String> fileTree;
	@FXML
	private Button btnHome, btnAlbum, btnSearch, btnAbout;
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		fileTree.setRoot(new FileTreeItem(Resource.FILE_ROOT));
		fileTree.setShowRoot(false);
	}
}
