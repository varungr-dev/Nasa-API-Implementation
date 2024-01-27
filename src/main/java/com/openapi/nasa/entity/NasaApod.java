package com.openapi.nasa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

// ENTITY CLASS FOR APOD DIRECTORY //
@Entity
@Table(name="apod")
@Builder
public class NasaApod {

    //define fields//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="copyright")

    @Size(min = 1,message = "is required")
    private String copyright;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    @Column(name="date")
    private String date;

    @Column(columnDefinition = "TEXT")
    @Lob
    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String explanation;


    @Size(min = 1,message = "is required")
    @Column(name="hdurl")
    private String hdurl;

    @Column(name="title")
    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String title;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    @Column(name="url")
    private String url;


    // define constructors //

    public NasaApod(String copyright, String date, String explanation, String hdurl,String title, String url) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.title = title;
        this.url = url;
    }

    public NasaApod()
    {
        // no argument constructor //
    }

    public NasaApod(int id, String copyright, String date, String explanation, String hdurl, String title, String url) {
        this.id = id;
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.title = title;
        this.url = url;
    }



    // defined getters and setters //


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // toString method //

    @Override
    public String toString() {
        return "NasaApod{" +
                "id=" + id +
                ", copyright='" + copyright + '\'' +
                ", date='" + date + '\'' +
                ", explanation='" + explanation + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
