package scerde

trait Error[E] {
  def custom(msg: String): E
}

object Error extends ErrorInstances {

  @inline def apply[E](implicit ev: Error[E]): Error[E] = ev

}

abstract private[scerde] class ErrorInstances {

  implicit val errorForThrowable: Error[Throwable] = new Error[Throwable] {
    override def custom(msg: String): Throwable = new RuntimeException(msg)
  }

  implicit val errorForString: Error[String] = new Error[String] {
    override def custom(msg: String): String = msg
  }

}
