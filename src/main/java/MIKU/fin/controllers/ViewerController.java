package MIKU.fin.controllers;

import MIKU.fin.Launcher;
import MIKU.fin.components.MultipleTextInputDialog;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 图片查看器控制器<p>
 * 用于显示图片, 并提供缩放, 移动等功能<p>
 */
public class ViewerController implements Initializable
{
	//singleton
	public static ViewerController instance = null;
	public static int MIN_SCALE = 10;
	public static int MAX_SCALE = 1000;
	public static int MARGIN = 5;
	private int currentScale = 100;
	private Point2D mousePos;
	@FXML
	private ImageView currentImage;
	private List<ImageFile> imageList;
	private int currentIdx = 0;
	@FXML
	private StackPane imageBox;
	@FXML
	private Button btnFullScreen;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		instance = this;
		
		imageBox.setOnMouseClicked(e ->{
			if(e.getClickCount() == 2)
			{
				recoverImage();
			}
		});
		
		imageList = new LinkedList<>();
	}
	
	/**
	 * 加载图片, 减少卡顿
	 */
	private void loadImage(int idx)
	{
		if(idx >= 0 && idx < imageList.size())
		{
			currentImage.setImage(imageList.get(idx).genImage());
			currentIdx = idx;
		}
		recoverImage();
	}
	
	/**
	 * 更新图片, 适应窗口大小
	 */
	private void updateImage()
	{
		if(currentImage.getImage() != null)
		{
			updateTitle();
			double width = currentImage.getImage().getWidth();
			double height = currentImage.getImage().getHeight();
			if(width > imageBox.getWidth() - MARGIN || height > imageBox.getHeight() - MARGIN)
			{
				currentImage.fitWidthProperty().bind(imageBox.widthProperty().subtract(MARGIN));
				currentImage.fitHeightProperty().bind(imageBox.heightProperty().subtract(MARGIN));
			}
			else
			{
				currentImage.fitWidthProperty().bind(imageBox.widthProperty());
				currentImage.fitHeightProperty().bind(imageBox.heightProperty());
			}
		}
	}
	
	/**
	 * 更新窗口标题
	 */
	private void updateTitle()
	{
		if(currentImage.getImage() != null)
		{
			Stage stage = (Stage) currentImage.getScene().getWindow();
			String imageName = FileUtil.getImageName(currentImage.getImage());
			String imageSize = FileUtil.genFileSize(imageList.get(currentIdx).getRawFile().length());
			int width = (int) currentImage.getImage().getWidth();
			int height = (int) currentImage.getImage().getHeight();
			String title = String.format("%s - %s(%s, %dx%dpx) - 共有%d张图片, 当前为第%d张图片",
					Resource.PROGRAM_NAME,
					imageName,
					imageSize,
					width,
					height,
					imageList.size(),
					currentIdx + 1);
			
			stage.setTitle(title);
		}
	}
	
	/**
	 * 检测是否需要显示第一张和最后一张提示
	 * @param currentIndex
	 */
	private void imageIndexTips(int currentIndex)
	{
		Stage stage = (Stage) currentImage.getScene().getWindow();
		if (currentIndex == imageList.size() - 1) {
			Notifications.create()
					.text("已经是最后一张!")
					.hideAfter(Duration.seconds(1.5))
					.position(Pos.BOTTOM_CENTER)
					.owner(stage)
					.darkStyle()
					.show();
		} else if (currentIndex == 0) {
			Notifications.create()
					.text("已经是第一张!")
					.hideAfter(Duration.seconds(1.5))
					.position(Pos.BOTTOM_CENTER)
					.owner(stage)
					.show();
		}
	}
	
	/**
	 * 恢复图片大小
	 */
	public void recoverImage()
	{
		currentScale = 100;
		currentImage.getTransforms().clear();
		currentImage.setRotate(0);
		updateImage();
	}
	
	/**
	 * 获取当前图片
	 * @return
	 */
	public ImageView getCurrentImage() {
		return currentImage;
	}
	
	/**
	 * 设置指定图片列表
	 * @param imageList 图片列表
	 */
	public void setImage(ImageFile[] imageList, int idx)
	{
		this.imageList.clear();
		this.imageList.addAll(Arrays.asList(imageList));
		loadImage(idx);
	}
	
	/**
	 * 设置指定图片列表
	 * @param files
	 * @param idx
	 */
	public void setImage(File[] files, int idx)
	{
		this.imageList.clear();
		for(File file : files)
		{
			this.imageList.add(new ImageFile(file));
		}
		loadImage(idx);
	}
	
	/**
	 * 上一张图片
	 */
	@FXML
	private void toPrevious()
	{
		if(currentIdx > 0)
		{
			loadImage(currentIdx - 1);
		}
		imageIndexTips(currentIdx);
		recoverImage();
	}
	
	/**
	 * 下一张图片
	 */
	@FXML
	private void toNext()
	{
		if(currentIdx < imageList.size() - 1)
		{
			loadImage(currentIdx + 1);
		}
		recoverImage();
	}
	
	/**
	 * 放大图片
	 */
	@FXML
	private  void zoomIn()
	{
		if (currentScale < MAX_SCALE)
		{
			currentScale += 10;
			currentImage.getTransforms()
					.add(new Scale(1.1, 1.1, currentImage.getFitWidth() / 2, currentImage.getFitHeight() / 2));
		}
	}
	
	/**
	 * 放大图片
	 * @param pivotX
	 * @param pivotY
	 */
	private void zoomIn(double pivotX, double pivotY)
	{
		if (currentScale < MAX_SCALE) {
			currentScale += 10;
			currentImage.getTransforms()
					.add(new Scale(1.1, 1.1, pivotX, pivotY));
		}
	}
	
	/**
	 * 缩小图片
	 */
	@FXML
	private void zoomOut()
	{
		if (currentScale > MIN_SCALE)
		{
			currentScale -= 10;
			currentImage.getTransforms()
					.add(new Scale(0.9, 0.9, currentImage.getFitWidth() / 2, currentImage.getFitHeight() / 2));
		}
	}
	
	/**
	 * 缩小图片
	 * @param pivotX
	 * @param pivotY
	 */
	private void zoomOut(double pivotX, double pivotY) {
		if (currentScale > MIN_SCALE) {
			currentScale -= 10;
			currentImage.getTransforms()
					.add(new Scale(0.9, 0.9, pivotX, pivotY));
		}
	}
	
	/**
	 * 旋转图片
	 */
	@FXML
	private  void rotateLeft()
	{
		currentImage.setRotate(currentImage.getRotate() - 90);
	}
	
	/**
	 * 旋转图片
	 */
	@FXML
	public void rotateRight()
	{
		currentImage.setRotate(currentImage.getRotate() + 90);
	}
	
	/**
	 * 播放幻灯片
	 * @throws IOException
	 */
	@FXML
	private  void slideShow() throws IOException
	{
		MultipleTextInputDialog dialog = new MultipleTextInputDialog(
				2,
				new String[]{"时间间隔", "循环次数(0为无限循环)"});
		dialog.setTitle("设置播放");
		dialog.setHeaderText("播放设置");
		Optional<String[]> result = dialog.showAndWait();
		if(result.isPresent()) {
			double timeGap = Double.parseDouble(result.get()[0]);
			int cycleCount = Integer.parseInt(result.get()[1]);
			Launcher.launchSlide();
			SlideController.instance.setImageList(imageList.toArray(new ImageFile[0]), 0);
			SlideController.instance.setup(timeGap, cycleCount);
		}
	}
	
	/**
	 * 全屏显示
	 */
	@FXML
	private  void fullScreen()
	{
		boolean isFullScreen = ((Stage) currentImage.getScene().getWindow()).isFullScreen();
		if(isFullScreen)
		{
			((ImageView)btnFullScreen.getGraphic()).setImage(new Image(getClass().getResource("/Icon/Grey/fullscreen.png").toExternalForm()));
		}
		else
		{
			((ImageView)btnFullScreen.getGraphic()).setImage(new Image(getClass().getResource("/Icon/Grey/fullscreen-exit.png").toExternalForm()));
		}
		((Stage) currentImage.getScene().getWindow()).setFullScreen(!isFullScreen);
	}
	
	/**
	 * 鼠标滚轮缩放
	 * @param e
	 */
	@FXML
	private  void scrollResize(ScrollEvent e) {
		if(e.getDeltaY() > 0) {
			zoomIn(e.getX(), e.getY());
		} else {
			zoomOut(e.getX(), e.getY());
		}
	}
	
	@FXML
	private void onMousePressed(MouseEvent e) {
		currentImage.setCursor(Cursor.CLOSED_HAND);
		mousePos = new Point2D(e.getX(), e.getY());
	}
	
	@FXML
	private void onMouseReleased(MouseEvent e) {
		currentImage.setCursor(Cursor.OPEN_HAND);
	}
	
	/**
	 * 鼠标拖动图片
	 * @param e
	 */
	@FXML
	private void onMouseDragged(MouseEvent e)
	{
		double offsetX = e.getX() - mousePos.getX();
		double offsetY = e.getY() - mousePos.getY();
		currentImage.getTransforms().add(new Translate(offsetX, offsetY));
	}
}
