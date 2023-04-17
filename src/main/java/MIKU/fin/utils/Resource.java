package MIKU.fin.utils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Objects;

/**
 * 常量垃圾箱
 * @author TritiumQ
 */
public class Resource
{
	/**
	 * 主图标路径
	 */
	public static String path_MainIcon = "/Icon/icon1.png";
	/**
	 * 主界面FXML文件路径
	 */
	public static String path_MainWindowFXML = "/view/main-view.fxml";
	/**
	 * 默认宽度
	 */
	public static int defaultWidth = 1280;
	/**
	 * 默认高度
	 */
	public static int defaultHeigth = 800;
	/**
	 * 文件树根目录, 在windows下为"此电脑", 在Linux下为"?";
	 */
	public static final File FILE_ROOT = Objects.requireNonNull(FileSystemView.getFileSystemView().getHomeDirectory().listFiles())[0];
	/**
	 * 程序标题
	 */
	public static String title = "MIKU's Image Manager";
}
