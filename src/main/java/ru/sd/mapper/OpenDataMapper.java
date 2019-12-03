package ru.sd.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sd.buspoll.model.OpenData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenDataMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<OpenData> toOpenDataList(final String rawJson) {
        List<OpenData> openData = new ArrayList<>();
        try {
            openData = OBJECT_MAPPER.readValue(
                    rawJson, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, OpenData.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openData;
    }
}
