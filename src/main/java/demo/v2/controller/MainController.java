package demo.v2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;

/**
 * 主页面控制器类
 * @author TritiumQ
 */
public class MainController
{
	@FXML
	private TreeView<String> fileTree;
	@FXML
	private Button btnHome, btnAlbum, btnSearch, btnAbout;
	@FXML
	private void initialize()
	{
	
	}
}
