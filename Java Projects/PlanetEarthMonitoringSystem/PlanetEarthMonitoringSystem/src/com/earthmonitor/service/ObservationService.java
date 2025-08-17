package com.earthmonitor.service;

import com.earthmonitor.dao.ObservationDAO;
import com.earthmonitor.exception.MyException;
import com.earthmonitor.model.Observation;

import java.time.LocalDateTime;
import java.util.List;

public class ObservationService {
    private final ObservationDAO dao = new ObservationDAO();

    public void create(Observation o) throws MyException {
        validate(o);
        if (o.getObservedAt() == null) o.setObservedAt(LocalDateTime.now());
        dao.insert(o);
    }

    public void update(Observation o) throws MyException {
        if (o.getId() <= 0) throw new MyException("Invalid observation id");
        validate(o);
        dao.update(o);
    }

    public List<Observation> listAll() throws MyException {
        return dao.listAll();
    }

    private void validate(Observation o) throws MyException {
        if (o.getLocation() == null || o.getLocation().isBlank()) throw new MyException("Location required");
        if (o.getAqi() != null && (o.getAqi() < 0 || o.getAqi() > 1000)) throw new MyException("AQI out of expected range (0-1000)");
        if (o.getWqi() != null && (o.getWqi() < 0 || o.getWqi() > 100)) throw new MyException("WQI out of expected range (0-100)");
        // other simple checks could go here
    }
}
