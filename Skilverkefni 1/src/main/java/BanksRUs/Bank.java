package BanksRUs;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class holds all Accounts and Customers. The class can print out all customers and accounts
 * and also all accounts for a given customer.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 1 sept 2016
 *
 */
public class Bank
{

    private ArrayList<Account> accLis = new ArrayList<Account>();
    private ArrayList<Customer> cusLis = new ArrayList<Customer>();

    /**
     *
     * @param account Savings -, 401k- or Checking Account which will be added to an arrayList of accounts.
     */
    //Function to add the account to an ArrayList
    public void addAccount(Account account)
    {
        accLis.add(account);
    }

    /**
     *
     * @param customer The customer which will be added to an arrayList of customers.
     */
    //Function to add Customer to an ArrayList
    public void addCustomer(Customer customer)
    {
        cusLis.add(customer);
    }

    /**
     *
     * @param customerId ID of a customer whos info will be printed out.
     */
    //Funcion which prints out all active accounts for a given customer
    public void printAccountsForAGivenCustomer(int customerId)
    {
        boolean first = true;

        for (Customer c : cusLis)
        {
            if(c.getCustomerId() == customerId)
            {
                for(Account a : accLis)
                {
                    if(customerId == a.getAccountOwner() && a.getAccountStatus() == "Active")
                    {

                        if(first)
                        {
                            System.out.println("The customer is: ");
                            c.printCustomer();
                            System.out.println();
                            System.out.println("His accounts are:");
                            System.out.println();
                            a.printAccount();
                            System.out.println();
                            first = false;
                        }
                        else
                        {
                            a.printAccount();
                            System.out.println();

                        }
                    }
                }
                first = true;
            }
        }
    }

    //Function which prints all customers and their accounts
    public void printAllAccountsAndCustomers()
    {
        for(Customer c : cusLis)
        {
            System.out.println("The customer is: ");
            System.out.println();
            c.printCustomer();
            System.out.println();
            System.out.println("His accounts are: ");
            System.out.println();
            for(Account a : accLis)
            {
                if(c.getCustomerId() == a.getAccountOwner())
                {
                    a.printAccount();
                    System.out.println();
                }
            }
        }

    }

    public static void main(String [ ] args)
    {
        Customer oldSteinar = new Customer(1, "Steinar", LocalDate.of(1950,1,1) , "Hvammshlíð 2");
        Customer youngSteinar = new Customer(2, "Steinar Marino", LocalDate.of(2000,1,1) , "Bandagerði 2");
        FourZeroOnekAccount fourZeroOneKAccPossible = new FourZeroOnekAccount(1, 1, "401k Account Possible", "Active" ,oldSteinar);
        FourZeroOnekAccount fourZeroOneImpossible = new FourZeroOnekAccount(1, 2, "401k Account Impossible", "Active", youngSteinar);
        CheckingAccount checkingAccount = new CheckingAccount(3, 1, " Checking Account", "Active", 100, 0, -50);
        CheckingAccount checkingAccount2 = new CheckingAccount(4, 2, "Checking Account2", "Active", 100, 1, -1000);
        SavingsAccount savingsAccount = new SavingsAccount(5, 1, "Savings Account", "Active");

        Bank bank = new Bank();
        bank.addCustomer(oldSteinar);
        bank.addCustomer(youngSteinar);
        bank.addAccount(fourZeroOneImpossible);
        bank.addAccount(fourZeroOneKAccPossible);
        bank.addAccount(checkingAccount);
        bank.addAccount(checkingAccount2);
        bank.addAccount(savingsAccount);

        System.out.println("-------401Impossible ACCOUNT  -------");
        fourZeroOneImpossible.deposit(1000); //Balance should be 1000
        fourZeroOneImpossible.printAccount(); //Prints the account
        System.out.println();
        fourZeroOneImpossible.withdraw(1000);//Should print out error
        System.out.println();
        fourZeroOneImpossible.printAccount(); //Prints out the account balance should be 1000
        System.out.println();
        System.out.println("-------401Possible ACCOUNT  -------");
        fourZeroOneKAccPossible.deposit(1000); //Balance should be 1000
        fourZeroOneKAccPossible.printAccount(); //Prints the account
        System.out.println();
        fourZeroOneKAccPossible.withdraw(2000); //Should print out error
        System.out.println();
        fourZeroOneKAccPossible.printAccount(); //Prints out the account should remain the same
        fourZeroOneKAccPossible.withdraw(500); //Allowed to withdraw. Balance should be 500
        System.out.println();
        fourZeroOneKAccPossible.printAccount(); //Account should had changed
        System.out.println();
        System.out.println("-------CHECKING ACCOUNT -------");
        checkingAccount.deposit(100);  //Balance should be 100
        checkingAccount.printAccount(); //Prints out the account
        System.out.println();
        checkingAccount.withdraw(100); //Should print out error
        System.out.println();
        checkingAccount.printAccount(); //Prints out the account should remain the same
        checkingAccount.deposit(1000);  //Allowed to deposit.
        checkingAccount.withdraw(100);  //Allowed to withdraw. costFee was added. balance should be 900
        System.out.println();
        checkingAccount.printAccount(); //Account printed out with the changes
        System.out.println();
        System.out.println("-------CHECKING ACCOUNT 2 -------");
        checkingAccount2.printAccount(); //Print account
        checkingAccount2.withdraw(100); //Allowed to withdraw. Balance should be -100
        System.out.println();
        checkingAccount2.printAccount(); //See changes
        checkingAccount2.withdraw(100); //Withdraws and adds a cost fee. Balance should be -300
        System.out.println();
        checkingAccount2.printAccount(); //See account changes
        System.out.println();
        System.out.println("-------SAVINGS ACCOUNT -------");
        savingsAccount.printAccount(); //Prints the account
        System.out.println();
        savingsAccount.withdraw(100); //Should print error
        System.out.println();
        savingsAccount.printAccount(); //Print account should remain the same
        savingsAccount.deposit(2000); //Allowed to deposit. Balance should be 2000
        System.out.println();
        savingsAccount.printAccount(); //Print out account to see changes
        savingsAccount.withdraw(1000); //Allowed to withdraw Balance should be 1000
        System.out.println();
        savingsAccount.withdraw(2000); //Should print error
        System.out.println();
        savingsAccount.printAccount(); //Print out account to see changes. Balance should be 1000
        savingsAccount.withdraw(100); //Allowed to withdraw. Balance should be 900
        savingsAccount.deposit(200); //Allowed to deposit. Balance should be 1100
        System.out.println();
        savingsAccount.withdraw(100); //Allowed to deposit. Balance should be 1000
        savingsAccount.printAccount(); //See how the account is
        System.out.println();
        savingsAccount.withdraw(100); //prints error transaction limit reached
        System.out.println();
        savingsAccount.deposit(100); //prints error transaction limit reached
        System.out.println();
        System.out.println();
        System.out.println("------Print out all accounts for a give customer -------");
        System.out.println();
        bank.printAccountsForAGivenCustomer(1); //Prints all accounts for a given customer
        System.out.println();
        System.out.println();
        System.out.println("------Print out all accounts for all customers -------");
        System.out.println();
        bank.printAllAccountsAndCustomers(); //Prints all customers and their accounts
    }


}

