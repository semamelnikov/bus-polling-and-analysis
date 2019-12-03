package ru.sd.db;

import ru.sd.buspoll.model.Bus;
import ru.sd.buspoll.model.OpenData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/bus_data";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private static final String INSERT_QUERY = "INSERT INTO bus_data(" +
            "updated_at, garag_numb, marsh, graph, smena, time_nav, latitude, longitude, speed, azimuth) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_MARSH_QUERY = "SELECT * FROM bus_data.public.bus_data WHERE marsh = ?";


    private static PreparedStatement insertPreparedStatement;
    private static PreparedStatement selectByMarshPreparedStatement;

    public BusRepository() {
        try {
            final Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            insertPreparedStatement = connection.prepareStatement(INSERT_QUERY);
            selectByMarshPreparedStatement = connection.prepareStatement(SELECT_BY_MARSH_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertBusData(final OpenData openData) {
        try {
            final Bus bus = openData.getData();
            insertPreparedStatement.setString(1, openData.getUpdatedAt());
            insertPreparedStatement.setString(2, bus.getGaragNumb());
            insertPreparedStatement.setString(3, bus.getMarsh());
            insertPreparedStatement.setString(4, bus.getGraph());
            insertPreparedStatement.setString(5, bus.getSmena());
            insertPreparedStatement.setString(6, bus.getTimeNav());
            insertPreparedStatement.setString(7, bus.getLatitude());
            insertPreparedStatement.setString(8, bus.getLongitude());
            insertPreparedStatement.setString(9, bus.getSpeed());
            insertPreparedStatement.setString(10, bus.getAzimuth());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OpenData> selectBusDataByMarsh(final String marsh) {
        final List<OpenData> openData = new ArrayList<>();
        try {
            selectByMarshPreparedStatement.setString(1, marsh);
            final ResultSet resultSet = selectByMarshPreparedStatement.executeQuery();
            while (resultSet.next()) {
                final Bus bus = new Bus(
                        resultSet.getString("garag_numb"),
                        resultSet.getString("marsh"),
                        resultSet.getString("graph"),
                        resultSet.getString("smena"),
                        resultSet.getString("time_nav"),
                        resultSet.getString("latitude"),
                        resultSet.getString("longitude"),
                        resultSet.getString("speed"),
                        resultSet.getString("azimuth")
                );
                final OpenData currentOpenData = new OpenData(bus, resultSet.getString("updated_at"));
                openData.add(currentOpenData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return openData;
    }
}
