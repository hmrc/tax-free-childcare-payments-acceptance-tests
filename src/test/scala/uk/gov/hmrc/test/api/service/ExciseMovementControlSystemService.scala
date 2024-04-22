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

package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration

import scala.concurrent.Await
import scala.concurrent.duration._

class ExciseMovementControlSystemService extends HttpClient {

  val host: String                           = TestConfiguration.url("emcs")
  val exciseMovementControlSystemURL: String = s"$host/" + TestConfiguration.getConfigValue("emcs-api-uri")
  val uri: String                            = TestConfiguration.getConfigValue("emcs-api-uri")

  def postPayload(authToken: String, payload: String): StandaloneWSRequest#Self#Response =
    Await.result(
      post(
        exciseMovementControlSystemURL,
        payload,
        ("Content-Type", "application/xml"),
        ("Authorization", authToken),
        ("Accept", "application/vnd.hmrc.1.0+xml")
      ),
      10.seconds
    )

  def postPayload(authToken: String, payload: String, lrn: String): StandaloneWSRequest#Self#Response = {
    val finalApiUrl = s"$exciseMovementControlSystemURL/$lrn/messages"
    Await.result(
      post(
        finalApiUrl,
        payload,
        ("Content-Type", "application/xml"),
        ("Authorization", authToken),
        ("Accept", "application/vnd.hmrc.1.0+xml")
      ),
      10.seconds
    )
  }
}
