package log.parser;


public class SRTBody {

    private Integer index;
    private String timeFrame;
    private GPSCoordinates coordinates;

    public SRTBody(String i, String tFrame, String fData) {
        index = Integer.parseInt(i);
        timeFrame = tFrame;
        coordinates = parseCoordinates(fData);
    }

    public GPSCoordinates getCoordinates() {
        return coordinates;
    }

    private GPSCoordinates parseCoordinates(String frameData) {
        var coordinates = frameData.substring(89, frameData.length() - 1).split("] ");

        return new GPSCoordinates(
                coordinates[0].substring(1, coordinates[0].length() -1 ),
                coordinates[1].substring(1, coordinates[1].length() -1 )
        );
    }

    public Integer getIndex() {
        return index;
    }

    public String toString() {
        return String.format("the GPS frame representation, coordinates are <Latitude: %s, Longitude: %s>",
                getCoordinates().getLatitude(),
                getCoordinates().getLongitude()
        );
    }
}
