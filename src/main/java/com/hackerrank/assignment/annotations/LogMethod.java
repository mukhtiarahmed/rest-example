package com.hackerrank.assignment.annotations;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * The annotation marking the need of logging.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Target(value = {METHOD})
public @interface LogMethod {
}
