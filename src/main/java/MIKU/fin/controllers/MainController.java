package MIKU.fin.controllers;

import MIKU.fin.components.ImageFlowPane;
import MIKU.fin.components.ImageThumbnailPane;
import MIKU.fin.components.InformationPane;
import MIKU.fin.components.SystemFileTreeItem;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.FontUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	private BorderPane main;
	@FXML
	private VBox center;
	@FXML
	private HBox bottom;
	@FXML
	private InformationPane informationPane;
	@FXML
	private HBox functionBar;
	@FXML
	private ImageFlowPane imagePane;
	@FXML
	private TreeView<String> systemFileTree;
	@FXML
	private AnchorPane leftPane;
	@FXML
	private MenuItem menuOpen;
	@FXML
	private Button  btnHome, btnDir, btnAbout;
	@FXML
	private Button btnBack, btnForward, btnSearch;
	@FXML
	private Button btnLikeit, btnDelete, btnCut, btnCopy, btnPaste, btnRename, btnSlide, btnInf;
	@FXML
	private TextField searchBox, addressBox;
	@FXML
	private Slider zoom;
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		initFileTree();
		initMenu();
		initButton();
		initImagePane();
		
		informationPane = new InformationPane();
		bottom.getChildren().add(informationPane);
		bottom.getChildren().get(0).toFront();
	}
	
	private void initButton() {
		//初始化按钮
		btnHome.setOnAction(event -> {
			Platform.runLater(()->{
				main.setLeft(null);
			});
		});
		
		btnDir.setOnAction(event -> {
			Platform.runLater(()->{
				main.setLeft(new AnchorPane(systemFileTree));
			});
		});
		
		btnAbout.setOnAction(event ->{
			Stage aboutStage = new Stage();
			VBox vBox = new VBox();
			Label l1 = new Label("项目作者: TritiumQ");
			l1.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
			Label l2 = new Label("项目地址: ");
			l2.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
			Hyperlink hl = new Hyperlink("https://github.com/TritiumQ/SCAU_JAVA_HomeWork.git");
			hl.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
			vBox.getChildren().addAll(l1,l2,hl);
			Scene scn = new Scene(vBox, 320, 120);
			aboutStage.setScene(scn);
			aboutStage.setTitle("关于");
			aboutStage.show();
		});
	}
	
	private void initMenu() {
		//初始化菜单
		menuOpen.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			File image = fileChooser.showOpenDialog(new Stage());
			System.out.println(image.getAbsolutePath());
		});
	}
	
	private void initFileTree() {
		//初始化目录树
		systemFileTree.setRoot(new SystemFileTreeItem(FileUtil.FILE_ROOT));
		systemFileTree.setShowRoot(false);
	}
	
	
	private void initImagePane()
	{
		imagePane = new ImageFlowPane();
		center.getChildren().add(imagePane);
		
		//测试
		imagePane.getChildren().add(new ImageThumbnailPane(new ImageFile("E:\\PICTURES\\2.gif"),100));
		
	}
}
