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
import uk.gov.hmrc.test.api.models.User._

class TfcpNSIErrorValidations extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCP Link NSI error validations") {
    val scenarios =
      List(
        ninoE0000,
        ninoE0001,
        ninoE0002,
        ninoE0003,
        ninoE0004,
        ninoE0005,
        ninoE0006,
        ninoE0007,
        ninoE0008,
        ninoE0009,
        ninoE0010,
        ninoE0020,
        ninoE0021,
        ninoE0022,
        ninoE0024,
        ninoE9000,
        ninoE9999,
        ninoE8000,
        ninoE8001
      )

    scenarios.foreach { scenarioName =>
      Scenario(s"Verify Link endpoint for NSI error : ${scenarioName.nino}") {
        val consignorToken = givenGetToken(scenarioName.nino, 250)
        val linkResponse   =
          tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
        thenValidateResponseCode(linkResponse, scenarioName.statusCode)
        checkJsonValue(linkResponse, "errorCode", scenarioName.errorCode)
        checkJsonValue(linkResponse, "errorDescription", scenarioName.errorDescription)

        val balanceResponse =
          tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
        thenValidateResponseCode(balanceResponse, scenarioName.statusCode)
        checkJsonValue(balanceResponse, "errorCode", scenarioName.errorCode)
        checkJsonValue(balanceResponse, "errorDescription", scenarioName.errorDescription)

        val paymentResponse = tfcPayment(
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
        thenValidateResponseCode(paymentResponse, scenarioName.statusCode)
        checkJsonValue(paymentResponse, "errorCode", scenarioName.errorCode)
        checkJsonValue(paymentResponse, "errorDescription", scenarioName.errorDescription)
      }
    }
  }
}
