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
    var consignorToken = givenGetToken(ninoEndsWithA.nino, 250, "Individual")
    Scenario(s"Connect to TFCP APIs with a payload missing correlation id") {
      var response =
        tfcLinkWithoutCorrelationId(consignorToken, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcBalanceWithoutCorrelationId(consignorToken, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcPaymentWithoutCorrelationId(
        consignorToken,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with a payload with an invalid correlation id") {
      var response =
        tfcLink(consignorToken, "correlationId", eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcBalance(consignorToken, "correlationId", eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")

      response = tfcPayment(
        consignorToken,
        "correlationId",
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }

    Scenario(s"Connect to TFCP API link with a payload with an invalid EPP unique customer ID") {
      var response =
        tfcLink(consignorToken, correlationId, "eppUniqueCusId", eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalance(consignorToken, correlationId, "eppUniqueCusId", eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPayment(
        consignorToken,
        correlationId,
        "eppUniqueCusId",
        eppRegReff,
        outboundChildPayReff,
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
        tfcLinkWithoutEPPCusId(consignorToken, correlationId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalanceWithoutEppRegReff(consignorToken, correlationId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPaymentWithoutEppCusId(
        consignorToken,
        correlationId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

    }
    Scenario(s"Connect to TFCP APIs with a payload with an invalid EPP registration reference") {
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, "eppRegReff", outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, "eppRegReff", outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        "eppRegReff",
        outboundChildPayReff,
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
        tfcLinkWithoutEppRegRef(consignorToken, correlationId, eppUniqueCusId, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalanceWithoutEppRegReff(consignorToken, correlationId, eppUniqueCusId, outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPaymentWithoutEppRegReff(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP APIs with a payload with an invalid Outbound child payment reference number") {
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, "outboundChildPayReff", childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, "outboundChildPayReff")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        "outboundChildPayReff",
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
        tfcLinkWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcBalanceWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")

      response = tfcPaymentWithoutOutboundChildPayRef(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
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
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, "childDOB")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload with an missing child date of birth") {
      val response =
        tfcLinkWithoutChildDOB(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }

    Scenario(s"Connect to TFCP API payments with a payload with an invalid Payment amount") {
      val response =
        tfcPaymentInvalidPaymentAmount(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          "paymentAmount",
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
          eppRegReff,
          outboundChildPayReff,
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
          eppRegReff,
          outboundChildPayReff,
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
          eppRegReff,
          outboundChildPayReff,
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
          eppRegReff,
          outboundChildPayReff,
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
          eppRegReff,
          outboundChildPayReff,
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
          eppRegReff,
          outboundChildPayReff,
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
        tfcLinkWithoutAuthorizationHeader(correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalanceWithoutAuthorization(correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPaymentWithoutAuthorization(
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
    }
    Scenario(s"Connect to TFCP API link with a token with insufficient confidence level") {
      consignorToken = givenGetToken(ninoEndsWithA.nino, 50, "Individual")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a token with affinity group Organisation") {
      consignorToken = givenGetToken(ninoEndsWithA.nino, 250, "Organisation")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a token with affinity group Agent") {
      consignorToken = givenGetToken(ninoEndsWithA.nino, 250, "Agent")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with bearer token expired") {
      consignorToken =
        "Bearer BXQ3/Treo4kQCZvVcCqKPoUOMmoBVy2UTaeDDgEhL3PTJijyU/5xyYQENUp4hMYDp1T3Gze4WkHQsusa967ZIKFulM6yR9mRKsZqpQqpjcLkm3OMGi/7U4hjAhKbWEZu4dvoCxWQcOCGXI/nMQlddHdXv2ZKEdcJ8bTUTpO0WX/9KwIkeIPK/mMlBESjue4V"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a invalid bearer token") {
      consignorToken = "this is invalid bearer token"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
        consignorToken,
        correlationId,
        eppUniqueCusId,
        eppRegReff,
        outboundChildPayReff,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
  }
}
