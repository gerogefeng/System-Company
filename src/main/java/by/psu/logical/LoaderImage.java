package by.psu.logical;

import by.psu.Config;
import by.psu.logical.model.Employee;
import com.sun.istack.internal.NotNull;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoaderImage {

    private Config config = Config.getInstance();
    private BufferedImage bufferedImage = null;

    private Logger logger = Logger.getLogger(LoaderImage.class);

    private File urlPhoto = null;

    public LoaderImage(@NotNull Employee employee){
        this.urlPhoto = (employee.getUrlPhoto() != null && !employee.getUrlPhoto().equals("null"))
                ? new File(config.getProperty("dir.photo") + File.separator + employee.getUrlPhoto())
                : new File(config.getProperty("dir.photo") + File.separator + config.getProperty("default.photo"));
    }

    public LoaderImage(){ }

    public void setFile(String urlLocal) {
        this.urlPhoto = new File(config.getProperty("dir.photo") + File.separator + urlLocal);
    }

    public Image getImage(){
        bufferedImage = null;
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
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file.getAbsoluteFile());
            fileOutputStream = new FileOutputStream(config.getProperty("dir.photo") + File.separator + file.getName());
            byte[] byteRead = new byte[2024];
            while ((fileInputStream.read(byteRead)) != -1) {
                fileOutputStream.write(byteRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
