package quadcore.paintproject.paint.model.app;

import quadcore.paintproject.paint.model.saveload.FileManager;

import java.io.File;
import java.io.IOException;


public class App {

    static private App instance;
    private Canvas canvas;
    private final FileManager fileManager = new FileManager();

    private App() {
        this.canvas = new Canvas();
    }

    public static App getInstance() {
        if (instance == null) instance = new App();
        return instance;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public Canvas createCanvas() {
        //System.out.println("Called");
        this.canvas = new Canvas();
        return this.canvas;
    }

    /**
     * do not forget to delete file from server after sending to front-end
     *
     * @param type either xml or json; the format in which the canvas is to be saved
     * @return file created in specified format
     */
    public File save(String type) throws IOException {
        if (type.equalsIgnoreCase("xml")) {
            return fileManager.saveAsXML(this.canvas);
        } else if (type.equalsIgnoreCase("json")){
            return fileManager.saveAsJson(this.canvas);
        }
        throw new RuntimeException("Invalid File Format: Valid formats are xml and json");
    }

    public Canvas load(String file, String type) throws IOException {
        if (type.equalsIgnoreCase("xml")) {
            this.canvas = fileManager.loadXML(file);
            return canvas;
        } else if (type.equalsIgnoreCase("json")){
            this.canvas = fileManager.loadJson(file);
            return canvas;
        }
        throw new RuntimeException("Invalid File Format: Valid formats are xml and json");
    }
}
