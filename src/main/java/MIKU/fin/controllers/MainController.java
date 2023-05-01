package MIKU.fin.controllers;

import MIKU.fin.components.ImageFlowPane;
import MIKU.fin.components.InformationPane;
import MIKU.fin.components.SystemFileTreeItem;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.FontUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * 主页面控制器
 * @author TritiumQ
 */
public class MainController implements Initializable
{
	//singleton
	public static MainController instance = null;
	
	@FXML
	private BorderPane main;
	@FXML
	private ScrollPane center;
	@FXML
	private HBox bottom;
	private InformationPane informationPane;
	@FXML
	private ImageFlowPane imagePane;
	private ContextMenu imagePaneMenu;
	@FXML
	private TreeView<String> systemFileTree;
	@FXML
	private AnchorPane leftPane;
	@FXML
	private Button btnFavorites, btnDir, btnAbout;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnLikeit, btnDelete, btnCut, btnCopy, btnPaste, btnRename, btnOpen, btnInf;
	@FXML
	private TextField searchBox, pathBox;
	@FXML
	private Slider zoom;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		instance = this;
		
		informationPane = new InformationPane();
		bottom.getChildren().add(informationPane);
		bottom.getChildren().get(0).toFront();
		
		initZoom();
		initFileTree();
		initImagePane();
		initContextMenu();
	}
	
	private void initContextMenu()
	{
		MenuItem refresh = new MenuItem("刷新");
		
		MenuItem like = new MenuItem("收藏");
		
		MenuItem play = new MenuItem("播放幻灯片");
		
		MenuItem open = new MenuItem("打开选中");
		open.setOnAction(event -> {
			try {
				imagePane.openSelected(null);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
		MenuItem delete = new MenuItem("删除");
		delete.setOnAction(event -> {
			btnDeleteClick();
		});
		
		MenuItem cut =  new MenuItem("剪切");
		cut.setOnAction(event -> {
			btnCutClick();
		});
		
		MenuItem copy = new MenuItem("复制");
		copy.setOnAction(event -> {
			btnCopyClick();
		});
		
		MenuItem paste = new MenuItem("粘贴");
		paste.setOnAction(event -> {
			btnPasteClick();
		});
		
		MenuItem rename = new MenuItem("重命名");
		rename.setOnAction(event -> {
			btnRenameClick();
		});
		
		MenuItem property = new MenuItem("属性");
		property.setOnAction(event -> {
			btnPropertyClick();
		});
		
		imagePaneMenu = new ContextMenu();
		imagePaneMenu.getItems().addAll(
				open,play,new SeparatorMenuItem(),
				refresh,new SeparatorMenuItem(),
				like,new SeparatorMenuItem(),
				delete, cut, copy, paste, new SeparatorMenuItem(),
				rename, new SeparatorMenuItem(),
				property);
		
		center.setOnContextMenuRequested(event -> {
			imagePaneMenu.show(imagePane, event.getScreenX(), event.getScreenY());
		});
	}
	
	private void initZoom()
	{
		zoom.setMin(50);
		zoom.setMax(250);
		zoom.setBlockIncrement(10);
		zoom.valueProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(()->{
				imagePane.update(newValue.doubleValue());
			});
		});
	}
	
	private void initFileTree() {
		//初始化目录树
		systemFileTree.setRoot(new SystemFileTreeItem(FileUtil.FILE_ROOT));
		systemFileTree.setShowRoot(false);
		systemFileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
			if(newValue instanceof SystemFileTreeItem)
			{
				SystemFileTreeItem item = (SystemFileTreeItem) newValue;
				setPath(item.getFile().getAbsolutePath());
			}
		});
	}
	
	private double x, y;
	private double selectX, selectY;
	
	private void initImagePane()
	{
		imagePane = new ImageFlowPane(null);
		center.setContent(imagePane);
		center.setFitToWidth(true);
		center.heightProperty().addListener((observable, oldValue, newValue) -> {
			if(imagePane.getHeight() < newValue.doubleValue())
			{
				imagePane.setPrefHeight(newValue.doubleValue());
			}
		});
		setPath("E:\\Pictures");
		
		imagePane.setOnMouseClicked(event -> {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && !event.isControlDown())
			{
				if(imagePane.equals(event.getPickResult().getIntersectedNode()))
				{
					imagePane.clearSelect();
				}
			}
		});
	}
	
	public void setPath(String path)
	{
		imagePane.update(new File(path), zoom.getValue());
		pathBox.setText(path);
	}
	
	public void setImageInf(int count, int selectedCounter, long selectSize)
	{
		informationPane.update(count, selectedCounter, selectSize);
	}
	
	@FXML
	private void goPath()
	{
		if(!pathBox.getText().equals(""))
		{
			setPath(pathBox.getText());
		}
	}
	
	@FXML
	private void serach()
	{
		if(!searchBox.getText().equals(""))
		{
			//TODO 搜索
		}
	}
	
	@FXML
	public void menuOpen() throws IOException
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择图片");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("所有图片文件", "*.*"),
				new FileChooser.ExtensionFilter("图片文件",
						"*.jpg", "*.png", "*.bmp", "*.gif", "*.jpeg", ".webp")
		);
		var files = fileChooser.showOpenMultipleDialog(main.getScene().getWindow());
		//TODO 打开图片
		Launcher.launchViewer();
		ViewerController.instance.setImage(files.toArray(new File[0]), 0);
	}
	
	@FXML
	public void menuExit()
	{
		Platform.exit();
	}
	
	@FXML
	public void about()
	{
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
	}
	
	@FXML
	public void btnDirClick()
	{
		main.setLeft(leftPane);
	}
	
	@FXML
	public void btnFavoritesClick()
	{
		main.setLeft(null);
		//TODO 收藏夹
	}
	
	@FXML
	public void btnBackClick()
	{
		setPath(new File(pathBox.getText()).getParent());
	}
	
	
	@FXML
	public void btnLikeitClick()
	{
		//TODO 收藏
	}
	
	@FXML
	public void btnDeleteClick()
	{
		if(imagePane.getSelectedImage().size() == 0) return;
		else
		{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("删除");
			alert.setHeaderText("删除文件");
			alert.setContentText("是否删除选中的文件?");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				List<File> files = new ArrayList<>();
				for(var img : imagePane.getSelectedImage())
				{
					files.add(img.getImageFile().getRawFile());
				}
				imagePane.clearSelect();
				for(var file : files)
				{
					if(file.delete())
					{
						System.out.println("删除成功");
					}
					else
					{
						System.out.println("删除失败");
					}
				}
				imagePane.refresh();
			}
		}
	}
	
	@FXML
	public void btnCutClick()
	{
	
	}
	
	@FXML
	public void btnCopyClick()
	{
	
	}
	
	@FXML
	public void btnPasteClick()
	{
	
	}
	
	@FXML
	public void btnRenameClick()
	{
	
	}
	
	@FXML
	public void btnPropertyClick()
	{
		//TODO 打开属性窗口
	}
	
	@FXML
	public void btnPlayClick()
	{
	
	}
	
	@FXML
	public void btnRefreshClick()
	{
		imagePane.refresh();
	}
	
}
