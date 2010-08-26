package com.codahale.jersey.params

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

/**
 * An abstract base class from which to build parameter classes.
 *
 * @author coda
 */
abstract class AbstractParam[A](val input: String) {
  val value: A = try {
    parse(input)
  } catch {
    case e => throw new WebApplicationException(onError(input, e))
  }

  /**
   * Given a string representation, parse it and return an instance of the
   * parameter type.
   */
  protected def parse(input: String): A

  /**
   * Given a string representation which was unable to be parsed and the
   * exception thrown, produce a Response to be sent to the client.
   *
   * By default, generates a 400 Bad Request with a plain text entity generated
   * by renderError.
   */
  protected def onError(input: String, e: Throwable): Response = {
    Response.status(Status.BAD_REQUEST).entity(renderError(input, e)).build
  }

  /**
   * Given a string representation which was unable to be parsed and the
   * exception thrown, produce a plain text entityt to be sent to the client.
   */
  protected def renderError(input: String, e: Throwable): String = {
    "Invalid parameter: %s (%s)".format(input, e.getMessage)
  }

  override def toString = value.toString
}