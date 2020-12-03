package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Client {
    public String[] Files;

    public void receive(){
        BufferedOutputStream bos = null;
        try (Socket socket = new Socket("localhost", 5245);//127.0.1.1
             BufferedReader echos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter send_data_to_server = new PrintWriter(socket.getOutputStream(), true)) {
            String[] temp,temp1;
            String t, response;
            int received = 0;

            send_data_to_server.println(InetAddress.getLocalHost().getHostAddress());

            response = echos.readLine();
            temp = response.split("##");
            temp1 = new String[temp.length];
            for (int i = 0; i < temp.length; i++) {
                t = temp[i];
                temp1[i] = t.replace(".", " ").split(" ")[1];
            }
            Files = temp;
            System.out.println(Arrays.toString(Files));
            for (int i = 0; i < temp.length; i++) {
                if (!temp1[i].equals("txt")) {
                    InputStream io = socket.getInputStream();
                    byte[] size = new byte[4];
                    io.read(size);
                    int sz = ByteBuffer.wrap(size).asIntBuffer().get();
                    byte[] ig = new byte[sz];
                    io.read(ig);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(ig));
                    ImageIO.write(image, temp1[i], new File(temp[i]));
                    System.out.println("received");
                } else {
                    int FILE_SIZE = 6022386;
                    byte[] bytes = new byte[FILE_SIZE];
                    InputStream is = socket.getInputStream();
                    bos = new BufferedOutputStream(new FileOutputStream(temp[i]));
                    int bt = is.read(bytes, 0, bytes.length);
                    int count = bt;
                    bos.write(bytes, 0, count);
                    bos.close();
                }
                received++;
            }
            System.out.println("Received "+received+" File(s) from server.");
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public String[] getFiles() {
        return Files;
    }
}
