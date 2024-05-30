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
    var consignorToken = givenGetToken(ninoEndsWithA.nino, 250)
    Scenario(s"Connect to TFCP API link with a payload with an invalid correlation id") {
      val response =
        tfcLink(consignorToken, "correlationId", eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload missing correlation id") {
      val response =
        tfcLinkWithoutCorelationId(consignorToken, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid EPP unique customer ID") {
      val response =
        tfcLink(consignorToken, correlationId, "eppUniqueCusId", eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid EPP registration reference") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, "eppRegReff", outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Request data is invalid or missing")
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid Outbound child payment reference number") {
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, "outboundChildPayReff", childDOB)
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
    Scenario(s"Connect to TFCP API link with missing authorization header") {
      val response =
        tfcLinkWithoutAuthorizationHeader(correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with missing nino") {
      consignorToken = givenGetToken("", 250)
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 500)
      checkJsonValue(response, "errorCode", "INTERNAL_SERVER_ERROR")
      checkJsonValue(response, "errorDescription", "Unable to retrieve NI number")
    }
    Scenario(s"Connect to TFCP API link with a token with insufficient confidence level") {
      consignorToken = givenGetToken(ninoEndsWithA.nino, 50)
      val response =
        tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with bearer token expired") {
      val response =
        tfcLink(
          "Bearer BXQ3/Treo4kQCZvVcCqKPoUOMmoBVy2UTaeDDgEhL3PTJijyU/5xyYQENUp4hMYDp1T3Gze4WkHQsusa967ZIKFulM6yR9mRKsZqpQqpjcLkm3OMGi/7U4hjAhKbWEZu4dvoCxWQcOCGXI/nMQlddHdXv2ZKEdcJ8bTUTpO0WX/9KwIkeIPK/mMlBESjue4V",
          correlationId,
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a invalid bearer token") {
      val response =
        tfcLink(
          "Bearer this is invalid bearer token",
          correlationId,
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          childDOB
        )
      thenValidateResponseCode(response, 401)
      checkJsonValue(response, "errorCode", "UNAUTHORISED")
      checkJsonValue(response, "errorDescription", "Invalid authentication credentials")
    }
    Scenario(s"Connect to TFCP API link with a payload with an invalid correlation id") {
      val response =
        tfcLink(consignorToken, "correlationId", eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
      thenValidateResponseCode(response, 400)
      checkJsonValue(response, "errorCode", "BAD_REQUEST")
      checkJsonValue(response, "errorDescription", "Correlation-ID header is invalid or missing")
    }
  }
  Feature("TFCP Link NSI error validations") {

    val scenarios =
      List(
        ninoE0000,
        ninoE0001,
        ninoE0002,
        ninoE0003,
        ninoE0004,
        ninoE0005,
        ninoE0006,
        ninoE0007,
        ninoE0008,
        ninoE0009,
        ninoE0010,
        ninoE0020,
        ninoE0021,
        ninoE0022,
        ninoE0024,
        ninoE9000,
        ninoE9999,
        ninoE8000,
        ninoE8001
      )

    scenarios.foreach { scenarioName =>
      Scenario(s"Verify Link endpoint for NSI error : ${scenarioName.errorDescription}") {
        val consignorToken = givenGetToken(scenarioName.nino, 250)
        val response       =
          tfcLink(consignorToken, correlationId, eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB)
        thenValidateResponseCode(response, scenarioName.statusCode)
        checkJsonValue(response, "errorCode", scenarioName.errorCode)
        checkJsonValue(response, "errorDescription", scenarioName.errorDescription)
      }
    }
  }
}
