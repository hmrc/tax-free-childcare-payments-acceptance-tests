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
import uk.gov.hmrc.test.api.models.UsersHappyPath.{aaResp, bbResp, ccResp, ddResp, ffResp}

class TfcpBalanceEndpointHappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCP Balance Endpoint happy path") {
    val consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    val scenarios      =
      List(
        aaResp,
        bbResp,
        ccResp,
        ddResp,
        ffResp
      )
    scenarios.foreach { scenarioName =>
      Scenario(s"Verify Balance endpoint for predefined test cases: $scenarioName") {
        val response =
          tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, scenarioName.outboundChildPaymentRef)
        thenValidateResponseCode(response, scenarioName.statusCode)
        assert(returnJsonValue(response, "tfc_account_status") == scenarioName.tfcAccountStatus)
        assert(validateJsonValueIsInteger(response, "government_top_up") == scenarioName.govTopUp)
        assert(validateJsonValueIsInteger(response, "top_up_allowance") == scenarioName.topUpAllowance)
        assert(validateJsonValueIsInteger(response, "paid_in_by_you") == scenarioName.paidInByYou)
        assert(validateJsonValueIsInteger(response, "total_balance") == scenarioName.totalBalance)
        assert(validateJsonValueIsInteger(response, "cleared_funds") == scenarioName.clearedFunds)
      }
    }
  }
}
