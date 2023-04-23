package MIKU.fin.utils;


import javafx.scene.text.Font;

/**
 * 字体工具类
 */
public class FontUtil
{
	public static Font genFont(String fontName, double fontSize)
	{
		return new Font(fontName, fontSize);
	}
	
	public static String FONT_YAHEI = "Microsoft YaHei";
	public static String FONT_YAHEI_BOLD = "Microsoft YaHei Bold";
	public static String FONT_SMILEY_SANS = "Smiley Sans";
}
