package MIKU.fin.components;

import MIKU.fin.moduel.ImageFile;
import MIKU.fin.utils.FontUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class ImageThumbnailPane extends BorderPane
{
	private static final int MAX_TEXT_LENGTH = 20;
	
	private final ImageView thumbnail;
	private final ImageFile imageFile;
	private final HBox thumbnailBox, imageNameBox;
	private Label imageName;
	private boolean isSelected = false;
	
	public ImageThumbnailPane(ImageFile imageFile, double size)
	{
		actionInit();
		
		this.imageFile = imageFile;
		this.thumbnail = new ImageView(new Image(imageFile.getImageURL(),100,100,true,true,true));
		this.thumbnail.setPreserveRatio(true);
		this.thumbnail.setFitHeight(size);
		this.thumbnail.setFitWidth(size);
		
		this.imageName = new Label(imageFile.getImageName());
		if(imageName.getText().length() > MAX_TEXT_LENGTH)
		{
			imageName.setText(imageName.getText().substring(0,MAX_TEXT_LENGTH) + "...");
		}
		imageName.setAlignment(Pos.CENTER);
		imageName.setFont(FontUtil.genFont(FontUtil.FONT_YAHEI,13));
		
		thumbnailBox = new HBox(thumbnail);
		thumbnailBox.setAlignment(Pos.CENTER);
		imageNameBox = new HBox(imageName);
		imageNameBox.setAlignment(Pos.CENTER);
		
		this.setMaxSize(size+50, size+50);
		this.setMinSize(size+50, size+50);
		this.setCache(false);
		this.setCenter(thumbnailBox);
		this.setBottom(imageNameBox);
		
		this.getStyleClass().add("image-thumbnail-pane");
		this.setPadding(new Insets(3,3,3,3));
		
		
	}
	
	private void actionInit()
	{
		this.setOnMouseClicked((MouseEvent event) -> {
			if(isSelected)
			{
				setSelected();
			}
			else
			{
				removeSelected();
			}
		});
	}
	
	private void removeSelected() {
		this.setStyle("-fx-background-color: transparent;");
		isSelected = true;
	}
	
	private void setSelected() {
		this.setStyle("-fx-background-color: GREY");
		isSelected = false;
	}
}
