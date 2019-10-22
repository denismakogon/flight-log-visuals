package flight_log.visualization;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;


public class SRTParserTest {

    @Test
    public void testSRTParser() throws Exception{
        var parser = new SRTParser();
        var resource = this.getClass().getResource("/flight_log.srt");
        if (resource == null) {
            throw new Exception("flight log: resource file not found");
        }

        var log = Paths.get(resource.getPath());
        try {
            var records = parser.parseToSRT(log);
            System.out.println("received " + records.size() + " coordinates");

            var coordinates = records.get(0).getCoordinates();
            System.out.println(coordinates.lat);
            System.out.println(coordinates.lng);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
