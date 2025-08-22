At the heart of any blockchain lies its consensus algorithm—a mechanism that ensures all nodes agree on the current state of the ledger without needing a central authority.

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
                                                           2. a Timestamp can be forged,
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



--------------------------------------------------------------------------END OF REQUIREMENTS FOR CONSESUS ALGORYTHM-----------------------------------------------------------------------
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

 Will the blockchain be involved or utilize other technology such as: ML, IoT, Big Data(5 V's Filecoin is a good example of this).


 -
