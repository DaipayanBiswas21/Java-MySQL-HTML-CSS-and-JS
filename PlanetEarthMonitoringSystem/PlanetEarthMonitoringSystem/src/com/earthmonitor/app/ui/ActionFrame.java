package com.earthmonitor.app.ui;

import com.earthmonitor.exception.MyException;
import com.earthmonitor.model.Observation;
import com.earthmonitor.model.User;
import com.earthmonitor.service.ObservationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ActionFrame extends JFrame {
    private final User currentUser;
    private final DashboardFrame.Action action;
    private final ObservationService obsService = new ObservationService();

    public ActionFrame(User user, DashboardFrame.Action action) {
        super("Action — " + action);
        this.currentUser = user;
        this.action = action;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        switch (action) {
            case VIEW -> setContentPane(viewPanel());
            case INSERT -> setContentPane(insertPanel(null));
            case UPDATE -> setContentPane(updatePanel());
        }
    }

    private JPanel viewPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel title = new JLabel("All Observations");
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        p.add(title, BorderLayout.NORTH);

        String[] cols = {"ID", "Location", "Lat", "Lon", "AQI", "WQI", "Temp(°C)", "Humidity(%)", "Noise(dB)", "Observed At", "Entered By"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(26);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refresh = new JButton("Refresh");
        bottom.add(refresh);
        if (currentUser.getRole() == User.Role.ADMIN) {
            JButton edit = new JButton("Edit Selected");
            bottom.add(edit);
            edit.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to edit"); return; }
                int id = (Integer) model.getValueAt(row, 0);
                Observation o = null;
                try {
                    List<Observation> all = obsService.listAll();
                    for (Observation ob : all) if (ob.getId() == id) { o = ob; break; }
                    if (o == null) { JOptionPane.showMessageDialog(this, "Selected record not found"); return; }
                    JFrame f = new JFrame("Edit Observation - ID " + id);
                    f.setContentPane(insertPanel(o));
                    f.setSize(700,520);
                    f.setLocationRelativeTo(this);
                    f.setVisible(true);
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        p.add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadObservations(model));
        loadObservations(model);
        return p;
    }

    private void loadObservations(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            List<Observation> all = obsService.listAll();
            for (Observation o : all) {
                model.addRow(new Object[]{
                    o.getId(), o.getLocation(), o.getLatitude(), o.getLongitude(),
                    o.getAqi(), o.getWqi(), o.getTemperature(), o.getHumidity(), o.getNoiseLevel(),
                    o.getObservedAt(), o.getEnteredBy()
                });
            }
        } catch (MyException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Failed to load", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel insertPanel(Observation toEdit) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(8); idField.setEditable(false);
        JTextField location = new JTextField(20);
        JTextField lat = new JTextField(10);
        JTextField lon = new JTextField(10);
        JTextField aqi = new JTextField(8);
        JTextField wqi = new JTextField(8);
        JTextField temp = new JTextField(8);
        JTextField hum = new JTextField(8);
        JTextField noise = new JTextField(8);
        JTextField observedAt = new JTextField(20);
        observedAt.setToolTipText("YYYY-MM-DDTHH:MM or leave blank for now");

        int y=0;
        c.gridx=0; c.gridy=y; p.add(new JLabel("ID"), c); c.gridx=1; p.add(idField, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Location"), c); c.gridx=1; p.add(location, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Latitude"), c); c.gridx=1; p.add(lat, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Longitude"), c); c.gridx=1; p.add(lon, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("AQI"), c); c.gridx=1; p.add(aqi, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("WQI"), c); c.gridx=1; p.add(wqi, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Temperature (°C)"), c); c.gridx=1; p.add(temp, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Humidity (%)"), c); c.gridx=1; p.add(hum, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Noise (dB)"), c); c.gridx=1; p.add(noise, c); y++;
        c.gridx=0; c.gridy=y; p.add(new JLabel("Observed At"), c); c.gridx=1; p.add(observedAt, c); y++;

        JButton save = new JButton(toEdit == null ? "Save Observation" : "Update Observation");
        c.gridx=0; c.gridy=y; c.gridwidth=2; p.add(save, c);

        if (toEdit != null) {
            idField.setText(String.valueOf(toEdit.getId()));
            location.setText(toEdit.getLocation());
            lat.setText(toEdit.getLatitude() == null ? "" : String.valueOf(toEdit.getLatitude()));
            lon.setText(toEdit.getLongitude() == null ? "" : String.valueOf(toEdit.getLongitude()));
            aqi.setText(toEdit.getAqi() == null ? "" : String.valueOf(toEdit.getAqi()));
            wqi.setText(toEdit.getWqi() == null ? "" : String.valueOf(toEdit.getWqi()));
            temp.setText(toEdit.getTemperature() == null ? "" : String.valueOf(toEdit.getTemperature()));
            hum.setText(toEdit.getHumidity() == null ? "" : String.valueOf(toEdit.getHumidity()));
            noise.setText(toEdit.getNoiseLevel() == null ? "" : String.valueOf(toEdit.getNoiseLevel()));
            observedAt.setText(toEdit.getObservedAt() == null ? "" : toEdit.getObservedAt().toString());
        }

        save.addActionListener(e -> {
            try {
                Observation o = new Observation();
                if (!idField.getText().isBlank()) o.setId(Integer.parseInt(idField.getText().trim()));
                o.setLocation(location.getText().trim());
                o.setLatitude(lat.getText().isBlank() ? null : Double.parseDouble(lat.getText().trim()));
                o.setLongitude(lon.getText().isBlank() ? null : Double.parseDouble(lon.getText().trim()));
                o.setAqi(aqi.getText().isBlank() ? null : Integer.parseInt(aqi.getText().trim()));
                o.setWqi(wqi.getText().isBlank() ? null : Integer.parseInt(wqi.getText().trim()));
                o.setTemperature(temp.getText().isBlank() ? null : Double.parseDouble(temp.getText().trim()));
                o.setHumidity(hum.getText().isBlank() ? null : Double.parseDouble(hum.getText().trim()));
                o.setNoiseLevel(noise.getText().isBlank() ? null : Double.parseDouble(noise.getText().trim()));
                o.setObservedAt(observedAt.getText().isBlank() ? LocalDateTime.now() : LocalDateTime.parse(observedAt.getText().trim()));
                o.setEnteredBy(currentUser.getId());

                if (toEdit == null) {
                    obsService.create(o);
                    JOptionPane.showMessageDialog(this, "Saved!");
                } else {
                    o.setId(Integer.parseInt(idField.getText().trim()));
                    obsService.update(o);
                    JOptionPane.showMessageDialog(this, "Updated!");
                }
                // Clear or close
                if (toEdit == null) {
                    location.setText(""); lat.setText(""); lon.setText(""); aqi.setText(""); wqi.setText("");
                    temp.setText(""); hum.setText(""); noise.setText(""); observedAt.setText("");
                } else {
                    SwingUtilities.getWindowAncestor(p).dispose();
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid number: " + nfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (MyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return p;
    }

    private JPanel updatePanel() {
        // For admins, show view but with Edit button (reuse viewPanel but trigger admin flow)
        return viewPanel();
    }
}
