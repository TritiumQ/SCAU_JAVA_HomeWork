package MIKU.fin.moduel;

import java.io.File;

public class ImageFile
{
	private final File imageFile;
	private final String imageURL;
	private final String imageName;
	
	public ImageFile(File imageFile)
	{
		this.imageFile = imageFile;
		this.imageName = imageFile.getName();
		this.imageURL = imageFile.toPath().toUri().toString();
		System.out.println(imageURL);
	}
	
	public File getRawFile()
	{
		return imageFile;
	}
	
	public String getImageURL()
	{
		return imageURL;
	}
	
	public String getImageName()
	{
		return imageName;
	}
}
