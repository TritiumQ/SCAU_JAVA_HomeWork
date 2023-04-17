module MIKU {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.swing;
	
	requires org.controlsfx.controls;
	
	requires java.desktop;
	
	opens MIKU.fin to javafx.fxml;
	
	opens demo.v2 to javafx.fxml;
	opens demo.v2.controller to javafx.fxml;
	exports demo.v2;
	exports demo.v2.util;
}