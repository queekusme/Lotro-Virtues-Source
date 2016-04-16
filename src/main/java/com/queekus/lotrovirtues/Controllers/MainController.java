package com.queekus.lotrovirtues.Controllers;

import com.google.gson.reflect.*;
import com.queekus.lotrovirtues.*;
import com.queekus.lotrovirtues.Models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class MainController implements Initializable{

    private File fileVirtues;
    private File fileRegions;

    @FXML private TableView<Deed> tblVirtues;

    @FXML private TableColumn tcVirtueName;
    @FXML private TableColumn tcIncrease;
    @FXML private TableColumn tcLevel;
    @FXML private TableColumn tcVirtueType;
    @FXML private TableColumn tcRegion;
    @FXML private TableColumn tcDeedName;

    @FXML private ComboBox<String> virtueCombo;
    @FXML private ComboBox<String> regionsCombo;
    @FXML private ComboBox<String> typeCombo;

    @FXML private CheckBox chkRetired;

    @FXML private TextField minLevel;
    @FXML private TextField maxLevel;

    private int levelCap = 1;

    private List<DeedBuilder> virtues;
    private List<String> regions;

    @FXML public void initialize(URL location, ResourceBundle resources){
        loadFileDescriptors();
        loadVirtues(fileVirtues,fileRegions);

        tcVirtueName.prefWidthProperty().bind(tblVirtues.widthProperty().multiply(2.0/12.0));
        tcIncrease.prefWidthProperty().bind(tblVirtues.widthProperty().multiply(1.0/12.0));
        tcLevel.prefWidthProperty().bind(tblVirtues.widthProperty().multiply(1.0/12.0));
        tcVirtueType.prefWidthProperty().bind(tblVirtues.widthProperty().multiply(2.0/12.0));
        tcRegion.prefWidthProperty().bind(tblVirtues.widthProperty().multiply(2.0 / 12.0));
        tcDeedName.prefWidthProperty().bind(tblVirtues.widthProperty().multiply(4.0 / 12.0));

        ChangeListener<String> listenerString = (ObservableValue<? extends String> selected, String oldSelection, String newSelection) -> customDisp();
        ChangeListener<Boolean> listenerBool = (ObservableValue<? extends Boolean> selected, Boolean oldSelection, Boolean newSelection) -> customDisp();

        virtueCombo.getSelectionModel().selectedItemProperty().addListener(listenerString);

        regionsCombo.getItems().addAll(regions);
        regionsCombo.getSelectionModel().selectedItemProperty().addListener(listenerString);

        typeCombo.getSelectionModel().selectedItemProperty().addListener(listenerString);

        chkRetired.selectedProperty().addListener(listenerBool);

        maxLevel.setText("" + levelCap);

        minLevel.textProperty().addListener(listenerString);
        maxLevel.textProperty().addListener(listenerString);

        customDisp();
    }

    private void addEntry(String virtueName, String increase, int level, String virtueType, String region, String deedName, boolean retired){
        addEntry(new Deed(virtueName, increase, level, virtueType, region, deedName, retired));
    }
    private void addEntry(Deed deed){
        ObservableList<Deed> data = tblVirtues.getItems();
        data.add(deed);
    }

    private void loadFileDescriptors(){
        fileVirtues = new File(new File("").getAbsolutePath() + File.separator + "VirtueDeeds.json");
        fileRegions = new File(new File("").getAbsolutePath() + File.separator + "Regions.json");
    }

    private void customDisp(){
        display(virtues, (DeedBuilder deedBuilder)->{
            Deed deed = deedBuilder.toDeed(regions);
            String nameValue = virtueCombo.getValue();
            String regionValue = regionsCombo.getValue();
            String typeValue = typeCombo.getValue();
            if(deed.getVirtueName().equalsIgnoreCase(nameValue) || "All".equalsIgnoreCase(nameValue) || nameValue == null){
                if(deed.getRegion().equalsIgnoreCase(regionValue) || "All".equalsIgnoreCase(regionValue) || regionValue == null) {
                    if(deed.getVirtueType().equalsIgnoreCase(typeValue) || "All".equalsIgnoreCase(typeValue) || typeValue == null) {
                        int min = (!minLevel.getText().equals("")) ? Integer.parseInt(minLevel.getText()) : 0;
                        int max = (!maxLevel.getText().equals("")) ? Integer.parseInt(maxLevel.getText()) : 0;
                        if (deed.getLevel() >= min && deed.getLevel() <= max) {
                            if (!deed.isRetired() || (deed.isRetired() && chkRetired.isSelected()) ) {
                                addEntry(deed);
                            }
                        }
                    }
                }
            }
        });
    }

    private void loadVirtues(File fVirtues, File fRegions){
        String fileContentsVirtues = "[]";
        try { fileContentsVirtues = FileUtils.readFileToString(fVirtues); }
        catch(IOException e){ e.printStackTrace(); }

        String fileContentsRegions = "[]";
        try { fileContentsRegions = FileUtils.readFileToString(fRegions); }
        catch(IOException e){ e.printStackTrace(); }

        Type listTypeVirtues = new TypeToken<ArrayList<DeedBuilder>>(){}.getType();
        virtues = Main.gson.fromJson(fileContentsVirtues, listTypeVirtues);

        Type listTypeRegions = new TypeToken<ArrayList<String>>(){}.getType();
        regions = Main.gson.fromJson(fileContentsRegions, listTypeRegions);

        for(DeedBuilder db : virtues){
            int dLev = db.toDeed(regions).getLevel();
            if(dLev > levelCap){ levelCap = dLev; }
        }
    }

    private void display(List<DeedBuilder> virts, DisplayAdjust da){
        tblVirtues.getItems().clear();
        virts.forEach(da::handle);
    }

    @FunctionalInterface private interface DisplayAdjust{
        void handle(DeedBuilder deedBuilder);
    }

}
