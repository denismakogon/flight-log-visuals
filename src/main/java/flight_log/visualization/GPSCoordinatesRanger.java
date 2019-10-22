package flight_log.visualization;

import java.util.ArrayList;

public class GPSCoordinatesRanger {

    /*
     * Google Maps Static API allows to define 2 or more path points
     * that will be used to draw a line/path through them.
     * The purpose of ranger class to provide ranging feature
     * for the list of GPS coordinates retrieved from the flight log
     */
    public ArrayList<ArrayList<SRTBody>> defineRanges(ArrayList<SRTBody> gpsCoordinates, Integer rangeStep) {
        return defineRanges(gpsCoordinates, rangeStep, 20);
    }

    public ArrayList<ArrayList<SRTBody>> defineRanges(ArrayList<SRTBody> gpsCoordinates, Integer rangeStep, Integer rangeStepDivider) {
        var finalSubsets = new ArrayList<ArrayList<SRTBody>>();
        var first = 0;
        var last = gpsCoordinates.size();

        while (first <= last && first + rangeStep <= last) {
            finalSubsets.add(
                    new ArrayList<>(
                            gpsCoordinates.subList(
                                    first, first + rangeStep
                            )
                    )
            );
            first += rangeStep / rangeStepDivider;
        }
        finalSubsets.add(new ArrayList<>(gpsCoordinates.subList(first, last)));

        return finalSubsets;

    }

}