package netty.blockingio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingIOTest {

    public static void main (String[] args) throws IOException {

        ExecutorService cacheExecutor = Executors.newCachedThreadPool();

        ServerSocket socket = new ServerSocket(6666);
        System.out.println("启动连接 127.0.0.1:6666");
        while(true) {
            Socket accept = socket.accept();
            System.out.println("有连接进来");
            cacheExecutor.execute(() -> {
                try {
                    byte[] bytes = new byte[1024];
                    InputStream inputStream = accept.getInputStream();

                    while (true) {
                        int read = inputStream.read(bytes);
                        if (read!=-1) {
                            System.out.println(new String(bytes,0,read));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        System.out.println("关闭和client的连接");
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
