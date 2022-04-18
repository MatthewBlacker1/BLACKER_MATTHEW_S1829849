package org.me.gcu.matthew_blacker_coursework_1;

public class Incidents {
    private String title, description, link, point, pubDate;

    public String getPubDate() {
        return pubDate;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setPoint(String point) {
        this.point = point;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return  "Title: " + title + "\nDescription: \n" + description + "\nLink: \n" + link + "\nPoint: " + point + "\nPublish Date: " + pubDate;
    }
}
