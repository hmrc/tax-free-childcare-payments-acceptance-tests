/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.test.api.utils

import io.circe.Json
import io.circe.parser.parse

import scala.io.Source

object JsonUtils {
  def getRequestJsonFileAsString(fileName: String): String =
    Source.fromResource(s"json/request/$fileName.json").getLines().mkString

  def getResponseJsonFileAsString(directory: String, fileName: String): String =
    Source.fromResource(s"json/$directory/$fileName.json").mkString

  def convertJsonFormatAsString(jsonBody: String): String = parse(jsonBody).getOrElse(Json.Null).noSpaces

  def flatten(json: Json, prefix: String = ""): Map[String, String] =
    json.fold(
      jsonNull = Map(prefix -> "null"),
      jsonBoolean = value => Map(prefix -> value.toString),
      jsonNumber = value => Map(prefix -> value.toString),
      jsonString = value => Map(prefix -> value),
      jsonArray = elements =>
        elements.zipWithIndex.flatMap { case (element, index) =>
          val newKey = s"$prefix[$index]"
          flatten(element, newKey)
        }.toMap,
      jsonObject = fields =>
        fields.toMap.flatMap { case (key, value) =>
          val newKey = if (prefix.isEmpty) key else s"$prefix.$key"
          flatten(value, newKey)
        }
    )
}
