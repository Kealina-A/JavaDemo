package netty.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;

public class NioReadTest {

    /**
     * 用channel读文件
     */
    public static void main (String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("./file1.txt");
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        fileInputStream.getChannel().read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
