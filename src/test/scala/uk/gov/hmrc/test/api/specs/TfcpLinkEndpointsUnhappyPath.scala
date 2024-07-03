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

  Feature("TFCP Link endpoints unhappy path") {
    var consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    Scenario(s"Connect to TFCP APIs with a payload missing correlation id") {
      var response =
        tfcLinkWithoutCorrelationId(
          consignorToken,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response =
        tfcBalanceWithoutCorrelationId(consignorToken, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcPaymentWithoutCorrelationId(
        consignorToken,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with a payload with an empty correlation id") {
      var response =
        tfcLink(consignorToken, "", eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcBalance(consignorToken, "", eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcPayment(
        consignorToken,
        "",
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }

    Scenario(s"Connect to TFCP API link with a payload with an empty EPP unique customer ID") {
      var response =
        tfcLink(consignorToken, correlationId, "", eppRegRef, e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalance(consignorToken, correlationId, "", eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPayment(
        consignorToken,
        correlationId,
        "",
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

    }
    Scenario(s"Connect to TFCP API link with a payload with an missing EPP unique customer ID") {
      var response =
        tfcLinkWithoutEPPCusId(consignorToken, correlationId, eppRegRef, e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalanceWithoutEppRegRef(consignorToken, correlationId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPaymentWithoutEppCusId(
        consignorToken,
        correlationId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

    }
    Scenario(s"Connect to TFCP APIs with a payload with an empty EPP registration reference") {
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, "", e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, "", e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        "",
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with a payload with an missing EPP registration reference") {
      var response =
        tfcLinkWithoutEppRegRef(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          e0000Resp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response =
        tfcBalanceWithoutEppRegRef(consignorToken, correlationId, eppUniqueCusId, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPaymentWithouteppRegRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with a payload with an empty Outbound child payment reference number") {
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "", childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        "",
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with a payload with an missing Outbound child payment reference number") {
      var response =
        tfcLinkWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalanceWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPaymentWithoutOutboundChildPayRef(
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
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid child date of birth") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef, "childDOB")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload with an missing child date of birth") {
      val response =
        tfcLinkWithoutChildDOB(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }

    Scenario(s"Connect to TFCP API payments with a payload with an empty Payment amount") {
      val response =
        tfcPaymentInvalidPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          "",
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }

    Scenario(s"Connect to TFCP API payments with a payload with an invalid ccp Reg Reference") {
      val response =
        tfcPaymentWithInvalidccpRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          paymentAmount,
          123.45,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API payments with a payload with an missing ccp Reg Reference") {
      val response =
        tfcPaymentWithoutCcpRegReference(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          paymentAmount,
          ccpPostcode,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API payments with a payload with an invalid ccp Postcode") {
      val response =
        tfcPaymentWithInvalidccpPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          123.45,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API payments with a payload with an missing ccp Postcode") {
      val response =
        tfcPaymentWithoutCcpPostcode(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          payeeType
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API payments with a payload with an invalid payee Type") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          "payeeType"
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API payments with a payload with an missing payee Type") {
      val response =
        tfcPaymentWithoutPayeeType(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with missing authorization header") {
      var response =
        tfcLinkWithoutAuthorizationHeader(
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response =
        tfcBalanceWithoutAuthorization(correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPaymentWithoutAuthorization(
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
    }
    Scenario(s"Connect to TFCP API link with a token with insufficient confidence level") {
      consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 50, "Individual")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a token with affinity group Organisation") {
      consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Organisation")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a token with affinity group Agent") {
      consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Agent")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with bearer token expired") {
      consignorToken =
        "Bearer BXQ3/Treo4kQCZvVcCqKPoUOMmoBVy2UTaeDDgEhL3PTJijyU/5xyYQENUp4hMYDp1T3Gze4WkHQsusa967ZIKFulM6yR9mRKsZqpQqpjcLkm3OMGi/7U4hjAhKbWEZu4dvoCxWQcOCGXI/nMQlddHdXv2ZKEdcJ8bTUTpO0WX/9KwIkeIPK/mMlBESjue4V"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a invalid bearer token") {
      consignorToken = "this is invalid bearer token"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          e0000Resp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, e0000Resp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        e0000Resp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
  }
}
