package object scerde extends syntax.AllSyntax {

  @inline private[scerde] def void(a: Any): Unit = (a, ())._2

  @inline private[scerde] def void(a: Any, b: Any): Unit = (a, b, ())._3

  @inline private[scerde] def tri[T](body: => T): Either[Throwable, Unit] =
    try {
      val _ = body
      Right(())
    } catch {
      case e: Exception =>
        Left(e)
    }

  @inline private[scerde] def etri[T](body: => Either[Throwable, T]): Either[Throwable, T] =
    try {
      body
    } catch {
      case e: Exception =>
        Left(e)
    }

}
