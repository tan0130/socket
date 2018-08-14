package socket;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

/**
 * create by tan on 2018/7/11
 * 阻塞 IO 客户端
 **/
public class IOClient {
    public static void main(String[] args) throws IOException {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 格式化时间
        String current_time = localDateTime.format(dateTimeFormatter);

        System.out.println(current_time + ":...TCP 客户端启动了...");
        Socket socket = new Socket("127.0.0.1",8080);
        // 创建输出流，向服务端发送数据
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        // 创建输入流，接收服务端发出的数据
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(current_time + ":请输入内容：");
            String str = scanner.nextLine();
            System.out.println(current_time + ":接收到服务端返回的数据：" + dataInputStream.readUTF());
            dataOutputStream.writeUTF(str);
            if ("exit".equals(str)) {
                System.exit(0);
            }
        }

    }
}
