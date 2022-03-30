import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Multiserver {

    private boolean condizione = true;
    private int clientCounter = 0;
    String n=null;
    private ClientIdentification clientId = null;
    private ClientIdentification [] arrayClients = new ClientIdentification[4];
    Scanner input = new Scanner(System.in);

    public void start(){
        ServerSocket server = null;

        try {
            server = new ServerSocket(6789);

            while(condizione){
                System.out.println("Server in attesa");
                Socket clientInComunicazione = server.accept();
                clientCounter++;
                System.out.println("Client " + clientCounter + ":" + clientInComunicazione.getLocalSocketAddress() + " stanza "+ clientInComunicazione.getPort());
                System.out.println("Ciao client come ti chiami?");
                n = input.nextLine();
                arrayClients[clientCounter] = new ClientIdentification(clientInComunicazione,n);
                clientInComunicazione = arrayClients[clientCounter].getClient();
                ServerThread processo = new ServerThread(clientInComunicazione);
                processo.start();
            }
        }
        catch(Exception e){}
    }

    public static void main(String[] args) {
        Multiserver serverBenvenuto = new Multiserver();
        serverBenvenuto.start();

    }
}

