package com.ecommerce.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public interface CommonUtilities {
	
	public static Function<String,String> formattedDateString=datePattern->DateTimeFormatter.ofPattern(datePattern).format(LocalDateTime.now());
	
	
}
