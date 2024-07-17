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

  Feature("Connect to TFCP API endpoints unhappy path") {
    var consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
    Scenario(s"Endpoints with payload missing correlation id") {
      var response =
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

      response =
        tfcBalanceWithoutCorrelationId(consignorToken, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentWithoutCorrelationId(
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
    Scenario(s"Endpoints payload with an empty correlation id") {
      var response =
        tfcLink(consignorToken, "", eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalance(consignorToken, "", eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "ETFC1")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPayment(
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
    Scenario(s"Endpoints with a payload with an empty EPP unique customer ID") {
      var response =
        tfcLink(consignorToken, correlationId, "", eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalance(consignorToken, correlationId, "", eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPayment(
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
    Scenario(s"Endpoints with a payload containing a different data type of EPP unique customer ID") {
      var response =
        tfcLinkInvalidDataTypeEPPUniqueCusId(consignorToken, correlationId, 123, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalanceInvalidDataTypeEPPUniqueCusId(consignorToken, correlationId, 123, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentInvalidDataTypeEPPUniqueCusId(
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
    Scenario(s"Endpoints with a payload containing a invalid json field of EPP unique customer ID") {
      var response =
        tfcLinkInvalidFieldEPPUniqueCusId(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalanceInvalidFieldEPPUniqueCusId(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentInvalidFieldEPPUniqueCusId(
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
    Scenario(s"Endpoints with a payload with an missing EPP unique customer ID") {
      var response =
        tfcLinkWithoutEPPCusId(consignorToken, correlationId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalanceWithoutEppRegRef(consignorToken, correlationId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0004")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentWithoutEppCusId(
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
    Scenario(s"Payload with an empty EPP registration reference") {
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, "", aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, "", aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPayment(
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
    Scenario(s"Payload with an invalid json field of EPP registration reference") {
      var response =
        tfcLinkInvalidFieldEPPRegRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalanceInvalidFieldEPPRegRef(consignorToken, correlationId, eppUniqueCusId,eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentInvalidFieldEPPRegRef(
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
    Scenario(s"Payload with an different data type of EPP registration reference") {
      var response =
        tfcLinkInvalidDataTypeEPPRegRef(consignorToken, correlationId, eppUniqueCusId, 123, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalanceInvalidDataTypeEPPRegRef(consignorToken, correlationId, eppUniqueCusId, 123, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentInvalidDataTypeEPPRegRef(
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
    Scenario(s"Payload with an missing EPP registration reference") {
      var response =
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

      response =
        tfcBalanceWithoutEppRegRef(consignorToken, correlationId, eppUniqueCusId, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0002")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcPaymentWithouteppRegRef(
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
    Scenario(s"Payload with an empty Outbound child payment reference number") {
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "", childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, "")
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

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
      checkJsonValue(response, "errorCode", "E0000")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payload with an missing Outbound child payment reference number") {
      var response =
        tfcLinkWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

      response = tfcBalanceWithoutOutboundChildPayRef(consignorToken, correlationId, eppUniqueCusId, eppRegRef)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0001")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)

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
    Scenario(s"Payments with a payload with an empty Payment amount") {
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
    Scenario(s"Payments with a payload with an missing Payment amount") {
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
    Scenario(s"Payments with a payload with an invalid ccp Reg Reference") {
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
    Scenario(s"Payments with a payload with an missing ccp Reg Reference") {
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
    Scenario(s"Payments with a payload with an invalid ccp Postcode") {
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
    Scenario(s"Payments with a payload with an missing ccp Postcode") {
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
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments with a payload with an invalid payee Type") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          "payeeType"
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0022")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments with a payload with payee Type as EPP") {
      val response =
        tfcPayment(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          "EPP"
        )
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "E0022")
      checkJsonValue(response, "errorDescription", jsonErrorDescription)
    }
    Scenario(s"Payments with a payload with an missing payee Type") {
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
    Scenario(s"Endpoints missing authorization header") {
      var response =
        tfcLinkWithoutAuthorizationHeader(
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response =
        tfcBalanceWithoutAuthorization(correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPaymentWithoutAuthorization(
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        aaResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
    }
    Scenario(s"Endpoints with a token with insufficient confidence level") {
      consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 50, "Individual")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Endpoints with a token with affinity group Organisation") {
      consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Organisation")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Endpoints with a token with affinity group Agent") {
      consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Agent")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Endpoints with bearer token expired") {
      consignorToken =
        "Bearer BXQ3/Treo4kQCZvVcCqKPoUOMmoBVy2UTaeDDgEhL3PTJijyU/5xyYQENUp4hMYDp1T3Gze4WkHQsusa967ZIKFulM6yR9mRKsZqpQqpjcLkm3OMGi/7U4hjAhKbWEZu4dvoCxWQcOCGXI/nMQlddHdXv2ZKEdcJ8bTUTpO0WX/9KwIkeIPK/mMlBESjue4V"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Endpoints with a invalid bearer token") {
      consignorToken = "this is invalid bearer token"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          aaResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, aaResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")

      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
//      checkJsonValue(response, "errorCode", "UNAUTHORISED")
//      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
  }
}
