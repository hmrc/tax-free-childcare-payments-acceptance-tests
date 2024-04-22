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

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.service.ExciseMovementControlSystemService
import uk.gov.hmrc.test.api.utils.TextUtils.updateLRNOnInputAndOutputTestDataString
import uk.gov.hmrc.test.api.utils.XmlUtils.getRequestXmlFileAsString

class ExciseMovementControlSystemHelper {

  val exciseMovementControlSystemService: ExciseMovementControlSystemService =
    new ExciseMovementControlSystemService

  def callPostEMCSResponse(authBearerToken: String, filename: String, lrn: String): StandaloneWSRequest#Self#Response =
    if (filename.equals("Stub"))
      postEMCSResponse(authBearerToken, filename)
    else
      postEMCSResponse(authBearerToken, filename, lrn)

  def callPostEMCSResponseIE818(
    authBearerToken: String,
    filename: String,
    lrn: String
  ): StandaloneWSRequest#Self#Response =
    if (filename.equals("Stub"))
      postEMCSResponseStubIE818(authBearerToken, filename, lrn)
    else
      postEMCSResponseIE818(authBearerToken, filename, lrn)

  def postEMCSResponse(authBearerToken: String, filename: String, lrn: String): StandaloneWSRequest#Self#Response = {
    val emcsPostResponse: StandaloneWSRequest#Self#Response =
      exciseMovementControlSystemService.postPayload(
        authBearerToken,
        updateLRNOnInputAndOutputTestDataString(getRequestXmlFileAsString("request", filename), lrn)
      )
    emcsPostResponse
  }

  def postEMCSResponse(authBearerToken: String, filename: String): StandaloneWSRequest#Self#Response = {
    val emcsPostResponse: StandaloneWSRequest#Self#Response =
      exciseMovementControlSystemService.postPayload(
        authBearerToken,
        getRequestXmlFileAsString("stubRequest", filename)
      )
    emcsPostResponse
  }

  def postEMCSResponseIE818(
    authBearerToken: String,
    filename: String,
    lrn: String
  ): StandaloneWSRequest#Self#Response = {
    val emcsPostResponse: StandaloneWSRequest#Self#Response =
      exciseMovementControlSystemService.postPayload(
        authBearerToken,
        getRequestXmlFileAsString("request", filename),
        lrn
      )
    emcsPostResponse
  }

  def postEMCSResponseStubIE818(
    authBearerToken: String,
    filename: String,
    lrn: String
  ): StandaloneWSRequest#Self#Response = {
    val emcsPostResponse: StandaloneWSRequest#Self#Response =
      exciseMovementControlSystemService.postPayload(
        authBearerToken,
        getRequestXmlFileAsString("stubRequest", filename),
        lrn
      )
    emcsPostResponse
  }

}
