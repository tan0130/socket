package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * create by tan on 2018/7/11
 * 非阻塞式 IO 客户端
 **/
public class NioClient {
    public static void main(String[] args) throws IOException {
        System.out.println("...客户端启动了...");
        // 1. 创建通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
        // 2. 切换非阻塞
        socketChannel.configureBlocking(false);
        // 3. 指定缓冲区大小
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入内容:");
        while (scanner.hasNext()) {
            System.out.println("请输入内容:");
            String str = scanner.nextLine();
            allocate.put((new Date().toString() + "\n" + str).getBytes());
            // 4. 切换到读取模式
            allocate.flip();
            socketChannel.write(allocate);
            allocate.clear();
        }
        socketChannel.close();
    }
}
