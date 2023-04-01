module com.demo.imagebrowser {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires org.controlsfx.controls;
	
	opens com.demo.imagebrowser to javafx.fxml;
	exports com.demo.imagebrowser;
	exports com.demo.imagebrowser.controller;
	opens com.demo.imagebrowser.controller to javafx.fxml;
}