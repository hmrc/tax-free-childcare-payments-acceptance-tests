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

  Feature("TFCP Link Balance and Payments NSI error validations") {
    val scenarios =
      List(
        e0000Resp,
        e0001Resp,
        e0002Resp,
        e0003Resp,
        e0004Resp,
        e0005Resp,
        e0006Resp,
        e0007Resp,
        e0008Resp,
        e0020Resp,
        e0021Resp,
        e0022Resp,
        e0023Resp,
        e0024Resp,
        e0025Resp,
        e0026Resp,
        e0401Resp,
        e0030Resp,
        e0031Resp,
        e0032Resp,
        e0033Resp,
        e0034Resp,
        e0035Resp,
        e0040Resp,
        e0041Resp,
        e0042Resp,
        e0043Resp,
        e9000Resp,
        e9999Resp,
        e8000Resp,
        e8001Resp,
        unknownResp
      )

    scenarios.foreach { scenarioName =>
      Scenario(s"Verify Link endpoint for NSI error : ${scenarioName.outboundChildPaymentRef}") {
        val consignorToken = givenGetToken(scenarioName.outboundChildPaymentRef, 250, "Individual")
        val linkResponse   =
          tfcLink(
            consignorToken,
            correlationId,
            eppUniqueCusId,
            eppRegRef,
            scenarioName.outboundChildPaymentRef,
            childDOB
          )
        thenValidateResponseCode(linkResponse, scenarioName.statusCode)
        checkJsonValue(linkResponse, "errorCode", scenarioName.errorCode)
        checkJsonValue(linkResponse, "errorDescription", scenarioName.errorDescription)

        val balanceResponse =
          tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, scenarioName.outboundChildPaymentRef)
        thenValidateResponseCode(balanceResponse, scenarioName.statusCode)
        checkJsonValue(balanceResponse, "errorCode", scenarioName.errorCode)
        checkJsonValue(balanceResponse, "errorDescription", scenarioName.errorDescription)

        val paymentResponse = tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          scenarioName.outboundChildPaymentRef,
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
