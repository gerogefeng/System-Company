package by.psu.gui;

import by.psu.Config;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoaderFilesFX {

    private Config config = Config.getInstance();

    private Logger logger = Logger.getLogger(LoaderFilesFX.class);

    private File urlPhoto = null;

    public LoaderFilesFX(String urlPhoto){
        this.urlPhoto = new File(urlPhoto);
    }

    public LoaderFilesFX() {

    }

    public void setFile(String urlLocal) {
        this.urlPhoto = new File(config.getProperty("dir.photo") + File.separator + urlLocal);
    }

    public Image getImage(){
        BufferedImage bufferedImage = null;
        Image image = null;
        try {
            bufferedImage = ImageIO.read(urlPhoto);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            logger.fatal(e.getMessage(), e.fillInStackTrace());
        }
        return image;
    }

    public void readImage(File file){
        try ( FileInputStream fileInputStream= new FileInputStream(file.getAbsoluteFile());
              FileOutputStream fileOutputStream = new FileOutputStream(config.getProperty("dir.photo") + File.separator + file.getName())
        ){
            byte[] byteRead = new byte[2024];
            while ((fileInputStream.read(byteRead)) != -1)
                fileOutputStream.write(byteRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File configureFileImageChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите аватар");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        return fileChooser.showOpenDialog(new Stage());
    }

    public static File configureFileDocumentChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите договор");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("DOC", "*.doc")
        );
        return fileChooser.showOpenDialog(new Stage());
    }
}
