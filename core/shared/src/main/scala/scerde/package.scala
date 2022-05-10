package object scerde extends syntax.AllSyntax {

  @inline private[scerde] def void(a: Any): Unit = (a, ())._2

  @inline private[scerde] def void(a: Any, b: Any): Unit = (a, b, ())._3

}
