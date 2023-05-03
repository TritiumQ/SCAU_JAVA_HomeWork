package MIKU.fin.utils;


import javafx.scene.text.Font;

/**
 * 字体工具类
 * @author TritiumQ
 */
public class FontUtil
{
	/**
	 * 生成字体对象
	 * @param fontName 字体名
	 * @param fontSize 字体大小
	 * @return 字体对象
	 */
	public static Font genFont(String fontName, double fontSize)
	{
		return new Font(fontName, fontSize);
	}
	
	/**
	 * 微软雅黑
	 */
	public static String FONT_YAHEI = "Microsoft YaHei";
	public static String FONT_YAHEI_BOLD = "Microsoft YaHei Bold";
	public static String FONT_SMILEY_SANS = "Smiley Sans";
}
