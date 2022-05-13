package scerde
package error

trait Error[+E] {
  def custom(msg: String): E
}

object Error extends ErrorInstances {

  @inline final def apply[E](implicit ev: Error[E]): Error[E] = ev

  @inline final def custom[E](msg: String)(implicit ev: Error[E]): E =
    ev.custom(msg)

}

abstract private[scerde] class ErrorInstances extends LowPriorityErrorInstances {

  implicit final def errorProjectionForSeqAccess[A](implicit ev: de.SeqAccess[A]): Error[ev.Err] =
    ev.error

  implicit final def errorProjectionForMapAccess[A](implicit ev: de.MapAccess[A]): Error[ev.Err] =
    ev.error

}

abstract private[scerde] class LowPriorityErrorInstances extends VeryLowPriorityErrorInstances {

  implicit final def errorProjectionForSerializer[S](implicit ev: ser.Serializer[S]): Error[ev.Err] =
    ev.error

  implicit final def errorProjectionForDeserializer[D](implicit ev: de.Deserializer[D]): Error[ev.Err] =
    ev.error

}

abstract private[scerde] class VeryLowPriorityErrorInstances {

  implicit final val errorForString: Error[String] = new Error[String] {
    final override def custom(msg: String): String = msg
  }

  implicit final val errorForThrowable: Error[Throwable] = new Error[Throwable] {
    final override def custom(msg: String): Throwable = new RuntimeException(msg)
  }

}
