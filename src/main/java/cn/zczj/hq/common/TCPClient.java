package cn.zczj.hq.common;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;

public class TCPClient {

    public static void main(String[] args) {
        // 服务器的IP地址和端口号
        String serverAddress = "192.168.1.131";
        int serverPort = 12346; // 你的服务器端口号

        try {
            // 创建一个Socket对象，连接到服务器
            Socket socket = new Socket(serverAddress, serverPort);

            // 读取图片文件并将内容转换为Base64字符串
            String base64Image = encodeImageToBase64("D:/test2.png");

            // 构建JSON请求字符串
            String jsonRequest = "{\"image\": \"" + base64Image + "\"}";
            System.out.println(jsonRequest.length());
            for (int i = 0; i < 5; i++) {
                // 获取输出流，用于向服务器发送数据
                OutputStream outputStream = socket.getOutputStream();

                // 向服务器发送JSON请求
                outputStream.write(jsonRequest.getBytes());
                outputStream.flush();

                // 获取输入流，用于从服务器接收数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                line = reader.readLine();
                response.append(line);

                // 打印从服务器接收到的响应
                System.out.println("Response from server: " + response.toString());

            }

            // 关闭连接
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main3(String[] args) {
//        String s = encodeImageToBase64("D:/test3.webp");
        String s = encodeImageToBase64("D:/test3.jpg");
        System.out.println(s.length());
    }
    // 将图片文件转换为Base64字符串
    private static String encodeImageToBase64(String imagePath) {
        String base64Image = "";
        try {
            File file = new File(imagePath);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            base64Image = Base64.getEncoder().encodeToString(bytes);
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
}
