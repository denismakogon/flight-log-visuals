package log.parser;

public class GPSCoordinates {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public GPSCoordinates(String ln, String lg) {
        latitude = Double.parseDouble(ln.split(" : ")[1]);
        longitude = Double.parseDouble(lg.split(" : ")[1]);
    }
}
