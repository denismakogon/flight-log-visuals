package gps.range.setup;

import log.parser.SRTParser;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;


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
            var rangeStep = 100;
            var records = parser.parseToSRT(log);
            var ranger = new GPSCoordinatesRanger();
            ranger.defineRanges(records, rangeStep);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGPSCoordinatesRanger() throws Exception {
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

            System.out.printf("range created: %d\n", ranges.size());
            var actualNumberOfRanges = ranges.size();
            var expectedNumberOfRanges = records.size() / rangeStep + (
                    (records.size() % rangeStep == 0) ? 0 : 1
            );

            assertEquals(expectedNumberOfRanges, actualNumberOfRanges);

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

    @Test
    public void testGPSCoordinatesRanges() throws Exception {
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

            assertEquals(records.size(), ranges.get(ranges.size() - 1).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
