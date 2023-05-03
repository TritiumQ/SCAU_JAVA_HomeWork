package MIKU.fin.components;

import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.FontUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class InformationPane extends HBox
{
	private final Label label;
	private int count = 0, selectedCount = 0;
	private long size = 0, selectedSize = 0;
	public InformationPane()
	{
		Font ft = FontUtil.genFont(FontUtil.FONT_YAHEI,13);
		Insets it = new Insets(0,5,0,5);
		
		label = new Label();
		label.setFont(ft);
		label.setPadding(it);
		
		this.getChildren().add(label);
		this.setAlignment(Pos.CENTER);
		update(0,0,0,0);
	}
	
	public void update(int newCount, long newSize,  int newSelectedCount, long newSelectedSize)
	{
		count = newCount;
		selectedCount = newSelectedCount;
		size = newSize;
		selectedSize = newSelectedSize;
		String inf = String.format("当前目录下共有: %d张图片(%s), 已选中: %d张图片(%s)", count, FileUtil.genFileSize(size), selectedCount, FileUtil.genFileSize(selectedSize));
		Platform.runLater(() -> label.setText(inf));
	}
}
