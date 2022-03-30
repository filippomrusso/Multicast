import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    String nomeServer = "localhost";
    int porta = 6789;
    Socket socket = null;
    BufferedReader inputTastiera;
    BufferedReader inDalServer;
    DataOutputStream outVersoServer;
    String stringaDaInviare;
    String stringaDaRicevere;

    String[] tabellina = new String[10];
    boolean condizione = true;


    public Socket connetti() {

        try{
            inputTastiera = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(nomeServer, porta);
            outVersoServer = new DataOutputStream(socket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("ok");
        }

        catch (UnknownHostException e){
            System.err.println("Connessione scaduta");
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(" Errore");
            System.exit(1);
        }

        return socket;
    }

    public void comunica() {

        while (condizione) {
            try {
                System.out.println("Calcola la tabellina di ...");
                stringaDaInviare = inputTastiera.readLine() + "\n";
                outVersoServer.writeBytes(stringaDaInviare);
                stringaDaRicevere = inDalServer.readLine() + "\n";

                /*if (stringaDaRicevere.equalsIgnoreCase("fine")) {
                    System.out.println("Fine connessione");
                    condizione = false;
                    socket.close();
                }*/

                for(int i=0; i<10; i++){
                    tabellina[i] = inDalServer.readLine();
                }

                for (int i=0; i<10; i++){
                    System.out.println(tabellina[i]);
                }

                System.out.println("Stringa ricevuta = " + stringaDaRicevere);
                inDalServer.close();
                outVersoServer.close();
                socket.close();

            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connetti();
        client.comunica();
    }


}
