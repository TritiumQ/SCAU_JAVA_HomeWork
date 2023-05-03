package MIKU.fin.module;

import javafx.scene.image.Image;

import java.io.File;

/**
 * 图片文件类, 用于存储图片文件的信息，作为文件对象和图片对象的中间层
 * @author TritiumQ
 */
public class ImageFile
{
	private final File imageFile;
	private final String imagePath;
	private final String imageUri;
	private final String imageName;
	
	public ImageFile(File imageFile)
	{
		this.imageFile = imageFile;
		this.imageName = imageFile.getName();
		this.imagePath = imageFile.getAbsolutePath();
		this.imageUri = imageFile.toPath().toUri().toString();
		//System.out.println(imagePath);
		//System.out.println(imageUri);
		
	}
	
	public ImageFile(String path)
	{
		this.imageFile = new File(path);
		this.imageName = imageFile.getName();
		this.imagePath = imageFile.getAbsolutePath();
		this.imageUri = imageFile.toPath().toUri().toString();
		//System.out.println(imagePath);
		//System.out.println(imageUri);
	}
	
	/**
	 * 获取图片原始文件对象
	 * @return
	 */
	public File getRawFile()
	{
		return imageFile;
	}
	
	/**
	 * 获取图片文件的绝对路径
	 * @return
	 */
	public String getImagePath()
	{
		return imagePath;
	}
	
	/**
	 * 获取图片文件URI
	 * @return
	 */
	public String getImageUri()
	{
		return imageUri;
	}
	
	/**
	 * 获取图片文件名
	 * @return
	 */
	public String getImageName()
	{
		return imageName;
	}
	
	/**
	 * 生成图片对象
	 * @return
	 */
	public Image genImage()
	{
		return new Image(getImageUri());
	}
}
