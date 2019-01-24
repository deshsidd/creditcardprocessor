package test.java.ccprocessor;

import org.junit.Test;

import main.java.com.ccprocessor.ccvalidator.Luhn;

import static org.junit.Assert.assertEquals;

public class LuhnTest {
	
   @Test
   public void testCardValidity() {
   
	   String cardNumber1 = "4111111111111111";
	   String cardNumber2 = "1234567890123456";
	  
	  Luhn validator = new Luhn();
      boolean isValid1 = validator.validate(cardNumber1);
      assertEquals(true, isValid1);
      
      boolean isValid2 = validator.validate(cardNumber2);
      assertEquals(false, isValid2);      
   }   
}