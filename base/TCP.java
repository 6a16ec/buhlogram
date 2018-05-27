package com.example.user.myapplication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
public class TCP extends Thread
{
    public boolean error = true;

    Socket socket;

    public TCP() {
        try
        {
            Socket socket = new Socket(InetAddress.getByName("phisika.tk").getHostAddress(), 777);
            this.socket = socket;
            error = false;
        }
        catch(Exception e){
            error = true;
        }
    }


    public void write(String string) throws IOException {
        socket.getOutputStream().write(string.getBytes());
    }

    public String read() throws IOException {
        byte buf[] = new byte[1024*1024*8];
        int r = socket.getInputStream().read(buf);
        String data = new String(buf, 0, r);
        return data;
    }


}
