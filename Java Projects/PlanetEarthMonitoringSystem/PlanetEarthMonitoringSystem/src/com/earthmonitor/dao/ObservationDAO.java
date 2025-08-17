package com.earthmonitor.dao;

import com.earthmonitor.exception.MyException;
import com.earthmonitor.model.Observation;
import com.earthmonitor.util.DB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ObservationDAO {
    public void insert(Observation o) throws MyException {
        String sql = "INSERT INTO observations (location, latitude, longitude, aqi, wqi, temperature, humidity, noise_level, observed_at, entered_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getLocation());
            if (o.getLatitude() == null) ps.setNull(2, Types.DECIMAL); else ps.setDouble(2, o.getLatitude());
            if (o.getLongitude() == null) ps.setNull(3, Types.DECIMAL); else ps.setDouble(3, o.getLongitude());
            if (o.getAqi() == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, o.getAqi());
            if (o.getWqi() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, o.getWqi());
            if (o.getTemperature() == null) ps.setNull(6, Types.DOUBLE); else ps.setDouble(6, o.getTemperature());
            if (o.getHumidity() == null) ps.setNull(7, Types.DOUBLE); else ps.setDouble(7, o.getHumidity());
            if (o.getNoiseLevel() == null) ps.setNull(8, Types.DOUBLE); else ps.setDouble(8, o.getNoiseLevel());
            ps.setTimestamp(9, Timestamp.valueOf(o.getObservedAt()));
            if (o.getEnteredBy() == null) ps.setNull(10, Types.INTEGER); else ps.setInt(10, o.getEnteredBy());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyException("Failed to insert observation", e);
        }
    }

    public List<Observation> listAll() throws MyException {
        String sql = "SELECT id, location, latitude, longitude, aqi, wqi, temperature, humidity, noise_level, observed_at, entered_by FROM observations ORDER BY observed_at DESC";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Observation> list = new ArrayList<>();
            while (rs.next()) {
                Observation o = new Observation();
                o.setId(rs.getInt("id"));
                o.setLocation(rs.getString("location"));
                java.math.BigDecimal blat = (java.math.BigDecimal) rs.getObject("latitude");
                java.math.BigDecimal blon = (java.math.BigDecimal) rs.getObject("longitude");
                o.setLatitude(blat == null ? null : blat.doubleValue());
                o.setLongitude(blon == null ? null : blon.doubleValue());
                Object aqiObj = rs.getObject("aqi"); o.setAqi(aqiObj == null ? null : rs.getInt("aqi"));
                Object wqiObj = rs.getObject("wqi"); o.setWqi(wqiObj == null ? null : rs.getInt("wqi"));
                Object tempObj = rs.getObject("temperature"); o.setTemperature(tempObj == null ? null : rs.getDouble("temperature"));
                Object humObj = rs.getObject("humidity"); o.setHumidity(humObj == null ? null : rs.getDouble("humidity"));
                Object noiseObj = rs.getObject("noise_level"); o.setNoiseLevel(noiseObj == null ? null : rs.getDouble("noise_level"));
                o.setObservedAt(rs.getTimestamp("observed_at").toLocalDateTime());
                Object eb = rs.getObject("entered_by");
                o.setEnteredBy(eb == null ? null : rs.getInt("entered_by"));
                list.add(o);
            }
            return list;
        } catch (SQLException e) {
            throw new MyException("Failed to list observations", e);
        }
    }

    public void update(Observation o) throws MyException {
        String sql = "UPDATE observations SET location=?, latitude=?, longitude=?, aqi=?, wqi=?, temperature=?, humidity=?, noise_level=?, observed_at=? WHERE id=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getLocation());
            if (o.getLatitude() == null) ps.setNull(2, Types.DECIMAL); else ps.setDouble(2, o.getLatitude());
            if (o.getLongitude() == null) ps.setNull(3, Types.DECIMAL); else ps.setDouble(3, o.getLongitude());
            if (o.getAqi() == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, o.getAqi());
            if (o.getWqi() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, o.getWqi());
            if (o.getTemperature() == null) ps.setNull(6, Types.DOUBLE); else ps.setDouble(6, o.getTemperature());
            if (o.getHumidity() == null) ps.setNull(7, Types.DOUBLE); else ps.setDouble(7, o.getHumidity());
            if (o.getNoiseLevel() == null) ps.setNull(8, Types.DOUBLE); else ps.setDouble(8, o.getNoiseLevel());
            ps.setTimestamp(9, Timestamp.valueOf(o.getObservedAt()));
            ps.setInt(10, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyException("Failed to update observation", e);
        }
    }
}
