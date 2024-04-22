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

package uk.gov.hmrc.test.api.helpers

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.models.{IndividualsLinks, User}
import uk.gov.hmrc.test.api.service.IndividualsMatchingService

import scala.util.matching.Regex

class TestDataHelper {
  val individualsMatchingServiceAPI: IndividualsMatchingService = new IndividualsMatchingService

  def createAnIndividual(authBearerToken: String, expectedIndividual: User): String = {
    val individualsMatchPostResponse: StandaloneWSRequest#Self#Response =
      individualsMatchingServiceAPI.postIndividualPayload(authBearerToken, expectedIndividual)
    obtainMatchIdFromHref(individualsMatchPostResponse)
  }

  def obtainMatchIdFromHref(individualsMatchPostResponse: StandaloneWSRequest#Self#Response): String = {
    val individualsLinks: IndividualsLinks =
      (Json.parse(individualsMatchPostResponse.body) \ "_links" \ "individual").as[IndividualsLinks]
    val pattern: Regex                     = "/individuals/matching/([0-9a-z-]+)".r
    val pattern(matchId: String)           = individualsLinks.href
    matchId
  }

  def getExpectedResponseCode(filename: String): String =
    filename.substring(filename.length - 3)

}
