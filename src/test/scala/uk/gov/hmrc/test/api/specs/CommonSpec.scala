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

import io.circe.Json
import io.circe.parser.parse
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import uk.gov.hmrc.test.api.client.{HttpClient, RestAssured}
import uk.gov.hmrc.test.api.utils.DateUtils
import uk.gov.hmrc.test.api.utils.DateUtils.date
import uk.gov.hmrc.test.api.utils.GenerateClientIdAndBoxId.{boxId, clientId, extractValue}
import uk.gov.hmrc.test.api.utils.JsonUtils.{convertJsonFormatAsString, flatten}
import uk.gov.hmrc.test.api.utils.RemoveJsonElement.removeJsonElement
import uk.gov.hmrc.test.api.utils.TextUtils.{updateDateOnInputAndOutputTestDataString, updateLRNOnInputAndOutputTestDataString, updateTextString}
import uk.gov.hmrc.test.api.utils.XmlUtils.{getRequestXmlFileAsString, updateXmlTagValue}

trait CommonSpec extends BaseSpec with HttpClient with RestAssured {
  val requestSpecification: RequestSpecification = getRequestSpec

  def givenGetToken(isValid: Boolean, identifier: String = ""): String = {
    Given(s"I receive a token $isValid")
    if (isValid) {
      authHelper.getAuthBearerToken(identifier)
    } else {
      identifier
    }
  }

  def thenValidateResponseCode(response: Response, responseCode: Int): Unit = {
    Then(s"I get the expected status code $responseCode")
    println(s"status code : ${response.statusCode()}")
    assert(response.statusCode() == responseCode, "Response is not as expected")
  }

  def thenValidateResponseMessage(response: Response, responseMessage: String): Unit = {
    Then(s"I get the expected status code $responseMessage")
    println("Actual Response : ")
    println(response.body().prettyPrint())
    println("Expected Response : ")
    println(responseMessage)

    assert(response.body().prettyPrint() == responseMessage, "Response message not as expected")
  }

  def thenValidateResponseData(
    responseFileName: String,
    lrnNumber: String,
    response: Response,
    date: String = DateUtils.date
  ): String = {
    Then(s"I validate the test data response file as a string $responseFileName")

    val actualResponse = cleanUpActualResponse(response)

    val movementId = actualResponse.getOrElse("movementId", "")
    println(s"movementId : $movementId")

    val expectedResponse = cleanUpExpectedResponse(responseFileName, lrnNumber, movementId, date)

    assert(actualResponse == expectedResponse, "Response not matched")

    movementId
  }

  private def cleanUpActualResponse(response: Response) = {
    val initial              = convertJsonFormatAsString(response.body().prettyPrint())
    val removedCorrelationId = removeJsonElement(initial, "correlationId")
    val removedDateTime      = removeJsonElement(removedCorrelationId, "dateTime")
    val removedValue         = removeJsonElement(removedDateTime, "value")

    flatten(parse(removedValue).getOrElse(Json.Null))

  }

  private def cleanUpExpectedResponse(responseFileName: String, lrnNumber: String, movementId: String, date: String) = {
    val removedCorrelationId = removeJsonElement("intermediateResponse", responseFileName, "correlationId")
    val removedDateTime      = removeJsonElement(removedCorrelationId, "dateTime")
    val removedValue         = removeJsonElement(removedDateTime, "value")

    val withDateUpdated =
      if (responseFileName.contains("Future"))
        updateDateOnInputAndOutputTestDataString(removedValue, 1)
      else if (responseFileName.contains("Past"))
        updateDateOnInputAndOutputTestDataString(removedValue, -1)
      else removedValue

    val withLrnUpdated = convertJsonFormatAsString(updateLRNOnInputAndOutputTestDataString(withDateUpdated, lrnNumber))

    val withBoxIdUpdated = updateTextString(withLrnUpdated, "PLACEHOLDER_BOX_ID", boxId)

    val withMovementIdUpdated = updateTextString(withBoxIdUpdated, "PLACEHOLDER_MOVEMENT_ID", movementId)

    val withRimErrorValueUpdated = updateTextString(withMovementIdUpdated, "PLACEHOLDER_RIM_VALIDATION", date)

    flatten(parse(withRimErrorValueUpdated).getOrElse(Json.Null))
  }

  def thenGetMovementId(
    response: Response
  ): String = {
    val jsonResponseBodyInitial         = convertJsonFormatAsString(response.body().prettyPrint())
    val jsonResponseBodyNoCorrelationid = removeJsonElement(jsonResponseBodyInitial, "emcsCorrelationId")
    val jsonResponseBodyNoDateTime      = removeJsonElement(jsonResponseBodyNoCorrelationid, "dateTime")
    val jsonResponseBodyFinal           = removeJsonElement(jsonResponseBodyNoDateTime, "value")

    val movementId = extractValue(jsonResponseBodyFinal, "\"movementId\" : \"", "\",")
    println(s"movementId : $movementId")

    movementId
  }

