/***
 * @author TritiumQun
 * @version alpha0.1
 *
 */
package com.demo.imagebrowser;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {
	@FXML
	private MenuBar m_bar;
	
	@FXML
	private Menu menu;
	
	@FXML
	private MenuItem m_open, m_close, m_about;
	
	@FXML
	private ImageView v_image;
	
	@FXML
	private Label l_address;
	
	@FXML
	public void OnClickExitAction()
	{
		System.exit(0);
	}
	
	@FXML
	public void OnClickOpenAction()
	{
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Image File");
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("GIF", "*.gif"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				new FileChooser.ExtensionFilter("PNG", "*.png")
		);
		File imageFile = chooser.showOpenDialog(null);
		if(imageFile != null)
		{
			String imagePath = imageFile.getPath();
			setImage(imagePath);
		}
	}
	
	@FXML
	public void OnClickAboutAction()
	{
	
	}
	
	@FXML
	private void setImage(String path)
	{
		Image img = new Image("file:" + path);
		v_image.setImage(img);
		l_address.setText(path);
	}
	
}