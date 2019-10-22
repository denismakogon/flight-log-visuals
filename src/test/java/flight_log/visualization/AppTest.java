package flight_log.visualization;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;


public class AppTest {

    @Before
    public void beforeMethod() {
        var key = System.getenv("GOOGLE_API_KEY");

        org.junit.Assume.assumeTrue(key != null);
    }

    @Test
    public void testStaticImage() throws Exception {
        var finalGIF = new File(System.getenv("PWD") + "/" +"final.gif");
        var outputStream = new FileImageOutputStream(finalGIF);
        var gifEncoder = new GIFEncoder(outputStream, BufferedImage.TYPE_INT_RGB, 0, true);

        var resource = this.getClass().getResource("/flight_log.srt");
        if (resource == null) {
            throw new Exception("flight log: resource file not found");
        }

        var flightLogPath = resource.getPath();
        var googleAPIKey = System.getenv("GOOGLE_API_KEY");
        try {

            App.buildGIF(flightLogPath, googleAPIKey, gifEncoder);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
