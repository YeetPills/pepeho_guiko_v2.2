package org.example;

import java.awt.*;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class Controller implements Initializable{

    public File selectedFile;
    public Image image;
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
            BufferedImage bImage = ImageIO.read((ImageInputStream) imageView.getImage());
            if (bImage != null) {
                ImageIO.write(bImage, "png", file);
            }
        } catch (Exception exception) {
            System.out.println(exception.getStackTrace());
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
        Image currentImage = imageView.getImage();

        BufferedImage bufferedImage = new BufferedImage((int)currentImage.getWidth(), (int)currentImage.getHeight(), BufferedImage.TYPE_INT_BGR);
        for (int x = 0;x<bufferedImage.getWidth();x++) {
            for (int y = 0;y<bufferedImage.getWidth();y++) {
                int rgb = bufferedImage.getRGB(x,y);
                Color col = new Color(rgb, false);
                System.out.println(col.getBlue() + col.getGreen() + col.getRed());
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                System.out.println(col.getBlue() + col.getGreen() + col.getRed());
                bufferedImage.setRGB(x, y, col.getRGB());
            }
        }
        imageView.setImage(getJavaFXImage(bufferedImage));
    }


    public void close() {
        System.exit(0);
    }
}
