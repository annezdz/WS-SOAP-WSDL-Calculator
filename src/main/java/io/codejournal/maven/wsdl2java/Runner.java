package io.codejournal.maven.wsdl2java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Runner {
	public static void main(String[] args) throws MalformedURLException {
		
		final String endpoint = "https://www.dataaccess.com/webservicesserver/numberconversion.wso";
	
		final URL url = URI.create(endpoint).toURL();
		
		final NumberConversion service = new NumberConversion(url);
		
		final NumberConversionSoapType port = service.getPort(NumberConversionSoapType.class);
		
		System.out.println("$15.99 --> "+ port.numberToDollars(BigDecimal.valueOf(15.99)));
		System.out.println("$27  --> "+ port.numberToWords(BigInteger.valueOf(34)));

				
		}

}
