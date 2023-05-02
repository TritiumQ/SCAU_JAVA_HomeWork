package MIKU.fin.components;

import MIKU.fin.controllers.Launcher;
import MIKU.fin.controllers.MainController;
import MIKU.fin.controllers.ViewerController;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片缩略图流布局
 */
public class ImageFlowPane extends FlowPane
{
	private File currentDir;
	private double currentSize;
	private final List<ImageThumbnailPane> selectedImage;
	
	public ImageFlowPane(File dir)
	{
		this.setCache(true);
		this.setVgap(5);
		this.setHgap(5);
		this.currentDir = dir;
		this.currentSize = 100;
		selectedImage = new LinkedList<>();
		update(dir, 100);
	}
	
	public List<ImageThumbnailPane> getSelectedImage()
	{
		return selectedImage;
	}
	
	public void clearSelect()
	{
		for(var image : selectedImage)
		{
			image.setSelected(false);
		}
		selectedImage.clear();
		setImageInfo();
	}
	
	public void removeSelect(ImageThumbnailPane imageThumbnailPane)
	{
		selectedImage.remove(imageThumbnailPane);
		imageThumbnailPane.setSelected(false);
		setImageInfo();
	}
	
	public void addSelect(ImageThumbnailPane imageThumbnailPane)
	{
		selectedImage.add(imageThumbnailPane);
		imageThumbnailPane.setSelected(true);
		setImageInfo();
	}
	
	
	public File getCurrentDir()
	{
		return currentDir;
	}
	
	private void setImageInfo()
	{
		long selectedSize = 0;
		for(var image : selectedImage)
		{
			selectedSize += image.getImageFile().getRawFile().length();
		}
		MainController.instance.setImageInf(this.getChildren().size() ,selectedImage.size() , selectedSize);
	}
	
	public void refresh()
	{
		clearSelect();
		this.getChildren().clear();
		File[] files = currentDir.listFiles(FileUtil::isSupportImageFormat);
		if(files != null)
		{
			for(File file : files)
			{
				ImageFile imageFile = new ImageFile(file);
				ImageThumbnailPane imageThumbnailPane = new ImageThumbnailPane(imageFile,currentSize);
				this.getChildren().add(imageThumbnailPane);
			}
			setImageInfo();
		}
	}
	
	/**
	 * 更新图片缩略图
	 * @param dir 图片所在文件夹
	 * @param size 缩略图大小
	 * @return 是否更新成功
	 */
	public boolean update(File dir, double size)
	{
		if(dir != null && dir.isDirectory())
		{
			currentDir = dir;
			currentSize = size;
			clearSelect();
			this.getChildren().clear();
			File[] files = dir.listFiles(FileUtil::isSupportImageFormat);
			if(files != null)
			{
				for(File file : files)
				{
					ImageFile imageFile = new ImageFile(file);
					ImageThumbnailPane imageThumbnailPane = new ImageThumbnailPane(imageFile,size);
					this.getChildren().add(imageThumbnailPane);
				}
				setImageInfo();
				return true;
			}
			else return false;
		}
		return false;
	}
	
	/**
	 * 更新缩略图大小
	 * @param size 缩略图大小
	 */
	public void update(double size)
	{
		currentSize = size;
		for(var child : this.getChildren())
		{
			((ImageThumbnailPane)child).setSize(size);
		}
	}
	
	public void openSelected(ImageFile current) throws IOException
	{
		if (selectedImage.size() == 0) return;
		else
		{
			Launcher.launchViewer();
			List<ImageFile> list = new ArrayList<>();
			for (var image : selectedImage) {
				list.add(image.getImageFile());
			}
			int idx = current == null ? 0 : list.indexOf(current);
			//System.out.println(idx);
			ViewerController.instance.setImage(list.toArray(new ImageFile[0]), idx);
			
		}
	}
	
	public  void openAll(ImageFile current) throws IOException
	{
		Launcher.launchViewer();
		List<ImageFile> list = new ArrayList<>();
		for(var image : this.getChildren())
		{
			list.add(((ImageThumbnailPane)image).getImageFile());
		}
		int idx = current == null ? 0 : list.indexOf(current);
		//System.out.println(idx);
		ViewerController.instance.setImage(list.toArray(new ImageFile[0]), idx);
	}
	
}
