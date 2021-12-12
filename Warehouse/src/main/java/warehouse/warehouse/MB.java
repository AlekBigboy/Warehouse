package warehouse.warehouse;

import java.sql.Date;

public class MB {
    private Integer id;
    private String name;
    private String socet;
    private Integer ram_amount;
    private String memtype;
    private String user;
    private Date date;
    private String note;

    public MB(Integer id, String name, String socet, Integer ram_amount, String memtype, String user, Date date,String note) {
        this.id = id;
        this.name = name;
        this.socet = socet;
        this.ram_amount = ram_amount;
        this.memtype = memtype;
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

    public String getSocet() {
        return socet;
    }

    public void setSocet(String socet) {
        this.socet = socet;
    }

    public Integer getRam_amount() {
        return ram_amount;
    }

    public void setRam_amount(Integer ram_amount) {
        this.ram_amount = ram_amount;
    }

    public String getMemtype() {
        return memtype;
    }

    public void setMemtype(String memtype) {
        this.memtype = memtype;
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
