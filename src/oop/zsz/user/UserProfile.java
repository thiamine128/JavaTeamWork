package oop.zsz.user;

import java.util.Date;
import java.util.Map;

public class UserProfile {
    private String username;
    private String portrait;
    private Date registeredDate;

    private Map<String, Long> history;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Map<String, Long> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Long> history) {
        this.history = history;
    }
}
