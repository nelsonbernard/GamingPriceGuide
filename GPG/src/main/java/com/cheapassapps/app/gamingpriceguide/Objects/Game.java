package com.cheapassapps.app.gamingpriceguide.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nelson on 8/23/2017.
 */

public class Game{

    private String imageName = "";
    private String consoleID = "";
    private String name = "";
    private String giantBombID = "";
    private int id = 0;
    private String loosePrice = "";
    private String completePrice = "";
    private String newPrice = "";
    private String gradedPrice = "";
    private String gameURL = "";
    private String description = "";
    private String deck = "";
    private String screen_url = "";
    private String releaseDate = "";
    private String consolerealname = "";
    private List<String> images = new ArrayList<String>();

    public String getConsoleRealName() {
        return consolerealname;
    }

    public void setConsoleRealName(String name) {
        this.consolerealname = name;
    }

    public  List<String> getImages() {
        return images;
    }

    public void setImages(String image) {
        this.images.add(image);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getScreen_url() {
        if(this.screen_url == "")
            setScreen_url();
        return screen_url;
    }

    public void setScreen_url() {

        Collections.shuffle(images);
        this.screen_url = images.get(0);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getConsoleID() { return consoleID; }

    public void setConsoleID(String consoleID) { this.consoleID = consoleID; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoosePrice() {
        return loosePrice;
    }

    public void setLoosePrice(String loosePrice) {
        this.loosePrice = loosePrice;
    }

    public String getCompletePrice() {
        return completePrice;
    }

    public void setCompletePrice(String completePrice) {
        this.completePrice = completePrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getGiantBombID() {
        return giantBombID;
    }

    public void setGiantBombID(String giantBombID) {
        this.giantBombID = giantBombID;
    }

    public String getGradedPrice() {
        return gradedPrice;
    }

    public void setGradedPrice(String gradedPrice) {
        this.gradedPrice = gradedPrice;
    }

    public String getGameURL() {
        return gameURL;
    }

    public void setGameURL(String gameURL) {
        this.gameURL = gameURL;
    }
}
