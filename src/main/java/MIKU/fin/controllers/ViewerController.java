package MIKU.fin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewerController implements Initializable
{
	@FXML
	private ImageView currentImage;
	@FXML
	private Button btnBack, btnForward, btnZoomIn, btnZoomOut, btnRotateLeft, btnRotateRight, btnSlideShow, btnInfo;
	
	private List<Image> imageList;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
	
	}
}
