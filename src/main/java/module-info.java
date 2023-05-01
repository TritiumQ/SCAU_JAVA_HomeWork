module MIKU {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.swing;
	
	requires org.controlsfx.controls;
	
	requires org.json;
	
	requires org.apache.logging.log4j;
	
	requires java.desktop;
	
	opens MIKU.fin to javafx.fxml;
	opens MIKU.fin.controllers to javafx.fxml;
	exports MIKU.fin;
	exports MIKU.fin.utils;
}