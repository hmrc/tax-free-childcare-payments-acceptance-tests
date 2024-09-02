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
import uk.gov.hmrc.test.api.models.UsersHappyPath.aaResp

class TfcpBalanceEndpointsUnhappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("Connect to TFCP API Balance endpoints unhappy path") {
    val consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    Scenario(s"Balance endpoints with payload missing correlation id") {
      val response =
        tfcBalanceWithoutCorrelationId(consignorToken, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Balance endpoints payload with an empty correlation id") {
      val response = tfcBalance(consignorToken, "", eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Balance endpoints payload with an correlation id as int") {
      val response = tfcBalanceInvalidDataTypeCorrelationID(
        consignorToken,
        123,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Balance endpoints payload with an invalid correlation id") {
      val response = tfcBalance(consignorToken, "1234", eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Balance endpoints with a payload with an empty EPP unique customer ID") {
      val response = tfcBalance(consignorToken, correlationId, "", eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")
    }
    Scenario(s"Balance endpoints with a payload containing a different data type of EPP unique customer ID") {
      val response = tfcBalanceInvalidDataTypeEPPUniqueCusId(
        consignorToken,
        correlationId,
        123,
        eppRegRef,
        aaResp.outboundChildPaymentRef
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")
    }
    Scenario(s"Balance endpoints with a payload containing a invalid json field of EPP unique customer ID") {
      val response = tfcBalanceInvalidFieldEPPUniqueCusId(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")
    }
    Scenario(s"Balance endpoints with a payload with an missing EPP unique customer ID") {
      val response =
        tfcBalanceWithoutEPPUniqueCusID(consignorToken, correlationId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")

    }
    Scenario(s"Balance endpoint payload with an empty EPP registration reference") {
      val response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, "", aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Balance endpoint payload with an invalid json field of EPP registration reference") {
      val response = tfcBalanceInvalidFieldEPPRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Balance endpoint payload with an different data type of EPP registration reference") {
      val response = tfcBalanceInvalidDataTypeEPPRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        123,
        aaResp.outboundChildPaymentRef
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Balance endpoint payload with an missing EPP registration reference") {
      val response =
        tfcBalanceWithoutEppRegRef(consignorToken, correlationId, eppUniqueCusId, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }

    Scenario(s"Balance endpoint payload with an unmatched Outbound child payment reference number in the stub") {
      val response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "AAaa00000TFC")
      thenValidateResponseCode(response, 500)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(
        response,
        "errorDescription",
        "We encountered an error on our servers and did not process your request, please try again later."
      )
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
        s"Verify Balance endpoint for predefined outbound child Payment reference number: $outboundPayRef"
      ) {
        val response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, outboundPayRef)
        thenValidateResponseCode(response, 400)
        checkJsonValue(response, "errorCode", "E0001")
        checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")

      }
    }
    Scenario(s"Balance endpoint payload with an missing Outbound child payment reference number") {
      val response = tfcBalanceWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
    }
    Scenario(s"Balance endpoint payload with an invalid data type Outbound child payment reference number") {
      val response =
        tfcBalanceInvalidDataTypeOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef, 123)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
    }
    Scenario(s"Balance endpoint payload with an invalid Field Outbound child payment reference number") {
      val response = tfcBalanceInvalidFieldOutboundChildPayRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
    }
    Scenario(s"Verify Balance endpoint for predefined test cases for UNKNOWN status") {
      val response =
        tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, etfc3Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, etfc3Resp.statusCode)
      checkJsonValue(response, "errorCode", etfc3Resp.errorCode)
      checkJsonValue(
        response,
        "errorDescription",
        etfc3Resp.errorDescription
      )
    }
  }
}
