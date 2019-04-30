This program was built following a few design princliples and patterns.
MVC and AccountFactory were used to split up the code. Some solid princliples like Depedency Injection as well as single responsibility
were used in the code for cleaner and more effecient code. Every class and method has a comment on its specific functionality.

The front end was developed using the .swing package.
========================================================================================================================================
Our program Instructions:

You have two options with our program:

1. You can make your own login (follow the instructions when you start the program) and then you will have no accounts.
You will then need to create an account by following the frontend and you will have no funds in any new account so you
will need to deposit money into the accounts. Money deposited will go directly to your primary chequing account but then
you can transfer money to other accounts

2. You can login as an existing user with some pre-setup accounts

The username is: chenpan
    password is: black

The user can do the following things:

Create login

Manager can set
    *password of the user
    *username of the user


Account information
    *your net total
    *the most recent transaction on any account

Login
    *change your password
    *create account

Account
    *send money to other account
    *Withdraw up to -100
    *Insert money
    *merge accounts
    *Can set a account to primary
    * pay of credit balance


To create a new account
     *If has no account would be asked to create on
     *Can quit if don't want to


To get information on some of the latest currencies
to add another
user to an account you own.

Employee
    *Given CAD returns Euros



=========================================
INFO ABOUT EVERY TXT FILE IN info folder:

Accounts.txt Stores
    *Username
    *Account Type and information about each specific account
alerts Stores
    * alerts when machine goes down in bills
Bills Stores
    *number of bills in the machine of each type.
id Stores
    *current id that no accounts are using so that every account of every user has a unique id
Login Stores
    *Username
    *Password
New Employee Stores
    *name of new employee
    *Temporary password
Outgoing Stores
    * when paying bills info
