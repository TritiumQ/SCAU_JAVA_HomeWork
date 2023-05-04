package MIKU.fin.controllers;

import MIKU.fin.Launcher;
import MIKU.fin.components.*;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FavoritesUtils;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.FontUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
	private AnchorPane centerPane;
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
	@FXML
	private ComboBox<String> sortMethod,sortOrder;
	
	private boolean isDir;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		instance = this;
		
		informationPane = new InformationPane();
		bottom.getChildren().add(informationPane);
		bottom.getChildren().get(0).toFront();
		
		isDir = true;
		
		initZoom();
		initFileTree();
		initImagePane();
		initContextMenu();
		initChoiceMenu();
	}
	
	private void initChoiceMenu()
	{
		sortMethod.setItems(FXCollections.observableArrayList("名称排序", "大小排序", "日期排序"));
		sortMethod.getSelectionModel().select(0);
		
		sortOrder.setItems(FXCollections.observableArrayList("升序", "降序"));
		sortOrder.getSelectionModel().select(0);
		
		imagePane.setSort(0, 0);
		
		sortMethod.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			imagePane.setSort(sortMethod.getSelectionModel().getSelectedIndex(), sortOrder.getSelectionModel().getSelectedIndex());
		});
		sortOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			imagePane.setSort(sortMethod.getSelectionModel().getSelectedIndex(),sortOrder.getSelectionModel().getSelectedIndex());
		});
		
	}
	
	private void initContextMenu()
	{
		MenuItem refresh = new MenuItem("刷新");
		refresh.setOnAction(event -> {
			imagePane.refresh();
		});
		
		MenuItem like = new MenuItem("收藏");
		like.setOnAction(event -> {
			btnLikeClick();
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
		zoom.setMin(100);
		zoom.setMax(300);
		zoom.setBlockIncrement(10);
		zoom.valueProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(()->{
				imagePane.setSize(newValue.doubleValue());
			});
		});
	}
	
	private void initFileTree() {
		systemFileTree.setRoot(new SystemFileTreeItem(FileUtil.FILE_ROOT));
		systemFileTree.setShowRoot(false);
		systemFileTree.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) ->{
					if(newValue instanceof SystemFileTreeItem)
					{
						SystemFileTreeItem item = (SystemFileTreeItem) newValue;
						setPath(item.getFile().getAbsolutePath());
					}
		});
	}
	private double selectX, selectY;
	Rectangle selectArea;
	private void initImagePane()
	{
		selectArea = new Rectangle();
		selectArea.setFill(Color.rgb(0, 0, 0, 0.1));
		selectArea.setVisible(false);
		
		imagePane = new ImageFlowPane(null);
		
		imagePane.setOnMousePressed(event -> {
			selectX = event.getX();
			selectY = event.getY();
			imagePaneMenu.hide();
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && !event.isControlDown())
			{
				if(imagePane.equals(event.getPickResult().getIntersectedNode()))
				{
					imagePane.clearSelect();
				}
			}
		});
		imagePane.setOnMouseDragged(event -> {
			double finalX = Math.min(selectX, event.getX());
			double finalY = Math.min(selectY, event.getY());
			double finalWidth = Math.abs(event.getX() - selectX);
			double finalHeight = Math.abs(event.getY() - selectY);
			selectArea.setX(finalX);
			selectArea.setY(finalY);
			selectArea.setWidth(finalWidth);
			selectArea.setHeight(finalHeight);
			selectArea.setVisible(true);
			
			areaSelect();
			
			if(event.getScreenY() < 100)
			{
				center.setVvalue(center.getVvalue() + (event.getSceneY() - 100) / imagePane.getHeight() / 10);
			}
			if(event.getScreenY() > 80 + center.getHeight())
			{
				center.setVvalue(center.getVvalue() + (event.getSceneY() - center.getHeight() - 80) / imagePane.getHeight() / 10);
			}
			
		});
		imagePane.setOnMouseReleased(event -> {
			selectArea.setVisible(false);
		});
		
		
		centerPane = new AnchorPane();
		centerPane.getChildren().addAll(imagePane,selectArea);
		
		center.setContent(centerPane);
		center.setFitToWidth(true);
		center.heightProperty().addListener((observable, oldValue, newValue) -> {
			imagePane.setPrefHeight(newValue.doubleValue());
		});
		center.widthProperty().addListener((observable, oldValue, newValue) -> {
			imagePane.setPrefWidth(newValue.doubleValue());
		});
		
		//setPath("E:\\Pictures\\Pic");
		
	}
	
	private void areaSelect()
	{
		imagePane.clearSelect();
		for (var pane: imagePane.getChildren()) {
			if(selectArea.intersects(pane.getBoundsInParent()))
			{
				imagePane.addSelect((ImageThumbnailPane) pane);
			}
		}
	}
	
	public void setPath(String path)
	{
		imagePane.setDir(new File(path), zoom.getValue());
		pathBox.setText(path);
	}
	
	public void setImageInf(int count, long size, int selectedCounter, long selectSize)
	{
		informationPane.update(count, size ,selectedCounter, selectSize);
	}
	
	@FXML
	private void goPath()
	{
		if(!pathBox.getText().equals(""))
		{
			File f = new File(pathBox.getText());
			if(f.exists() && f.isDirectory())
			{
				imagePane.setDir(f, zoom.getValue());
			}
		}
	}
	@FXML
	private void serach()
	{
		String text = searchBox.getText();
		if(!text.equals(""))
		{
			for (var pane: imagePane.getChildren()) {
				//System.out.println(((ImageThumbnailPane)pane).getImageFile().getImageName());
				if(((ImageThumbnailPane)pane).getImageFile().getImageName().contains(text))
				{
					imagePane.clearSelect();
					imagePane.addSelect((ImageThumbnailPane) pane);
					center.setVvalue(((ImageThumbnailPane) pane).getLayoutY() / imagePane.getHeight());
					return;
				}
			}
		}
	}
	
	@FXML
	public void menuOpen() throws IOException
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择图片");
		fileChooser.getExtensionFilters().addAll(
				//new FileChooser.ExtensionFilter("所有图片文件", "*.*"),
				new FileChooser.ExtensionFilter("图片文件",
						"*.jpg", "*.png", "*.bmp", "*.gif", "*.jpeg", ".webp")
		);
		var files = fileChooser.showOpenMultipleDialog(main.getScene().getWindow());
		if(files!=null)
		{
			Launcher.launchViewer();
			ViewerController.instance.setImage(files.toArray(new File[0]), 0);
		}
	}
	
	@FXML
	public void menuExit()
	{
		Platform.exit();
	}
	
	@FXML
	public void about() throws IOException
	{
		Stage aboutStage = new Stage();
		VBox vBox = new VBox();
		Label l1 = new Label("项目作者: 周子川, 洪佳杰, 阳华松");
		l1.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
		l1.setAlignment(Pos.CENTER);
		Label l2 = new Label("华南农业大学OOP课程实习作业");
		l2.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI, 15));
		l2.setAlignment(Pos.CENTER);
		
		vBox.getChildren().addAll(l1,l2);
		Scene scn = new Scene(vBox, 320, 120);
		aboutStage.setScene(scn);
		aboutStage.setTitle("关于");
		aboutStage.show();
	}
	private String currentPath = "";
	@FXML
	public void btnDirClick()
	{
		isDir = true;
		main.setLeft(leftPane);
		if(!currentPath.equals(""))
		{
			setPath(currentPath);
		}
		else
		{
			setPath("C:\\");
		}
		((ImageView)btnDir.getGraphic()).setImage(
				new Image(getClass().getResource("/Icon/Grey/folder-open-fill.png").toExternalForm())
		);
		((ImageView)btnFavorites.getGraphic()).setImage(
				new Image(getClass().getResource("/Icon/Grey/book.png").toExternalForm())
		);
	}
	
	@FXML
	public void btnFavoritesClick() throws IOException, ClassNotFoundException
	{
		isDir = false;
		currentPath = pathBox.getText();
		pathBox.setText("图片收藏夹");
		main.setLeft(null);
		imagePane.setFavorites(zoom.getValue());
		((ImageView)btnDir.getGraphic()).setImage(
				new Image(getClass().getResource("/Icon/Grey/folder.png").toExternalForm())
		);
		((ImageView)btnFavorites.getGraphic()).setImage(
				new Image(getClass().getResource("/Icon/Grey/book-fill.png").toExternalForm())
		);
		
	}
	
	@FXML
	public void btnBackClick()
	{
		File f = new File(pathBox.getText());
		if(f.getParent() != null && isDir)
		{
			setPath(f.getParent());
		}
	}
	
	
	@FXML
	public void btnLikeClick()
	{
		if(imagePane.getSelectedImage().size() != 0)
		{
			List<ImageFile> list = new ArrayList<>();
			for(var img : imagePane.getSelectedImage())
			{
				list.add(img.getImageFile());
			}
			FavoritesUtils.addFavorites(list.toArray(new ImageFile[0]));
			if(!isDir) imagePane.refresh();
		}
	}
	
	@FXML
	public void btnCancelLikeClick()
	{
		if (imagePane.getSelectedImage().size() != 0)
		{
			List<ImageFile> list = new ArrayList<>();
			for(var img : imagePane.getSelectedImage())
			{
				list.add(img.getImageFile());
			}
			FavoritesUtils.removeFavorites(list.toArray(new ImageFile[0]));
			if(!isDir) imagePane.refresh();
		}
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
		if(imagePane.getSelectedImage().size() > 0)
		{
			ImageInfomationDialog infDlg = new ImageInfomationDialog(imagePane.getSelectedImage().get(0).getImageFile());
			Stage dlg = (Stage)infDlg.getDialogPane().getScene().getWindow();
			dlg.getIcons().add(new Image(getClass().getResource(FileUtil.PATH_MAIN_ICON).toExternalForm()));
			infDlg.show();
		}
	}
	
	@FXML
	public void btnPlayClick() throws IOException
	{
		
		MultipleTextInputDialog dialog = new MultipleTextInputDialog(
				2,
				new String[]{"时间间隔", "循环次数(0为无限循环)"});
		dialog.setTitle("设置播放");
		dialog.setHeaderText("播放设置");
		Optional<String[]> result = dialog.showAndWait();
		if(result.isPresent())
		{
			if(result.get()[0].isEmpty() || result.get()[1].isEmpty()) return;
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
