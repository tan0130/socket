package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by tan on 2018/7/11
 * 使用线程池来解决阻塞 IO 问题
 **/
public class PoolServer {
    public static void main(String[] args) throws IOException {

            System.out.println("...服务端启动了...");


            // 创建无界缓冲池
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String str = "";
                        ServerSocket serverSocket = new ServerSocket(8080);
                        Socket socket = serverSocket.accept();
                        System.out.println("客户端ip：" + socket.getInetAddress() + "已连接");
                        // 创建输入流来换取客户端的输出流
                        InputStream inputStream = socket.getInputStream();
                        DataInputStream dataInputStream = new DataInputStream(inputStream);
                        // 创建输出流来向客户端发送消息
                        OutputStream outputStream = socket.getOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                        dataOutputStream.writeUTF("欢迎连接");
                        // str = ;
                        while (true) {
                            str = dataInputStream.readUTF();
                            if (str.contains("exit")) {
                                System.out.println("客户端ip:" + socket.getInetAddress() + "已断开连接");
                            } else {
                                System.out.println("来自客户端的数据:" + str);
                                dataOutputStream.writeUTF("数据已接收");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
