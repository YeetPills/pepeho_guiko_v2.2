package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.embed.swing.SwingFXUtils.fromFXImage;
import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class Controller implements Initializable{

    public File selectedFile;
    public Image image;
    public Image modifiedImage;
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
    public ImageView imageView;

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
        originalImageRadio1.setDisable(true);

        image = new Image("file:" + selectedFile.getAbsolutePath(), true);
        imageView.setImage(image);
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
            //BufferedImage bImage = ImageIO.read((ImageInputStream) );
            //if (bImage != null) {
                ImageIO.write(fromFXImage(image, null), "png", file);
          //  }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void generateImage(ActionEvent event) {
        BufferedImage bImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0;x < bImage.getWidth();x++){
            for (int y = 0; y < bImage.getHeight();y++){
                bImage.setRGB(x, y, (new Color(x*y %255, 2*y %255, x %255).getRGB()));
            }
        }

        imageView.setImage(getJavaFXImage(bImage));
        image = getJavaFXImage(bImage);
        originalImageRadio1.setDisable(false);
    }

    public Image getJavaFXImage(BufferedImage bImage) {
        WritableImage wr;
        wr = new WritableImage(bImage.getWidth(), bImage.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < bImage.getWidth(); x++) {
            for (int y = 0; y < bImage.getHeight(); y++) {
                pw.setArgb(x, y, bImage.getRGB(x, y));
            }
        }
        return wr;
    }

    public void applyNegativeFilter() {
        try {
            BufferedImage currentImage = null;
            currentImage = fromFXImage(image, null);
            for (int x = 0;x<currentImage.getWidth();x++) {
                for (int y = 0;y<currentImage.getHeight();y++) {
                    int rgb = currentImage.getRGB(x,y);
                    Color col = new Color(rgb, false);
                    col = new Color(255 - col.getRed(),
                            255 - col.getGreen(),
                            255 - col.getBlue());
                    currentImage.setRGB(x, y, col.getRGB());
                }
            }
            imageView.setImage(getJavaFXImage(currentImage));
            modifiedImage = getJavaFXImage(currentImage);
            modifiedImageRadio2.setDisable(false);
            modifiedImageRadio2.setSelected(true);
            originalImageRadio1.setSelected(false);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void showOriginal(ActionEvent actionEvent) {
        modifiedImageRadio2.setSelected(false);
        imageView.setImage(image);
    }

    public void showModified(ActionEvent actionEvent) {
        originalImageRadio1.setSelected(false);
        imageView.setImage(modifiedImage);
    }

    public void showAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("this is a GUI thing");
        alert.setContentText("it does some stuff like loading and saving images and applies a negative filter");

        alert.showAndWait();
    }
}
