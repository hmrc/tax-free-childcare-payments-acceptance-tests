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

package uk.gov.hmrc.test.api.models

import play.api.libs.json.{Json, OFormat}

case class User(
  outboundChildPaymentRef: String,
  statusCode: Int,
  errorCode: String,
  errorDescription: String
)
case class UsersHappyPath(
  outboundChildPaymentRef: String,
  statusCode: Int,
  childName: String,
  tfcAccountStatus: String,
  govTopUp: Int,
  topUpAllowance: Int,
  paidInByYou: Int,
  totalBalance: Int,
  clearedFunds: Int,
  paymentReference: String,
  estimatedPaymentDate: String
)
object UsersHappyPath {
  val AAAAResp: UsersHappyPath = UsersHappyPath(
    "AAAA00000TFC",
    200,
    "Peter Pan",
    "ACTIVE",
    4500,
    5500,
    5000,
    9500,
    8000,
    "1234567887654321",
    "2024-10-01"
  )
  val EEZZResp: UsersHappyPath = UsersHappyPath(
    "EEZZ00000TFC",
    200,
    "Peter Pan",
    "ACTIVE",
    4500,
    5500,
    5000,
    9500,
    8000,
    "1234567887654321",
    "2024-10-01"
  )
  val EEBAResp: UsersHappyPath = UsersHappyPath(
    "EEBA00000TFC",
    200,
    "Peter Pan",
    "ACTIVE",
    4500,
    5500,
    5000,
    9500,
    8000,
    "1234567887654321",
    "2024-10-01"
  )
  val AAaaResp: UsersHappyPath = UsersHappyPath(
    "AAaa00000TFC",
    200,
    "Peter Pan",
    "ACTIVE",
    4500,
    5500,
    5000,
    9500,
    8000,
    "1234567887654321",
    "2024-10-01"
  )
  val AABBResp: UsersHappyPath = UsersHappyPath(
    "AABB00000TFC",
    200,
    "Benjamin Button",
    "INACTIVE",
    5500,
    4500,
    6000,
    11500,
    9000,
    "1234567887654322",
    "2024-10-02"
  )
  val AACCResp: UsersHappyPath = UsersHappyPath(
    "AACC00000TFC",
    200,
    "Christopher Columbus",
    "ACTIVE",
    6500,
    3500,
    7000,
    13500,
    10000,
    "1234567887654323",
    "2024-10-03"
  )
  val AADDResp: UsersHappyPath = UsersHappyPath(
    "AADD00000TFC",
    200,
    "Donald Duck",
    "ACTIVE",
    7500,
    2500,
    8000,
    16500,
    11000,
    "1234567887654324",
    "2024-10-04"
  )
  val AAFFResp: UsersHappyPath = UsersHappyPath(
    "AAFF00000TFC",
    200,
    "Fred Flintstone",
    "ACTIVE",
    38327,
    85,
    86,
    87,
    88,
    "ETFC3",
    "Bad Gateway"
  )
}
object User {

  implicit val userJsonFormat: OFormat[User] = Json.format[User]

  val EXPECTED_500_ERROR_DESC =
    "We encountered an error on our servers and did not process your request, please try again later."
  val EXPECTED_502_ERROR_DESC = "Bad Gateway"
  val EXPECTED_503_ERROR_DESC =
    "The service is currently unavailable."
  val correlationId           = "5c5ef9c2-72e8-4d4f-901e-9fec3db8c64b"
  val eppUniqueCusId          = "12345678910"
  val eppRegRef               = "EPPRegRefEPPReg"
  val nino                    = "AA110000A"
  val childDOB                = "2018-05-23"
  val paymentAmount           = 1234
  val ccpRegReference         = "CCP1234"
  val ccpPostcode             = "AB12 3CD"
  val payeeType               = "CCP"

