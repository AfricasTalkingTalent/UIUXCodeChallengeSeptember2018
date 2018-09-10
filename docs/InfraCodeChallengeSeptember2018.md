# Infra Code Challenge: September 2018
In this code challenge you will be required to build a toy network protocol with a client/server architecture:

#### For the server
Create a program that binds to TCP port 24240. When it recieves a new connection, the program shall read three unsigned bytes. The first byte denotes a function while the second and third bytes are arguments to the function

The functions are as follows:
- 0 -> Addition. This function adds the second and third byte
- 1 -> This function multiplies the second and third byte
- 2 -> Bitwise AND
- 3 -> Bitwise OR
- 4 -255 -> Reserved

The program shall respond with one byte, the result of applying the arguments to the function, and close the connection. If the result of the function is greater than 255, the server should return the value mod 255. Start the program.

#### For the client

Create a program that takes three command-line arguments. The program will be invoked in the command-line as:

```
>>> ./prog function arg1 arg2
```

For example:

```
>>> ./prog add 6 4
```

Valid functions are the following set of strings: [add, mul, and, or],
which correspond to the functions above. Valid arguments are 0-255.
When the program is invoked, it should send 3 bytes to the server on port 24240. The three bytes are the byte representing the function, and the two arguments to the function. The program should read one byte from the server, print it, print a newline character, and exit.


## Get Support from Africa's Talking
In case you have any questions, reach out [Roina Ochieng](rochieng@africastalking.com) or [Anthony Kiplimo](anthony.kiplimo@africastalking.com)
