package BanksRUs;

/**
 * This class extends from the Account class, has all the functions and variables from it. This class has
 * some extra variables which are only used in it. We can deposit to a checking account and withdraw if and only if
 * if we don't go over the over draw limit, that the account has. We have also limit of free transactions which is
 * given when we make an object of this account.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 1 sept 2016
 *
 */
public class CheckingAccount extends Account
{

    private int freeWithdrawTransactionsLimit;
    private int costFee;
    private int overDrawnLimit;


    /**
     *
     * @param accountNumber ID of an account.
     * @param accountOwner ID of the owner of the account.
     * @param accountName  The name of the account.
     * @param accountStatus The account status.
     * @param costFee The fee which will be added if customer has done to many transactions.
     * @param freeWithdrawTransactionsLimit How many free transactions are allowed. If the customer has done to many
     *                                      he will be charge with a cost fee with every transaction.
     * @param overDrawnLimit The over draw limit of the account.
     */
    //Constructor
    public CheckingAccount(int accountNumber, int accountOwner, String accountName, String accountStatus, int costFee, int freeWithdrawTransactionsLimit,int overDrawnLimit)
    {
        super(accountNumber, accountOwner ,accountName, accountStatus);
        this.freeWithdrawTransactionsLimit = freeWithdrawTransactionsLimit;
        this.costFee = costFee;
        this.overDrawnLimit = overDrawnLimit;
    }


    /**
     *
     * @param amount The amount wich will be deposit to this account.
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
     *
     * @param amount The amount which will be withdrawn from the account.
     */
    //Function which withdraws from an account
    @Override
    public void withdraw(int amount)
    {
        int newBalance = getBalance();
        newBalance -= amount;
        if(newBalance < this.overDrawnLimit || (newBalance - costFee) < this.overDrawnLimit)
        {
            System.out.println("Error! Could not withdraw, not enough funds");
        }
        else
        {
            if(this.freeWithdrawTransactionsLimit == 0)
            {
                newBalance -= costFee;
                setBalance(newBalance);
            }
            else
            {
                setBalance(newBalance);
                this.freeWithdrawTransactionsLimit--;
            }
        }
    }
}
