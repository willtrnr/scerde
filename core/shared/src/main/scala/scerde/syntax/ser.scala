package scerde
package syntax

trait SerializeSyntax {

  implicit def toSerializeOps[T: Serialize](self: T): Serialize.SerializeOps[T] =
    new Serialize.SerializeOps(self)

}

trait SerializerSyntax {

  implicit def toSerializerOps[S: Serializer](self: S): Serializer.SerializerOps[S] =
    new Serializer.SerializerOps(self)

}

object ser extends SerializeSyntax with SerializerSyntax
