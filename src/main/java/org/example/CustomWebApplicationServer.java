package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {

    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class); //로그를 찍기 위한 코드

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        //서버 소켓을 만듬
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port", port);

            //클라이언트 소켓을 만듬
            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client");

            //accept()를 이용해 서버 소켓이 대기를 하게 하고는 클라이언트 소캣이 들어오면 널이 아니게 됨
            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected");

                try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    DataOutputStream dos = new DataOutputStream(out);

                    String line;
                    while ((line = br.readLine()) != "") {
                        System.out.println(line);
                    }
                }
            }
        }
    }
}
