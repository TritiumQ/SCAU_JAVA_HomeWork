module com.demo.imagebrowser {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires org.controlsfx.controls;
	
	opens demo.v1 to javafx.fxml;
	exports demo.v1;
	exports demo.v1.controller;
	opens demo.v1.controller to javafx.fxml;
}