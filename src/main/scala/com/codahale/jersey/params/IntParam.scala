package com.codahale.jersey.params

/**
 * Parses ints.
 * 
 * @author coda
 */
case class IntParam(s: String) extends AbstractParam[Int](s) {
  protected def parse(input: String) = input.toInt

  override protected def renderError(input: String, e: Throwable) =
    "Invalid parameter: %s (Must be an integer value.)".format(input)
}