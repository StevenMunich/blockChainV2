import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Block {
    int index;
    String timestamp;
    String data;
    String prevHash;
    String hash;
    List<String> transactions;
    String merkleRoot;

    public Block(int index, String data,List<String> transactions, String prevHash) {
        this.index = index;
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.data = data;
        this.prevHash = prevHash;
        this.hash = calculateHash();
        this.transactions = transactions;
        this.merkleRoot = computeMerkleRoot(transactions);
    }

    public String calculateHash() {
        String input = index + timestamp + data + prevHash;
        return applySha256(input);
    }

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }//end Function
    public static String computeMerkleRoot(List<String> transactions) {
        if (transactions.isEmpty()) return "";
        List<String> tempList = new ArrayList<>(transactions);

        while (tempList.size() > 1) {
            List<String> updatedList = new ArrayList<>();
            for (int i = 0; i < tempList.size(); i += 2) {
                String left = tempList.get(i);
                String right = (i + 1 < tempList.size()) ? tempList.get(i + 1) : left;
                updatedList.add(applySha256(left + right));
                System.out.println("in Loop, value of List is: " + updatedList);
            }
            tempList = updatedList;
        }
        System.out.println("Root: " + tempList.get(0));
        return tempList.get(0);
    }
}//End Class

class Blockchain {
    List<Block> chain;
    List<String> pendingTransactions;


    public Blockchain() {
        chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();

        List<String> transactions = new ArrayList<>();
        transactions.add("Creation of the Genisis Block");
        chain.add(new Block(0, "Genesis Block",transactions, "0"));

    }

    public void addBlock(String data) {
        //NOTE: data is anything we want to add as a USER. or SALT.
        //The main block data is going to be built in the mine() function
        Block prevBlock = chain.get(chain.size() - 1);
        List<String> transactions = new ArrayList<>();
        chain.add(new Block(chain.size(), data,transactions, prevBlock.hash));
    }

    public void addTransaction(String transaction) {
        pendingTransactions.add(transaction);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currBlock = chain.get(i);
            Block prevBlock = chain.get(i - 1);
            if (!currBlock.hash.equals(currBlock.calculateHash()) || !currBlock.prevHash.equals(prevBlock.hash)) {
                return false;
            }
        }//End Loop
        return true;
    }//End Function

    //- Transactions are collected until enough exist to form a block.
    public void mineBlock() {
        if (pendingTransactions.isEmpty()) {
            System.out.println("No transactions to mine!");
            return;
        }

        Block prevBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(chain.size(), pendingTransactions.toString(), pendingTransactions, prevBlock.hash);

        chain.add(newBlock);
        pendingTransactions.clear(); // Transactions are now in the block

        System.out.println("Block mined successfully!");
    }//End Function

}//End BlockChain


class bcNode {
    private List<Block> blockchain;
    private int port;

