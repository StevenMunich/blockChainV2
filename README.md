At the heart of any blockchain lies its consensus algorithm—a mechanism that ensures all nodes agree on the current state of the ledger without needing a central authority.

2 Good examples mentioned are Filecoin and Bitcoin Core(i do not own or am promoting the sale of the tokens used on these networks, this is strictly for educational purpouses only).

Core Requirements of Blockchain Consensus Algorithms
-------------------------------------------------------
Functional:

1FA Agreement

• 	All honest nodes shall agree on the same block to add to the chain.

• 	The entire network shall prevent forks and ensures a single source of truth.

2FA Integrity

• 	Malicious actors shall not be able to forge or alter transactions. - Immutability

• 	The algorithm must resist tampering and double-spending. 

          - this one needs to be written in more detailed: 1. hashes at merkle root shall match, if not...a node(s) have been tampered with. Will not be detected if 51% are tampered.
                                                           2. a Timestamp can be forged page 213 and 195
                                                           
• 	The algorithm shall synchronize nodes - Page 195 

3FA Liveness

• 	The system shall continiously  add blocks.

• 	Shall not have any indefinite delays, or deadlocks in reaching consensus.
- Shall be in a timely manner understandable to the users.
- Shall have a reset timer if consensus is not met in MAX_TIME or interval
  
4FA Fairness

• 	Malicious actors shall not be able to divert others to steal rewards. - Selfish Miners

• 	Every node shall have a fair chance to participate in consensus.

• 	The algorhtym shall either be proof of work or stake.

Non-Functional

1NFA Fault Tolerance

• 	Must handle Byzantine faults—nodes that act arbitrarily or maliciously.

• 	Many algorithms aim for tolerance up to 1/3 of nodes being faulty.

2NFA Scalability

• 	Shall perform well as the number of nodes increases.

• 	Efficient communication and computation are critical.

3NFA Efficiency

• 	Shall Minimize energy consumption, latency, and bandwidth usage(Especially important for mobile or IoT-based blockchains).



---------------------------------------------------      END OF REQUIREMENTS FOR CONSESUS ALGORYTHM     --------------------------------------------
Talking points about Blockchain(Not consensus Algo like above)

Tokenized Assests - Are they digital or tangable property / ownership rights?

                        -What can be Tokenized? A house, transportation, energy, access to website or building(like Gym).
                        
                        -Every company that uses rewards systems after payment as a form of credit. Can those rewards become fungable? interchangable? For one another, or a common network token?
                    
                  Who Owns it? Who Uses it? Who Manages it?
                  Where is it located?(physical object or digital which can be local or cloud)

How much is virtualization is used for effieciency? Is serverless a selling point for blockchain technology?

Governance. Besides 51% there is selfish mining read page 213 of ai/blockchain book.

Use case may determine whether network is private or public blockchain. Public block chains like Bitcoin are transparent at the expense of privacy.
 -Finance
 
 -Healthcare
 
 -Rentals(of any physical object)
 
 -Identity
 Will the blockchain be involved or utilize other technology such as: ML, IoT, Big Data(5 V's: Filecoin is a good example)


Core architectural components of Bitcoin Core:
Networking (net): The lowest layer of the node's stack handles all peer-to-peer (P2P) communication. It uses a single select loop to manage network connections with other nodes, transmitting and receiving transaction and block data. A node connects to multiple peers (typically 8–10) to discover other nodes and relay information across the decentralized network.

Blockchain and database: This component is responsible for storing the entire history of Bitcoin transactions. The full node downloads and keeps a complete copy of the blockchain.
Initial block download (IBD): When a new node joins, it synchronizes with the network by downloading all blocks from existing nodes, a process that can take a significant amount of time.
Data storage: The node stores the blockchain data on a hard drive (HDD or SSD), and the storage requirements increase as the blockchain grows.

Validation engine: As the core of the node's function, this component independently verifies all transactions and blocks against Bitcoin's consensus rules. A node rejects any invalid transactions or blocks that violate the rules, such as attempts at double-spending. The validation process involves checking for correct digital signatures and verifying that the sender has sufficient funds.

Memory pool (mempool): This component is a temporary holding area for unconfirmed, but valid, transactions that have been received by the node. Miners pull transactions from the memory pool to assemble a new block. When a node receives a new block, it removes the included transactions from its mempool.

Wallet: Bitcoin Core includes an optional built-in wallet functionality. This allows a user to store, send, and receive bitcoins and track their transaction history. The wallet module securely manages private keys, which are used to authorize outgoing payments.

User interfaces and RPC:
1. Graphical User Interface (GUI): For non-technical users, Bitcoin Core provides a GUI for easy interaction with the node.

2. Command Line Interface (CLI): Advanced users can use the command-line interface for more granular control.

3. JSON-RPC interface: The Remote Procedure Call (RPC) interface allows other applications to programmatically interact with and query information from the node.

4. Scheduler (CScheduler): This asynchronous message service handles event-driven tasks, such as triggering a message when a relevant event occurs. 

Different node implementations
While Bitcoin Core is the reference implementation, other variations exist with different architectural focuses: 

1. Pruned nodes: A type of full node that downloads the entire blockchain but then discards older blocks to save disk space. It still retains enough information to validate new transactions and enforce consensus rules.

2. Lightweight nodes (SPV): These do not store the full blockchain. Instead, they use Simplified Payment Verification (SPV) to check if a transaction is included in a block by only downloading block headers. They rely on full nodes for a higher level of security.

3. Alternative full-node implementations: Other projects, such as BTCD (Go language), offer full-node functionality with different architectural designs, though they must adhere to the same Bitcoin protocol. 

 
