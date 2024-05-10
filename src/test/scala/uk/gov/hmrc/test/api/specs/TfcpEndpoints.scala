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
class TfcpEndpoints extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCP Link Endpoints happy path for different ninos") {

    val scenarios =
      List(
        ninoEndsWithA,
        ninoEndsWithB,
        ninoEndsWithC,
        ninoEndsWithD
      )

    scenarios.foreach { scenarioName =>
      Scenario(s"Verify Link endpoint for ninos: $scenarioName") {
        val consignorToken = givenGetToken(scenarioName.nino)
        val response       =
          tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
        thenValidateResponseCode(response, 200)
        checkJsonValue(response, "child_full_name", scenarioName.childName)
      }
    }
    Scenario("Verify Balance Endpoints happy path") {
      val consignorToken = givenGetToken(ninoEndsWithA.nino)
      val response       = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 200)
      checkJsonValue(response, "tfc_account_status", "active")
    }
    Scenario("Verify Payments Endpoints happy path") {
      val consignorToken = givenGetToken(ninoEndsWithA.nino)
      val response       = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 200)
    }
  }
}
