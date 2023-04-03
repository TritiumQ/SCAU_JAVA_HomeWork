package demo.v1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.io.File;

public class ImageManagerController {
	/*FX组件成员*/
	@FXML
	private TreeView<File> fileTreeView;
	@FXML
	private Button  b_home, b_file, b_search, b_about;
	@FXML
	private TextField t_search;
	@FXML
	private Label l_address, l_footerBar;
	
	/*其他成员*/
	public File CurrentAddress;

	
 
	
}
