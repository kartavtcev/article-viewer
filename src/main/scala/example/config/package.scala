package example

import io.circe.Decoder
import io.circe.generic.semiauto._

package object  config {
  implicit val eleviocDec: Decoder[ElevioConfig] = deriveDecoder

  implicit val aacDec: Decoder[ApiAuth] = deriveDecoder
  implicit val aucDec: Decoder[ApiUrls] = deriveDecoder
}