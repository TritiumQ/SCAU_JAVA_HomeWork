module MIKU {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires org.controlsfx.controls;
	
	opens demo.v1 to javafx.fxml;
	opens demo.v1.controller to javafx.fxml;
	exports demo.v1;
	
	opens demo.v2 to javafx.fxml;
	opens demo.v2.controller to javafx.fxml;
	exports demo.v2;
}