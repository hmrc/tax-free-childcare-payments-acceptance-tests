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

  Feature("TFCP Link APIs") {
    val consignorToken       = givenGetToken("AB123456C")
    val correlationId        = "5c5ef9c2-72e8-4d4f-901e-9fec3db8c64b"
    val eppUniqueCusId       = "12345678910"
    val eppRegReff           = "EPPRegReffEPPReg"
    val outboundChildPayReff = "AAAA00000TFC"
    val childDOB             = "2018-05-23"

    Scenario(s"Connect to TFCP api link with valid payload") {
      val response = tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 200)
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid correlation id") {
      val response =
        tfcLink(consignorToken, "correlationId", eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid EPP unique customer ID") {
      val response =
        tfcLink(consignorToken, correlationId, "eppUniqueCusId", eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid EPP registration reference") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, "eppRegReff", outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid Outbound child payment reference number") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, "outboundChildPayReff", childDOB)
      thenValidateResponseCode(response, 400)
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid child date of birth") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, "childDOB")
      thenValidateResponseCode(response, 400)
    }
  }
}
