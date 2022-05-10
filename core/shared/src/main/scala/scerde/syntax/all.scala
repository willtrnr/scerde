package scerde
package syntax

trait AllSyntax extends DeserializerSyntax with SerializeSyntax with SerializerSyntax with VisitorSyntax

object all extends AllSyntax
