package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp {

    private static String host = "netology.homework";
    private static int hostPort = 1235;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String messageFromHost;
        System.out.println("Client App startup!");
        try(Socket clientSocket = new Socket(host, hostPort);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Пока от сервера не придет сообщение, начинающееся на Welcome читаем от него сообщения и отвечаем на них
            while(!(messageFromHost = in.readLine()).startsWith("Welcome")) {
                System.out.println(messageFromHost);
                out.println(scanner.nextLine());
            }
            System.out.println(messageFromHost);
        } catch (UnknownHostException e) {
            System.out.println("Неверно задано имя сервера!" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка соединения с сервером или чтения/записи в поток!" + e.getMessage());
        }
    }
}
