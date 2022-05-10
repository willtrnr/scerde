package scerde
package syntax

trait DeserializerSyntax {

  implicit def toDeserializerOps[D: Deserializer](self: D): Deserializer.DeserializerOps[D] =
    new Deserializer.DeserializerOps[D](self)

}

trait VisitorSyntax {

  implicit def toVisitorOps[V: Visitor](self: V): Visitor.VisitorOps[V] =
    new Visitor.VisitorOps[V](self)

}

object de extends DeserializerSyntax with VisitorSyntax
