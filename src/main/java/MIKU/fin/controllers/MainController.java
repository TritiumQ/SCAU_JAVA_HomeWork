package MIKU.fin.controllers;

import MIKU.fin.components.ImageThumbnailPane;
import MIKU.fin.components.SystemFileTreeItem;
import MIKU.fin.moduel.ImageFile;
import MIKU.fin.utils.FileUtil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主页面控制器
 * @author TritiumQ
 */
public class MainController implements Initializable
{
	@FXML
	private BorderPane mainPane;
	@FXML
	private FlowPane imagePane;
	@FXML
	private TreeView<String> systemFileTree;
	@FXML
	private MenuItem menuOpen;
	@FXML
	private Button  btnHome, btnDir, btnSearch, btnAbout;
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//初始化目录树
		systemFileTree.setRoot(new SystemFileTreeItem(FileUtil.FILE_ROOT));
		systemFileTree.setShowRoot(false);
		
		//初始化菜单
		menuOpen.setOnAction(event -> {
		
		});
		
		//初始化按钮
		btnHome.setOnAction(event -> {
			mainPane.setLeft(null);
		});
		
		btnDir.setOnAction(event -> {
			mainPane.setLeft(new AnchorPane(systemFileTree));
		});
		
		btnSearch.setOnAction(event -> {
		
		});
		
		btnAbout.setOnAction(event -> {
		
		});
		
		
		//测试
		
		imagePane.getChildren().addAll(
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\1.png")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\2.gif")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\kailu.jpg")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\1.png")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\2.gif")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\1.png")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\2.gif")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\1.png")), 100),
				new ImageThumbnailPane(new ImageFile(new File("E:\\Pictures\\2.gif")), 100)
		);
		
	}
}
