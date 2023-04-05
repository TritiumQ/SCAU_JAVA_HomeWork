package demo.v2.util;

import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 文件工具类
 * @author TritiumQ
 */
public class FileUtil
{
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
