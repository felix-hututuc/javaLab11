import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {


        int PORT = 8765;
        String serverIP = "127.0.0.1";
        Socket socket = new Socket(serverIP, PORT);

        socket.setSoTimeout(60 * 1000);

        try (PrintWriter output = new PrintWriter(
                socket.getOutputStream(), true);

             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             Scanner cin = new Scanner(System.in)){

            while (true) {
                String request = cin.nextLine();
                output.println(request);
                String answer = input.readLine();
                if (request.equals("read")) {
                    answer = answer.replace("--newline--", "\n");
                }
                System.out.println(answer);
                if (request.equals("stop") || request.equals("exit")) {
                    System.out.println("Closing the client...\n");
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out ... ");
        } catch (IOException e) {
            System.err.println("Communication error...\n" + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
