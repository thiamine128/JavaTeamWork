package oop.zsz.user;

import java.util.Date;
import java.util.Map;

public class UserProfile {
    // 用户名
    private String username;
    // 没有设置过头像则为null
    private String portrait;
    // 注册日期
    private Date registeredDate;
    // 发帖记录
    private Map<String, Long> history;
    private Long jigsawTime;
    private Boolean jigsawFlag;
    private Boolean quizFlag;
    private Boolean administrator;

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

    public Boolean getJigsawFlag() {
        return jigsawFlag;
    }

    public Boolean getQuizFlag() {
        return quizFlag;
    }

    public Long getJigsawTime() {
        return jigsawTime;
    }

    public void setJigsawTime(Long jigsawTime) {
        this.jigsawTime = jigsawTime;
    }

    public void setJigsawFlag(Boolean jigsawFlag) {
        this.jigsawFlag = jigsawFlag;
    }

    public void setQuizFlag(Boolean quizFlag) {
        this.quizFlag = quizFlag;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public Boolean getAdministrator() {
        return administrator;
    }
}
