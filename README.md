# Infra Code Challenge
## Due: Wednesday 12th September (6am) 
#### This code challenge is due on the 12th September, 2018 or earlier.

## Simple Unchanging Rules
The code challenge is and will always be judged using the following criteria:
  - A Correct fork, branch and pull request
  - Using the GitHub Pull Request Time Stamp and correct code quality & structure, the first developer whose code runs successfully wins
  - Code quality and structure will be evaluated
  - The order for pull requests will be followed, first come first win basis!
  - Do not share any code that you cannot opensource on the Git Repository as its open source and Africa's Talking will not be liable for any breach of intellectual property (if any) once shared on the platform.

## Terms and Conditions
You can participate on as many challenges as you wish:
  - Do not share any code that you cannot opensource on the Git Repository as its open source and Africa's Talking will not be liable for any breach of intellectual property (if any) once shared on the platform.
  - Code Challenges are time bound - the time restriction is specified on the challenge
  - Additional rules MAY be provided on the code challenge and will vary for each challenge
  - You are free to use all manner of tools
  - Successive interviews for projects MAY be run to satisfy participating Africa's Talking Partners

## Code Challenge Bounty:
  - A chance to work with some of the most brilliant minds in Africa

## Task
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


# Working on the Code Challenge
1.Fork the code challenge repository provided.

2.Make a topic branch. In your github form, keep the master branch clean. When you create a branch, it essentially will be a copy of the master.

>Pull all changes, make sure your repository is up to date

```sh
$ cd InfraCodeChallengeSeptember2018
$ git pull origin master
```

>Create a new branch as follows-> git checkout -b [your_phone_number], e.g.

```sh
$ git checkout -b 25472XXXXXXX master
```

>See all branches created

```sh
$ git branch
* 25472XXXXXXX
  master
```

>Push the new branch to github

```sh
$ git push origin -u 25472XXXXXXX
```

3.Make changes to the fork following the Code Challenge provided.

4.Commit the changes to your fork.

5.Make a pull request to the InfraCodeChallengeSeptember2018 
Repo.

### Additional:
1. See the online version of the code challenge [here](http://atdevoutreach.viewdocs.io/InfraCodeChallengeSeptember2018/InfraCodeChallengeSeptember2018/) to get acquainted.

2.  Check out the format for submitting your code [here](http://atdevoutreach.viewdocs.io/InfraCodeChallengeSeptember2018/InfraCodeChallengeSeptember2018Steps/)

## About Africa's Talking Code Challenges
Please read the overview for all code challenges [here.](http://atdevoutreach.viewdocs.io/InfraCodeChallengeSeptember2018/)

## Get Support from Africa's Talking
In case you have any questions, reach out [Roina Ochieng](rochieng@africastalking.com) or [Anthony Kiplimo](anthony.kiplimo@africastalking.com)