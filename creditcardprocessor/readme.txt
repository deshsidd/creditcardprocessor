Credit Card Processor
---------------------
---------------------
Credit card processor that supports adding a new card, crediting and debiting an amount.
Can take input from a file or command line.


OVERVIEW OF DESIGN DECISIONS
----------------------------
- Parsed the input into a Transaction object. Transaction is a generic class and each input
can be parsed to a specific Transaction(Credit, Debit, NewCard).
- Created a Luhn interface, we can add multiple implementations of Luhn validator later if needed.
- Organized classes in a neat package structure.
- Created a CreditCard entity that stores all the information related to a credit card account.
- TransactionProcessor processes the main transactions and contains the core logic.
- I store a TreeSet of names which makes it easy and efficient while printing the names in alphabetically order.
- I have a hashmap of name to CreditCard which enables us to retrieve Credit Card information 
based on a given name(which is unique id) in O(1) time.
- All modifications are also done in O(1) time.
- I have tried to make the code as clean as possible.

WHY I PICKED JAVA
-----------------
- I am most comfortable in Java and I use it in my daily coding.

HOW TO RUN CODE AND TESTS
-------------------------
- I have provided a runnable jar
- You can execute it in the following 2 ways

java -jar ccprocessor.jar < input.txt

java -jar ccprocessor.jar input.txt

NOTE : Please rename the jar from ccprocessor.jar.removeme to ccprocessor.jar

- Since you mentioned automated tests, the tests run each time the program is 
executed and the result is printed on the screen. 
- After the tests are finished the program runs with the given input.
- You can import the project in an IDE such as eclipse and run the tests(junit tests).

DEPENDENCIES
------------
JUnit 5 and JDK needed to run.

LIMITATIONS
-----------
- No comprehensive tests.
- One person can obtain only one card.
- No synchronization implemented.
- No comprehensive exception handling(only basic).
- No logging.

NOTE : A lot of extra features/addons could have been added. This is a basic implementation based on the limited time. Hope you like it!