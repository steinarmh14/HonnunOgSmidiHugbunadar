package BanksRUs;

/**
 * This class extends from the Account class, has all the functions and variables from it. With this class we are able
 * to deposit and withdraw to a savings account. With a savings account we have a transaction limit, so this class
 * has an extra variable from type int which is used to see how many transaction have been used. It is only possible
 * to withdraw and depoist if the user hasn't done to many transactions. We also can't withdraw if we dont have
 * enough funds.
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 1 sept 2016
 *
 */
public class SavingsAccount extends Account
{

    private int transactionLimit;

    /**
     *
     * @param accountNumber The ID of the account.
     * @param accountOwner The ID of the account owner.
     * @param accountName The name of the account.
     * @param accountStatus The status of the account.
     */
    //Constructor
    public SavingsAccount(int accountNumber, int accountOwner, String accountName, String accountStatus)
    {
        super(accountNumber, accountOwner ,accountName, accountStatus);
        this.transactionLimit = 5;
    }


    /**
     * @param amount The amount to deposit from the account.
     */
    //Function which deposit funds to an account
    @Override
    void deposit(int amount)
    {
        if(this.transactionLimit == 0)
        {
            System.out.println("Error! This account has hit transaction limit for this month");
        }
        else
        {
            this.transactionLimit--;
            int newBalance = getBalance();
            newBalance += amount;
            setBalance(newBalance);
        }

    }


    /**
     * @param amount The amount to withdraw from the account.
     */
    //Function which withdraws from an account
    @Override
    void withdraw(int amount)
    {
        if(this.transactionLimit == 0)
        {
            System.out.println("Error! This account has hit transaction limit for this month");
        }
        else
        {
            int newBalance = getBalance();
            newBalance -= amount;

            if(newBalance < 0)
            {
                System.out.println("Error! Not enough funds");
            }
            else
            {
                this.transactionLimit--;
                setBalance(newBalance);
            }
        }
    }
}