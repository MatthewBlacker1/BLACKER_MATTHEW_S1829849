package org.me.gcu.matthew_blacker_coursework_1;

import java.util.Date;

public class PlannedRoadWorks {
    private String title;
    private String description;
    private String link;
    private String point;
    private String pubDate;
    private Date startDate;
    private Date endDate;

    public String getPubDate() {
        return pubDate;
    }
    public void setTitle(String title) { this.title = title; }
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
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }



    @Override
    public String toString() {
        return  "Title: " + title + "\nDescription: \n" + description + "\nstartDate: \n" + startDate + "\nend date: \n" + endDate + "\nLink: \n" + link + "\nPoint: "+ point + "\nPublish Date: " + pubDate;
    }
}