  def thenValidateResponseDataSize(response: Response, count: Int): Unit = {
    Then(s"I validate the get movements response object count is $count")
    // println(s"multiple responses : "+response.body().jsonPath().getList("").get(0))
    assert(response.body().jsonPath().getList("").size() == count, "Response length")
  }

  def whenIPostIE815Request(
    authBearerToken: String,
    fileName: String,
    dateChange: String,
    consignorId: String = "",
    date: String = date,
    lrn: String = ""
  ): Response = {
    When(s"I post IE815 request with test file name $fileName with $dateChange and receive a response")

    val requestBodyInitial =
      updateLRNOnInputAndOutputTestDataString(getRequestXmlFileAsString("request", fileName), lrn)
    println(s"Request body : $requestBodyInitial")

    val requestBodyDateUpdated = if (dateChange.equals("Yes")) {
      updateXmlTagValue(requestBodyInitial, "DateOfDispatch", date)
    } else {
      requestBodyInitial
    }

    val requestBodyDateConsignorUpdated = if (consignorId != "") {
      updateXmlTagValue(requestBodyDateUpdated, "TraderExciseNumber", consignorId)
    } else {
      requestBodyDateUpdated
    }

    requestSpecification
      .header("Authorization", authBearerToken)
      .header("X-Client-Id", clientId)
      .when()
      .body(requestBodyDateConsignorUpdated)
      .post(url)
      .andReturn()
  }

  def postIE818Request(token: String, requestBody: String, consigneeId: String = "", movementId: String): Response = {
    When(s"I post IE818 request with test file name and receive a response")
    val request =
      if (consigneeId != "") {
        updateXmlTagValue(requestBody, "Traderid", consigneeId)
      } else requestBody

    println(s"clientId & movementId : $clientId & $movementId")
    println(s"uri : " + url + s"/$movementId/messages")
    requestSpecification
      .header("Authorization", token)
      .when()
      .body(request)
      .post(url + s"/$movementId/messages")
      .andReturn()
  }

  def getMovements(token: String): Response = {
    When(s"I get Get Movements request without query params and receive a response")
    // clearQueryParam(requestSpecification)
    requestSpecification
      .header("Authorization", token)
      .when()
      .get(url)
      .andReturn()
  }

//  def getMovements(token: String, queryParameters: Map[String, String]): Response = {
//    When(s"I get Get Movements request with query params $queryParameters and receive a response")
//    clearQueryParam(requestSpecification)
//    val request = requestSpecification
//      .header("Authorization", token)
//      .queryParams(mapAsJavaMap(queryParameters))
//
//    request.get(url).andReturn()
//  }

  def whenIPostRequest(token: String, scenarioName: String, administrativeCode: String, lrn: String): Response = {
    When(s"I post  request with test file name $scenarioName and receive a response")
    //  clearQueryParam(requestSpecification)
    val requestBody = updateXmlTagValue(
      getRequestXmlFileAsString("request", scenarioName),
      "AdministrativeReferenceCode",
      administrativeCode
    )
    requestSpecification
      .header("Authorization", token)
      .when()
      .body(requestBody)
      .post(url + s"/$lrn/messages")
      .andReturn()
  }

  def whenIPostRequest(token: String, scenarioName: String, movementId: String): Response = {
    When(s"I post  request with test file name $scenarioName and receive a response")
    //  clearQueryParam(requestSpecification)
    val requestBody = updateXmlTagValue(
      getRequestXmlFileAsString("request", scenarioName)
    )
    println(s"clientId : $clientId")
    println(s"url : $url/$movementId/messages")
    requestSpecification
      .header("Authorization", token)
      .header("X-Client-Id", clientId)
      .when()
      .body(requestBody)
      .post(url + s"/$movementId/messages")
      .andReturn()
  }
  def tfcLink(token: String): Response                                                    =
    requestSpecification
      .header("Authorization", token)
      .header("Content-Type", "application/json")
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .when()
      .body(
        "{\"correlationId\":\"AAAA-BBBB-CCCC-DDDD\", \"epp_unique_customer_id\":\"EPP-ID\", \"epp_reg_reference\":\"EPP-Req-Ref\", \"outbound_child_payment_ref\":\"Out-Bound-Child-Ref\", \"child_date_of_birth\":\"01-02-2023\"}"
      )
      .post(url + s"/link")
      .andReturn()
  def tfcBalance(token: String): Response                                                 =
    requestSpecification
      .header("Authorization", token)
      .header("Accept", "application/vnd.hmrc.1.0+json")
      .when()
      .body("")
      .post(url + s"/balance")
      .andReturn()

}
