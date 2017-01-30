package BanksRUs;

/**
 * This class extends from the Account class, has all the functions and variables from it. With this class we are able
 * to deposit and withdraw to a 401kaccount. This class has an extra variable from type Costumer which is used to see
 * how old the costumer is so we can see if it is possible to withdraw from his account, because he must be older
 * than 65 years old and also have enough funds to be able to withdraw from this account.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 1 sept 2016
 *
 */
public class FourZeroOnekAccount extends Account
{

    private Customer cus;

    /**
     *
     * @param accountNumber The ID of the account.
     * @param accountOwner The ID of the customer.
     * @param accountName The name of the account.
     * @param accountStatus The status of the account.
     * @param cus Customer to check if he is old enough to withdraw from his account.
     */
    //Constructor
    public FourZeroOnekAccount(int accountNumber, int accountOwner, String accountName, String accountStatus, Customer cus)
    {
        super(accountNumber, accountOwner ,accountName, accountStatus);
        this.cus = cus;
    }

    /**
     * @param amount The amount to deposit on to the account.
     */
    //Function which deposit funds to an account
    @Override
    public void deposit(int amount)
    {
        int newBalance = getBalance();
        newBalance += amount;
        setBalance(newBalance);
    }


    /**
     * @param amount The amount to withdraw from the account.
     */
    //Function which withdraws from an account
    @Override
    public void withdraw(int amount)
    {
        if(cus.calculateCustomerAge() > 65)
        {
            int newBalance = getBalance();
            newBalance -= amount;
            if(newBalance < 0)
            {
                System.out.println("Error! not enough funds");
            }
            else
            {
                setBalance(newBalance);
            }
        }
        else
        {
            System.out.println("Error! you are not old enough to withdraw from this account");
        }

    }
}