    public List<Block> getBlockchain(){return blockchain;}
    public bcNode(int port, List<Block> b) {
        this.port = port;
        blockchain = b;
        //blockchain = new ArrayList<>();
        //blockchain.add(new Block(0, "Genesis Block", List.of("Genesis Block"), "0"));
        startServer();
    }

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Node running on port " + port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    handleClient(socket);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            String request = reader.readLine();
            if (request.equals("GET_BLOCKCHAIN")) {
                writer.println(blockchain.toString());
            } else if (request.startsWith("ADD_BLOCK")) {
                String data = request.replace("ADD_BLOCK ", ""); //gets appended data from request.
                addBlock(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBlock(String data) {
        Block prevBlock = blockchain.get(blockchain.size() - 1);
        blockchain.add(new Block(blockchain.size(), data, List.of(data), prevBlock.hash));
        System.out.println("New block added: " + data);
    }
    /*
    4. Connecting Multiple Nodes
    To allow nodes to communicate:

    Implement peer discovery using a bootstrap node.

    Enable blockchain synchronization by exchanging data upon connection.

    Use JSON or Protocol Buffers to format messages.

    Example Client Code to Connect to Another Node:
     */

    //This function can be hacked and requires a consensus algo.
    public void sendTransaction(String host, int port, String data) { //Was static, I am changing that.
        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println("ADD_BLOCK " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end Function


    //public static void main(String[] args) {

    //}
}//End Class


public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        blockchain.addBlock("Transaction 1");
        blockchain.addBlock("Transaction 2");

        print_BlockChain(blockchain);

        System.out.println("Is blockchain valid? " + blockchain.isChainValid());

        //Nodes
        bcNode node1 = new bcNode(83, blockchain.chain);
        bcNode node2 = new bcNode(82, blockchain.chain); //Is a shared OBJECT
        printColors("CYAN", "After original Blockchain");

        String myIPaddress = "127.0.0.1";

        System.out.println("Enter 1 to add block");
        Scanner scanner = new Scanner(System.in);
        if(scanner.next().equals("1"))
            node2.sendTransaction(myIPaddress, 83, " overDaNetworkBABYY");
        System.out.println("Enter 1 to add block AGAIN.");
        if(scanner.next().equals("1"))
            node2.sendTransaction(myIPaddress, 83, " overDaNetworkDADDY");

        //System.out.println(Arrays.toString(node1.getBlockchain().toArray()));
        System.out.println("Enter 1 to print out the new blockchain. This pause is because of race conditions.");
        if(scanner.next().equals("1"))
            print_BlockChain(blockchain);

        printColors("Purple", "Debugging Transactions");
        blockchain.addTransaction("Fat Albert");
        blockchain.addTransaction("in a Can");
        blockchain.addTransaction("is what Hamburger meat");
        blockchain.addTransaction("has spoken!");
        blockchain.mineBlock();
        //done mining

        printColors("purple", "Done!");
        print_BlockChain(blockchain);

    }//Main

    private static void print_BlockChain(Blockchain blockchain){
        for(Block b : blockchain.chain){
            System.out.println("\n\n");
            System.out.println(b.index);
            System.out.println(b.merkleRoot);
            System.out.println(b.data);
            printColors("GREEN", b.transactions.toString()); //problems mining
            System.out.println(b.hash);
            System.out.println(b.prevHash);
            System.out.println(b.timestamp);
        }//End Loop
    }//end function



    private static void printColors(String ColorI, String Text){

        final String ANSI_RESET = "\u001B[0m";

        String Color = ColorI.toUpperCase();

        final String RED = "\u001B[31m";
        if (Color.equals("RED"))
            System.out.println( RED + Text + ANSI_RESET);
        final String RED_BACKGROUND = 	"\u001B[41m";
        if (Color.equals("RED_BACKGROUND"))
            System.out.println( RED_BACKGROUND + Text + ANSI_RESET);
        final String GREEN	= "\u001B[32m";
        if (Color.equals("GREEN"))
            System.out.println( GREEN + Text + ANSI_RESET);
        final String GREEN_BACKGROUND = "\u001B[42m";
        if (Color.equals("GREEN_BACKGROUND"))
            System.out.println( GREEN_BACKGROUND + Text + ANSI_RESET);
        final String YELLOW = "\u001B[33m";
        if (Color.equals("YELLOW"))
            System.out.println( YELLOW + Text + ANSI_RESET);
        final String YELLOW_BACKGROUND	= "\u001B[43m";
        if (Color.equals("YELLOW_BACKGROUND"))
            System.out.println( YELLOW_BACKGROUND + Text + ANSI_RESET);
        final String BLUE =	"\u001B[34m";
        if (Color.equals("BLUE"))
            System.out.println( BLUE + Text + ANSI_RESET);
        final String BLUE_BACKGROUND=	"\u001B[44m";
        if (Color.equals("BLUE_BACKGROUND"))
            System.out.println( RED_BACKGROUND + Text + ANSI_RESET);
        final String PURPLE	="\u001B[35m";
        if (Color.equals("PURPLE"))
            System.out.println( PURPLE + Text + ANSI_RESET);
        final String PURPLE_BACKGROUND=	"\u001B[45m";
        if (Color.equals("PURPLE_BACKGROUND"))
            System.out.println( PURPLE_BACKGROUND + Text + ANSI_RESET);
        final String CYAN=	"\u001B[36m";
        if (Color.equals("CYAN"))
            System.out.println( CYAN + Text + ANSI_RESET);
        final String CYAN_BACKGROUND=	"\u001B[46m";
        if (Color.equals("CYAN_BACKGROUND"))
            System.out.println( CYAN_BACKGROUND + Text + ANSI_RESET);
        final String  WHITE=	"\u001B[37m";
        if (Color.equals("WHITE"))
            System.out.println( WHITE + Text + ANSI_RESET);
        final String WHITE_BACKGROUND=	"\u001B[47m";
        if (Color.equals("WHITE_BACKGROUND"))
            System.out.println( WHITE_BACKGROUND + Text + ANSI_RESET);


    }//end of PrintColors

}//end Class
