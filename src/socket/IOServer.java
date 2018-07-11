package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * create by tan on 2018/7/11
 * 同步阻塞 IO 实现 Socket 通信
 **/
public class IOServer {
    public static void main(String[] args) throws IOException {
        try {
            System.out.println("...服务端启动了...");
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            // 创建输入流来换取客户端的输出流
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            // 创建输出流来向客户端发送消息
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            System.out.println("客户端ip：" + socket.getInetAddress() + "已连接");
            dataOutputStream.writeUTF("欢迎连接");
            String str = "";
            while (true) {
                str = dataInputStream.readUTF();
                if (str.contains("exit")) {
                    System.out.println("客户端ip:" + socket.getInetAddress() + "已断开连接");
                } else {
                    System.out.println("来自客户端的数据:" + str);
                    dataOutputStream.writeUTF("数据已接收");
                }
            }
        } catch (SocketException socketException) {
            System.out.println("服务端安全退出");
        }
    }
}
