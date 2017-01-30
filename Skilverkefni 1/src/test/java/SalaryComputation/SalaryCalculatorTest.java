package SalaryComputation;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  This is a tester class who tests a class which calculates salary.
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 31 Aug 2016
 *
 */
public class SalaryCalculatorTest
{

    @Test
    public void getSalaryTest()
    {
        SalaryCalculator salCalc = new SalaryCalculator();
        assertEquals(0, salCalc.getSalary()); //Salary should be 0
        salCalc.salaryCalculate(900,47);
        assertEquals(45450, salCalc.getSalary()); //Salary should be 45450
    }

    @Test
    public void salaryCalculateTest()
    {
        SalaryCalculator salCalc = new SalaryCalculator();
        salCalc.salaryCalculate(900,47);
        assertEquals(45450, salCalc.getSalary()); //Salary should be 45450
    }

    @Test
    public void fourtyOrlessTest()
    {
        SalaryCalculator salCalc = new SalaryCalculator();
        salCalc.salaryCalculate(900,47);
        assertEquals(45450, salCalc.getSalary()); //Salary should be 45450
    }

    @Test
    public void moreThanFourtyTest()
    {
        SalaryCalculator salCalc = new SalaryCalculator();
        salCalc.salaryCalculate(500,40);
        assertEquals(20000, salCalc.getSalary()); //Salary should be 20000
    }

}


