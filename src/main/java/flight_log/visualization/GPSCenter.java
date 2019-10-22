package flight_log.visualization;

import com.google.maps.model.LatLng;

import java.util.ArrayList;

public class GPSCenter {

    public static LatLng center(ArrayList<SRTBody> coordinates) {
        double lat = 0;
        double lng = 0;

        for (var r: coordinates) {
            var c = r.getCoordinates();
            lat += c.lat;
            lng += c.lng;
        }

        return new LatLng(lat/coordinates.size(), lng/coordinates.size());
    }
}
