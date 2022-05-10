package scerde
package syntax

trait DeserializerSyntax {

  implicit def toDeserializerOps[D: Deserializer](self: D): Deserializer.DeserializerOps[D] =
    new Deserializer.DeserializerOps(self)

}

trait VisitorSyntax {

  implicit def toVisitorOps[V: Visitor](self: V): Visitor.VisitorOps[V] =
    new Visitor.VisitorOps(self)

}

object de extends DeserializerSyntax with VisitorSyntax
