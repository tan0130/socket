package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * create by tan on 2018/7/11
 * 非阻塞式 IO 服务端
 **/
public class NioServer {
    public static void main(String[] args) throws IOException {
        // 获取当前时间
        String current_time = function.Function.getCurrentTime();


        System.out.println(current_time + ":...服务端已经启动了...");
        // 1. 创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2. 切换非阻塞
        serverSocketChannel.configureBlocking(false);
        // 3. 绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 4. 获取选择器
        Selector selector = Selector.open();
        // 5. 将通道注册到选择器，并且指定监听接收事件
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        // 6. 轮询式获取“已经准备就绪”的事件
        while (selector.select() > 0) {
            // 7. 获取当前选择器所在的注册的“选择键”
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                // 8. 获取准备就绪的事件
                SelectionKey selectionKey = iterator.next();
                // 9. 判断具体已准备就绪的事件
                if (selectionKey.isAcceptable()) {
                    // 10. 若已经就绪，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 11. 设置阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12. 在服务器上注册通道
                    socketChannel.register(selector,SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // 13. 获取当前选择器就绪状态的通道
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    // 14. 读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        if (new String(byteBuffer.array(), 0 ,len).equals("exit")) {
                            System.out.println(current_time + ":客户端已安全退出");
                            System.exit(0);
                        } else {
                            System.out.println(current_time + ":收到来自客户端的数据:" +  new String(byteBuffer.array(), 0 ,len));

                        }
                        byteBuffer.clear();
                    }
                } else if (selectionKey.isWritable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.clear();
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    String str = "hello world";
                    byteBuffer.put(str.getBytes());
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    //socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println(current_time + ":服务端向客户端发送信息:" + str);
                }
            }
            iterator.remove();
        }
        serverSocketChannel.close();
    }
}
