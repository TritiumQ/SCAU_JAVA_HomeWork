package MIKU.fin.utils;

import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 文件工具类
 * @author TritiumQ
 */
public class FileUtil
{
	/**
	 * 判断文件是否为支持的图片格式
	 * @param file 文件
	 * @return 是否为支持的图片格式
	 */
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
	/**
	 * 用户文件夹路径
	 */
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
	 * 全屏查看器FXML文件路径
	 */
	public static String PATH_FULLSCREENVIEWER_FXML = "/view/slide-view.fxml";
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
	
	/**
	 * 复制文件到指定目录
	 */
	public static void copyFileToDirectory(File file, File currentDir) throws IOException
	{
		if(currentDir.exists())
		{
			File newFile = new File(currentDir.getAbsolutePath() + File.separator + file.getName());
			//System.out.println(newFile.getAbsolutePath());
			Files.copy(file.toPath(), newFile.toPath());
		}
	}
	
	/**
	 * 复制文件到同一目录
	 * @param file 要复制的文件
	 * @param currentDir 当前目录
	 * @throws IOException
	 */
	public  static void copyFileToSameDirectory(File file, File currentDir) throws IOException
	{
		if(currentDir.exists())
		{
			String fileName = file.getName();
			String type = fileName.substring(fileName.lastIndexOf("."));
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			int i = 1;
			File newFile = new File(currentDir.getAbsolutePath() + File.separator + fileName + " (" + i + ")" + type);
			while(newFile.exists())
			{
				newFile = new File(currentDir.getAbsolutePath() + File.separator + fileName + " (" + i +")" + type);
				i++;
			}
			//System.out.println(newFile.getAbsolutePath());
			Files.copy(file.toPath(), newFile.toPath());
		}
	}
	
	/**
	 * 重命名文件
	 * @param file 文件
	 * @param s 新文件名
	 * @throws IOException
	 */
	public static void renameFile(File file, String s) throws IOException
	{
		String fileName = file.getName();
		String type = fileName.substring(fileName.lastIndexOf("."));
		File newFile = new File(file.getParent() + File.separator + s + type);
		Files.move(file.toPath(), newFile.toPath());
	}
	
	/**
	 * 获取图片名称
	 * @param image 图片对象
	 * @return 图片名称
	 */
	public static String getImageName(Image image)
	{
		String name = image.getUrl();
		name = name.substring(name.lastIndexOf("/") + 1);
		return name;
	}
	
	/**
	 * 获取文件大小, 根据文件长度自动转换为合适的单位
	 * @param length 文件长度，单位为‘B’
	 * @return 表示文件的大小的字符串
	 */
	public static String genFileSize(long length)
	{
		if(length == 0)
		{
			return "0B";
		}
		double size = length;
		String unit = "B";
		if(size > 1024)
		{
			size /= 1024;
			unit = "KB";
		}
		if(size > 1024)
		{
			size /= 1024;
			unit = "MB";
		}
		if(size > 1024)
		{
			size /= 1024;
			unit = "GB";
		}
		return String.format("%.3f", size) + unit;
	}
	
	/**
	 * 获取文件类型
	 * @param f 文件
	 * @return 文件类型
	 */
	public static String getFileType(File f)
	{
		String fileName = f.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
