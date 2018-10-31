/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualloop;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author My ASUS
 */
public class visualloop extends Application implements Runnable{
    
    //Loop Parameters
    private final static int MAX_FPS = 60;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    
    //Thread
    private Thread thread;
    private volatile boolean running = true;
    
    //Canvas
    Canvas canvas = null;
    
    //ATRIBUT
    float cx = 0;
    float cy = 0;
    float velocity = 2;
    float sudutrotasi =0f;
    float sudut;
    float kecepatanSudut = 2;
    float sisi = 50;
    
    //KEYBOARD HANDLER
    ArrayList<String> inputKeyboard = new ArrayList<>();
    
    public visualloop(){
        resume();
    }
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene (root);
        canvas = new Canvas (700,700);
        root.getChildren().add(canvas);
        
        //HANDLING KEYBOARD EVENT
        scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!inputKeyboard.contains(code)){
                inputKeyboard.add(code);
                System.out.println(code);
            }
        });
        scene.setOnKeyReleased ((KeyEvent e) -> {
            String code = e.getCode().toString();
            inputKeyboard.remove(code);
        });
         //HANDLING MOUSE EVENT
         scene.setOnMouseClicked ((MouseEvent e) -> {
        });
        
        //primaryStage.getIcons().add (new Image (getClass().getResourceAsStream)("logo.Jpg")));
        primaryStage.setTitle("Visual Loop");
        primaryStage.setScene (scene);
        primaryStage.show();
    }
 public static void main(String[] args) {
        launch(args);
    }
    
    //THREAD
    private void resume (){
        reset();
        thread = new Thread (this);
        running = true;
        thread.start();
    }

    //THREAD
    private void pause(){
    running = false;
    try {
            thread.join();
    } catch (InterruptedException e) {
        }
    }
    
    //LOOP
    private void reset (){
    }
    
    //loop
    private void update (){   
        if (inputKeyboard.contains("RIGHT")){//kotak bergerak kekanan saat
            cx+=2;
        } else if (inputKeyboard.contains("LEFT")){//kotak 
            cx-=2;
        }
        if (inputKeyboard.contains("UP")){//
            cy-=2;    
              
        }else if (inputKeyboard.contains("DOWN")){
            cy+=2;
        }
        if (inputKeyboard.contains("R")){
              sudutrotasi+=2;
        }
    }
    
    //LOOP
    private void draw (){
    try {
        if (canvas != null) {
           GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            //CONTOH MENGGAMBAR KOTAK
            gc.save();
            gc.translate(cx, cy);
            gc.rotate(sudut);
            gc.setFill(Color.PINK);
            gc.fillRect(-sisi/2.0, -sisi/2.0, 130, 200);            
            gc.restore();

//            //CEK INPUT KEYBOARD
//            if (inputKeyboard.contains("UP")){
//                gc.setFill(Color.PINK);
//                gc.fillText("UP",300,300);
//            }
        }
    }catch (Exception e) {
    }
}                
            @Override
public void run(){
    long beginTime;
    long timeDiff;
    int sleepTime = 0;
    int framesSkipped;
    //LOOP WHILE running = true;
    while (running){
        try{
            synchronized (this ){
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;
                update();
                draw();
           }
           timeDiff=System.currentTimeMillis() - beginTime;
           sleepTime=(int) (FRAME_PERIOD - timeDiff);
           if (sleepTime >0) {
            try {
                Thread.sleep(sleepTime);
            }catch (InterruptedException e) {
        }
    }
        while (sleepTime <0 && framesSkipped <MAX_FRAME_SKIPS){
        update();
        sleepTime += FRAME_PERIOD;
        framesSkipped++;
    } 
        }finally{
    }
    }
}
}