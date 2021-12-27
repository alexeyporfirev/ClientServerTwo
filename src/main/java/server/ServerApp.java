package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    private static int port = 1234;

    public static void main(String[] args) {
        System.out.println("Server startup!");

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("New connection accepted: ");
                out.println("Write your name: ");
                String name = in.readLine();
                out.println("Hello, " + name + "! Are you child? (yes/no)");

                boolean check = false;
                String isChildMsg = "";

                // задаем один и тот же вопрос пока клиент не ответит да(yes) или нет(no)
                while (!check) {
                    isChildMsg = in.readLine();
                    if (isChildMsg.equalsIgnoreCase("yes")) {
                        out.println(String.format("Welcome to the kids area, %s! Let's play!", name));
                        check = true;
                    } else if (isChildMsg.equalsIgnoreCase("no")) {
                        out.println(
                                String.format(
                                        "Welcome to the adult zone, %s! Have a good rest, or a good working day!",
                                        name
                                )
                        );
                        check = true;
                    } else {
                        out.println("Hello, " + name + "! Are you child? (yes/no)");
                    }
                }

                // выводим информацию об этом новом клиенте
                System.out.println(
                        String.format(
                                "\t%s: port is %d, child: %s",
                                name,
                                clientSocket.getPort(),
                                isChildMsg.equalsIgnoreCase("yes") ? "true" : "false"
                        )
                );

            } catch (IOException e) {
                System.out.println("Ошибка создания серверного сокета или потока чтения/записи!" + e.getMessage());
            }
        }
    }
}
