package MIKU.fin.components;

import MIKU.fin.module.ImageFile;
import MIKU.fin.utils.FileUtil;
import MIKU.fin.utils.FontUtil;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;

public class ImageInfomationDialog extends Dialog<ButtonType>
{
	private GridPane gridPane;
	private Label imageName;
	private Label imageSize;
	private Label imageType;
	private Label imageResolution;
	private Label imageLoaction;
	
	public ImageInfomationDialog(ImageFile file)
	{
		init();
		getDialogPane().setContent(gridPane);
		File f = file.getRawFile();
		Image image = file.genImage();
		imageName.setText(f.getName());
		imageSize.setText(FileUtil.genFileSize(f.length()));
		imageType.setText(FileUtil.getFileType(f));
		imageResolution.setText((int) image.getWidth() + "x" + (int) image.getHeight()+"px");
		imageLoaction.setText(f.getAbsolutePath());
		
	}
	
	private void init()
	{
		imageName = new Label();
		imageName.setAlignment(Pos.CENTER);
		
		imageSize = new Label();
		imageSize.setAlignment(Pos.CENTER);
		
		imageType = new Label();
		imageType.setAlignment(Pos.CENTER);
		
		imageResolution = new Label();
		imageResolution.setAlignment(Pos.CENTER);
		
		imageLoaction = new Label();
		imageLoaction.setAlignment(Pos.CENTER);
		
		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(0);
		gridPane.setAlignment(Pos.CENTER_LEFT);
		gridPane.addColumn(0, new Label("图片名称："), new Label("图片大小："), new Label("图片类型："), new Label("图片分辨率："), new Label("图片位置："));
		gridPane.addColumn(1, imageName, imageSize, imageType, imageResolution, imageLoaction);
		gridPane.setStyle(
				"-fx-font: 15px \"微软雅黑\";" +
				"-fx-text-fill: #000000;");
		
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);
		this.setTitle("图片信息");
		this.setHeaderText(null);
	}
}
