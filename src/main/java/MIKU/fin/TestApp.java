package MIKU.fin;

import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;

public class TestApp
{
	public static void main(String[] args)
	{
		ImageFile imageFile = new ImageFile("E:\\PICTURES\\2.gif");
		System.out.println(imageFile.getRawFile().exists());
	}
}
