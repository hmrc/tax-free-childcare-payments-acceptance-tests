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
  val requestSpecification: RequestSpecification = getRequestSpec
  val payload: AuthService                       = new AuthService

  def givenGetToken(nino: String, confidenceLevel: Int): String = {
    Given(s"I generate token for NINO:" + nino)
    authHelper.getAuthBearerToken(nino, confidenceLevel)
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

  def getRequestSpec: RequestSpecification = {
    val requestSpec = given()
      .config(config().headerConfig(headerConfig().overwriteHeadersWithName("Authorization", "Content-Type")))
      .contentType(ContentType.XML)
      .baseUri(url)
    requestSpec
  }

  def tfcLink(
    token: String,
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    requestSpecification
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .header("Correlation-ID", correlationId)
      .when()
      .body(payload.linkPayload(eppUniqueCusId, eppRegReff, outboundChildPayReff, childDOB))
      .post(url + s"/link")
      .andReturn()
  def tfcLinkWithoutCorelationId(
    token: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): Response =
    requestSpecification
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
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
    requestSpecification
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
    requestSpecification
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .header("Correlation-ID", correlationId)
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
    requestSpecification
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
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
}
