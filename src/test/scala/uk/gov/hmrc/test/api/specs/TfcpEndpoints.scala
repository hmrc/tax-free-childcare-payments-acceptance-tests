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

class TfcpEndpoints extends BaseSpec with CommonSpec with HttpClient {

  Feature("TFCPayments APIs") {

    Scenario(s"Connect to TFC api link") {

      val consignorToken = givenGetToken("AB123456C")
      val response       = tfcLink(consignorToken)
      thenValidateResponseCode(response, 200)

    }

    Scenario("Connect to TFC api Balance") {
      val consignorToken = givenGetToken("AB123456C")
      val response       = tfcBalance(consignorToken)
      thenValidateResponseCode(response, 200)

    }
  }
}
