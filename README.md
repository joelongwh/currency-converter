# currency-converter
Automated currency conversion system.

In this system, a set of user profiles as well as instructions pertaining to a set of transactions are available.
Each transaction is a conversion of some sort for one user. 
A conversion system has been created to process all of the transactions, updating the user profiles as required. 

Assumptions:
1. File formats will not change
2. Transaction currencies are ordered (FROM, TO)
3. Transaction amount is indicating the FROM amount
4. If user's wallet does not contain the FROM currency, the transaction is skipped
5. If user's wallet does not contain the TO currency, add that currency to the wallet 
6. If the user does not have enough money, skip the transaction
