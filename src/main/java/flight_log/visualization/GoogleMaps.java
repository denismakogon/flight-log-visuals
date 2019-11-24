package flight_log.visualization;

import com.google.maps.GeoApiContext;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.model.Size;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class GoogleMaps {

    private GeoApiContext context;
    private final int WIDTH = 1024;
    private final int HEIGHT = 800;

    public GoogleMaps(String apiToken) throws Exception {
        if (apiToken == null || apiToken.isEmpty()) {
            throw new Exception("empty google api token");
        }

        context = new GeoApiContext.Builder()
                .apiKey(apiToken)
                .build();
    }

    private StaticMapsRequest.Path getPathFromRange(ArrayList<SRTBody> gpsCoordinatesRanges) {
        var path = new StaticMapsRequest.Path();
        path.color("0xFF0000FF");
        path.fillcolor("0xAACCFF");
        path.weight(10);
        path.geodesic(true);
        for (var body: gpsCoordinatesRanges) {
            var coordinates = body.getCoordinates();
            path.addPoint(coordinates);
        }

        return path;
    }

    public ArrayList<StaticMapsRequest> getRequestFromRanges(
            ArrayList<SRTBody> records,
            ArrayList<ArrayList<SRTBody>> gpsCoordinatesRanges) {
        var finalResult = new ArrayList<StaticMapsRequest>(gpsCoordinatesRanges.size());

        var center = GPSCenter.center(records);
        System.out.println(String.format("center point: [%s, %s]", center.lat, center.lng));
        for (var range: gpsCoordinatesRanges) {
            var req = StaticMapsApi.newRequest(context, new Size(WIDTH, HEIGHT));

            req.maptype(StaticMapsRequest.StaticMapType.hybrid);
            req.center(center);
            req.zoom(15);

            req.path(getPathFromRange(range));

            finalResult.add(req);
        }

        return finalResult;
    }

    // todo: add fibers
    public ArrayList<BufferedImage> imageFromRequests(ArrayList<StaticMapsRequest> reqs) throws Exception {
        System.out.println("entering imageFromRequests");
        var images = new ArrayList<BufferedImage>();
        for (var req: reqs) {
            try {
                var resp = req.await();
                var rawImg = new ByteArrayInputStream(
                        resp.imageData
                );
                images.add(
                        ImageIO.read(
                                rawImg
                        )
                );
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return images;
    }

}
