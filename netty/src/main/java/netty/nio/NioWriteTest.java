package netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class NioWriteTest {

    /**
     * 用channel写文件
     */
    public static void main (String[] args) throws Exception {

        String str = "hello,好朋友";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        FileOutputStream outputStream = new FileOutputStream("./file1.txt");
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        outputStream.getChannel().write(byteBuffer);

        outputStream.close();
    }

}
