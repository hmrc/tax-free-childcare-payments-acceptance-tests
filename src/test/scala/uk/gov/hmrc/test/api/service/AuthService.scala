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

  def payLoadTFCP(nino: String, confidenceLevel: Int, affinityGroup: String): String =
    s"""{
     |  "credId"            : "$nino",
     |  "affinityGroup"     : "$affinityGroup",
     |  "confidenceLevel"   : $confidenceLevel,
     |  "credentialStrength": "strong",
     |  "enrolments"        : [],
     |  "nino"              : "$nino"
     |}""".stripMargin

  def linkPayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin

  def balancePayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin

  def paymentPayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadWithInvalidCcpRegReference(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: BigDecimal,
    ccpRegReference: BigDecimal,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": $ccpRegReference,
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin

  def paymentPayloadWithInvalidCcpPostcode(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: BigDecimal,
    ccpRegReference: String,
    ccpPostcode: BigDecimal,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": $ccpPostcode,
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin

  def paymentInvalidPaymentAmountPayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: String,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": "$paymentAmount",
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin

  def postLogin(nino: String, confidenceLevel: Int, affinityGroup: String): StandaloneWSRequest#Self#Response = {
    val url = s"$host" + TestConfiguration.getConfigValue("auth-login-stub_uri")
    Await.result(
      post(url, payLoadTFCP(nino, confidenceLevel, affinityGroup), ("Content-Type", "application/json")),
      10.seconds
    )
  }
}
