package BanksRUs;
import java.time.LocalDate;
import java.time.Period;

/**
 * This class contains information of a customer, and also prints out the customer info.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 1 sept 2016
 *
 */

public class Customer
{
    private int customerId;
    private String customerName;
    private String customerAddress;
    private LocalDate customerDateOfBirth;

    /**
     *
     * @param customerId The ID of the customer.
     * @param customerName The name of the customer.
     * @param customerDateOfBirth The date of birth of the customer.
     * @param customerAddress The address of the customer.
     */
    //Constructor
    public Customer(int customerId, String customerName, LocalDate customerDateOfBirth, String customerAddress)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerDateOfBirth = customerDateOfBirth;
        this.customerAddress = customerAddress;
    }

    //Function to print out customer details
    public void printCustomer()
    {
        System.out.println("Customer id: " + customerId);
        System.out.println("Customer name: " + customerName);
        System.out.println("Customer year of birth: " + customerDateOfBirth);
        System.out.println("Customer Address: " + customerAddress);
    }

    /**
     *
     * @return The customer ID.
     */
    //Getter function to get customer id
    public int getCustomerId()
    {
        return this.customerId;
    }

    /**
     *
     * @return The customer name.
     */
    //Getter function to get customer name
    public String getCustomerName()
    {
        return this.customerName;
    }

    /**
     *
     * @return The customer date of birth.
     */
    //Getter function to get customer date of birth
    public LocalDate getCustomerDateOfBirth()
    {
        return this.customerDateOfBirth;
    }

    /**
     *
     * @return The customer address.
     */
    //Getter function to get customer address
    public String getCustomerAddress()
    {
        return this.customerAddress;
    }

    //Function to calculate customer age

    /**
     *
     * @return The customer age.
     */
    public int calculateCustomerAge()
    {
        LocalDate today = LocalDate.now();
        return Period.between(customerDateOfBirth, today).getYears();
    }

}
