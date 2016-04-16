package com.queekus.lotrovirtues;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Main extends Application {

    public static Gson gson;

    public final double windowMinWidth = 800;
    public final double windowMinHeight = 600;
    private final double borderH = 20;
    private final double borderV = 40;

    public static void main(String[] args) {
        gson = new GsonBuilder().create();
        String fileURLS = "";

        try{ fileURLS = getLatestFileURLs(); }
        catch(IOException e){ e.printStackTrace(); }

        if(!fileURLS.equals("")){
            FileDescriptors fd = Main.gson.fromJson(fileURLS, FileDescriptors.class);

            if(args.length == 0){ // for debugging so I can make sure files are working before pushing
                try{
                    URL website = new URL(fd.virtues);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream("VirtueDeeds.json");
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    URL website = new URL(fd.regions);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream("Regions.json");
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        launch(args);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        Parent rootLogin = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));

        Scene scene = new Scene(rootLogin, windowMinWidth, windowMinHeight);

        primaryStage.setTitle("Lotro Virtues");
        primaryStage.setMinWidth(windowMinWidth + borderH);
        primaryStage.setMinHeight(windowMinHeight + borderV);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    private static String getLatestFileURLs() throws IOException{
        URL fileInformation = new URL("https://raw.githubusercontent.com/queekusme/Lotro-Virtues/master/updatefile");
        BufferedReader in = new BufferedReader( new InputStreamReader(fileInformation.openStream()));

        String inputLine;
        StringBuilder inputBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            inputBuilder.append(inputLine);
        }
        in.close();
        return inputBuilder.toString();
    }

    private class FileDescriptors{
        public String virtues;
        public String regions;
    }
}
