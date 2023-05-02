package MIKU.fin.controllers;

import MIKU.fin.components.*;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.FontUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
	private Button btnLikeit, btnDelete, btnCut, btnCopy, btnPaste, btnRename, btnPlay, btnInf;
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
		refresh.setOnAction(event -> {
			imagePane.refresh();
		});
		
		MenuItem like = new MenuItem("收藏");
		like.setOnAction(event -> {
			btnLikeitClick();
		});
		
		MenuItem play = new MenuItem("播放幻灯片");
		play.setOnAction(event -> {
			try {
				btnPlayClick();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
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
			btnInfClick();
		});
		
		imagePaneMenu = new ContextMenu();
		imagePaneMenu.getItems().addAll(
				open,play,new SeparatorMenuItem(),
				refresh,new SeparatorMenuItem(),
				like,new SeparatorMenuItem(),
				delete, cut, copy, paste, new SeparatorMenuItem(),
				rename, new SeparatorMenuItem(),
				property);
		
		imagePane.setOnContextMenuRequested(event -> {
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
	//TODO
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
					imagePaneMenu.hide();
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
		Launcher.launchViewer();
		ViewerController.instance.setImage(files.toArray(new File[0]), 0);
	}
	
	@FXML
	public void menuExit()
	{
		Platform.exit();
	}
	
	@FXML
	public void about() throws IOException
	{
//		Stage aboutStage = new Stage();
//		VBox vBox = new VBox();
//		Label l1 = new Label("项目作者: TritiumQ");
//		l1.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
//		Label l2 = new Label("项目地址: ");
//		l2.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
//		Hyperlink hl = new Hyperlink("https://github.com/TritiumQ/SCAU_JAVA_HomeWork.git");
//		hl.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
//		vBox.getChildren().addAll(l1,l2,hl);
//		Scene scn = new Scene(vBox, 320, 120);
//		aboutStage.setScene(scn);
//		aboutStage.setTitle("关于");
//		aboutStage.show();
		Launcher.launchSlide();
		SlideController.instance.play();
		
	}
	
	@FXML
	public void btnDirClick()
	{
		main.setLeft(leftPane);
		//TODO 主目录
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
			Stage dlg = (Stage)alert.getDialogPane().getScene().getWindow();
			dlg.getIcons().add(new Image(getClass().getResource(FileUtil.PATH_MAIN_ICON).toExternalForm()));
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
						System.out.println(file.toPath()+" 删除成功");
					}
					else
					{
						System.out.println(file.toPath()+" 删除失败");
					}
				}
				imagePane.refresh();
			}
		}
	}
	
	private File operateDir = null;
	private List<File> prepareToCut = new ArrayList<>();
	private List<File> prepareToCopy = new ArrayList<>();
	@FXML
	public void btnCutClick()
	{
		if(imagePane.getSelectedImage().size() == 0) return;
		else
		{
			prepareToCopy.clear();
			prepareToCut.clear();
			operateDir = imagePane.getCurrentDir();
			for(var img : imagePane.getSelectedImage())
			{
				prepareToCut.add(img.getImageFile().getRawFile());
			}
			//imagePane.clearSelect();
		}
	}
	
	@FXML
	public void btnCopyClick()
	{
		if(imagePane.getSelectedImage().size() == 0) return;
		else
		{
			prepareToCut.clear();
			prepareToCopy.clear();
			operateDir = imagePane.getCurrentDir();
			for(var img : imagePane.getSelectedImage())
			{
				prepareToCopy.add(img.getImageFile().getRawFile());
			}
			//imagePane.clearSelect();
		}
	}
	
	@FXML
	public void btnPasteClick()
	{
		try
		{
			if(prepareToCut.size() != 0)
			{
				if (!imagePane.getCurrentDir().equals(operateDir))
				{
					for(var file : prepareToCopy)
					{
						FileUtil.copyFileToDirectory(file, imagePane.getCurrentDir());
					}
					for (var file : prepareToCut)
					{
						file.delete();
					}
				}
				prepareToCut.clear();
				operateDir = null;
				imagePane.refresh();
			}
			else if(prepareToCopy.size() != 0)
			{
				if (imagePane.getCurrentDir().equals(operateDir))
				{
					for(var file : prepareToCopy)
					{
						FileUtil.copyFileToSameDirectory(file, imagePane.getCurrentDir());
					}
				}
				else
				{
					for(var file : prepareToCopy)
					{
						FileUtil.copyFileToDirectory(file, imagePane.getCurrentDir());
					}
				}
				imagePane.refresh();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnRenameClick()
	{
		try
		{
			if(imagePane.getSelectedImage().size() == 0) return;
			else if(imagePane.getSelectedImage().size() == 1)
			{
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("重命名");
				dialog.setHeaderText("重命名");
				dialog.setContentText("请输入新的文件名:");
				Stage dlg = (Stage)dialog.getDialogPane().getScene().getWindow();
				dlg.getIcons().add(new Image(getClass().getResource(FileUtil.PATH_MAIN_ICON).toExternalForm()));
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent())
				{
					for(var img : imagePane.getSelectedImage())
					{
						FileUtil.renameFile(img.getImageFile().getRawFile(), result.get());
					}
					imagePane.refresh();
				}
			}
			else
			{
				MultipleTextInputDialog dialog = new MultipleTextInputDialog(3,new String[]{"请输入文件名前缀:","请输入文件编号位数:","请输入文件编号起始值:"});
				dialog.setTitle("重命名");
				dialog.setHeaderText("批量重命名");
				Stage dlg = (Stage)dialog.getDialogPane().getScene().getWindow();
				dlg.getIcons().add(new Image(getClass().getResource(FileUtil.PATH_MAIN_ICON).toExternalForm()));
				Optional<String[]> result = dialog.showAndWait();
				System.out.println(result.isPresent());
				if(result.isPresent())
				{
					String prefix = result.get()[0];
					int num = Integer.parseInt(result.get()[1]);
					int start = Integer.parseInt(result.get()[2]);
					System.out.println(prefix+" "+num+" "+start);
					for(var img : imagePane.getSelectedImage())
					{
						FileUtil.renameFile(img.getImageFile().getRawFile(), String.format("%s-%0"+num+"d",prefix,start++));
					}
					imagePane.refresh();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnInfClick()
	{
		//TODO 打开属性窗口
	}
	
	@FXML
	public void btnPlayClick() throws IOException
	{
		
		MultipleTextInputDialog dialog = new MultipleTextInputDialog(2, new String[]{"时间间隔", "循环次数(0为无限循环)"});
		dialog.setTitle("设置播放");
		dialog.setHeaderText("播放设置");
		Optional<String[]> result = dialog.showAndWait();
		if(result.isPresent())
		{
			double timeGap = Double.parseDouble(result.get()[0]);
			int cycleCount = Integer.parseInt(result.get()[1]);
			Launcher.launchSlide();
			List<ImageFile> list = new ArrayList<>();
			if(imagePane.getSelectedImage().size() == 0)
			{
				imagePane.getChildren().forEach((node) -> {
					list.add(((ImageThumbnailPane)node).getImageFile());
				});
			}
			else
			{
				for(var img : imagePane.getSelectedImage())
				{
					list.add(img.getImageFile());
				}
			}
			SlideController.instance.setImageList(list.toArray(new ImageFile[0]), 0);
			SlideController.instance.setup(timeGap, cycleCount);
		}
		
	}
	
	@FXML
	public void btnRefreshClick()
	{
		imagePane.refresh();
	}
	
}
