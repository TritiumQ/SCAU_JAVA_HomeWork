package MIKU.fin.components;

import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FontUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


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
		this.thumbnail = new ImageView(new Image(imageFile.getImageUri(),200,200,true,true,true));
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
	
	public void setSize(double size)
	{
		this.setMaxSize(size+50, size+50);
		this.setMinSize(size+50, size+50);
		this.thumbnail.setFitHeight(size);
		this.thumbnail.setFitWidth(size);
	}
	
	private void actionInit()
	{
		this.setOnMouseClicked((MouseEvent event) -> {  //为了保证方法调用整洁, 不允许调用自身的setSelected()方法
			if(event.getButton() == MouseButton.PRIMARY )
			{
				ImageFlowPane parent = this.getParent() instanceof ImageFlowPane ? (ImageFlowPane)this.getParent() : null;
				if(event.getClickCount() == 1)
				{
					if(parent != null)
					{
						if(event.isShiftDown())    //shift多选
						{
							if(parent.getSelectedImage().size() == 0)
							{
								parent.addSelect(this);
							}
							else
							{
								int startIndex = parent.getChildren().indexOf(parent.getSelectedImage().get(parent.getSelectedImage().size()-1));
								int endIndex = parent.getChildren().indexOf(this);
								if(startIndex > endIndex)
								{
									int temp = startIndex;
									startIndex = endIndex;
									endIndex = temp;
								}
								parent.clearSelect();
								for(int i = startIndex; i <= endIndex; i++)
								{
									parent.addSelect((ImageThumbnailPane)parent.getChildren().get(i));
								}
							}
						}
						else if(event.isControlDown())
						{
							if(isSelected)
							{
								parent.removeSelect(this);
							}
							else
							{
								parent.addSelect(this);
							}
						}
						else
						{
							parent.clearSelect();
							parent.addSelect(this);
						}
					}
					else throw new RuntimeException("ImageThumbnailPane's parent is not ImageFlowPane");
				}
				else if(event.getClickCount() == 2)
				{
					if (parent != null) {
						try {
							parent.openAll(this.imageFile);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
			
		});
	}
	
	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
		if(isSelected)
		{
			this.setStyle("-fx-background-color: #c8eaf5");
		}
		else
		{
			this.setStyle("-fx-background-color: transparent");
		}
	}
	
	public ImageFile getImageFile() {
		return imageFile;
	}
	
	public Image getThumbnail() {
		return thumbnail.getImage();
	}
	
}
