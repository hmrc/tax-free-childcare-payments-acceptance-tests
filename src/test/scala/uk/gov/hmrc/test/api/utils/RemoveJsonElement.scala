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

import play.api.libs.json._
import uk.gov.hmrc.test.api.utils.JsonUtils.getResponseJsonFileAsString

object RemoveJsonElement {

  def removeJsonElement(directory: String, filename: String, key: String): String = {
    val filePath = s"src/test/resources/json/$directory/$filename.json"

    val source       = scala.io.Source.fromFile(filePath)
    val parsedJson   = Json.parse(source.mkString)
    val modifiedJson = removeKey(parsedJson, key)

    source.close()

    Json.prettyPrint(modifiedJson)
  }

  def removeJsonElement(payload: String, key: String): String = {
    val parsedJson   = Json.parse(payload)
    val modifiedJson = removeKey(parsedJson, key)
    Json.prettyPrint(modifiedJson)
  }

  def removeKey(json: JsValue, keyToRemove: String): JsValue =
    json match {
      case obj: JsObject =>
        JsObject(
          obj.fields
            .filterNot { case (key, _) =>
              key == keyToRemove
            }
            .map { case (key, value) =>
              key -> removeKey(value, keyToRemove)
            }
        )
      case arr: JsArray  =>
        JsArray(arr.value.map(removeKey(_, keyToRemove)))
      case _             => json
    }

}
