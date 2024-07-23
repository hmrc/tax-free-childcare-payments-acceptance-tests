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

  def authLoginPayload(outboundChildPaymentReference: String, confidenceLevel: Int, affinityGroup: String): String =
    if (confidenceLevel == 50) {
      s"""{
         |  "credId"            : "$outboundChildPaymentReference",
         |  "affinityGroup"     : "$affinityGroup",
         |  "confidenceLevel"   : $confidenceLevel,
         |  "credentialStrength": "strong",
         |  "enrolments"        : []
         |}""".stripMargin
    } else {
      s"""{
         |  "credId"            : "$outboundChildPaymentReference",
         |  "affinityGroup"     : "$affinityGroup",
         |  "confidenceLevel"   : $confidenceLevel,
         |  "credentialStrength": "strong",
         |  "enrolments"        : [],
         |  "nino"              : "AA110000A"
         |}""".stripMargin
    }

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
  def linkPayloadInvalidDataTypeOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: Int,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":$outboundChildPayRef,
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadInvalidFieldOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "Outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadInvalidFieldChildDOB(
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
       | "CHILD_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadInvalidDataTypeChildDOB(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: BigDecimal
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":$childDOB
       | }
    """.stripMargin
  def linkPayloadInvalidDataTypeEPPUniqueCusId(
    eppUniqueCusId: Int,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":$eppUniqueCusId,
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadInvalidFieldEPPUniqueCusId(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "EPP_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadInvalidDataTypeEPPRegRef(
    eppUniqueCusId: String,
    eppRegRef: Int,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":$eppRegRef,
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadInvalidFieldEPPRegRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "EPP_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadWithoutChildDOB(
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
  def linkPayloadWithoutOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadWithoutEppRegRef(
    eppUniqueCusId: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "outbound_child_payment_ref":"$outboundChildPayRef",
       | "child_date_of_birth":"$childDOB"
       | }
    """.stripMargin
  def linkPayloadWithoutEPPCusId(
    eppRegRef: String,
    outboundChildPayRef: String,
    childDOB: String
  ): String =
    s"""
       | {
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
  def balancePayloadInvalidDataTypeOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: Int
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":$outboundChildPayRef
       | }
    """.stripMargin
  def balancePayloadInvalidFieldOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "Outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin
  def balancePayloadInvalidDataTypeEPPUniqueCusId(
    eppUniqueCusId: Int,
    eppRegRef: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":$eppUniqueCusId,
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin
  def balancePayloadInvalidDataTypeEPPRegRef(
    eppUniqueCusId: String,
    eppRegRef: Int,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":$eppRegRef,
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin
  def balancePayloadInvalidFieldEPPUniqueCusId(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "EPP_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin
  def balancePayloadInvalidFieldEPPRegRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "EPP_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin
  def balancePayloadWithoutOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "epp_reg_reference":"$eppRegRef"
       | }
    """.stripMargin
  def balancePayloadWithoutEppRegRef(
    eppUniqueCusId: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_unique_customer_id":"$eppUniqueCusId",
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin
  def balancePayloadWithoutEppCusId(
    eppRegRef: String,
    outboundChildPayRef: String
  ): String =
    s"""
       | {
       | "epp_reg_reference":"$eppRegRef",
       | "outbound_child_payment_ref":"$outboundChildPayRef"
       | }
    """.stripMargin

  def paymentPayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
  def paymentInvalidFieldPostcodePayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
       |  "CCP_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidDataTypeOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: Int,
    paymentAmount: Int,
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
       |  "outbound_child_payment_ref": $outboundChildPayRef
       | }
    """.stripMargin
  def paymentPayloadInvalidFieldOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
       |  "Outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidDataTypeEPPUniqueCusId(
    eppUniqueCusId: Int,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": $eppUniqueCusId,
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidDataTypeEPPRegRef(
    eppUniqueCusId: String,
    eppRegRef: Int,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": $eppRegRef,
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidFieldEPPUniqueCusId(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "EPP_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidFieldEPPRegRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "EPP_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidFieldPayeeType(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
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
       |  "PAYEE_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadInvalidDataTypePayeeType(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: Int
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": $payeeType,
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadWithoutPayeeType(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadWithoutOutboundChildPayRef(
    eppUniqueCusId: String,
    eppRegRef: String,
    paymentAmount: Int,
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
       |  "payee_type": "$payeeType"
       | }
    """.stripMargin
  def paymentPayloadWithoutEppRegRef(
    eppUniqueCusId: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadWithoutEppCusId(
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
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
    paymentAmount: Int,
    ccpRegReference: Int,
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
  def paymentPayloadWithInvalidFieldCcpRegReference(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |  "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "Ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin
  def paymentPayloadWithoutCcpRegReference(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin

  def paymentPayloadWithInvalidCcpPostcode(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: Int,
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
  def paymentPayloadWithoutCcpPostcode(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin

  def paymentPayloadWithoutPaymentAmount(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
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
  def paymentInvalidFieldPaymentAmountPayload(
    eppUniqueCusId: String,
    eppRegRef: String,
    outboundChildPayRef: String,
    paymentAmount: Int,
    ccpRegReference: String,
    ccpPostcode: String,
    payeeType: String
  ): String =
    s"""
       | {
       |   "epp_unique_customer_id": "$eppUniqueCusId",
       |  "epp_reg_reference": "$eppRegRef",
       |  "Payment_amount": $paymentAmount,
       |  "ccp_reg_reference": "$ccpRegReference",
       |  "ccp_postcode": "$ccpPostcode",
       |  "payee_type": "$payeeType",
       |  "outbound_child_payment_ref": "$outboundChildPayRef"
       | }
    """.stripMargin

  def postLogin(
    outboundChildPaymentReference: String,
    confidenceLevel: Int,
    affinityGroup: String
  ): StandaloneWSRequest#Self#Response = {
    val url = s"$host" + TestConfiguration.getConfigValue("auth-login-stub_uri")
    Await.result(
      post(
        url,
        authLoginPayload(outboundChildPaymentReference, confidenceLevel, affinityGroup),
        ("Content-Type", "application/json")
      ),
      10.seconds
    )
  }
}
