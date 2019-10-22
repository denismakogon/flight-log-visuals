package flight_log.visualization;

import com.google.maps.errors.ApiException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.stream.FileImageOutputStream;


public class App {

    public static void buildGIF(String flightLogPath, String googleAPIKey, GIFEncoder gifEncoder) throws Exception {
        var fLP = Paths.get(flightLogPath);
        var parser = new SRTParser();
        var records = parser.parseToSRT(fLP);
        var ranger = new GPSCoordinatesRanger();
        var ranges = ranger.defineRanges(records, 150);

        var gMaps = new GoogleMaps(googleAPIKey);
        var requests = gMaps.getRequestFromRanges(records, ranges);

        try {
            var images = gMaps.imageFromRequests(requests);
            for (var image: images) {
                gifEncoder.writeToSequence(image);
            }
        } catch (IOException | ApiException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }

    }

    public static void main(String[] args) {

        var finalGIF = new File((args.length > 2) ? args[2] : System.getenv("PWD") + "/" + "final.gif");

        try(FileImageOutputStream outputStream = new FileImageOutputStream(finalGIF);
            GIFEncoder gifEncoder = new GIFEncoder(
                    outputStream, BufferedImage.TYPE_INT_RGB, 0, true)) {

            var flightLogPath = (args.length > 1) ? args[1] : System.getenv("PWD") + "/" + "src/test/resources/flight_log.srt";
            var googleAPIKey = System.getenv("GOOGLE_API_KEY");

            buildGIF(flightLogPath, googleAPIKey, gifEncoder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
