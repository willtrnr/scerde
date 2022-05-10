package scerde
package syntax

trait AllSyntax extends DeserializerSyntax with VisitorSyntax

object all extends AllSyntax
