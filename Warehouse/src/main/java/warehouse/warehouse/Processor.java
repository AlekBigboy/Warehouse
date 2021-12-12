package warehouse.warehouse;

import java.sql.Date;

public class Processor {
    private Integer id;
    private String name;
    private String socket;
    private Integer cores;
    private String freq;
    private String user;
    private Date date;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String note;


    public Processor(Integer id, String name, String socet, Integer cores, String freq, String user, Date date, String note) {
        this.id = id;
        this.name = name;
        this.socket = socet;
        this.cores = cores;
        this.freq = freq;
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

    public String getSocet() {
        return socket;
    }

    public void setSocet(String socet) {
        this.socket = socet;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
