package SalaryComputation;

/**
 *  This class calculates salary. It is given the base pay and hours. If base pay is lower than 500 and/or
 *  the hours are more than 60 then it prints out error message and explains whats wrong.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 31 Aug 2016
 *
 */
public class SalaryCalculator
{

    private int salary;

    //Constructor
    public SalaryCalculator()
    {
        salary = 0;
    }


    /**
     *
     * @return The salary.
     */
    //Getter function to get salary which is used in the tester class.
    public int getSalary()
    {
        return salary;
    }

    /**
     *
     * @param basePay The pay per hour.
     * @param hours Hours worked.
     */
    //Calculate salary. Has two helper functions, underFourty and overFourty which are explained below.
    public void salaryCalculate(double basePay, double hours)
    {

        if(basePay < 500.0 && hours > 60.0)
        {
            System.out.println("ERROR. There are to many hours and the base pay is to low ");
        }
        else if(basePay < 500.0)
        {
            System.out.println("ERROR. The base pay is to low ");
        }
        else if(hours > 60.0)
        {
            System.out.println("ERROR. There are to many hours ");
        }
        else
        {
            if(hours > 40.0)
            {
                salary += moreThanFourty(basePay,hours);
            }
            else
            {
                salary += fourtyOrLess(basePay,hours);
            }
        }
    }

    /**
     *
     * @param basePay The pay per hour.
     * @param hours Hours worked.
     * @return The salary for 40 hours or less
     */
    //Calculates the salary where the hours are 40 or less
    private double fourtyOrLess(double basePay, double hours)
    {
        double retNumb = basePay * hours;

        return retNumb;
    }


    /**
     *
     * @param basePay The pay per hour.
     * @param hours Hours worked.
     * @return The overtime salary.
     */
    //Calculates the salary where the hours are more than 40
    private double moreThanFourty(double basePay, double hours)
    {
        double hoursOverFourty = hours - 40;
        hours = 40;
        double numb;
        numb = fourtyOrLess(basePay, hours);
        basePay *= 1.5;
        double retNumb = basePay * hoursOverFourty + numb;

        return retNumb;
    }

    public static void main(String [ ] args)
    {
        SalaryCalculator salaryCalculator = new SalaryCalculator();
        salaryCalculator.salaryCalculate(450, 35); //Should print out ERROR. The base pay is to low
        System.out.println("The salary is: " + salaryCalculator.getSalary()); //Salary should be 0 still
        salaryCalculator.salaryCalculate(1500, 73); //ERROR. There are to many hours
        System.out.println("The salary is: " + salaryCalculator.getSalary()); //Salary should be 0 still
        salaryCalculator.salaryCalculate(900, 47); //Shouldn't print an error and should calculate everything correctly
        System.out.println("The salary is: " + salaryCalculator.getSalary()); //Salary should be 45450


    }
}