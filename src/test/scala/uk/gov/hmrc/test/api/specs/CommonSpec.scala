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

import io.restassured.RestAssured.{`given`, config}
import io.restassured.config.HeaderConfig.headerConfig
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import play.api.libs.json.Json
import uk.gov.hmrc.test.api.client.{HttpClient, RestAssured}
import uk.gov.hmrc.test.api.service.AuthService

trait CommonSpec extends BaseSpec with HttpClient with RestAssured {
  val payload: AuthService = new AuthService

  def givenGetToken(nino: String, confidenceLevel: Int, affinityGroup: String): String = {
    Given(s"I generate token for NINO:" + nino)
    authHelper.getAuthBearerToken(nino, confidenceLevel, affinityGroup)
  }

  def thenValidateResponseCode(response: Response, responseCode: Int): Unit = {
    Then(s"I get the expected status code $responseCode")
    println(s"status code : ${response.statusCode()}")
    assert(response.statusCode() == responseCode, "Response is not as expected")
  }

  def thenValidateResponseMessage(response: Response, responseMessage: String): Unit      = {
    Then(s"I get the expected status code $responseMessage")
    println("Actual Response : ")
    println(response.body().prettyPrint())
    println("Expected Response : ")
    println(responseMessage)

    assert(response.body().prettyPrint() == responseMessage, "Response message not as expected")
  }
  def checkJsonValue(response: Response, jsonKeyName: String, jsonKeyValue: String): Unit = {
    val json        = Json.parse(response.body.prettyPrint)
    val actualValue = (json \ jsonKeyName).as[String]
    assert(actualValue == jsonKeyValue)
  }

  def getRequestSpec: RequestSpecification = given()
    .config(config().headerConfig(headerConfig().overwriteHeadersWithName("Authorization", "Content-Type")))
    .contentType(ContentType.XML)
    .baseUri(url)

  def tfcLink(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutChildDOB(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutChildDOB(eppUniqueCusId, eppRegReff, outboundChildPayReff))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutOutboundChildPayRef(eppUniqueCusId, eppRegReff, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutEppRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutEppRegRef(eppUniqueCusId, outboundChildPayReff, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutEPPCusId(
    token: String,
    correlationId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutEPPCusId(eppRegReff, outboundChildPayReff, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutCorrelationId(
    token: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB))
      .post(url + s"/link")
      .andReturn()

  def tfcLinkWithoutAuthorizationHeader(
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB))
      .post(url + s"/link")
      .andReturn()

  def tfcBalance(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayload(eppUniqueCusId, eppRegReff, outboundChildPayReff))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadWithoutOutboundChildPayRef(eppUniqueCusId, eppRegReff))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutEppRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    outboundChildPayReff: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadWithoutEppRegRef(eppUniqueCusId, outboundChildPayReff))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutEppRegReff(
    token: String,
    correlationId: String,
    eppRegReff: String,
    outboundChildPayReff: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadWithoutEppCusId(eppRegReff, outboundChildPayReff))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutAuthorization(
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String
  ): Response =
    getRequestSpec
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayload(eppUniqueCusId, eppRegReff, outboundChildPayReff))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutCorrelationId(
    token: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .when()
      .body(payload.balancePayload(eppUniqueCusId, eppRegReff, outboundChildPayReff))
      .post(url + s"/balance")
      .andReturn()
  def tfcPayment(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayload(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutPayeeType(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithoutPayeeType(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithoutOutboundChildPayRef(
          eppUniqueCusId,
          eppRegReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutEppRegReff(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithoutEppRegRef(
          eppUniqueCusId,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutEppCusId(
    token: String,
    correlationId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithoutEppCusId(
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithInvalidccpRegReference(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: BigDecimal,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithInvalidCcpRegReference(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutCcpRegReference(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithoutCcpRegReference(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()

  def tfcPaymentWithInvalidccpPostcode(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: BigDecimal,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithInvalidCcpPostcode(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutCcpPostcode(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayloadWithoutCcpPostcode(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()

  def tfcPaymentWithoutAuthorization(
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentPayload(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidPaymentAmount(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: String,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.paymentInvalidPaymentAmountPayload(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutCorrelationId(
    token: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .when()
      .body(
        payload.paymentPayload(
          eppUniqueCusId,
          eppRegReff,
          outboundChildPayReff,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
}
