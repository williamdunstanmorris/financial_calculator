package core;

import utilities.Date;

public class HP12CFunctions {

	/*
	 * TODO: Erros for:
	 * [STO] [+] (0 to 4) | > MAXVALUE | THROW SRO_RROR
	 * [STO] [-] (0 to 4) | > MAXVALUE | THROW SRO_RROR
	 * [STO] [*] (0 to 4) | > MAXVALUE | THROW SRO_RROR
	 * [STO] [/] (0 to 4) | > MAXVALUE | THROW SRO_RROR
	 * [STO] [/] (0 to 4) | x < 0      | THROW MATHE_RROR
	 * [n]                | The values in i, PV, and FV are such that no solution exists for n. | THROW CI_RROR
	 * [i]                | Cash flows all have same sign. | THROW CI_RROR
	 * [AMORT]            | x <= 0 THROW | CI_RROR 
	 *  [SL], [SOYD], [DB] | n <= 0  | CI_RROR 
	 *  [SL], [SOYD], [DB] | n > 10e10  | CI_RROR 
	 *  [SL], [SOYD], [DB] | x <= 0  | CI_RROR 
	 *  [SL], [SOYD], [DB] | x is not integer  | CI_RROR 
	 */
	
	private static final double MAXVAL  =  9.999999999E99; // Maximum value admitted
	private static final double MINVAL  = -9.999999999E99; // Minimum value admitted
	private static final double INTERPOLMAX = 50;
	
	/* 
	 * Fits the return value to the maximum value if it is to big
	 * or to the minimum value if it is to small 
	 * Avoids infinite or too small values.
     */
	public static double fitToHP12C(double v) throws HP12CInputException{
		if (v > MAXVAL) 
			return MAXVAL;
		else if (v == Double.POSITIVE_INFINITY)
			return MAXVAL;
		else if (v < MINVAL)
			return MINVAL;
		else if (v == Double.NEGATIVE_INFINITY)
			return MINVAL;
		if (Double.isNaN(v))
			throw new HP12CInputException(HP12CErrors.ERROR_MATH, "Not a number");
		else
			return v;
	}
	
	// Sum of two numbers
	// Key: [+]
	// Description: Calculates the sum of two numbers x and y.
	public static double sum(double x, double y) throws HP12CInputException{
		return fitToHP12C( y + x );
	}
	
	// Sum of many values
	// Key: None
	// Description: Calculates the sum of many values (values in an array)
	private static double sum(double[] values) throws HP12CInputException{
		
		double sum = 0;
		
		for(int i=0; i<values.length; i++){
			sum  += values[i];
		}
		
		return fitToHP12C( sum );
	}
	
	// Subtraction of two numbers
	// Key: [-]
	// Description: Calculates the subtraction of two numbers x and y (y minus x).
	public static double sub(double x, double y) throws HP12CInputException{
		return fitToHP12C( y - x );
	}
	
	// Multiplication of two numbers
	// Key: [*]
	// Description: Calculates the multiplication of two numbers x and y.
	public static double mul(double x, double y) throws HP12CInputException{
		return fitToHP12C( y * x );
	}
	
	// Division of two numbers
	// Key: [/]
	// Description: Calculates the division of two numbers x and y (y divided by x).
	public static double div(double x, double y) throws HP12CInputException{
			if (x == 0.0)
				throw new HP12CInputException(HP12CErrors.ERROR_MATH, "Division by ZERO");
			else 
				return fitToHP12C(y / x);
	}
	
	// Change sign
	// Key: [CHS]
	// Description: Changes sign of a number or exponent x, for example, 2 --> (-2)
	public static double sign(double x) throws HP12CInputException{
		if (x == 0.0)	
			return 0.0;
		else 
			return (-x);
	}
	
	// Square root
	// Key: [g][Root x]
	// Description: Calculates the square root of a number x.
	public static double sqrt(double x) throws HP12CInputException{
		if (x < 0) 
			throw new HP12CInputException(HP12CErrors.ERROR_MATH, "Result is a complex number");
		else 
			return fitToHP12C( Math.sqrt(x) );
	}
	
	// Reciprocal number (inverse of a number, for example, 2 --> 1/2)
	// Key: [1/x]
    // Description: Calculates the reciprocal of the number displayed in
	public static double reciprocal(double x) throws HP12CInputException{
		if (x == 0.0) 
			throw new HP12CInputException(HP12CErrors.ERROR_MATH, "Division by ZERO");
		else 
			return fitToHP12C( 1 / x );
	}
	
	// Integer part
	// Key: [g][INTG]
	// Description: Extracts only the integer portion of a number x.
	public static double intPart(double x) throws HP12CInputException{
		return fitToHP12C( Math.floor(x) );
	}
	
