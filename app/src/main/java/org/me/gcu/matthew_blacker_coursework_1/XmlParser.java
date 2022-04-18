package org.me.gcu.matthew_blacker_coursework_1;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlParser {
    static final String title = "title";
    static final String description = "description";
    static final String link = "link";
    static final String point = "point";
    static final String pubDate = "pubDate";
    private List<Incidents> incidents = new ArrayList<Incidents>();
    private Incidents incident;
    private String text;
    private List<Roadworks> roadworks = new ArrayList<Roadworks>();
    private Roadworks roadwork;
    private List<PlannedRoadWorks> Roadworks = new ArrayList<PlannedRoadWorks>();
    private PlannedRoadWorks plannedRoadWorks;

//Parser for incidents data
    public List<Incidents> parseIncidents(String parseDataInput) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParse = factory.newPullParser();
            pullParse.setInput(new StringReader(parseDataInput));
            int eventType = pullParse.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = pullParse.getName();
                switch (eventType) {
                    case XmlPullParser.TEXT:
                        text = pullParse.getText();
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(title)) {
                            incident = new Incidents();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //Setting values to the variables based on tag
                        if (tagName.equalsIgnoreCase(title)) {
                            incident.setTitle(text);
                        } else if (tagName.equalsIgnoreCase(title)) {
                            incident.setTitle(text);

                        } else if (tagName.equalsIgnoreCase(description)) {
                            incident.setDescription(text);

                        }
                        else if (tagName.equalsIgnoreCase(pubDate)) {
                            incident.setPubDate(text);

                        }
                        else if (tagName.equalsIgnoreCase(link)) {
                            incident.setLink(text);

                        } else if (tagName.equalsIgnoreCase(point)) {
                            incident.setPoint(text);

                        }
                        if(incident.getPubDate() == null)
                            incidents.remove(incident);
                        else
                            incidents.add(incident);
                        break;
                }
                eventType = pullParse.next();
            }
        } catch (XmlPullParserException ex) {
            Log.e("My Tag", "Parsing Error" + ex.toString());
        } catch (IOException exIO) {
            Log.e("My Tag", "IO error");
        }
        Log.e("My Tag", "End Document");
        return incidents;
    }










    //Parser for roadworks data
    public List<Roadworks> parseRoadWorks(String parseDataInput) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParse = factory.newPullParser();
            pullParse.setInput(new StringReader(parseDataInput));
            int eventType = pullParse.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = pullParse.getName();
                switch (eventType) {
                    case XmlPullParser.TEXT:
                        text = pullParse.getText();
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(title)) {
                            roadwork = new Roadworks();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(title)) {
                            roadwork.setTitle(text);
                        } else if (tagName.equalsIgnoreCase(title)) {
                            roadwork.setTitle(text);

                        } else if (tagName.equalsIgnoreCase(description)) {
                            String[] split = text.split("<br />");
                            StringBuilder stringBuilder = new StringBuilder();
                            for(String str : split) {
                                stringBuilder.append(str).append(" ");
                                 if (str.contains("end")) {
                                    try
                                    {
                                        String endDate = str.split("End Date: ")[1];
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("E, dd MMM yyyy - HH:mm");
                                        Date end = dateFormat1.parse(endDate);
                                        roadwork.setEndDate(end);

                                    } catch (Exception ex) {
                                        Log.e("Error: ", ex.getMessage());
                                    }
                                }
                            }
                            roadwork.setDescription(stringBuilder.toString());

                        } else if (tagName.equalsIgnoreCase(link)) {
                            roadwork.setLink(text);

                        } else if (tagName.equalsIgnoreCase(point)) {
                            roadwork.setPoint(text);

                        } else if (tagName.equalsIgnoreCase(pubDate)) {
                            roadwork.setPubDate(text);

                        }
                        if(roadwork.getPubDate() == null)
                            roadworks.remove(roadwork);
                        else
                            roadworks.add(roadwork);
                        break;
                }
                eventType = pullParse.next();
            }
        } catch (XmlPullParserException ex) {
            Log.e("My Tag", "Error" + ex.toString());
        } catch (IOException exIO) {
            Log.e("My Tag", "IO Error");
        }
        Log.e("My Tag", "End Document");
        return roadworks;
    }







    //Parser for planned roadworks data
    public List<PlannedRoadWorks> parsePlannedRoadWorks(String parseDataInput) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParse = factory.newPullParser();
            pullParse.setInput(new StringReader(parseDataInput));
            int eventType = pullParse.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = pullParse.getName();
                switch (eventType) {
                    case XmlPullParser.TEXT:
                        text = pullParse.getText();
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(title)) {
                            plannedRoadWorks = new PlannedRoadWorks();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(title)) {
                            plannedRoadWorks.setTitle(text);
                        } else if (tagName.equalsIgnoreCase(title)) {
                            plannedRoadWorks.setTitle(text);

                        } else if (tagName.equalsIgnoreCase(description)) {
                            String[] split = text.split("<br />");
                            StringBuilder stringBuilder = new StringBuilder();
                            for(String str : split) {
                                stringBuilder.append(str).append(" ");
                                //Formats the start and end date values with a familiar date pattern
                                if (str.contains("Start Date")) {
                                    try {
                                        String startDate = str.split("Start Date: ")[1];
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy - HH:mm");
                                        Date start = dateFormat.parse(startDate);
                                        plannedRoadWorks.setStartDate(start);

                                        Log.e("Start", "Date: " + start);
                                    } catch (Exception ex) {
                                        Log.e("Error: ", ex.getMessage());
                                    }
                                }
                                else if (str.contains("End Date")) {
                                    try {
                                        String endDate = str.split("End Date: ")[1];
                                        SimpleDateFormat format1 = new SimpleDateFormat("E, dd MMM yyyy - HH:mm");
                                        Date end = format1.parse(endDate);
                                        plannedRoadWorks.setEndDate(end);

                                    } catch (Exception ex) {
                                        Log.e("Error: ", ex.getMessage());
                                    }

                                }
                            }
                            plannedRoadWorks.setDescription(stringBuilder.toString());

                        } else if (tagName.equalsIgnoreCase(link)) {
                            plannedRoadWorks.setLink(text);

                        } else if (tagName.equalsIgnoreCase(point)) {
                            plannedRoadWorks.setPoint(text);

                        } else if (tagName.equalsIgnoreCase(pubDate)) {
                            plannedRoadWorks.setPubDate(text);

                        }
                        if(plannedRoadWorks.getPubDate() == null)
                            Roadworks.remove(plannedRoadWorks);
                        else
                            Roadworks.add(plannedRoadWorks);
                        break;
                }
                eventType = pullParse.next();
            }
        } catch (XmlPullParserException ex) {
            Log.e("My Tag", "Error" + ex.toString());
        } catch (IOException exIO) {
            Log.e("My Tag", "IO Error");
        }
        Log.e("My Tag", "End Document");
        return Roadworks;
    }
}
