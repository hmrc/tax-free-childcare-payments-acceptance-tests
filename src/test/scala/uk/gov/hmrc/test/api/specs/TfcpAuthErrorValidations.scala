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

class TfcpAuthErrorValidations extends BaseSpec with CommonSpec with HttpClient {

  Feature("Connect to TFCP API endpoints unhappy path") {
    var consignorToken = givenGetToken(aaResp.outboundChildPaymentRef, 250, "Individual")
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
      consignorToken = givenGetToken("AAAA12345TFC", 50, "Individual")
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
