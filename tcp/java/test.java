package tcp;

import java.net.InetAddress;
import java.net.Socket;
public class test extends Thread
{
    public test(String[] args) {
        try
        {
            // открываем сокет и коннектимся к localhost:3128
            // получаем сокет сервера // InetAddress.getByName("phisika.tk").getHostAddress()
//            Socket s = new Socket(InetAddress.getByName("phisika.tk").getHostAddress(), 777);
            Socket s = new Socket("127.0.0.1", 777);
//            s.setSoTimeout(100000);


            String line = "sdc AAF";
            s.getOutputStream().write(line.getBytes());

            byte buf[] = new byte[1024*1024*8];
            int r = s.getInputStream().read(buf);
            String data = new String(buf, 0, r);

            System.out.println(data);
        }
        catch(Exception e)
        {System.out.println("init eUrror: "+e);} // вывод исключений
    }
}
