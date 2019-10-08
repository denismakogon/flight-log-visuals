package gmaps.path.builder;

import gps.range.setup.GPSCoordinatesRanger;
import log.parser.SRTParser;
import org.junit.Test;

import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class GoogleMapsTest {

    @Test
    public void testPathBuilder() throws Exception {
        var parser = new SRTParser();
        var resource = this.getClass().getResource("/flight_log.srt");
        if (resource == null) {
            throw new Exception("flight log: resource file not found");
        }

        var log = Paths.get(resource.getPath());
        var rangeStep = 30;
        var records = parser.parseToSRT(log);
        var ranger = new GPSCoordinatesRanger();
        var ranges = ranger.defineRanges(records, rangeStep);

        System.out.printf("range created: %d\n", ranges.size());

        var gMaps = new GoogleMaps("blah");
        var requests = gMaps.getRequestFromRanges(ranges);

        assertEquals(ranges.size(), requests.size());

    }

}
