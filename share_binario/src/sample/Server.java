package sample;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public void serve(String[] st){
        try(ServerSocket serverSocket = new ServerSocket(5245)){
            while (true){
                new Thread(new ServerImg(serverSocket.accept(),st)).start();
                break;
            }
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
