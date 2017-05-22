/**
 * 
 */
package com.metaship.edu.utils.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author None
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
public @interface GppXlsxObject {
	String filepath() default "";
	String sheet()	default "";
}
