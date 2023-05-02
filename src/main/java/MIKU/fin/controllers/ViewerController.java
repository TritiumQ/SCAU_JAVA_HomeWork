package MIKU.fin.controllers;

import MIKU.fin.components.MultipleTextInputDialog;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
	private List<Image> imageList;
	@FXML
	private StackPane imageBox;
	@FXML
	private Button btnBack, btnNext, btnZoomIn, btnZoomOut, btnRotateLeft, btnRotateRight, btnSlideShow, btnFullScreen;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		instance = this;
		
		imageList = new LinkedList<>();
	}
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
	
	private void updateTitle()
	{
		if(currentImage.getImage() != null)
		{
			Stage stage = (Stage) currentImage.getScene().getWindow();
			String imageName = FileUtil.getImageName(currentImage.getImage());
			String title = String.format("MIKU's ImageViewer - %s - 共有%d张图片, 当前为第%d张图片",
					imageName,
					imageList.size(),
					imageList.indexOf(currentImage.getImage()) + 1);
			
			stage.setTitle(title);
		}
	}
	
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
	
	public void recoverImage()
	{
		currentScale = 100;
		currentImage.getTransforms().clear();
		updateImage();
	}
	
	private void locateImage(int idx)
	{
		recoverImage();
		if(imageList.size() > 0)
		{
			if(idx >= 0 && idx < imageList.size())
			{
				currentImage.setImage(imageList.get(idx));
			}
			else
			{
				currentImage.setImage(imageList.get(0));
			}
		}
		else
		{
			currentImage.setImage(null);
		}
	}
	
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
		for(ImageFile image : imageList)
		{
			this.imageList.add(image.genImage());
		}
		locateImage(idx);
	}
	
	public void setImage(File[] files, int idx)
	{
		this.imageList.clear();
		for(File file : files)
		{
			this.imageList.add(new Image(file.toURI().toString()));
		}
		locateImage(idx);
	}
	
	
	
	/**
	 * 上一张图片
	 */
	@FXML
	private void toPrevious()
	{
		if(imageList.indexOf(currentImage.getImage()) != 0)
		{
			currentImage.setImage(imageList.get(imageList.indexOf(currentImage.getImage()) - 1));
		}
		imageIndexTips(imageList.indexOf(currentImage.getImage()));
		recoverImage();
	}
	
	/**
	 * 下一张图片
	 */
	@FXML
	private void toNext()
	{
		if(imageList.indexOf(currentImage.getImage()) != imageList.size() - 1)
		{
			currentImage.setImage(imageList.get(imageList.indexOf(currentImage.getImage()) + 1));
		}
		imageIndexTips(imageList.indexOf(currentImage.getImage()));
		recoverImage();
	}
	
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
	
	private void zoomIn(double pivotX, double pivotY)
	{
		if (currentScale < MAX_SCALE) {
			currentScale += 10;
			currentImage.getTransforms()
					.add(new Scale(1.1, 1.1, pivotX, pivotY));
		}
	}
	
	@FXML
	private  void zoomOut()
	{
		if (currentScale > MIN_SCALE)
		{
			currentScale -= 10;
			currentImage.getTransforms()
					.add(new Scale(0.9, 0.9, currentImage.getFitWidth() / 2, currentImage.getFitHeight() / 2));
		}
	}
	
	private void zoomOut(double pivotX, double pivotY) {
		if (currentScale > MIN_SCALE) {
			currentScale -= 10;
			currentImage.getTransforms()
					.add(new Scale(0.9, 0.9, pivotX, pivotY));
		}
	}
	
	@FXML
	private  void rotateLeft()
	{
		currentImage.getTransforms()
				.add(new Rotate(-90, currentImage.getFitWidth() / 2, currentImage.getFitHeight() / 2));
	}
	
	@FXML
	public void rotateRight()
	{
		currentImage.getTransforms()
				.add(new Rotate(90, currentImage.getFitWidth() / 2, currentImage.getFitHeight() / 2));
	}
	
	@FXML
	private  void slideShow() throws IOException
	{
		MultipleTextInputDialog dialog = new MultipleTextInputDialog(2, new String[]{"时间间隔", "循环次数(0为无限循环)"});
		dialog.setTitle("设置播放");
		dialog.setHeaderText("播放设置");
		Optional<String[]> result = dialog.showAndWait();
		if(result.isPresent()) {
			double timeGap = Double.parseDouble(result.get()[0]);
			int cycleCount = Integer.parseInt(result.get()[1]);
			Launcher.launchSlide();
			SlideController.instance.setImageList(imageList.toArray(new Image[0]), 0);
			SlideController.instance.setup(timeGap, cycleCount);
		}
	}
	
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
	
	@FXML
	private void onMouseDragged(MouseEvent e)
	{
		double offsetX = e.getX() - mousePos.getX();
		double offsetY = e.getY() - mousePos.getY();
		currentImage.getTransforms().add(new Translate(offsetX, offsetY));
	}
}
