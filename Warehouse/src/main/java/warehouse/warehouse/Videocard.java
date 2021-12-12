package warehouse.warehouse;

import java.sql.Date;

public class Videocard {
    private Integer id;
    private String name;
    private String memory;
    private String pci_version;
    private String port;
    private String user;
    private Date date;
    private String note;



    public Videocard(Integer id, String name, String memory, String pci_version, String port, String user, Date date, String note) {
        this.id = id;
        this.name = name;
        this.memory = memory;
        this.pci_version = pci_version;
        this.port = port;
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

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getPci_version() {
        return pci_version;
    }

    public void setPci_version(String pci_version) {
        this.pci_version = pci_version;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
