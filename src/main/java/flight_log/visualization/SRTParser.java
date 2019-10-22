package flight_log.visualization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class SRTParser {

    public SRTParser() {}

    public ArrayList<SRTBody> parseToSRT(Path srtFilePath) throws IOException {
        var parts = Files.readString(srtFilePath).split("\n\n");
        var result = new ArrayList<SRTBody>(parts.length);
        for (String part: parts) {
            var internalParts = part.split("\n");
            if (internalParts.length > 0) {
                var index = internalParts[0];
                var tFrame = internalParts[1];
                var fData = internalParts[4];
                result.add(
                        Integer.parseInt(index) - 1,
                        new SRTBody(index, tFrame, fData)
                );
            }
        }
        return result;
    }

}
