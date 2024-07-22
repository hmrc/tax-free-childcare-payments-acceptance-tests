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

class TfcpLinkEndpointsUnhappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("Connect to TFCP API Link endpoints unhappy path") {
    val consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    Scenario(s"Link endpoints with payload missing correlation id") {
      val response =
        tfcLinkWithoutCorrelationId(
          consignorToken,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints payload with an empty correlation id") {
      val response =
        tfcLink(consignorToken, "", eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints payload with an correlation id as int") {
      val response =
        tfcLinkInvalidDataTypeCorrelationID(
          consignorToken,
          123,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints payload with an invalid correlation id") {
      val response =
        tfcLink(consignorToken, "1234", eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints with a payload with an empty EPP unique customer ID") {
      val response =
        tfcLink(consignorToken, correlationId, "", eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints with a payload containing a different data type of EPP unique customer ID") {
      val response =
        tfcLinkInvalidDataTypeEPPUniqueCusId(
          consignorToken,
          correlationId,
          123,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints with a payload containing a invalid json field of EPP unique customer ID") {
      val response =
        tfcLinkInvalidFieldEPPUniqueCusId(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoints with a payload with an missing EPP unique customer ID") {
      val response =
        tfcLinkWithoutEPPCusId(consignorToken, correlationId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint payload with an empty EPP registration reference") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, "", aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint payload with an invalid json field of EPP registration reference") {
      val response =
        tfcLinkInvalidFieldEPPRegRef(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint payload with an different data type of EPP registration reference") {
      val response =
        tfcLinkInvalidDataTypeEPPRegRef(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          123,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint payload with an missing EPP registration reference") {
      val response =
        tfcLinkWithoutEppRegRef(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }

    Scenario(s"Link endpoint payload with an unmatched Outbound child payment reference number in the stub") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "AAaa00000TFC", childDOB)
      thenValidateResponseCode(response, 500)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", "The server encountered an error and couldn't process the request")
    }
    val scenarios =
      List(
        "",
        "AAAA00000TFC123",
        "123AAAA00000TFC",
        "ABBdddddddddddddddddBB",
        "AAAA00000tfc",
        "AAAAXXXXXXTFC",
        "AAAA123456789TFC"
      )

    scenarios.foreach { outboundPayRef =>
      Scenario(
        s"Verify Link endpoint for predefined outbound child Payment reference number: $outboundPayRef"
      ) {
        val response =
          tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, outboundPayRef, childDOB)
        thenValidateResponseCode(response, 400)
        checkJsonValue(response, "errorCode", "E0000")
        checkJsonValue(response, "errorDescription", jsonErrorDescription)
      }
    }
    Scenario(s"Link endpoint payload with an missing Outbound child payment reference number") {
      val response =
        tfcLinkWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint payload with an invalid data type Outbound child payment reference number") {
      val response =
        tfcLinkInvalidDataTypeOutboundChildPayRef(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          123,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint payload with an invalid Field Outbound child payment reference number") {
      val response =
        tfcLinkInvalidFieldOutboundChildPayRef(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint with a payload with an invalid child date of birth") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, "childDOB")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0021")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint with a payload with an child date of birth different date format") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, "23-05-2018")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0021")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint with a payload with an empty child date of birth") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, "")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0021")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint with a payload with an child date of birth data type as int") {
      val response =
        tfcLinkInvalidDataTypeChildDOB(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          123.45
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0021")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link endpoint with a payload with an child date of birth invalid field") {
      val response =
        tfcLinkInvalidFieldChildDOB(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0021")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Link with a payload with an missing child date of birth") {
      val response =
        tfcLinkWithoutChildDOB(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0006")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
  }
}
