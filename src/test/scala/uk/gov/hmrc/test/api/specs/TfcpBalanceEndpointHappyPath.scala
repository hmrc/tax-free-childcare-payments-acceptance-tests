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

class TfcpBalanceEndpointHappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCP Balance Endpoint happy path") {
    val consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    Scenario(s"Verify Balance endpoint for predefined test cases for ACTIVE status") {

      val response =
        tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 200)
      assert(returnJsonValue(response, "tfc_account_status") == "ACTIVE")
      assert(validateJsonValueIsInteger(response, "government_top_up") == 14159)
      assert(validateJsonValueIsInteger(response, "top_up_allowance") == 26535)
      assert(validateJsonValueIsInteger(response, "paid_in_by_you") == 89793)
      assert(validateJsonValueIsInteger(response, "total_balance") == 23846)
      assert(validateJsonValueIsInteger(response, "cleared_funds") == 26433)
    }
    Scenario(s"Verify Balance endpoint for predefined test cases for INACTIVE status") {
      val response =
        tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, bbResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 200)
      assert(returnJsonValue(response, "tfc_account_status") == "INACTIVE")
      assert(validateJsonValueIsInteger(response, "government_top_up") == 14159)
      assert(validateJsonValueIsInteger(response, "top_up_allowance") == 26535)
      assert(validateJsonValueIsInteger(response, "paid_in_by_you") == 89793)
      assert(validateJsonValueIsInteger(response, "total_balance") == 23846)
      assert(validateJsonValueIsInteger(response, "cleared_funds") == 26433)
    }
    Scenario(s"Verify Balance endpoint for predefined test cases for UNKNOWN status") {
      val response =
        tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "AAEE12345TFC")
      thenValidateResponseCode(response, 502)
      checkJsonValue(response, "errorCode", "ETFC3")
      checkJsonValue(
        response,
        "errorDescription",
        "Bad Gateway"
      )
    }
  }
}
