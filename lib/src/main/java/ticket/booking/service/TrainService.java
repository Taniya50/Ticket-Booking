package ticket.booking.service;

import ticket.booking.entities.Train;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    // Updated to work with List<Train> instead of Node
    public List<Train> searchTrains(String source, String destination, List<Train> allTrains) {

        // Filter trains based on valid source and destination
        return allTrains.stream()
                .filter(train -> validTrain(train, source, destination))
                .collect(Collectors.toList());
    }

    // Updated to work with Train object
    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations(); // Assuming Train has getStations() method that returns a list of stations

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}






