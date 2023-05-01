package MIKU.fin.utils;

import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

/**
 * 文件工具类
 * @author TritiumQ
 */
public class FileUtil
{
	
	public static boolean isSupportImageFormat(File file) {
		String fileName = file.getName().toUpperCase();
		//支持的照片格式：.JPG、.JPEG、.GIF、.PNG、和.BMP。
		return fileName.endsWith("JPG") ||
				fileName.endsWith("JPEG") ||
				fileName.endsWith("GIF") ||
				fileName.endsWith("PNG") ||
				fileName.endsWith("BMP") ||
				fileName.endsWith("WEBP");
	}
	/**
	 * 文件树根目录, 在windows下为"此电脑", 在Linux下为"/";
	 */
	public static final File FILE_ROOT = Objects.requireNonNull(FileSystemView.getFileSystemView().getHomeDirectory().listFiles())[0];
	
	public static final String PATH_APP_DATA = System.getenv("APPDATA");
	/**
	 * 主图标路径
	 */
	public static String PATH_MAIN_ICON = "/Icon/icon1.png";
	/**
	 * 主界面FXML文件路径
	 */
	public static String PATH_MAIN_FXML = "/view/main-view_new.fxml";
	/**
	 * 图片查看器FXML文件路径
	 */
	public static String PATH_VIEWER_FXML = "/view/viewer-view.fxml";
	
	/**
	 * 获取文件在系统中的图标
	 */
	public static Image getFileSystemIcon(File file)
	{
		Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(null, bi.getGraphics(),0,0);
		return SwingFXUtils.toFXImage(bi, null);
	}
	
	/**
	 * 获取文件在系统中的名称
	 */
	public static String getFileSystemName(File file)
	{
		return FileSystemView.getFileSystemView().getSystemDisplayName(file);
	}
}
