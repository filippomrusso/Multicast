import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientIdentification {

    DataOutputStream outVersoClient;
    private Socket client;


    private SocketAddress ipClient;
    private String nomeClient;
    private String stanza;

    public ClientIdentification(Socket socketClient) throws IOException {
        outVersoClient = new DataOutputStream(socketClient.getOutputStream());
        this.client = socketClient;
        this.stanza = Integer.toString(socketClient.getPort());
        this.ipClient = socketClient.getLocalSocketAddress();
    }

    public ClientIdentification(Socket socketClient, String nome)throws IOException{
        outVersoClient = new DataOutputStream(socketClient.getOutputStream());
        this.client = socketClient;
        this.stanza = Integer.toString(socketClient.getPort());
        this.ipClient = socketClient.getLocalSocketAddress();
        this.nomeClient = nome;
    }

    public void inviaDettagliClient(ClientIdentification client) throws IOException {
        outVersoClient.writeBytes(client.getStanza() + "\n");
        outVersoClient.writeBytes(String.valueOf(client.getIpClient()) + "\n");
        if(client.getNomeClient() != "") {
            outVersoClient.writeBytes(client.getNomeClient() + "\n");
        }
        else{
            outVersoClient.close();
        }
    }

    public Socket getClient() {
        return client;
    }

    public SocketAddress getIpClient() {
        return ipClient;
    }

    public String getStanza() {
        return stanza;
    }

    public void setStanza(String stanza) {
        this.stanza = stanza;
    }

    public void setIpClient(SocketAddress ipClient) {
        this.ipClient = ipClient;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public String getNomeClient() {
        return nomeClient;
    }

    public void setNomeClient(String nomeClient) {
        this.nomeClient = nomeClient;
    }

}