	// Decimal part
	// Key: [g][FRAC]
	// Description: Extracts only the fractional portion of a number x, 
	// for example, 1.55 --> 1.00
	public static double fracPart(double x) throws HP12CInputException{
		return fitToHP12C( x - intPart(x) );
	}
	
	// Power function (y^x)
	// Key: y^x
	// Description: Raises the number y to the power of the number x, 
	// for example, 1.55 --> 0.55
	public static double pow(double x, double y) throws HP12CInputException{
		if ((y == 0) && (x <= 0))   
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"Y is ZERO and X is negative or ZERO");
		else if ((y < 0) && (fracPart(x) != 0))
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"Y is negative and X is not integer");
		else 
			return fitToHP12C( Math.pow(y, x) );
	}

	// Natural antilogarithm (e^x)
	// Key: [g][e^x]
	// Description: Raises e (Euler Number) to power of the a number x
	public static double ePowerX(double x) throws HP12CInputException{
		return fitToHP12C( Math.exp(x) );
	}
	
	// Natural Log of X
	// Key: [g][LN]
	// Description: Calculates natural logarithm (base e) of a number x.
	public static double logN(double x) throws HP12CInputException{
		if (x <= 0)
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"Log of ZERO or negative number");
		else 
			return fitToHP12C( Math.log(x) );
	}
	
	// Fatorial
	// Key: [g][n!]
	// Description: Calculates factorial of a number x.
	public static double fat(double x) throws HP12CInputException{
		double r = 1;
		
		if (fracPart(x) != 0) 
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"x is not integer");
		else if (x < 0)
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"x is negative");
		else {
			for(double i = x; i > 0; i--){
				r = r * i;
				if (r > MAXVAL){
					i = 0;
				}
			}
		}
		// If no error occurs, it returns r value.
		return fitToHP12C( r );
	}
	
	// Round
	// Key: [f][RND]
	// Description: Rounds a value (x) to a given precision (prec)
	public static double round(double x, int prec) throws HP12CInputException{
		return fitToHP12C( ( Math.rint(x * Math.pow(10, prec)) / Math.pow(10, prec) ) );
	}
	
	// Percentage 
	// Key: [%]
	// Description: Calculates x% of y
	public static double percent(double x, double y) throws HP12CInputException{
		return fitToHP12C( y * (x / 100) );
	}
	
	// Percentage of Total
	// Key: [%T]
	// Description: Calculates what percentage one number is of another
	public static double percentOfTotal(double x, double y) throws HP12CInputException{
		if (y == 0) 
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"Division by ZERO");
		else
			return fitToHP12C( (x * 100) / y );
	}
	
	// Percentage Difference
	// Key: [%Delta]
	// Description: Calculates the percent difference between two numbers
	public static double percentDifference(double x, double y) throws HP12CInputException{
		if (y == 0) 
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"Division by ZERO");
		return fitToHP12C( ( (x - y) * 100 ) / y );
	}
	
	
	// Calendar =============================================================
	
	// getDate
	// Key: none
	// Description: returns a Date object based on a double value.
	public static Date getDate(double val, int dmy) throws HP12CInputException{
		
		int y, m, d;
		
		if(dmy != 0){                                        
			d = (int) Math.round( val );
			m = (int) Math.round( HP12CFunctions.fracPart(val)*100 );
			y = (int) Math.round( HP12CFunctions.fracPart(HP12CFunctions.fracPart(val)*100)*10000);
		}
		else{
			m = (int) Math.round( val );
			d = (int) Math.round( HP12CFunctions.fracPart(val)*100 );
			y = (int) Math.round( HP12CFunctions.fracPart(HP12CFunctions.fracPart(val)*100)*10000);
		}
		
		Date dt = new Date(y, m, d);
		
		if(!dt.isValid())
			throw new HP12CInputException(HP12CErrors.ERROR_CAL,"Invalid date");
		
		return dt;
	}
	
	// addDays
	// Key: [g][DATE]
	// Description: Calculates the date that is a given number of days 
	// from a given date
	public static double addDays(Date begin, int days, int dmy) throws HP12CInputException{

		double rtn = 0;

		String strDate="";
		String day="";
		String month="";
		String year="";
		
		Date result = new Date();

		try{
		
			result = Date.addDays(begin, days);
			
			day = String.format("%02d", result.getDay());
			month = String.format("%02d", result.getMonth());
			year = String.format("%04d", result.getYear());

			if(dmy != 0){ strDate =day  +"."+month+""+year;}
			else{    strDate =month+"."+day  +""+year;}
			
			rtn = Double.parseDouble(strDate);
			
		}catch(Exception e){
			throw new HP12CInputException(HP12CErrors.ERROR_CAL,"Date sum error");
		}
		
		return fitToHP12C( rtn );
	}
	
	// weekDay
	// Key: [g][DATE] (complement of addDays() method)
	// Description: Calculates the day of the week as a number 1-7, 
	//              where 1 is for Monday and 7 is for Sunday.
	public static double weekDay(Date date) throws HP12CInputException{
		
		return fitToHP12C( (double) date.getWeekDay() );
	}
	
	// diffDates
	// Key: [g][DeltaDYS]
	// Description: Calculates the number of days between two dates in actual days
	public static double diffDates(Date begDate, Date endDate) throws HP12CInputException{
		
		return fitToHP12C( (double) Date.diffDates(begDate, endDate) );
	}
	
	// diffCommercialDates
	// Key: [g][DeltaDYS]
	// Description: Calculates the number of days between two dates  on the basis of a 30-days month
	public static double diffCommercialDates(Date begDate, Date endDate) throws HP12CInputException{
		
		return fitToHP12C( (double) Date.diffCommercialDates(begDate, endDate) );
	}
	
	// Simple interest (Traditional Formula)
	// Key: [f][INT]
	// Description: Calculates the simple interest in financial problems.
	public static double simpleInterest(double n, double i, double pv) throws HP12CInputException{
		return fitToHP12C( -(pv * i/100.0 * n) ); 
	}
	
	// Compound interest (Traditional formula)
	// Key: None
	// Description: Calculates the compounding interest in financial problems.
	public static double interest(double n, double i, double pv) throws HP12CInputException{
		return fitToHP12C( -((pv * Math.pow(1 + (i/100.0), n)) - pv) );
	}
	
	// Compound future value (Traditional formula)
	// Key: None
	// Description: Calculates the future value of a financial problem.
	public static double simpleFutureValue(double n, double i, double pv) throws HP12CInputException{
		return fitToHP12C( -( pv + pv * (i/100.0) * n ) );			
	}	
	
	// Compound future value (Traditional formula)
	// Key: None
	// Description: Calculates the future value of a financial problem.
	public static double futureValue(double n, double i, double pv) throws HP12CInputException{
		return fitToHP12C( -(pv * Math.pow(1 + i, n)) );			
	}
	
	// Compound future value (HP12C formula)
	// Key: [PV]
	// Description: Calculates the future value of a financial problem.
	public static double futureValue(double n, double i, double pv, double pmt, double beg, double c) throws HP12CInputException{

		if (i <= (-100))
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i <= -100");
		
		double fv    = 0.0;
		double tmp[] = new double[4];
		
		// Changing rate to decimal format
		i = i / 100.0;
		
		// Without odd period
		if(fracPart(n) == 0.0){
			tmp[0] = ( 1 + i * (beg));
			tmp[1] = ( 1 - Math.pow( 1 + i, 0 - n )) / i;
			tmp[2] = Math.pow( 1 + i, 0 - n );
			
			fv = -((pv + tmp[0] * pmt * tmp[1]) / tmp[2]) ;
			
		}
		// With odd period
		else{
			// With odd period using simple interest
			if(c==0){
				
				tmp[0] = ( 1 + ( i * fracPart(n) ) );
				tmp[1] = ( 1 + i * (beg));
				tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
				tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			}
			// With odd period using compound interest
			else{
				tmp[0] = Math.pow(1 + i, fracPart(n));
				tmp[1] = ( 1 + i * (beg));
				tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
				tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			}
						
			fv = -( (pv * tmp[0] + tmp[1] * pmt * tmp[2]) / tmp[3]) ;
			
		}
		
		return fitToHP12C( fv );
	}
	
	// Simple period (Traditional formula)
	// Key: None
	// Description: Calculates number of simple periods in financial problems.
	public static double simplePeriod(double i, double pv, double fv) throws HP12CInputException{
		return fitToHP12C( (fv - pv) / (pv * i/100.0) );
	}
		
	// Conpound period (Traditional formula)
	// Key: [n]
	// Description: Calculates number of compounding periods in financial problems.
	public static double period(double i, double pv, double fv) throws HP12CInputException{
		return fitToHP12C( (Math.log(Math.abs(fv/pv)))/(Math.log(1+i/100.0)) );
	}
	
	// Conpound period (HP12C formula)
	// Key: [n]
	// Description: Calculates number of compounding periods in financial problems.
	public static double period(double i, double pv, double pmt, double fv, double beg, double c) throws HP12CInputException{
		
		double n = 0;
		double tmp[] = new double[3];

		// Variable used to detect error and throw it.
		// Defined in Appendix D of HP12C Users Guide.
		 double d = (i/100.0) / (1 + i * (beg/100.0));
		

		if (pmt <= -(pv) * i)
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: pmt <= -(pv) * i");
		else if (pmt == fv * i)
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: pmt == fv * i");
		else if (i <= (-100))
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i <= -100");
		else if ((i == 0) && (pmt == 0)) // Platinum
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i == 0 and PMT == 0");		
		else if ( (pmt >= fv * d ) && ( pmt <= -pv * d ) ) // Platinum
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: PMT between (FV * d) and (-PV * d), inclusive.");

		// Changing rate to decimal format
		i = i / 100.0;
		
		tmp[0] = pmt - (i * fv) + (i * pmt * beg); 
		tmp[1] = pmt + (i * pv) + (i * pmt * beg);
		tmp[2] = Math.log(i+1);
		
		n = Math.log( tmp[0] / tmp[1] ) / tmp[2];

		// Information in a footnote of the Platinum Users Manual
		if (fracPart(n) < 0.005){
			// Round down the result to represent a full period.
			n = Math.floor(n);
		}
		else{
			// Round up the result to represent a full period.
			n = Math.ceil(n);
		}
		
		return fitToHP12C( n );

	}
	
	// Price payment (PMT)
	// Key: [PMT]
	// Description: Calculates the payment amount.
	public static double pricePayment(double n, double i, double pv, double fv, double beg, double c) throws HP12CInputException{
		if (n == 0)
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: n == 0");
		else if (i == 0)
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i == 0");
		else if (i <= (-100))
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i <= -100");

		double pmt = 0.0;
		double tmp[] = new double[4];
		
		// Changing rate to decimal format
		i = i / 100.0;
		
		// Without odd period
		if(fracPart(n) == 0.0){
			tmp[0] = ( 1 + i * (beg));
			tmp[1] = ( 1 - Math.pow( 1 + i, 0 - n )) / i;
			tmp[2] = Math.pow( 1 + i, 0 - n );
			
			pmt =  -( (pv + fv * tmp[2]) / (tmp[0] * tmp[1]) );
			
		}
		// With odd period
		else{
			// With odd period using simple interest
			if(c==0){
				
				tmp[0] = ( 1 + ( i * fracPart(n) ) );
				tmp[1] = ( 1 + i * (beg));
				tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
				tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			}
			// With odd period using compound interest
			else{
				tmp[0] = Math.pow(1 + i, fracPart(n));
				tmp[1] = ( 1 + i * (beg));
				tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
				tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			}
			
			pmt =  -( (pv * tmp[0] + fv * tmp[3]) / (tmp[1] * tmp[2]) );
			
		}
		
		return fitToHP12C( pmt );
		
	}
	
	// Simple present value (Traditional formula)
	// Key: None
	// Description: Calculates the present (the initial cash flow) value of 
	// a financial problem.
	public static double simplePresentValue(double n, double i, double fv) throws HP12CInputException{
		return ( -(fv / (1 + (i/100.0 * n) )) );			
	}
	
	// Compound future value (Traditional formula)
	// Key: None
	// Description: Calculates the present (the initial cash flow) value of 
	// a financial problem.
	public static double presentValue(double n, double i, double fv) throws HP12CInputException{
		return fitToHP12C( -(fv / Math.pow(1 + i/100.0, n)) );
	}
	
	// Compound future value (HP12C formula)
	// Key: [PV]
	// Description: Calculates the present (the initial cash flow)	value of
	// a financial problem.
	public static double presentValue(double n, double i, double pmt, double fv, double beg, double c) throws HP12CInputException{
		if (i <= -100) 
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i <= -100");

		double pv = 0.0;
		double tmp[] = new double[4];
		
		// Changing rate to decimal format
		i = i / 100.0;
		
		// Without odd period
		if(fracPart(n) == 0.0){
			tmp[0] = ( 1 + i * (beg));
			tmp[1] = ( 1 - Math.pow( 1 + i, 0 - n )) / i;
			tmp[2] = Math.pow( 1 +i, 0 - n );
			
			pv = -(tmp[0] * pmt * tmp[1] + fv * tmp[2]);

		}
		// With odd period
		else{
			// With odd period using simple interest
			if(c==0){
				
				tmp[0] = ( 1 + ( i * fracPart(n) ) );
				tmp[1] = ( 1 + i * (beg));
				tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
				tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			}
			// With odd period using compound interest
			else{
				tmp[0] = Math.pow(1 + i, fracPart(n));
				tmp[1] = ( 1 + i * (beg));
				tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
				tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			}
			
			pv = -( ( tmp[1] * pmt * tmp[2] + fv * tmp[3] ) / tmp[0] );			
		}
		
		return fitToHP12C( pv );
		
	}
	
	// Compound rate (HP12C formula)
	// Key: None
    // Description: Computes interest rate per simple period
	public double simpleRate(double n, double pv, double fv) throws HP12CInputException{
		return fitToHP12C( ( fv - pv ) / ( pv * n ) );
	}
	
	// Compound rate (Traditional formula)
	// Key: None
    // Description: Computes interest rate per compounding period
	public static double rate(double n, double pv, double fv) throws HP12CInputException{
		return fitToHP12C( ( Math.pow( Math.abs( fv / pv ), 1 / n ) - 1 ) );
	}
	
	// Compound rate (HP12C formula)
	// Key: [i]
    // Description: Computes interest rate per compounding period
	public static double rate(double n, double pv, double pmt, double fv, double beg, double c) throws HP12CInputException{
		
		if ((pmt == 0.0) && (n < 0))
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: no solution exists for N.");
		else if ( ( (pv > 0) && (fv > 0) ) || ( (pv < 0) && (fv < 0) ) )
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: both PV and FV are positive or negative.");
		

		double init_interest = -1;
		double final_interest = 99999.0;
		double suposed_interest = 0.0;
		double suposed_payment = 0.0;
		double suposed_difference = 0.0;
		
		int cnt = 1;
		boolean found = false;
		
		// PMT and FV cannot be negative here
		// This algorithm only works if the PMT and FV are positive
		pv = (-Math.abs(pv));
		pmt = Math.abs(pmt);
		fv = Math.abs(fv);
	
		while (true) {
			suposed_interest = (final_interest + init_interest)/2.0;
			suposed_payment = pricePayment(n, suposed_interest, pv, fv, beg, c);
			suposed_difference = Math.abs(pmt-suposed_payment);
			
			if (suposed_difference > 0.000000001) {
				if (suposed_payment > pmt) {
					final_interest = suposed_interest;
				}
				else {
					init_interest = suposed_interest;
				}
			}
			else {
				found = true;
				break;
			}
			if (cnt > 5000) {
				break;
			}
			cnt++;
		}
		
		if (!found)
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: no solution found.");
		
		return fitToHP12C( final_interest );

	}
	
	// Internal Rate or Return
	// Key: [f][IRR]
	// Description: Calculates the internal rate of return (yield) for 
	//              cash flow and initial investment.
	public static double irr(double n, double[][] cf) throws HP12CInputException{

		double npv = 0;
		double irr = 0;
		double u = 0;		
		double cont = 0;
		double expo = 0;
		
		// Checks for safety
		if (cf == null) return 0;
		if (cf.length < n) return 0;
				
		// Flag that is used to sign
		// when an NPV passes from negative to positive 
		// and vice-versa.
		boolean signBefore = true; // true: positive;  false: negative
		boolean signAfter = true;  // true: positive;  false: negative
		
		for(int a=0; a<n; a++){
			cont += cf[a][1];
		}
		
		double IRRBegin = 100.0;
		
		irr = IRRBegin;			
							
		for(int m = 0; m < 100; m++){
		
			u = 1 + (irr / 100.0);

			expo = 0;
			
			if(npv >= 0){
				signBefore = true;
			}else{
				signBefore = false;
			}
							
			for(int j = 0;j <= n; j++){
				if(j == 0){
					npv = cf[0][0];
					
					// Test to correct problems with the loop
					if(npv >= 0){
						signBefore=true;
					}else{
						signBefore=false;
					}
					
					cont++;

				}else{
					for(int k = 0;k < cf[j][1]; k++){
						if(expo <= cont){
						   npv += cf[j][0] / Math.pow(u, ++expo);	
						}
					}					
				}
			}
							
			if(npv >= 0){
				signAfter = true;
			}else{
				signAfter = false;
			}	
			
			if(signBefore != signAfter){
				irr = irr + IRRBegin / Math.pow(2.0, m);

			}else{
				irr = irr - IRRBegin / Math.pow(2.0, m);

			}
			
			if(npv == 0){
				m = 100000;
			}
		}

		return fitToHP12C( irr );
	}
	

	// Net Present Value
	// Key: [f][NPV]
	// Description: Calculates the net present value of cash flow and initial investment.
	public static double npv(double n, double i, double[][] cf) throws HP12CInputException{

		if (i <= (-100))
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"Compound Interest Error: i <= -100");
		
		double npv  = 0.0;
		double u    = 1 + (i / 100.0);		
		double cont = 0.0;
		double expo = 0.0;
		
		// Checks for safety
		if (cf == null) return 0;
		if (cf.length < n) return 0;
		
		for(int a = 0; a <= n; a++){
			cont += cf[a][1];
		}
		for(int j = 0; j <= n; j++){
			if(j == 0){
				npv = cf[0][0];
				cont++;
			}else{
				for(int k = 0; k < cf[j][1]; k++){
					if(expo <= cont){
						npv += cf[j][0] / Math.pow(u, ++expo);	
					}
				}					
			}				
		}
		
		return fitToHP12C( npv );
					
	}
	
	// Amortization (HP12C formula, returns 5 values, take a look at HP12C Users Guide to details)
	// Key: [f][AMORT]
	// Description: Amortizes "x" number of periods in a financial problem. (Calculates the balance after "x" amortizations) 
	public static double[] amortization(double x, double n, double i, double pv, double pmt, double beg, double precision) throws HP12CInputException{

		double tmp[] = new double[5];
		
		double INT = 0;
		double SumINT = 0;
		double PRN = 0;
		double SumPRN = 0;
		double PVj = pv;
		
		// Updating n;
		n += x;
		
		int j = 0;
		for(j=0; j<x; j++){
			if((j==0)&&(beg==1.0)){
				INT=0;
			}else{
				INT = Math.abs(PVj * i/100.0);
				
				// Rounding INT. 
				// HP12C Rounds internally to the current display precision
				INT = Math.round(INT * Math.pow(10, precision)) / Math.pow(10, precision); 
				
				if(pmt<0){
					INT = (-INT);
				}
			}
			
			SumINT += INT;
			
			PRN = pmt - INT;
			
			SumPRN += PRN;
			
			PVj = PVj + PRN;
			
		}

		tmp[0] = fitToHP12C( j );      //  Number of payment periods to be amortized
		tmp[1] = fitToHP12C( SumPRN ); //  Returns a sum of the amortizations in the informed period
		tmp[2] = fitToHP12C( SumINT ); //  Returns a sum of the interests paid in the informed period
		tmp[3] = fitToHP12C( PVj );    //  Returns the remaining balance
		tmp[4] = fitToHP12C( n );      //  Total number of payments amortized
		
		return ( tmp );
	}
	
	// Statistics ===========================================================

	// Arithmetic average or just Mean (Traditional formula)
	// Key: [g][ẍ]
	// Description: calculates the means (arithmetic averages) of the x-values ( x )
	public static double avg(double[] values) throws HP12CInputException{
		
		if (values.length == 0)
			throw new HP12CInputException(HP12CErrors.ERROR_STAT,
			"Average of zero size array");
		
		return fitToHP12C( sum(values)/values.length );
	}		

	// Arithmetic average or just Mean (HP12C formula)
	// Key: [g][ẍ]
	// Description: calculates the means (arithmetic averages) of the x-values ( x ) and of the y-values ( y )
	public static double mean(double sum, double count) throws HP12CInputException{
		
		if (count == 0)
			throw new HP12CInputException(HP12CErrors.ERROR_STAT,
			"Average of zero size array");
		
		return fitToHP12C( sum /count );
	}
	
	// Weighted Average (Traditional formula)
	// Key: [g][ẅ]
	// Description: calculate the weighted mean
	public static double weightedAverage(double[] values, double[] weights) throws HP12CInputException{

		if ((values.length == 0) || (weights.length == 0) || (values.length != weights.length))
			throw new HP12CInputException(HP12CErrors.ERROR_STAT,
			"Weighted Average of zero size array");
		else if  (values.length != weights.length)
			throw new HP12CInputException(HP12CErrors.ERROR_STAT,
			"Weighted Average has different quantity of values and weights");
		
		double productSum = 0;
		double weightSum = 0;
		
		for(int i=0; i<values.length; i++){
			productSum += values[i] * weights[i];
		}
		
		for(int i=0; i<weights.length; i++){
			weightSum += weights[i];
		}
		
		if (weightSum == 0)
			throw new HP12CInputException(HP12CErrors.ERROR_MATH,
			"Weight sum in weighted average is ZERO causing division by ZERO");
		
		return fitToHP12C( productSum / weightSum );
	}
	
	// Weighted Average (HP12C Formula)
	// Key: [g][ẅ]
	// Description: calculate the weighted mean
	public static double weightedAverage(double xySum, double wSum) throws HP12CInputException{
		return fitToHP12C( div(wSum, xySum) );
	}
	
	// Standard deviation (Traditional formula)
	// Key: [g][s]
	// Description: Calculates the standard deviation of the x-values (sx) 
	// and of the y-values (sy)
	public static double standardDeviation(double[] values) throws HP12CInputException{
		
		double sum = sum(values);
		double square = 0;
		double count = values.length;
		
		for(int i=0; i<values.length; i++){
			square += Math.pow(values[i], 2);
		}
		
		double p = count * square - Math.pow(sum, 2);
		double q = count * (count - 1);
			
		return fitToHP12C( Math.pow( (p / q) , 0.5) );
	}
	
	// Standard deviation (HP12C formula)
	// Key: [g][s]
	// Description: Calculates the standard deviation of the x-values (sx) and of the y-values (sy)
	public static double standardDeviation(double sum, double sqr, double count) throws HP12CInputException{

		double p = count * sqr - Math.pow(sum, 2);
		double q = count * (count - 1);
			
		return fitToHP12C( Math.pow( (p / q) , 0.5) );
	}
	
	public static double xLinearEstimation(double xSum, double ySum, double xySum, double xSqr, double count, double yVal) throws HP12CInputException{
		
		double a, b;
		
		b = (xySum - ( ( xSum * ySum ) / count ) ) / ( xSqr - ( Math.pow(xSum,2.0) / count ) );
		
		a = ( ySum / count ) - ( b * ( xSum / count ) );
		
		return fitToHP12C( (yVal - a) / b );
	}

	public static double yLinearEstimation(double xSum, double ySum, double xySum, double xSqr, double count, double xVal) throws HP12CInputException{
		
		double a, b;
		
		b = (xySum - ( ( xSum * ySum ) / count ) ) / ( xSqr - ( Math.pow(xSum,2.0) / count ) );
		
		a = ( ySum / count ) - ( b * ( xSum / count ) );

		return fitToHP12C( a + ( b * xVal ) );
	}

	public static double rLinearEstimation(double xSum, double ySum, double xySum, double xSqr, double ySqr, double count) throws HP12CInputException{
	
		double p, q1, q2;
		
		p  = Math.abs( xySum - ( (xSum * ySum) / count) );
		
		q1 = Math.abs( xSqr - ( Math.pow(xSum, 2.0) / count ));
		
		q2 = Math.abs( ySqr - ( Math.pow(ySum, 2.0) / count ));
		
		return  fitToHP12C( p / Math.sqrt(q1 * q2) );
	}
	
	// Depreciation =============================================================
	
	// Straight line Depreciation (HP12C Formula)
	// Key: [f][SL]
	// Description: Calculates deprecation and remaining depreciable value using the straight-line method.	
	public static double[] slDepreciation(double n, double i, double pv, double fv, double x) throws HP12CInputException{
		
		double cost = pv;
		double sell = fv;
		double life = n;
		double year = x;
		double depr = 0;
		double rest = cost - sell;
		
		double tmp[] = new double[2];
			
		if (year < 0)			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"year < 0");
		else if (year != Math.floor(year))			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"year is not integer");
		else if (life <= 0)			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"life <= 0");
		else if (life > Math.pow(10,10))		
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"life > 10^10");

		while (--year >= 0) {
			depr = (cost - sell) / life;
			rest -= depr;
		}

		tmp[0] = fitToHP12C(rest);
		tmp[1] = fitToHP12C(depr);
		
		return tmp;
	}

	// Sum Of The Years Digits Depreciation (HP12C Formula)
	// Key: [f][SOYD]
	// Description: Calculates deprecation and remaining depreciable value using the sum of the years digits method.	
	public static double[] soydDepreciation(double n, double i, double pv, double fv, double x) throws HP12CInputException{
		
		double cost = pv;
		double sell = fv;
		double life = n;
		double year = x;
		double depr = 0;
		double rest = cost - sell;

		double tmp[] = new double[2];
		
		if (year < 0)			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"year < 0");
		else if (year != Math.floor(year))			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"year is not integer");
		else if (life <= 0)			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"life <= 0");
		else if (life > Math.pow(10,10))		
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"life > 10^10");

		double year_up = 0;
		double soyd = life * ( life + 1.0 ) / 2.0;
		
		while (--year >= 0) {
			depr = ( cost - sell ) * ( life - ( ++year_up ) + 1 ) / soyd;
			rest -= depr;
		}
		
		tmp[0] = fitToHP12C(rest);
		tmp[1] = fitToHP12C(depr);
		
		return tmp;

	}

	// Declining Balance Depreciation (HP12C Formula)
	// Key: [f][DB]
	// Description: Calculates deprecation and remaining depreciable value using the declining balance method.
	public static double[] dbDepreciacion(double n, double i, double pv, double fv, double x) throws HP12CInputException{
		
		double cost = pv;
		double sell = fv;
		double life = n;
		double db = i/100.0;
		double year = x;
		double depr = 0;
		double rest = cost - sell;
		
		double tmp[] = new double[2];

		if (year < 0)			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"year < 0");
		else if (year != Math.floor(year))			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"year is not integer");
		else if (life <= 0)			
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"life <= 0");
		else if (life > Math.pow(10,10))		
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"life > 10^10");
		
		while (--year >= 0) {
			depr = ( rest + sell ) * db / life;
			rest -= depr;
		}

		tmp[0] = fitToHP12C(rest);
		tmp[1] = fitToHP12C(depr);
		
		return tmp;
	}

	// Bond Calculations ============================================
	
	// Bond Price
	// Key: [f][PRICE]
	// Description: calculates bond price, given desired yield to maturity.
	public static double[] bondPrice(double i, double pmt, Date y, Date x) throws HP12CInputException{
		
		/*
		i = 4.75;
		pmt = 6.75;
		y = new Date(2004,4,28);
		x = new Date(2018,6,4);
		*/
		
		if ((x == null) || (!x.isValid()))
			throw new HP12CInputException(HP12CErrors.ERROR_CAL,
			"invalid x date (maturity)");
		else if ((y == null) || (!y.isValid()))
			throw new HP12CInputException(HP12CErrors.ERROR_CAL,
			"invalid y date (buy)");
		else if(i <= -100)
			throw new HP12CInputException(HP12CErrors.ERROR_CI,
			"i <= -100");
		
		double dim = 0; // days between issue date and maturity date
		double dsm = 0; // days between settlement date and maturity date
		double dcs = 0; // days between beginning of current coupon period and settlement date
		double e   = 0; // number of days in coupon period where settlement occurs
		double dsc = e - dcs; // days from settlement date to next 6-month coupon date
		double n   = 0; // number of semiannual coupon payable between settlement date and maturity date
		double cpn = 0; // annual coupon rate (as a percentage)
		double yield = 0; // annual yield (as percentage)
		double price = 0; // dollar price per $100 par value
		double rdv   = 0; // redemption value
		
		// Temporary array
		double tmp[] = new double[2];
		
		// annual yield to maturity
		yield = i;
		// annual coupon rate
		cpn = pmt;
		// redemption value
		rdv = 100;

		// settlement (purchase) date
		Date settlement = new Date(y);
		// maturity (redemption) date
		Date maturity   = new Date(x);
		
		// days between settlement and maturity date
		dsm = Date.diffDates(settlement, maturity); 
		
		// Begin auxiliary section
		Date a = new Date(maturity); 
		Date b = new Date(); 

		while ( a.getSerial() > settlement.getSerial()){
			b = new Date(a);
			++n;
			a.setDay(1);
			a.setMonth(a.getMonth()-6);
			a.setDay(maturity.getDay());
		}
		// End auxiliary section
		
		
		// number of days in coupon period where settlement occurs
		e = Date.diffDates(a, b); 
		//  days from settlement date to next 6-month coupon date
		dcs = Date.diffDates(settlement, b); 
		// days between beginning of current coupon period and settlement date
		dsc = e - dcs;

		
		// Begin HP12C bond price formula
		if(dsm <= e){
			
			tmp[0] = 100 * ( rdv + ( cpn / 2 ) );
			tmp[1] = 100 + ( ( dsm / e ) * ( yield / 2 ) );
			
			tmp[0] = (tmp[0] / tmp[1]);
		}
		else{
			tmp[0] = rdv / Math.pow( ( 1 + ( yield / 200 ) ),  ( n - 1 + ( dcs / e ) ) );
			
			for (int k = 1; k <= n; ++k){
				tmp[1] += ( cpn / 2 ) / Math.pow( ( 1 + ( yield / 200 ) ), ( k - 1 + ( dcs / e ) ) );
			}
			
			tmp[0] = tmp[0] + tmp[1];
		}
		
		tmp[1] = ( cpn / 2 ) * ( dsc / e );
		tmp[0] = tmp[0] - tmp[1];
		// End HP12C bond price formula
		
		
		
		// bond price (as a percent of par)
		tmp[0] = HP12CFunctions.fitToHP12C(tmp[0]);
		// accrued interest
		tmp[1] = HP12CFunctions.fitToHP12C(tmp[1]);
		
		return tmp;
		
	}

	// Bond Yield
	// Key: [f][YTM]
	// Description: calculates yield to maturity, given bond price.
	public static double bondYield(double pv, double pmt, Date y, Date x) throws HP12CInputException{
		// TODO
		return 0;
	}

}

