package warehouse.warehouse;

import java.sql.Date;

public class Ram {
    private Integer id;
    private String name;
    private String memtype;
    private String freq;
    private String memory;
    private String user;
    private Date date;
    private String note;


    public Ram(Integer id, String name, String memtype, String freq, String memory, String user, Date date, String note) {
        this.id = id;
        this.name = name;
        this.memtype = memtype;
        this.freq = freq;
        this.memory = memory;
        this.user = user;
        this.date = date;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
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

    public String getMemtype() {
        return memtype;
    }

    public void setMemtype(String memtype) {
        this.memtype = memtype;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
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
}
