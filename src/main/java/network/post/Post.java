package network.post;

import java.util.*;

/**
 * The post entity.
 */
public class Post {
    private UUID id;
    private String poster;
    private String title;
    private String province;
    private String text;
    private Date createdDate;
    private Long likes;

    /**
     * Get id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get province.
     *
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * Get poster.
     *
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Get created date.
     *
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Get title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Set province.
     *
     * @param province the province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Set created date.
     *
     * @param date the date
     */
    public void setCreatedDate(Date date) {
        this.createdDate = date;
    }

    /**
     * Set title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set poster.
     *
     * @param poster the poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * Set text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Set likes.
     *
     * @param likes the likes
     */
    public void setLikes(Long likes) {
        this.likes = likes;
    }

    /**
     * Get likes.
     *
     * @return the likes
     */
    public Long getLikes() {
        return likes;
    }

    private List<UUID> images;

    /**
     * Get images.
     *
     * @return the images
     */
    public List<UUID> getImages() {
        return images;
    }

    /**
     * Set images.
     *
     * @param images the images
     */
    public void setImages(List<UUID> images) {
        this.images = images;
    }
}
