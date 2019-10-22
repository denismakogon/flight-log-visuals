package flight_log.visualization;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;


public class GPSCoordinatesRangerTest {

    @Test
    public void testGPSCoordinatesRangerNoException() throws Exception {
        var parser = new SRTParser();
        var resource = this.getClass().getResource("/flight_log.srt");
        if (resource == null) {
            throw new Exception("flight log: resource file not found");
        }

        var log = Paths.get(resource.getPath());
        try {
            var rangeStep = 150;


            var records = parser.parseToSRT(log);
            var ranger = new GPSCoordinatesRanger();
            ranger.defineRanges(records, rangeStep);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGPSCoordinatesRangerFist() throws Exception {
        var parser = new SRTParser();
        var resource = this.getClass().getResource("/flight_log.srt");
        if (resource == null) {
            throw new Exception("flight log: resource file not found");
        }

        var log = Paths.get(resource.getPath());
        try {
            var rangeStep = 100;
            var records = parser.parseToSRT(log);
            var ranger = new GPSCoordinatesRanger();
            var ranges = ranger.defineRanges(records, rangeStep);

            // flight log starts with index 1
            assertEquals(new Integer(1), ranges.get(0).get(0).getIndex());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGPSCoordinatesRangerLast() throws Exception {
        var parser = new SRTParser();
        var resource = this.getClass().getResource("/flight_log.srt");
        if (resource == null) {
            throw new Exception("flight log: resource file not found");
        }

        var log = Paths.get(resource.getPath());
        try {
            var rangeStep = 100;
            var records = parser.parseToSRT(log);
            var ranger = new GPSCoordinatesRanger();
            var ranges = ranger.defineRanges(records, rangeStep);

            // flight log starts with index 1
            var finalRange = ranges.get(ranges.size() -1);
            assertEquals(new Integer(records.size()),
                    ranges.get(ranges.size() -1).get(finalRange.size() - 1).getIndex());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
