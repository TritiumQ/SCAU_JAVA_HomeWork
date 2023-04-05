import demo.v2.util.FileUtil;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.io.File;

public class UniversalTest
{
	@Test
	public void test_getSystemIcon()
	{
		File f = new File("C:\\Users\\Tritium\\Desktop");
		Image icon = FileUtil.getFileSystemIcon(f);
		System.out.println(icon);
	}
	@Test
	public void test_getSystemName()
	{
		File f = new File("C:\\Users\\Tritium\\Desktop");
		String name = FileUtil.getFileSystemName(f);
		System.out.println(name);
	}
}
