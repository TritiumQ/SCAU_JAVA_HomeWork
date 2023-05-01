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
	private final Label imageCounter, selectedImageCounter;
	private final Label imageSize;
	private int count = 0, selectedCount = 0;
	private long size = 0, selectedSize = 0;
	public InformationPane()
	{
		Font ft = FontUtil.genFont(FontUtil.FONT_YAHEI,13);
		Insets it = new Insets(0,5,0,5);
		
		Label l1_1 = new Label("当前目录下共有: ");
		l1_1.setFont(ft);
		l1_1.setAlignment(Pos.CENTER);
		l1_1.setPadding(it);
		
		selectedImageCounter = new Label("0");
		selectedImageCounter.setFont(ft);
		selectedImageCounter.setAlignment(Pos.CENTER);
		selectedImageCounter.setPadding(it);
		
		Label l1_2 = new Label("张图片.");
		l1_2.setPadding(it);
		l1_2.setFont(ft);
		l1_2.setAlignment(Pos.CENTER);
		
		Label l1 = new Label("当前已选中: ");
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
		
		imageSize = new Label("0");
		imageSize.setFont(ft);
		imageSize.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(l1_1,imageCounter, l1_2, l1,selectedImageCounter,l2,l3,imageSize);
		this.setAlignment(Pos.CENTER);
	}
	
	public void update(int newCount, int newSelectedCount, long newSelectedSize)
	{
		count = newCount;
		selectedCount = newSelectedCount;
		size = newSelectedSize;
		double sizeCounterD = newSelectedSize;
		String unit = "B";
		if(sizeCounterD > 1024)
		{
			sizeCounterD /= 1024;
			unit = "KB";
		}
		if(sizeCounterD > 1024)
		{
			sizeCounterD /= 1024;
			unit = "MB";
		}
		if(sizeCounterD > 1024)
		{
			sizeCounterD /= 1024;
			unit = "GB";
		}
		double finalSizeCounterD = sizeCounterD;
		String finalUnit = unit;
		Platform.runLater(()->{
			imageCounter.setText(String.valueOf(count));
			selectedImageCounter.setText(String.valueOf(selectedCount));
			imageSize.setText(String.format("%.2f %s", finalSizeCounterD, finalUnit));
		});
	}
}