  val e0000Resp: User         = User("EEAA00000TFC", 500, "E0000", EXPECTED_500_ERROR_DESC)
  val e0001RespLink: User     = User("EEBB00000TFC", 500, "E0001", EXPECTED_500_ERROR_DESC)
  val e0001RespBalance: User  = User("EEBL00000TFC", 500, "E0001", EXPECTED_500_ERROR_DESC)
  val e0001RespPayments: User = User("EEBP00000TFC", 500, "E0001", EXPECTED_500_ERROR_DESC)
  val e0002Resp: User         = User("EECC00000TFC", 500, "E0002", EXPECTED_500_ERROR_DESC)
  val e0003Resp: User         = User("EEDD00000TFC", 500, "E0003", EXPECTED_500_ERROR_DESC)
  val e0004Resp: User         = User("EEEE00000TFC", 500, "E0004", EXPECTED_500_ERROR_DESC)
  val e0005Resp: User         = User("EEFF00000TFC", 500, "E0005", EXPECTED_500_ERROR_DESC)
  val e0006Resp: User         = User("EEGG00000TFC", 500, "E0006", EXPECTED_500_ERROR_DESC)
  val e0007Resp: User         = User("EEHH00000TFC", 500, "E0007", EXPECTED_500_ERROR_DESC)
  val e0008Resp: User         = User("EEII00000TFC", 500, "E0008", EXPECTED_500_ERROR_DESC)
  val e0009Resp: User         = User("EEIJ00000TFC", 500, "E0009", EXPECTED_500_ERROR_DESC)
  val e0020Resp: User         = User("EELL00000TFC", 502, "E0020", EXPECTED_502_ERROR_DESC)
  val e0021Resp: User         = User("EEMM00000TFC", 500, "E0021", EXPECTED_500_ERROR_DESC)
  val e0022Resp: User         = User("EENN00000TFC", 500, "E0022", EXPECTED_500_ERROR_DESC)
  val e0023Resp: User         = User("EEOO00000TFC", 500, "E0023", EXPECTED_500_ERROR_DESC)
  val e0024Resp: User         = User(
    "EEPP00000TFC",
    400,
    "E0024",
    "Please check that the epp_reg_reference and epp_unique_customer_id are both correct"
  )
  val e0025Resp: User         = User(
    "EEQQ00000TFC",
    400,
    "E0025",
    "Please check that the child_date_of_birth and outbound_child_payment_reference are both correct"
  )
  val e0026Resp: User         = User("EERR00000TFC", 400, "E0026", "Please check the outbound_child_payment_ref supplied")
  val e0027Resp: User         = User(
    "EERS00000TFC",
    400,
    "E0027",
    "The Childcare Provider (CCP) you have specified is not linked to the TFC Account. The parent must go into their TFC Portal and add the CCP to their account first before attempting payment again later."
  )
  val e0401Resp: User         = User("EESS00000TFC", 500, "E0401", EXPECTED_500_ERROR_DESC)
  val e0030Resp: User         = User(
    "EETT00000TFC",
    400,
    "E0030",
    "The External Payment Provider (EPP) record is inactive on the TFC system. The EPP must complete the sign up process on the TFC Portal or contact their HMRC POC for further information."
  )
  val e0031Resp: User         = User(
    "EEUU00000TFC",
    400,
    "E0031",
    "The CCP is inactive, please check the CCP details and ensure that the CCP is still registered with their childcare regulator and that they have also signed up to TFC via the TFC portal to receive TFC funds."
  )
  val e0032Resp: User         = User(
    "EEVV00000TFC",
    400,
    "E0032",
    "The epp_unique_customer_id or epp_reg_reference is not associated with the outbound_child_payment_ref"
  )
  val e0033Resp: User         =
    User("EEWW00000TFC", 400, "E0033", "The TFC account used to request payment contains insufficient funds.")
  val e0034Resp: User         = User("EEXX00000TFC", 503, "E0034", EXPECTED_503_ERROR_DESC)
  val e0035Resp: User         = User(
    "EEYY00000TFC",
    400,
    "E0035",
    "There is an issue with this TFC Account, please advise parent / carer to contact TFC customer Services"
  )
  val e0036Resp: User         = User("EEYZ00000TFC", 400, "E0036", "Error processing payment due to Payee bank details")
  val e0042Resp: User         = User(
    "EEBC00000TFC",
    400,
    "E0042",
    "The ccp_reg_reference could not be found in the TFC system or does not correlate with the ccp_postcode. Please check the details and try again."
  )
  val e0043Resp: User         = User(
    "EEBD00000TFC",
    400,
    "E0043",
    "Parent associated with the bearer token does not have a TFC account. The parent must create a TFC account."
  )
  val e9000Resp: User         = User("EEBE00000TFC", 503, "E9000", EXPECTED_503_ERROR_DESC)
  val e9999Resp: User         = User("EEBF00000TFC", 503, "E9999", EXPECTED_503_ERROR_DESC)
  val e8000Resp: User         = User("EEBG00000TFC", 503, "E8000", EXPECTED_503_ERROR_DESC)
  val e8001Resp: User         = User("EEBH00000TFC", 503, "E8001", EXPECTED_503_ERROR_DESC)
  val unknownResp: User       = User("EEBI00000TFC", 502, "ETFC4", EXPECTED_502_ERROR_DESC)
  val etfc3Resp: User         = User("AAEE00000TFC", 502, "ETFC3", EXPECTED_502_ERROR_DESC)
}
