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
import uk.gov.hmrc.test.api.models.UsersHappyPath.{AAAAResp, AAFFResp}

class TfcpPaymentsEndpointsUnhappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("Connect to TFCP API Payments endpoints unhappy path") {
    val consignorToken = givenGetToken(AAAAResp.outboundChildPaymentRef, 200, "Individual")
    Scenario(s"Payments endpoints with payload missing correlation id") {
      val response = tfcPaymentWithoutCorrelationId(
        consignorToken,
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Payments endpoint payload with an empty correlation id") {
      val response = tfcPayment(
        consignorToken,
        "",
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Payments endpoint payload with an correlation id as int") {
      val response = tfcPaymentInvalidDataTypeCorrelationID(
        consignorToken,
        123,
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Payments endpoint payload with an invalid correlation id") {
      val response = tfcPayment(
        consignorToken,
        "1234",
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", "Correlation ID is in an invalid format or is missing")
    }
    Scenario(s"Payments endpoints with a payload with an empty EPP unique customer ID") {
      val response = tfcPayment(
        consignorToken,
        correlationId,
        "",
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")

    }
    Scenario(s"Payments endpoints with a payload containing a different data type of EPP unique customer ID") {
      val response = tfcPaymentInvalidDataTypeEPPUniqueCusId(
        consignorToken,
        correlationId,
        123,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")

    }
    Scenario(s"Payments endpoints with a payload containing a invalid json field of EPP unique customer ID") {
      val response = tfcPaymentInvalidFieldEPPUniqueCusId(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")

    }
    Scenario(s"Payments endpoints with a payload with an missing EPP unique customer ID") {
      val response = tfcPaymentWithoutEppCusId(
        consignorToken,
        correlationId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", "epp_unique_customer_id is in invalid format or missing")

    }
    Scenario(s"Payments endpoint payload with an empty EPP registration reference") {
      val response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        "",
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint payload with an invalid json field of EPP registration reference") {
      val response = tfcPaymentInvalidFieldEPPRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint payload with an different data type of EPP registration reference") {
      val response = tfcPaymentInvalidDataTypeEPPRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        123,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint payload with an missing EPP registration reference") {
      val response = tfcPaymentWithouteppRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", "epp_reg_reference is in invalid format or missing")
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
        s"Verify Payments endpoint for predefined outbound child Payment reference number: $outboundPayRef"
      ) {
        val response = tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          outboundPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
        thenValidateResponseCode(response, 400)
        checkJsonValue(response, "errorCode", "E0001")
        checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
      }
    }
    Scenario(s"Payments endpoint payload with an missing Outbound child payment reference number") {
      val response = tfcPaymentWithoutOutboundChildPayRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
    }
    Scenario(s"Payments endpoint payload with an invalid data type Outbound child payment reference number") {
      val response = tfcPaymentInvalidDataTypeOutboundChildPayRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        paymentAmount,
        123,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
    }
    Scenario(s"Payments endpoint payload with an invalid Field Outbound child payment reference number") {
      val response = tfcPaymentInvalidFieldOutboundChildPayRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", "outbound_child_payment_ref is in invalid format or missing")
    }
    Scenario(s"Payments endpoint payments with a payload with an empty Payment amount") {
      val response =
        tfcPaymentInvalidPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          "",
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", "payment_amount is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid field Payment amount") {
      val response =
        tfcPaymentInvalidFieldPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", "payment_amount is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid data type Payment amount") {
      val response =
        tfcPaymentInvalidPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          "1234",
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", "payment_amount is in invalid format or missing")
    }
    Scenario(s"Payments with a payload with an Payment amount zero") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          0,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", "payment_amount is in invalid format or missing")
    }
    Scenario(s"Payments with a payload with an Payment amount negative number") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          -123,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", "payment_amount is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an missing Payment amount") {
      val response =
        tfcPaymentWithoutPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", "payment_amount is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid ccp Reg Reference data type") {
      val response =
        tfcPaymentWithInvalidCCPRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          123,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0003")
      checkJsonValue(response, "errorDescription", "ccp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid field ccp Reg Reference") {
      val response =
        tfcPaymentWithInvalidFieldCCPRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0003")
      checkJsonValue(response, "errorDescription", "ccp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an ccp Reg Reference as empty") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          "",
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0003")
      checkJsonValue(response, "errorDescription", "ccp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an missing ccp Reg Reference") {
      val response =
        tfcPaymentWithoutCcpRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0003")
      checkJsonValue(response, "errorDescription", "ccp_reg_reference is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid ccp Postcode data type") {
      val response =
        tfcPaymentWithInvalidCCPPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          123,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0009")
      checkJsonValue(response, "errorDescription", "ccp_postcode is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid field ccp Postcode") {
      val response =
        tfcPaymentInvalidFieldPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0009")
      checkJsonValue(response, "errorDescription", "ccp_postcode is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an missing ccp Postcode") {
      val response =
        tfcPaymentWithoutCcpPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0009")
      checkJsonValue(response, "errorDescription", "ccp_postcode is in invalid format or missing")
    }

    val postcodeScenarios =
      List(
        "",
        "AB12_3CD",
        "AB12 3C",
        "AB12 3_@",
        "@£12 3CD",
        "A 3CD",
        "INVALID",
        "123456",
        " B 13CD "
      )
    postcodeScenarios.foreach { postcode =>
      Scenario(s"Payments endpoint with a payload with an invalid Postcode : $postcode") {
        val response = tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          postcode,
          payeeType
        )
        thenValidateResponseCode(response, 400)
        checkJsonValue(response, "errorCode", "E0009")
        checkJsonValue(response, "errorDescription", "ccp_postcode is in invalid format or missing")
      }
    }

    val ccpSscenarios =
      List(
        "",
        "payeeType",
        "EPP",
        "12CCP",
        "CCP12",
        "ccp",
        "cCp"
      )

    ccpSscenarios.foreach { ccp =>
      Scenario(s"Verify Payments endpoint for predefined payee type : $ccp") {
        val response = tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          ccp
        )
        thenValidateResponseCode(response, 400)
        checkJsonValue(response, "errorCode", "E0007")
        checkJsonValue(response, "errorDescription", "payee_type is in invalid format or missing")
      }
    }
    Scenario(s"Payments endpoint with a payload with an missing payee Type") {
      val response =
        tfcPaymentWithoutPayeeType(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0007")
      checkJsonValue(response, "errorDescription", "payee_type is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid field payee Type") {
      val response =
        tfcPaymentInvalidFieldPayeeType(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0007")
      checkJsonValue(response, "errorDescription", "payee_type is in invalid format or missing")
    }
    Scenario(s"Payments endpoint with a payload with an invalid data type payee Type") {
      val response =
        tfcPaymentInvalidDataTypePayeeType(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          123
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0007")
      checkJsonValue(response, "errorDescription", "payee_type is in invalid format or missing")
    }
    Scenario(s"Payments endpoint for un matching responses") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAFFResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 502)
      checkJsonValue(response, "errorCode", "ETFC3")
      checkJsonValue(response, "errorDescription", EXPECTED_502_ERROR_DESC)
    }
  }

}
