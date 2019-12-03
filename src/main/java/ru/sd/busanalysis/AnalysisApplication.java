package ru.sd.busanalysis;

import ru.sd.busanalysis.model.Coordinates;
import ru.sd.busanalysis.model.Timings;
import ru.sd.buspoll.model.Bus;
import ru.sd.buspoll.model.OpenData;
import ru.sd.db.BusRepository;
import ru.sd.util.Formatter;
import ru.sd.util.Position;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisApplication {
    private static final Coordinates churchPoint = new Coordinates("55.8795", "49.1477");
    private static final Coordinates dkPoint = new Coordinates("55.8543", "49.0873");
    private static final Coordinates garagePoint = new Coordinates("55.8313", "49.0484");

    public static void main(String[] args) {
        final BusRepository busRepository = new BusRepository();
        final Map<String, Integer> averageLoopMap = new HashMap<>();

        final Map<String, List<OpenData>> openDataOf42MarshByDate = busRepository.selectBusDataByMarsh("42")
                .stream()
                .filter(openData -> {
                    final Bus bus = openData.getData();
                    return bus.getSpeed().equals("0") &&
                            bus.getSmena().equals("1") &&
                            (isPosition(bus, Position.CHURCH) || isPosition(bus, Position.DK));
                }).collect(
                        Collectors.groupingBy(openData -> Formatter.formatDate(openData.getData().getTimeNav()))
                );

        for (Map.Entry<String, List<OpenData>> entry : openDataOf42MarshByDate.entrySet()) {
            final List<OpenData> openDataOf42Marsh = entry.getValue();
            final Map<Integer, Timings> timings = getTimingsForEveryDate(openDataOf42Marsh);
            fillAverageLoopMap(averageLoopMap, entry.getKey(), timings);
        }
        System.out.println(averageLoopMap);
    }

    private static void fillAverageLoopMap(final Map<String, Integer> averageLoopMap, final String currentKey, final Map<Integer, Timings> timings) {
        int loopSumInMinutes = 0;
        int loopCount = 0;
        for (final Timings timing : timings.values()) {
            if (timing.getEnd() == null) {
                continue;
            }
            loopCount += 1;
            final Date d1 = Formatter.formatStringToDate(timing.getStart());
            final Date d2 = Formatter.formatStringToDate(timing.getEnd());
            final long diff = d2.getTime() - d1.getTime();
            final long diffMinutes = diff / 1000 / 60;
            loopSumInMinutes += diffMinutes;
        }
        int averageInMinutes = loopSumInMinutes / loopCount;
        averageLoopMap.put(currentKey, averageInMinutes);
    }

    private static Map<Integer, Timings> getTimingsForEveryDate(final List<OpenData> openDataOf42Marsh) {
        final Map<Integer, Timings> timings = new HashMap<>();

        int currentLoopNumber = 0;
        Coordinates coordinates = getFirstCoordinates(openDataOf42Marsh);
        for (final OpenData openData : openDataOf42Marsh) {
            final Bus currentBus = openData.getData();
            if (!coordinates.equals(currentBus.getCoordinates())) {
                final Timings currentTiming = timings.get(currentLoopNumber);
                currentTiming.setEnd(currentBus.getTimeNav());
                currentLoopNumber += 1;
            } else {
                if (!timings.containsKey(currentLoopNumber)) {
                    timings.put(currentLoopNumber, new Timings());
                }
                final Timings currentTiming = timings.get(currentLoopNumber);
                currentTiming.setStart(currentBus.getTimeNav());
            }
            coordinates = currentBus.getCoordinates();
        }
        return timings;
    }

    private static Coordinates getFirstCoordinates(final List<OpenData> openData) {
        final Bus bus = openData.get(0).getData();
        return new Coordinates(bus.getLatitude(), bus.getLongitude());
    }

    private static boolean isPosition(final Bus bus, final Position position) {
        final Coordinates currentPositionCoordinate =
                position.equals(Position.CHURCH) ? churchPoint : position.equals(Position.DK) ? dkPoint : garagePoint;
        return Formatter.formatCoordinates(bus.getLatitude()).equals(currentPositionCoordinate.getLatitude())
                && Formatter.formatCoordinates(bus.getLongitude()).equals(currentPositionCoordinate.getLongitude());
    }
}
