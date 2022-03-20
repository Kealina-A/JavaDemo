package netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioCopyFileTest1 {

    /**
     * 文件复制
     */
    public static void main (String[] args) throws Exception {

        FileInputStream inputStream = new FileInputStream("./file1.txt");
        FileOutputStream outputStream = new FileOutputStream("./file2.txt");

        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        inputStreamChannel.read(byteBuffer);
        byteBuffer.flip();
        outputStreamChannel.write(byteBuffer);
        inputStream.close();
        outputStream.close();
    }
}
