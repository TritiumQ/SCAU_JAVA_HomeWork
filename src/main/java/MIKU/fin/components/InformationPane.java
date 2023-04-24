package MIKU.fin.components;

import MIKU.fin.utils.FontUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class InformationPane extends HBox
{
	private final Label imageCounter;
	private final Label imageSizeCounter;
	private int counter = 0;
	private long sizeCounter = 0;
	public InformationPane()
	{
		Font ft = FontUtil.genFont(FontUtil.FONT_YAHEI,13);
		Insets it = new Insets(0,5,0,5);
		
		Label l1 = new Label("当前目录下共有: ");
		l1.setFont(ft);
		l1.setAlignment(Pos.CENTER);
		l1.setPadding(it);
		
		imageCounter = new Label("0");
		imageCounter.setFont(ft);
		imageCounter.setAlignment(Pos.CENTER);
		
		Label l2 = new Label("张图片.");
		l2.setPadding(it);
		l2.setFont(ft);
		l2.setAlignment(Pos.CENTER);
		
		Label l3 = new Label("已选中的图片文件大小: ");
		l3.setFont(ft);
		l3.setAlignment(Pos.CENTER);
		l3.setPadding(it);
		
		imageSizeCounter = new Label("0");
		imageSizeCounter.setFont(ft);
		imageSizeCounter.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(l1,imageCounter,l2,l3,imageSizeCounter);
		this.setAlignment(Pos.CENTER);
	}
	
	public void update(int newCount, int newSize)
	{
		counter = newCount;
		sizeCounter = newSize;
		Platform.runLater(()->{
			imageCounter.setText(Integer.toString(counter));
			imageSizeCounter.setText(Long.toString(sizeCounter));
		});
	}
}
