package scerde
package syntax

import scerde.de.{Deserializer, Visitor}
import scerde.ser.{Serialize, Serializer}

private[scerde] trait DeserializerSyntax {

  implicit final def toDeserializerOps[D: Deserializer](self: D): Deserializer.Ops[D] =
    new Deserializer.Ops(self)

}

private[scerde] trait SerializeSyntax {

  implicit final def toSerializeOps[T: Serialize](self: T): Serialize.Ops[T] =
    new Serialize.Ops(self)

}

private[scerde] trait SerializerSyntax {

  implicit final def toSerializerOps[S: Serializer](self: S): Serializer.Ops[S] =
    new Serializer.Ops(self)

}

private[scerde] trait VisitorSyntax {

  implicit final def toVisitorOps[V: Visitor](self: V): Visitor.Ops[V] =
    new Visitor.Ops(self)

}

private[scerde] trait AllSyntax extends DeserializerSyntax with SerializeSyntax with SerializerSyntax with VisitorSyntax

object all extends AllSyntax
