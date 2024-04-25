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

package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration

import scala.concurrent.Await
import scala.concurrent.duration._

class AuthService(filename: Any) extends HttpClient {

  val host: String        = TestConfiguration.url("auth")
  val redirectUrl: String = TestConfiguration.getConfigValue("redirect-url")

  def authPayloadTFC(nino: String): String =
    s"""
       | {
       |    "authorityId": "",
       |    "redirectionUrl": "$redirectUrl",
       |    "excludeGnapToken": true,
       |    "credentialStrength": "strong",
       |    "confidenceLevel": 250,
       |    "affinityGroup": "Individual",
       |    "email": "user@test.com",
       |    "credentialRole": "User",
       |    "additionalInfo.emailVerified": "N/A",
       |    "nino":"$nino",
       |    "presets-dropdown": "IR-SA",
       |    "credId": "temp",
       |    "enrolments": [
       |        {
       |            "key": "",
       |            "identifiers": [
       |                {
       |                    "key": "",
       |                    "value": ""
       |                }
       |            ],
       |            "state": "Activated"
       |        }
       |    ]
       |}
    """.stripMargin

  def authPayloadTFC2(nino: String): String =
    s"""
       | {
       |    "authorityId": "",
       |    authorityId": "",
       |    "gatewayToken": "",
       |    "redirectionUrl": "http://localhost:9949/auth-login-stub/session",
       |    "excludeGnapToken": true,
       |    "credentialStrength": "strong",
       |    "confidenceLevel": 250,
       |    "affinityGroup": "Individual",
       |    "usersName": "",
       |    "email": "user@test.com",
       |    "credentialRole": "User",
       |    "oauthTokens.accessToken": "",
       |    "oauthTokens.refreshToken": "",
       |    "oauthTokens.idToken": "",
       |    "additionalInfo.profile": "",
       |    "additionalInfo.groupProfile": "",
       |    "additionalInfo.emailVerified": "N/A",
       |    "nino": "AB123456A",
       |    "groupIdentifier": "",
       |    "agent.agentId": "",
       |    "agent.agentCode": "",
       |    "agent.agentFriendlyName": "",
       |    "unreadMessageCount": "",
       |    "mdtp.sessionId": "",
       |    "mdtp.deviceId": "",
       |    "presets-dropdown": "IR-SA",
       |    "enrolment[0].name": "",
       |    "enrolment[0].taxIdentifier[0].name": "",
       |    "enrolment[0].taxIdentifier[0].value": "",
       |    "enrolment[0].state": "Activated",
       |    "enrolment[1].name": "",
       |    "enrolment[1].taxIdentifier[0].name": "",
       |    "enrolment[1].taxIdentifier[0].value": "",
       |    "enrolment[1].state": "Activated",
       |    "enrolment[2].name": "",
       |    "enrolment[2].taxIdentifier[0].name": "",
       |    "enrolment[2].taxIdentifier[0].value": "",
       |    "enrolment[2].state": "Activated",
       |    "enrolment[3].name": "",
       |    "enrolment[3].taxIdentifier[0].name": "",
       |    "enrolment[3].taxIdentifier[0].value": "",
       |    "enrolment[3].state": "Activated",
       |    "itmp.givenName": "",
       |    "itmp.middleName": "",
       |    "itmp.familyName": "",
       |    "itmp.dateOfBirth": "",
       |    "itmp.address.line1": "",
       |    "itmp.address.line2": "",
       |    "itmp.address.line3": "",
       |    "itmp.address.line4": "",
       |    "itmp.address.line5": "",
       |    "itmp.address.postCode": "",
       |    "itmp.address.countryName": "",
       |    "itmp.address.countryCode": "",
       |}
    """.stripMargin
  def linkPayload(
    correlationId: String,
    eppUniqueCusId: String,
    eppRegReff: String,
    outboundChildPayReff: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "correlationId":"$correlationId",
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegReff",
       | "outbound_child_payment_ref":"$outboundChildPayReff",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin

  def postLogin(nino: String): StandaloneWSRequest#Self#Response = {
    val url = s"$host" + TestConfiguration.getConfigValue("auth-login-stub_uri")
    println("NINI -" + nino)
    Await.result(post(url, authPayloadTFC(nino), ("Content-Type", "application/json")), 10.seconds)
  }
}
