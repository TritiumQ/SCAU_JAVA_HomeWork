package MIKU.fin.controllers;

import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import static MIKU.fin.utils.FileUtil.FILE_ROOT;

public class ViewerController implements Initializable
{
	//singleton
	public static ViewerController instance = null;
	
	
	
	private ImageView currentImage;
	private List<Image> imageList;
	@FXML
	private ScrollPane imageBox;
	@FXML
	private Button btnBack, btnForward, btnZoomIn, btnZoomOut, btnRotateLeft, btnRotateRight, btnSlideShow;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		instance = this;
		
		currentImage = new ImageView();
		currentImage.setPreserveRatio(true);
		currentImage.addEventFilter(ScrollEvent.SCROLL, event -> {
			if(event.isControlDown())
			{
				if(event.getDeltaY() > 0)
				{
					currentImage.setFitWidth(currentImage.getFitWidth() * 1.1);
				}
				else
				{
					currentImage.setFitWidth(currentImage.getFitWidth() / 1.1);
				}
			}
		});
		
		imageBox.setContent(currentImage);
		imageBox.setFitToWidth(true);
		imageBox.setFitToHeight(true);
		
		imageList = new LinkedList<>();
		
		File[] files = FILE_ROOT.listFiles(FileUtil::isSupportImageFormat);
		if (files != null)
		{
			imageList.clear();
			for(File f : files)
			{
				imageList.add(new Image(f.toURI().toString()));
				System.out.println(f.toURI());
			}
			refresh(0);
		}
		
	}
	
	private void refresh(int idx)
	{
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
		refresh(idx);
	}
	
	public void setImage(File[] files, int idx)
	{
		this.imageList.clear();
		for(File file : files)
		{
			this.imageList.add(new Image(file.toURI().toString()));
		}
		refresh(idx);
	}
	
	/**
	 * 上一张图片
	 */
	@FXML
	public void toPrevious()
	{
		if(imageList.indexOf(currentImage.getImage()) != 0)
		{
			currentImage.setImage(imageList.get(imageList.indexOf(currentImage.getImage()) - 1));
		}
	}
	
	/**
	 * 下一张图片
	 */
	@FXML
	public void toNext()
	{
		if(imageList.indexOf(currentImage.getImage()) != imageList.size() - 1)
		{
			currentImage.setImage(imageList.get(imageList.indexOf(currentImage.getImage()) + 1));
		}
	}
}
