package Home.helper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Picture {

    public ImageView createImageView(String imageName) {
        Image image = new Image(this.getClass().getResource("/Libraries/icon/" + imageName).toExternalForm());
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public Image createImage(String imageName) {
        Image image = new Image(this.getClass().getResource("/Libraries/icon/" + imageName).toExternalForm());
        return image;
    }

    public static boolean saveAvatar(File file) {
        File dir = new File("avatar");
        // Tạo thư mục nếu chưa tồn tại
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {
            // Copy vào thư mục logos (đè nếu đã tồn tại)
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Image readAvatar(String fileName) {
        try {
            if (fileName == null) {
                return null;
            }
            File file = new File(new File("avatar"), fileName);
            String localUrl = file.toURI().toURL().toExternalForm();
            
            return new Image(localUrl);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
