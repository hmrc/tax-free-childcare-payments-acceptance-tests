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
import uk.gov.hmrc.test.api.models.UsersHappyPath.AAAAResp

class TfcpAuthErrorValidations extends BaseSpec with CommonSpec with HttpClient {

  Feature("Connect to TFCP API endpoints unhappy path") {
    var consignorToken = givenGetToken(AAAAResp.outboundChildPaymentRef, 200, "Individual")
    Scenario(s"Endpoints missing authorization header") {
      var response =
        tfcLinkWithoutAuthorizationHeader(
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      response =
        tfcBalanceWithoutAuthorization(correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
      response = tfcPaymentWithoutAuthorization(
        correlationId,
        eppUniqueCusId,
        eppRegRef,
        AAAAResp.outboundChildPaymentRef,
        paymentAmount,
        ccpRegReference,
        ccpPostcode,
        payeeType
      )
    }
    Scenario(s"Endpoints with a token with insufficient confidence level") {
      consignorToken = givenGetToken("AAAA12345TFC", 50, "Individual")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
    }
    Scenario(s"Endpoints with a token with insufficient confidence level 200") {
      consignorToken = givenGetToken("AAAA12345TFC", 200, "Individual")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 401)
      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
    }
    Scenario(s"Endpoints with a token with affinity group Organisation") {
      consignorToken = givenGetToken(AAAAResp.outboundChildPaymentRef, 200, "Organisation")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 200)
      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 200)
      response = tfcPayment(
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
      thenValidateResponseCode(response, 200)
    }
    Scenario(s"Endpoints with a token with affinity group Agent") {
      consignorToken = givenGetToken(AAAAResp.outboundChildPaymentRef, 200, "Agent")
      var response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef, childDOB)
      thenValidateResponseCode(response, 200)
      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 200)
      response = tfcPayment(
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
      thenValidateResponseCode(response, 200)
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
          AAAAResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
    }
    Scenario(s"Endpoints with a invalid bearer token") {
      consignorToken = "this is invalid bearer token"
      var response =
        tfcLink(
          consignorToken,
          correlationId,
          eppUniqueCusId,
          eppRegRef,
          AAAAResp.outboundChildPaymentRef,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      response = tfcBalance(consignorToken, correlationId, eppUniqueCusId, eppRegRef, AAAAResp.outboundChildPaymentRef)
      thenValidateResponseCode(response, 401)
      response = tfcPayment(
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
      thenValidateResponseCode(response, 401)
    }
  }
}
