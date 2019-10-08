package app.main;

import gif.encoder.GIFEncoder;
import gmaps.path.builder.GoogleMaps;
import gps.range.setup.GPSCoordinatesRanger;
import log.parser.SRTParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import javax.imageio.stream.FileImageOutputStream;


public class App {

    public static void main(String[] args) throws Exception {

        var finalGIF = new File(System.getenv("PWD") + "/" +"final.gif");
        var outputStream = new FileImageOutputStream(finalGIF);
        var gifEncoder = new GIFEncoder(outputStream, BufferedImage.TYPE_INT_RGB, 0, true);

        var flightLogPath = Paths.get(System.getenv("PWD") + "/" + "src/test/resources/flight_log.srt");
        var parser = new SRTParser();
        var records = parser.parseToSRT(flightLogPath);
        var ranger = new GPSCoordinatesRanger();
        var ranges = ranger.defineRanges(records, 50);

        var gMaps = new GoogleMaps(System.getenv("GOOGLE_API_TOKEN"));
        var requests = gMaps.getRequestFromRanges(ranges);
        for (var image: gMaps.imageFromRequests(requests)) {
            gifEncoder.writeToSequence(image);
        }

        gifEncoder.close();
        outputStream.close();
    }

}
