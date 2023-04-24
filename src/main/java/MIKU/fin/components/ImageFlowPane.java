package MIKU.fin.components;

import javafx.scene.layout.FlowPane;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ImageFlowPane extends FlowPane
{
	private final File currentDir;
	private final List<ImageThumbnailPane> imageThumbnails;
	private final List<ImageThumbnailPane> selectedImage;
	
	public ImageFlowPane()
	{
		this.setCache(true);
		currentDir = null;
		imageThumbnails = new LinkedList<>();
		selectedImage = new LinkedList<>();
	}
	
	
	
}
