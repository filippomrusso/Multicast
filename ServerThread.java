import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    ServerSocket server = null;
    Socket client = null;
    BufferedReader inputTastiera;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    String stringaRicevuta = "";
    int numeroRicevuto;
    int[] tabellina = new int [11];
    String[] valoriDaInviare = new String[tabellina.length];
    private boolean exit = false;
    ClientIdentification identification = null;
    String nClient;

    //Il server prende l'indirizzo e la porta del client che vi si connette
    public ServerThread(Socket socket){
        this.client = socket;
        try {
            identification = new ClientIdentification(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        try{
            comunica();
        }
        catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    public void comunica(){

        try {
            inputTastiera = new BufferedReader(new InputStreamReader(System.in));
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());

            while (!exit){
                stringaRicevuta = inDalClient.readLine();
                numeroRicevuto = Integer.parseInt(stringaRicevuta);
                System.out.println(numeroRicevuto);


                if (numeroRicevuto == 0){
                    exit = true;
                    System.out.println("Chiusura della connesione");
                    outVersoClient.writeBytes("Connessione interrotta! \n");
                }

                else {
                    System.out.println("------");

                    System.out.println("Calcolo la tabellina del " + numeroRicevuto + " ...");
                    for (int i = 0; i < tabellina.length; i++) {
                        tabellina[i] = numeroRicevuto * i;

                    }

                    System.out.println("Invio i risultati");
                    for (int i = 0; i < tabellina.length; i++) {
                        valoriDaInviare[i] = Integer.toString(tabellina[i]);
                        outVersoClient.writeBytes(valoriDaInviare[i] + "\n");

                    }

                    System.out.println("Comunica con un server specifico");
                    nClient = inputTastiera.readLine();




                }

            }

            outVersoClient.close();
            inDalClient.close();
            client.close();

        }

        catch (Exception e){

        }

    }
}
