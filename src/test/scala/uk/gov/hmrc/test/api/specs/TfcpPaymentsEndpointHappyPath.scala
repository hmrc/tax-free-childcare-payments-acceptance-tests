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

class TfcpPaymentsEndpointHappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCP Payment Endpoints happy path") {

    Scenario(s"Verify Payments endpoint for predefined test cases: $aaResp.outboundChildPaymentRef") {
      val consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
      val response       = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 200)
      checkJsonValue(response, "payment_reference","8327950288419716")
      returnJsonValueIsDate(response, "estimated_payment_date")
      checkJsonValue(response, "estimated_payment_date", "2024-10-01")
    }
  }
}
