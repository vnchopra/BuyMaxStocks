import java.util.*;

public class BuyMaxStocks 
{

	private static long k_moneyInPocket_revert = 0;
	
	static long buyMaximumProducts(int n, long k, int[] a) 
	{
        // Complete this function
		
		int dayNumber = 0;
		long k_moneyInPocket = k;
		long stocksPurchased = 0;
		
		BuyMaxStocks solution = new BuyMaxStocks();

		for (int i = 0; i < a.length; i++) 
		{
			dayNumber = i+1;
			k_moneyInPocket_revert = k_moneyInPocket; //for rollbacks
			k_moneyInPocket = solution.calculateMoneyInPocket(k_moneyInPocket, solution.calculateStockOverallTransactionPrice(dayNumber, a[i]));
			stocksPurchased = solution.addtoStocksPurchasedIfMoneyInPocketIsntBelowZero(dayNumber, a[i], k_moneyInPocket, stocksPurchased);
		}
		
		return stocksPurchased;
    }

    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); //n is number of days
        int[] arr = new int[n];
        for(int arr_i = 0; arr_i < n; arr_i++)
        {
            arr[arr_i] = in.nextInt();//The next line contains n space-separated integers where ith integer denotes the price of the stock on the ith day.
        }
        long k = in.nextLong(); // Next line contains a positive integer k which is the initial amount with the customer. 
        long result = buyMaximumProducts(n, k, arr);
        System.out.println(result);
        in.close();
    }
    
    private int recalculateIfCanPurchaseStocks(int dayNumber, int a_price, long k_moneyInPocket)
    {	
    	int localdayNumber = dayNumber;
    	long localk_moneyInPocket = k_moneyInPocket;
    	
    	while(localk_moneyInPocket < 0)
    	{
    		// money in pocket needs to be reverted
    		// to an earlier value before we went below zero
    		localk_moneyInPocket = k_moneyInPocket_revert;
    		if(localdayNumber == 0)
    		{
    			break;
    		}
    		
    		//recalculate
			localdayNumber = localdayNumber-1;
			localk_moneyInPocket = this.calculateMoneyInPocket(localk_moneyInPocket, this.calculateStockOverallTransactionPrice(localdayNumber, a_price));
    		
    	}
    	
    	//this in turn is also the number of stocks
    	return localdayNumber;
    }
    
    private long addtoStocksPurchasedIfMoneyInPocketIsntBelowZero(int dayNumber, int a_price, long k_moneyInPocket, long stocksPurchased)
    {
    	
    	if(k_moneyInPocket > 0)
    	{
    		//dayNumber is also equivalent to number of stocks unless going through recalculationz
    		stocksPurchased = stocksPurchased + dayNumber;
    	}
    	else
    	{
    		stocksPurchased = stocksPurchased + this.recalculateIfCanPurchaseStocks(dayNumber, a_price, k_moneyInPocket);
    	}
    	
    	return stocksPurchased;
	
    }
    
    private int calculateStockOverallTransactionPrice(int dayNumber, int a_price)
    {
    	int transactionPrice = dayNumber * a_price;
    	return transactionPrice;
    }
    
    private long calculateMoneyInPocket (long k, int transactionPrice)
    {
    	long moneyInPocket = k - (long)transactionPrice;

    	return moneyInPocket;
    }
    
}
