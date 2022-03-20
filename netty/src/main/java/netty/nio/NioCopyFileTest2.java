package netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NioCopyFileTest2 {

    /**
     * Nio 文件复制
     */
    public static void main (String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("./file1.txt");
        FileOutputStream outputStream =new FileOutputStream("./file2.txt");

        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());
        inputStream.close();
        outputStream.close();
    }
}
