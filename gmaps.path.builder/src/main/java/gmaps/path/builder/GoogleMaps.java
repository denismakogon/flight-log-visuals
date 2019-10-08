package gmaps.path.builder;

import com.google.maps.GeoApiContext;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.Size;
import log.parser.SRTBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class GoogleMaps {

    private GeoApiContext context;
    private final int WIDTH = 1024;
    private final int HEIGHT = 800;

    public GoogleMaps(String apiToken) throws Exception {
        if (apiToken == "") {
            throw new Exception("empty google api token");
        }

        context = new GeoApiContext.Builder()
                .apiKey(apiToken)
                .build();
    }

    private StaticMapsRequest.Path getPathFromRange(ArrayList<SRTBody> gpsCoordinatesRanges) {
        var path = new StaticMapsRequest.Path();
        path.color("green");
        path.fillcolor("0xAACCEE");
        path.weight(3);
        path.geodesic(true);
        for (var body: gpsCoordinatesRanges) {
            var coordinates = body.getCoordinates();
            path.addPoint(
                    new LatLng(coordinates.getLatitude(),
                            coordinates.getLongitude())
            );
        }

        return path;
    }

    public ArrayList<StaticMapsRequest> getRequestFromRanges(
            ArrayList<ArrayList<SRTBody>> gpsCoordinatesRanges) {
        var finalResult = new ArrayList<StaticMapsRequest>(gpsCoordinatesRanges.size());
        for (var range: gpsCoordinatesRanges) {
            var req = StaticMapsApi.newRequest(context, new Size(WIDTH, HEIGHT));
            req.maptype(StaticMapsRequest.StaticMapType.hybrid);

            req.path(getPathFromRange(range));
            finalResult.add(req);
        }

        return finalResult;
    }

    public ArrayList<BufferedImage> imageFromRequests(ArrayList<StaticMapsRequest> reqs) throws Exception {
        var images = new ArrayList<BufferedImage>();
        for (var req: reqs) {
            images.add(ImageIO.read(new ByteArrayInputStream(req.await().imageData)));
        }
        return images;
    }

}
