package warehouse.warehouse;

import java.sql.Date;

public class Monitor {
    private Integer id;
    private String name;
    private String port;
    private String freq;
    private String resol;
    private String user;
    private Date date;
    private String note;

    public Monitor(Integer id, String name, String port, String freq, String resol, String user, Date date, String note) {
        this.id = id;
        this.name = name;
        this.port = port;
        this.freq = freq;
        this.resol = resol;
        this.user = user;
        this.date = date;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getResol() {
        return resol;
    }

    public void setResol(String resol) {
        this.resol = resol;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
