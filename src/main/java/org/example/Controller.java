package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;

public class Controller implements Initializable{

    public File selectedFile;

    @FXML
    public Button selectImage;
    public Button editMatrix;
    public Canvas canvas;
    public Button applyMatrixFilter;
    public ListView listView;
    public Button generateImage;
    public Button restoreOriginalImage;
    public RadioButton originalImageRadio1;
    public RadioButton modifiedImageRadio2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void selectFile(ActionEvent event) {
        Window mainWindow = selectImage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)", "*.png", "*-jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedFile = fileChooser.showOpenDialog(mainWindow);
    }

    @FXML
    public void saveFile(ActionEvent event) {
        Window mainWindow = selectImage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save image file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)", "*.png", "*jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        try {
            File file = fileChooser.showSaveDialog(mainWindow);
            BufferedImage bImage = ImageIO.read(selectedFile);
            if (bImage != null) {
                ImageIO.write(bImage, "png", file);
            }
        } catch (Exception exception) {
            System.out.println(exception.getStackTrace());
        }

    }

    public void close() {
        System.exit(0);
    }
}
