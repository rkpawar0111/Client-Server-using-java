package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerImg extends Thread{
    private final Socket socket;
    private final String[] Files_to_share;

    public ServerImg(Socket socket, String[] st) {
        this.socket = socket;
        this.Files_to_share = st;
    }

    @Override
    public void run() {
        BufferedReader input = null;
        PrintWriter output = null;
        OutputStream outputStream = null;
        BufferedInputStream bis;
        try {
            int count = 0;
            String t, client_request = "";
            String[] temp, file_extensions = new String[this.Files_to_share.length];
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);

            for (int i = 0; i<this.Files_to_share.length; i++) {
                temp = this.Files_to_share[i].replace("\\", " ").split(" ");
                t = temp[temp.length-1];
                file_extensions[i] = t.replace(".", " ").split(" ")[1];
                client_request += temp[temp.length - 1] + "##";
            }

            String client_ip_address = input.readLine();
            System.out.println("Received client request. IP address= "+client_ip_address);
            if(client_ip_address == null){
                System.out.println("Error!");
            }
            output.println(client_request);

            for (int i = 0; i<this.Files_to_share.length; i++) {
                if (!file_extensions[i].equals("txt")){
                    outputStream = socket.getOutputStream();
                    BufferedImage image = ImageIO.read(new File(this.Files_to_share[i]));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, file_extensions[i], byteArrayOutputStream);
                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    outputStream.write(size);
                    outputStream.write(byteArrayOutputStream.toByteArray());
                }else {
                    File file = new File(this.Files_to_share[i]);
                    byte[] bytes = new byte[(int) file.length()];
                    bis = new BufferedInputStream(new FileInputStream(file));
                    bis.read(bytes, 0, bytes.length);
                    outputStream = socket.getOutputStream();
                    outputStream.write(bytes, 0, bytes.length);
                }
                outputStream.flush();
                count ++;
            }
            System.out.println("Total "+count+" File(s) sent.");
        } catch (IOException e) {
            System.out.println(e.toString());
        }finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                }
                if(input != null){
                    input.close();
                }
                if(output != null){
                    output.close();
                }
                if (socket != null){
                    socket.close();
                }
            }catch (IOException e){
                System.out.println("Error occurred during closing of files");
                e.printStackTrace();
            }
        }

    }
}
/*
            for (int i = 0; i<this.Files_to_share.length; i++) {
                if (!temp1[i].equals("txt")){
                    outputStream = socket.getOutputStream();
                    BufferedImage image = ImageIO.read(new File(this.Files_to_share[i]));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, temp1[i], byteArrayOutputStream);
                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    outputStream.write(size);
                    outputStream.write(byteArrayOutputStream.toByteArray());
                    outputStream.flush();
                    count ++;
                }else {
                    File file = new File(this.Files_to_share[i]);
                    byte[] bytes = new byte[(int) file.length()];
                    bis = new BufferedInputStream(new FileInputStream(file));
                    bis.read(bytes, 0, bytes.length);
                    outputStream = socket.getOutputStream();
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    count++;
                }
            }
            System.out.println("Total "+count+" File(s) sent.");

 */