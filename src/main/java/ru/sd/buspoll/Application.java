package ru.sd.buspoll;

import org.apache.http.client.fluent.Request;
import ru.sd.buspoll.model.OpenData;
import ru.sd.db.BusRepository;
import ru.sd.mapper.OpenDataMapper;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.asList;

public class Application {
    private static final List<String> MARSH_LIST = asList("43", "42", "75");

    public static void main(String[] args) {
        final BusRepository busRepository = new BusRepository();

        final Request request = Request.Get("http://data.kzn.ru:8082/api/v0/dynamic_datasets/bus.json");

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                try {
                    final String rawData = request.execute().returnContent().asString();
                    final List<OpenData> openData = OpenDataMapper.toOpenDataList(rawData);

                    openData.stream()
                            .filter(
                                    data -> MARSH_LIST.contains(data.getData().getMarsh())
                            )
                            .forEach(busRepository::insertBusData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 5000L;
        long period = 60000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}
