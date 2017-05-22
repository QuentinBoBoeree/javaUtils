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
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface GppXlsxColumn {
	String name() default "";
	ValueType valueType() default ValueType.TYPE_STRING;
}
