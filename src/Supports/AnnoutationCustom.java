package Supports;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class AnnoutationCustom {

	//This is an Custom Annotation
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface CustomeAnnoutation {
		String baseURL();
	}
}
