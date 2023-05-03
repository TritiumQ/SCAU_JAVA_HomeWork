package MIKU.fin.controllers;

import MIKU.fin.components.MultipleTextInputDialog;
import MIKU.fin.module.ImageFile;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

public class SlideController implements Initializable
{
	//singleton
	public static SlideController instance = null;
	
	@FXML
	private StackPane pane;
	@FXML
	private Button btnPlayOrStop, btnSetup, btnFullScreen;
	
	private ImageView currentImage;
	private List<Image> imageList;
	private double timeGap = 1;
	private int currentIdx = 0;
	private boolean isPlaying = false;
	private boolean isCycleINF = false;
	private int cycleCount = 0;
	
	private Timeline timeline;
	private FadeTransition fadeIn;
	private FadeTransition fadeOut;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		instance = this;
		
		currentImage = new ImageView();
		currentImage.setPreserveRatio(true);
		currentImage.setCache(false);
		
		pane.getChildren().add(currentImage);
		
		fadeOut = new FadeTransition(Duration.seconds(0.8), currentImage);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		
		fadeIn = new FadeTransition(Duration.seconds(0.8), currentImage);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		
		timeline = new Timeline();
		timeline.setOnFinished(event -> {
			refresh(0);
			pause();
		});
		
		imageList = new LinkedList<>();
		
		currentImage.setImage(null);
		
		setup(1, 1);
		
		pane.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case ESCAPE-> {
					//System.out.println("退出");
					Platform.exit();
				}
				case SPACE -> {
					if (timeline.getStatus() == Animation.Status.PAUSED) {
						timeline.play();
					} else {
						timeline.pause();
					}
				}
			}
		});
	}
	
	
	private void updateImage()
	{
		if(currentImage.getImage() != null)
		{
			currentImage.fitWidthProperty().bind(pane.widthProperty());
			currentImage.fitHeightProperty().bind(pane.heightProperty());
		}
	}
	
	@FXML
	public void onPlayOrStop()
	{
		if(isPlaying)
		{
			pause();
		}
		else
		{
			play();
		}
	}
	
	@FXML
	public void onSetup()
	{
		pause();
		MultipleTextInputDialog dialog = new MultipleTextInputDialog(2, new String[]{"时间间隔", "循环次数(0为无限循环)"});
		dialog.setTitle("设置播放");
		dialog.setHeaderText("播放设置");
		Optional<String[]> result = dialog.showAndWait();
		if(result.isPresent())
		{
			if(result.get()[0].isEmpty() || result.get()[1].isEmpty()) return;
			double timeGap = Double.parseDouble(result.get()[0]);
			int cycleCount = Integer.parseInt(result.get()[1]);
			setup(timeGap, cycleCount);
		}
	}
	
	@FXML
	public void onFullScreen()
	{
//		boolean isFullScreen = ((Stage) pane.getScene().getWindow()).isFullScreen();
//		if(isFullScreen)
//		{
//			((ImageView)btnFullScreen.getGraphic()).setImage(new Image(getClass().getResource("/Icon/Grey/fullscreen.png").toExternalForm()));
//		}
//		else
//		{
//			((ImageView)btnFullScreen.getGraphic()).setImage(new Image(getClass().getResource("/Icon/Grey/fullscreen-exit.png").toExternalForm()));
//		}
//		((Stage) pane.getScene().getWindow()).setFullScreen(!isFullScreen);
//		updateImage();
		((Stage) pane.getScene().getWindow()).close();
	}
	
	public void refresh(int idx)
	{
		currentImage.setImage(imageList.get(idx));
		updateImage();
	}
	public void setImageList(File[] imageFiles, int idx)
	{
		imageList.clear();
		for(File file : imageFiles)
		{
			imageList.add(new Image(file.toURI().toString()));
		}
		refresh(idx);
	}
	
	public void setImageList(Image[] imageFiles, int idx)
	{
		imageList.clear();
		imageList.addAll(Arrays.asList(imageFiles));
		refresh(idx);
	}
	
	public void setImageList(ImageFile[] imageFiles, int idx)
	{
		imageList.clear();
		for(ImageFile file : imageFiles)
		{
			imageList.add(file.genImage());
		}
		refresh(idx);
	}
	
	public void play()
	{
		isPlaying = true;
		((ImageView)btnPlayOrStop.getGraphic()).setImage(new Image(getClass().getResource("/Icon/Grey/timeout.png").toExternalForm()));
		timeline.play();
	}
	
	public void pause()
	{
		isPlaying = false;
		((ImageView)btnPlayOrStop.getGraphic()).setImage(new Image(getClass().getResource("/Icon/Grey/play-circle.png").toExternalForm()));
		timeline.pause();
	}
	
	public void replay()
	{
		pause();
		currentIdx = 0;
		refresh(currentIdx);
		play();
	}
	
	public void setup(double timeGap, int cycleCount)
	{
		this.timeGap = timeGap;
		this.cycleCount = cycleCount;
		isCycleINF = cycleCount == 0;
		timeline.getKeyFrames().clear();
		for(int i = 0; i < imageList.size(); i++)
		{
			int finalI = i;
			timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeGap * i), event -> {
				fadeOut.play();
				currentIdx = finalI;
				currentImage.setImage(imageList.get(finalI));
				currentImage.setOpacity(0);
				updateImage();
				fadeIn.play();
			}));
		}
		if(isCycleINF)
		{
			timeline.setCycleCount(Timeline.INDEFINITE);
		}
		else
		{
			timeline.setCycleCount(cycleCount);
		}
	}
	
	public ImageView getCurrentImage()
	{
		return currentImage;
	}
}
