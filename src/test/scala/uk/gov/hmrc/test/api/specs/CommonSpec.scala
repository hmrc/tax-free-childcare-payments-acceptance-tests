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

import java.text.SimpleDateFormat

trait CommonSpec extends BaseSpec with HttpClient with RestAssured {
  val payload: AuthService = new AuthService

  def givenGetToken(outboundChildPaymentReference: String, confidenceLevel: Int, affinityGroup: String): String = {
    Given(s"I generate token for Outbound child payment reference:" + outboundChildPaymentReference)
    authHelper.getAuthBearerToken(outboundChildPaymentReference, confidenceLevel, affinityGroup)
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
  def returnJsonValue(response: Response, jsonKeyName: String): String                    = {
    val json        = Json.parse(response.body.prettyPrint)
    val actualValue = (json \ jsonKeyName).as[String]
    actualValue
  }

  def returnJsonValueIsNumbers(response: Response, jsonKeyName: String): Boolean = {
    val json        = Json.parse(response.body.prettyPrint)
    val actualValue = (json \ jsonKeyName).as[String]
    assert(actualValue.forall(Character.isDigit))
    actualValue.forall(Character.isDigit)
  }
  def returnJsonValueIsDate(response: Response, jsonKeyName: String): String     = {
    val json        = Json.parse(response.body.prettyPrint)
    val actualValue = (json \ jsonKeyName).as[String]
    val df          = new SimpleDateFormat("yyyy-MM-dd")
    df.parse(actualValue)
    actualValue
  }
  def validateJsonValueIsInteger(response: Response, jsonKeyName: String): Int   = {
    val json        = Json.parse(response.body.prettyPrint)
    val actualValue = (json \ jsonKeyName).as[Int]
    assert(actualValue.isValidInt)
    actualValue
  }

  def getRequestSpec: RequestSpecification = given()
    .config(config().headerConfig(headerConfig().overwriteHeadersWithName("Authorization", "Content-Type")))
    .contentType(ContentType.XML)
    .baseUri(url)

  def tfcLink(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidDataTypeOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: Int,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.linkPayloadInvalidDataTypeOutboundChildPayRef(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB)
      )
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidFieldOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(
        payload.linkPayloadInvalidFieldOutboundChildPayRef(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB)
      )
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidDataTypeChildDOB(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: BigDecimal
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadInvalidDataTypeChildDOB(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidFieldChildDOB(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadInvalidFieldChildDOB(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()

  def tfcLinkInvalidDataTypeEPPUniqueCusId(
    token: String,
    correlationId: String,
    eppUniqueCusId: Int,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadInvalidDataTypeEPPUniqueCusId(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidFieldEPPUniqueCusId(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadInvalidFieldEPPUniqueCusId(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutChildDOB(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutChildDOB(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidDataTypeEPPRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: Int,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadInvalidDataTypeEPPRegRef(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkInvalidFieldEPPRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadInvalidFieldEPPRegRef(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutOutboundChildPayRef(eppUniqueCusId, eppRegRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutEppRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutEppRegRef(eppUniqueCusId, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutEPPCusId(
    token: String,
    correlationId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayloadWithoutEPPCusId(eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutCorrelationId(
    token: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()

  def tfcLinkWithoutAuthorizationHeader(
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): Response =
    getRequestSpec
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegRef, outboundChildPayRef, childDOB))
      .post(url + s"/link")
      .andReturn()

  def tfcBalance(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayload(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceInvalidDataTypeOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: Int
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadInvalidDataTypeOutboundChildPayRef(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceInvalidFieldOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadInvalidFieldOutboundChildPayRef(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceInvalidDataTypeEPPUniqueCusId(
    token: String,
    correlationId: String,
    eppUniqueCusId: Int,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadInvalidDataTypeEPPUniqueCusId(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceInvalidDataTypeEPPRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: Int,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadInvalidDataTypeEPPRegRef(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceInvalidFieldEPPUniqueCusId(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadInvalidFieldEPPUniqueCusId(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceInvalidFieldEPPRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadInvalidFieldEPPRegRef(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadWithoutOutboundChildPayRef(eppUniqueCusId, eppRegRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutEppRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadWithoutEppRegRef(eppUniqueCusId, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithouteppRegRef(
    token: String,
    correlationId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.2+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayloadWithoutEppCusId(eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutAuthorization(
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.balancePayload(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcBalanceWithoutCorrelationId(
    token: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): Response =
    getRequestSpec
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .when()
      .body(payload.balancePayload(eppUniqueCusId, eppRegRef, outboundChildPayRef))
      .post(url + s"/balance")
      .andReturn()
  def tfcPayment(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidDataTypeOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: Int,
    paymentAmount: Int,
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
        payload.paymentPayloadInvalidDataTypeOutboundChildPayRef(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidFieldOutboundChildPayRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidDataTypeEPPUniqueCusId(
    token: String,
    correlationId: String,
    eppUniqueCusId: Int,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
        payload.paymentPayloadInvalidDataTypeEPPUniqueCusId(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidDataTypeEPPRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: Int,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
        payload.paymentPayloadInvalidDataTypeEPPRegRef(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidFieldEPPUniqueCusId(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
        payload.paymentPayloadInvalidFieldEPPUniqueCusId(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidFieldEPPRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
        payload.paymentPayloadInvalidFieldEPPRegRef(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithouteppRegRef(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithInvalidCCPRegReference(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: Int,
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
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: Int,
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
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
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
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentInvalidFieldPaymentAmount(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
        payload.paymentInvalidFieldPaymentAmountPayload(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
  def tfcPaymentWithoutPaymentAmount(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
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
        payload.paymentPayloadWithoutPaymentAmount(
          eppUniqueCusId,
          eppRegRef,
          outboundChildPayRef,
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
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
          eppRegRef,
          outboundChildPayRef,
          paymentAmount,
          ccpRegReference,
          ccpPostcode,
          payeeType
        )
      )
      .post(url + "/")
      .andReturn()
}
