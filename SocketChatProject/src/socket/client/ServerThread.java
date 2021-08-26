package socket.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread {
    private static int port;

    public static void main(int port, String nickname) {
    	
    	port = port;
        ServerSocket serverSocket = null;
        List<PrintWriter> listWriters = new ArrayList<PrintWriter>();

        try {
// 1. ���� ���� ����
            serverSocket = new ServerSocket();

// 2. ���ε�
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind( new InetSocketAddress(hostAddress, port));
            consoleLog("���� ��ٸ� - " + hostAddress + ":" + port);
            System.out.println(serverSocket.getLocalPort());
// 3. ��û ���
            
            ChatClientApp chatClientApp = new ChatClientApp();
    		chatClientApp.main(port, nickname);
            while(true) {
                Socket socket = serverSocket.accept();
                new ProcessThread(socket, listWriters).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if( serverSocket != null && !serverSocket.isClosed() ) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consoleLog(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }
}
