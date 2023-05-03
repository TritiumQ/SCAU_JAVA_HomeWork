module MIKU {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.swing;
	
	requires org.json;
	
	requires java.desktop;
	requires org.controlsfx.controls;
	
	opens MIKU.fin to javafx.fxml;
	opens MIKU.fin.controllers to javafx.fxml;
	
	exports MIKU.fin to javafx.graphics;
	//exports MIKU.fin;
	exports MIKU.fin.utils;
	exports MIKU.fin.module;
}