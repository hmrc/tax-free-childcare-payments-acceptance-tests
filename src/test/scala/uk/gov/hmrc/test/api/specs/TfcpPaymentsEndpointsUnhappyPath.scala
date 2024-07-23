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

class TfcpPaymentsEndpointsUnhappyPath extends BaseSpec with CommonSpec with HttpClient {

  Feature("Connect to TFCP API Payments endpoints unhappy path") {
    val consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    Scenario(s"Payments endpoints with payload missing correlation id") {
      val response = tfcPaymentWithoutCorrelationId(
        consignorToken,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an empty correlation id") {
      val response = tfcPayment(
        consignorToken,
        "",
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an correlation id as int") {
      val response = tfcPaymentInvalidDataTypeCorrelationID(
        consignorToken,
        123,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an invalid correlation id") {
      val response = tfcPayment(
        consignorToken,
        "1234",
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoints with a payload with an empty EPP unique customer ID") {
      val response = tfcPayment(
        consignorToken,
        correlationId,
        "",
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

    }
    Scenario(s"Payments endpoints with a payload containing a different data type of EPP unique customer ID") {
      val response = tfcPaymentInvalidDataTypeEPPUniqueCusId(
        consignorToken,
        correlationId,
        123,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

    }
    Scenario(s"Payments endpoints with a payload containing a invalid json field of EPP unique customer ID") {
      val response = tfcPaymentInvalidFieldEPPUniqueCusId(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

    }
    Scenario(s"Payments endpoints with a payload with an missing EPP unique customer ID") {
      val response = tfcPaymentWithoutEppCusId(
        consignorToken,
        correlationId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

    }
    Scenario(s"Payments endpoint payload with an empty EPP registration reference") {
      val response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        "",
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an invalid json field of EPP registration reference") {
      val response = tfcPaymentInvalidFieldEPPRegRef(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an different data type of EPP registration reference") {
      val response = tfcPaymentInvalidDataTypeEPPRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        123,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an missing EPP registration reference") {
      val response = tfcPaymentWithouteppRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }

    Scenario(s"Payments endpoint payload with an unmatched Outbound child payment reference number in the stub") {
      val response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        "AAaa00000TFC",
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
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
        checkJsonValue(response, "errorCode", "E0000")
        checkJsonValue(response, "errorDescription", jsonErrorDescription)
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
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
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
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payload with an invalid Field Outbound child payment reference number") {
      val response = tfcPaymentInvalidFieldOutboundChildPayRef(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint payments with a payload with an empty Payment amount") {
      val response =
        tfcPaymentInvalidPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          "",
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0023")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid field Payment amount") {
      val response =
        tfcPaymentInvalidFieldPaymentAmount(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid data type Payment amount") {
      val response =
        tfcPaymentInvalidPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          "1234",
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0023")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    //    Scenario(s"Payments with a payload with an Payment amount zero") {
    //      val response =
    //        tfcPayment(
    //          consignorToken,
    //          correlationId,
    //          eppUniqueCusId,
    //          eppRegRef,
    //          aaResp.outboundChildPaymentRef,
    //          0,
    //          ccpRegReference,
    //          ccpPostcode,
    //          payeeType
    //        )
    //      thenValidateResponseCode(response, 400)
    //      checkJsonValue(response, "errorCode", "E0023")
    //      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    //    }
    //    Scenario(s"Payments with a payload with an Payment amount negative number") {
    //      val response =
    //        tfcPayment(
    //          consignorToken,
    //          correlationId,
    //          eppUniqueCusId,
    //          eppRegRef,
    //          aaResp.outboundChildPaymentRef,
    //          -123,
    //          ccpRegReference,
    //          ccpPostcode,
    //          payeeType
    //        )
    //      thenValidateResponseCode(response, 400)
    //      checkJsonValue(response, "errorCode", "E0023")
    //      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    //    }
    Scenario(s"Payments endpoint with a payload with an missing Payment amount") {
      val response =
        tfcPaymentWithoutPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0008")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid ccp Reg Reference data type") {
      val response =
        tfcPaymentWithInvalidCCPRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          123,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid field ccp Reg Reference") {
      val response =
        tfcPaymentWithInvalidFieldCCPRegReference(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0003")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an ccp Reg Reference as empty") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          "",
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an missing ccp Reg Reference") {
      val response =
        tfcPaymentWithoutCcpRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0003")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid ccp Postcode data type") {
      val response =
        tfcPaymentWithInvalidccpPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          123,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid field ccp Postcode") {
      val response =
        tfcPaymentInvalidFieldPostcode(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an missing ccp Postcode") {
      val response =
        tfcPaymentWithoutCcpPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an ccp Postcode as empty") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          "",
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
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
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          ccp
        )
        thenValidateResponseCode(response, 400)
        checkJsonValue(response, "errorCode", "E0022")
        checkJsonValue(response, "errorDescription", jsonErrorDescription)
      }
    }
    Scenario(s"Payments endpoint with a payload with an missing payee Type") {
      val response =
        tfcPaymentWithoutPayeeType(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0007")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid field payee Type") {
      val response =
        tfcPaymentInvalidFieldPayeeType(
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
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0007")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments endpoint with a payload with an invalid data type payee Type") {
      val response =
        tfcPaymentInvalidDataTypePayeeType(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          123
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0022")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
  }
}
