package BanksRUs;

/**
 * This class contains information of an account, and also prints out the accounts info.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 1 sept 2016
 *
 */
public abstract class Account
{

    private int accountNumber;
    private String accountStatus;
    private String accountName;
    private int accountOwner;
    private int accountBalance;


    /**
     *
     * @param accountNumber The account ID.
     * @param accountOwner  The ID of the account owner.
     * @param accountName   The name of the account.
     * @param accountStatus The status of the account.
     */
    //Constructor
    public Account(int accountNumber, int accountOwner ,String accountName, String accountStatus)
    {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountOwner = accountOwner;
        this.accountBalance = 0;
        this.accountStatus = accountStatus;
    }

    /**
     *
     * @return The account ID.
     */
    //Getter function to get account number
    public int getAccountNumber()
    {
        return this.accountNumber;
    }

    /**
     *
     * @return The account status.
     */
    //Getter function to get account status
    public String getAccountStatus()
    {
        return this.accountStatus;
    }

    /**
     *
     * @return The account name.
     */
    //Getter function to get account name
    public String getAccountName()
    {
        return this.accountName;
    }

    /**
     *
     * @return The account owner.
     */
    //Getter function to get account owner
    public int getAccountOwner()
    {
        return this.accountOwner;
    }

    /**
     *
     * @return The account Balance
     */
    //Getter function to get balance of the account
    public int getBalance()
    {
        return this.accountBalance;
    }

    /**
     * @param amount the balance of the account.
     */
    //Setter function to change the balance when someone withdraws or deposit on the account
    public void setBalance(int amount)
    {
        this.accountBalance = amount;
    }

    //Function to print out the account info
    public void printAccount()
    {
        System.out.println("Account id: " + accountNumber);
        System.out.println("Account name: " + accountName);
        System.out.println("Account owner: " + accountOwner);
        System.out.println("Account balance: " + accountBalance);
        System.out.println("Account Status: " + accountStatus);

    }

    /**
     *
     * @param amount The amount which will be deposit to an account.
     */
    abstract void deposit(int amount);

    /**
     *
     * @param amount The amount which will be withdrawn from an account.
     */
    abstract void withdraw(int amount);

}
