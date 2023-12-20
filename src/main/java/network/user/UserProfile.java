package network.user;

import java.util.Date;
import java.util.Map;

/**
 * The user profile.
 */
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

    /**
     * Get username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get portrait.
     *
     * @return the portrait
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * Set portrait.
     *
     * @param portrait the portrait
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    /**
     * Get registered date.
     *
     * @return the registered date
     */
    public Date getRegisteredDate() {
        return registeredDate;
    }

    /**
     * Set registered date.
     *
     * @param registeredDate the registered date
     */
    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    /**
     * Get history.
     *
     * @return the history
     */
    public Map<String, Long> getHistory() {
        return history;
    }

    /**
     * Set history.
     *
     * @param history the history
     */
    public void setHistory(Map<String, Long> history) {
        this.history = history;
    }

    /**
     * Get jigsaw flag.
     *
     * @return the jigsaw flag
     */
    public Boolean getJigsawFlag() {
        return jigsawFlag;
    }

    /**
     * Get quiz flag.
     *
     * @return the quiz flag
     */
    public Boolean getQuizFlag() {
        return quizFlag;
    }

    /**
     * Get jigsaw time.
     *
     * @return the jigsaw time
     */
    public Long getJigsawTime() {
        return jigsawTime;
    }

    /**
     * Set jigsaw time.
     *
     * @param jigsawTime the jigsaw time
     */
    public void setJigsawTime(Long jigsawTime) {
        this.jigsawTime = jigsawTime;
    }

    /**
     * Set jigsaw flag.
     *
     * @param jigsawFlag the jigsaw flag
     */
    public void setJigsawFlag(Boolean jigsawFlag) {
        this.jigsawFlag = jigsawFlag;
    }

    /**
     * Set quiz flag.
     *
     * @param quizFlag the quiz flag
     */
    public void setQuizFlag(Boolean quizFlag) {
        this.quizFlag = quizFlag;
    }

    /**
     * Set administrator.
     *
     * @param administrator the administrator
     */
    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * Get administrator.
     *
     * @return the administrator
     */
    public Boolean getAdministrator() {
        return administrator;
    }
}
