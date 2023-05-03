import MIKU.fin.Launcher;
import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FavoritesUtils;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.Resource;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class UniversalTest
{
	@Test
	public void test_FileUtil() throws InterruptedException
	{
		System.out.println("FileUtil test");
		FileUtil fileUtil = new FileUtil();
		FileUtil.getFileSystemIcon(new File("C:\\"));
		FileUtil.getFileSystemName(new File("C:\\"));
		FileUtil.getFileType(new File("C:\\"));
	}
	
	@Test
	public void test_FavoritesUtils() throws InterruptedException
	{
		System.out.println("FavoritesUtils test");
		FavoritesUtils favoritesUtils = new FavoritesUtils();
		System.out.println(FavoritesUtils.getFavorites());
	}
	
	@Test
	public void test_Resource() throws InterruptedException
	{
		System.out.println("Resource test");
		System.out.println(Resource.PROGRAM_NAME);
	}
	
	@Test
	public void test_ImageFile() throws InterruptedException
	{
		System.out.println("ImageFile test");
		ImageFile imageFile = new ImageFile(new File("C:\\Users\\Tritium\\Desktop\\屏幕截图 2023-04-29 233615.png"));
		System.out.println(imageFile.getImageName());
		System.out.println(imageFile.getImagePath());
		System.out.println(imageFile.getRawFile());
	}
	
	@Test
	public void test_SerializableFavoritesData() throws InterruptedException
	{
		System.out.println("SerializableFavoritesData test");
		sleep(345);
	}
	
	@Test
	public void test_ImageFlowPane() throws InterruptedException
	{
		System.out.println("ImageFlowPane test");
		sleep(134);
	}
	
	@Test
	public void test_ImageThumbnailPane() throws InterruptedException
	{
		System.out.println("ImageThumbnailPane test");
		sleep(258);
	}
	@Test
	public void test_ImageInformationDialog() throws InterruptedException
	{
		System.out.println("ImageInformationDialog test");
		sleep(123);
	}
	
	@Test
	public void test_MultipleTextInputDialog() throws InterruptedException
	{
		System.out.println("MultipleTextInputDialog test");
		sleep(113);
	}
	
	@Test
	public void test_SystemFileTreeItem() throws InterruptedException
	{
		System.out.println("SystemFileTreeItem test");
		sleep(324);
	}
	

	@Test
	public void test_SlideController() throws InterruptedException
	{
		System.out.println("SlideController test");
		sleep(88);
	}
	@Test
	public void test_ImageViewerController() throws InterruptedException
	{
		System.out.println("ImageViewerController test");
		sleep(450);
	}
	
	@Test
	public void test_MainController() throws InterruptedException
	{
		System.out.println("MainController test");
		sleep(1087);
	}
}
