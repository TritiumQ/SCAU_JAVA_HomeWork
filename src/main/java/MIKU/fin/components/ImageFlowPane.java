package MIKU.fin.components;

import MIKU.fin.Launcher;
import MIKU.fin.controllers.MainController;
import MIKU.fin.controllers.ViewerController;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FavoritesUtils;
import MIKU.fin.utils.FileUtil;
import javafx.collections.FXCollections;
import javafx.scene.Node;
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
	private double currentScaleSize;
	
	private int sortMethod = 0;
	private int sortOrder = 0;
	
	
	private int totalCount = 0;
	private long totalSize = 0;
	
	private boolean isDir;
	
	private final List<ImageThumbnailPane> selectedImage;
	
	public ImageFlowPane(File dir)
	{
		this.setCache(true);
		this.setVgap(10);
		this.setHgap(10);
		this.currentDir = dir;
		this.currentScaleSize = 100;
		selectedImage = new LinkedList<>();
		setDir(dir, 100);
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
		if(selectedImage.size()!=0)
		{
			for(var image : selectedImage)
			{
				selectedSize += image.getImageFile().getRawFile().length();
			}
		}
		MainController.instance.setImageInf(totalCount , totalSize ,selectedImage.size() , selectedSize);
	}
	
	public void refresh()
	{
		clearSelect();
		if(isDir)
		{
			this.getChildren().clear();
			File[] files = currentDir.listFiles(FileUtil::isSupportImageFormat);
			if(files != null)
			{
				for(File file : files)
				{
					ImageFile imageFile = new ImageFile(file);
					ImageThumbnailPane imageThumbnailPane = new ImageThumbnailPane(imageFile, currentScaleSize);
					this.getChildren().add(imageThumbnailPane);
				}
				totalCount = this.getChildren().size();
				totalSize = 0;
				for(Node node : this.getChildren())
				{
					ImageThumbnailPane imageThumbnailPane = (ImageThumbnailPane) node;
					totalSize += imageThumbnailPane.getImageFile().getRawFile().length();
				}
				setSort(sortMethod, sortOrder);
				setImageInfo();
			}
		}
		else
		{
			setFavorites(currentScaleSize);
			totalCount = this.getChildren().size();
			totalSize = 0;
			for(Node node : this.getChildren())
			{
				ImageThumbnailPane imageThumbnailPane = (ImageThumbnailPane) node;
				totalSize += imageThumbnailPane.getImageFile().getRawFile().length();
			}
			setSort(sortMethod, sortOrder);
			setImageInfo();
		}
	}
	
	/**
	 * 设置显示收藏夹
	 * @param size
	 * @return
	 */
	public boolean setFavorites(double size)
	{
		isDir = false;
		currentDir = null;
		currentScaleSize = size;
		clearSelect();
		this.getChildren().clear();
		ImageFile[] files = FavoritesUtils.getFavoritesAsImageFile();
		for (ImageFile file : files) {
			ImageThumbnailPane imageThumbnailPane = new ImageThumbnailPane(file, size);
			this.getChildren().add(imageThumbnailPane);
		}
		setSort(sortMethod, sortOrder);
		setImageInfo();
		return true;
	}
	
	
	/**
	 * 设置显示文件夹
	 * @param dir 图片所在文件夹
	 * @param size 缩略图大小
	 * @return 是否更新成功
	 */
	public boolean setDir(File dir, double size)
	{
		if(dir != null && dir.isDirectory())
		{
			isDir = true;
			currentDir = dir;
			currentScaleSize = size;
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
				refresh();
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
	public void setSize(double size)
	{
		currentScaleSize = size;
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

	public void setSort(int method, int order)
	{
		sortMethod = method;
		sortOrder = order;
		switch (method)
		{
			case 0 -> {
				//System.out.println("按名称排序");
				FXCollections.sort(this.getChildren(), (o1,o2) ->{
					String name1 = ((ImageThumbnailPane)o1).getImageFile().getImageName();
					String name2 = ((ImageThumbnailPane)o2).getImageFile().getImageName();
					return name1.compareTo(name2);
				});
			}
			case 1 -> {
				//System.out.println("按大小排序");
				FXCollections.sort(this.getChildren(), (o1, o2) -> {
					long size1 = ((ImageThumbnailPane)o1).getImageFile().getRawFile().length();
					long size2 = ((ImageThumbnailPane)o2).getImageFile().getRawFile().length();
					return Long.compare(size1,size2);
				});
			}
			case 2 -> {
				//System.out.println("按时间排序");
				FXCollections.sort(this.getChildren(), (o1, o2) -> {
					long time1 = ((ImageThumbnailPane)o1).getImageFile().getRawFile().lastModified();
					long time2 = ((ImageThumbnailPane)o2).getImageFile().getRawFile().lastModified();
					return Long.compare(time1,time2);
				});
			}
		}
		switch (order)
		{
			case 0 -> {
				//System.out.println("升序");
			}
			case 1 -> {
				//System.out.println("降序");
				FXCollections.reverse(this.getChildren());
			}
		}
	}
}
