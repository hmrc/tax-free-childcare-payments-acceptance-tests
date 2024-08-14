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

package uk.gov.hmrc.test.api.specs

import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.models.User
import User._
class TfcpLinkEndpointHappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCP Link Endpoint happy path for different outbound child reference") {

    val scenarios =
      List(
        aaResp,
        bbResp,
        ccResp,
        ddResp,
        ffResp
      )

    scenarios.foreach { scenarioName =>
      Scenario(s"Verify Link endpoint for predefined test cases: $scenarioName") {
        val consignorToken = givenGetToken(scenarioName.outboundChildPaymentRef, 250, "Individual")
        val response       =
          tfcLink(
            consignorToken,
            correlationId,
            eppUniqueCusId,
            eppRegRef,
            scenarioName.outboundChildPaymentRef,
            childDOB
          )
        thenValidateResponseCode(response, scenarioName.statusCode)
        checkJsonValue(response, "child_full_name", scenarioName.childName)
      }
    }
  }
}
