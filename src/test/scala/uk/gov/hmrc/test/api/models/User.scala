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
  childName: String,
  statusCode: Int,
  errorCode: String,
  errorDescription: String
)

object User {
  implicit val userJsonFormat: OFormat[User] = Json.format[User]
  val nsiErrorDescription = "The server encountered an error and couldn't process the request. Please refer to API Documentation for further information"
  val jsonErrorDescription = "Request data is invalid or missing. Please refer to API Documentation for further information"
  val correlationId = "5c5ef9c2-72e8-4d4f-901e-9fec3db8c64b"
  val eppUniqueCusId = "12345678910"
  val eppRegRef = "EPPRegRefEPPReg"
  val nino = "AA110000A"
  val childDOB = "2018-05-23"
  val paymentAmount = 1234
  val ccpRegReference = "string"
  val ccpPostcode = "AB12 3CD"
  val payeeType = "CCP"
  val aaResp: User = User("AAAA00000TFC", "Peter Pan", 200, "", "")
  val bbResp: User = User("AABB00000TFC", "Benjamin Button", 200, "", "")
  val ccResp: User = User("AACC00000TFC", "Christopher Columbus", 200, "", "")
  val ddResp: User = User("AADD00000TFC", "Donald Duck", 200, "", "")
  val e0000Resp: User = User("EEAA00000TFC", "", 500, "E0000", nsiErrorDescription)
  val e0001Resp: User = User("EEBB00000TFC", "", 500, "E0001", nsiErrorDescription)
  val e0002Resp: User = User("EECC00000TFC", "", 500, "E0002", nsiErrorDescription)
  val e0003Resp: User = User("EEDD00000TFC", "", 500, "E0003", nsiErrorDescription)
  val e0004Resp: User = User("EEEE00000TFC", "", 500, "E0004", nsiErrorDescription)
  val e0005Resp: User = User("EEFF00000TFC", "", 500, "E0005", nsiErrorDescription)
  val e0006Resp: User = User("EEGG00000TFC", "", 500, "E0006", nsiErrorDescription)
  val e0007Resp: User = User("EEHH00000TFC", "", 500, "E0007", nsiErrorDescription)
  val e0008Resp: User = User("EEII00000TFC", "", 500, "E0008", nsiErrorDescription)
  val e0020Resp: User = User("EELL00000TFC", "", 502, "E0020", nsiErrorDescription)
  val e0021Resp: User = User("EEMM00000TFC", "", 500, "E0021", nsiErrorDescription)
  val e0022Resp: User = User("EENN00000TFC", "", 500, "E0022", nsiErrorDescription)
  val e0023Resp: User = User("EEOO00000TFC", "", 500, "E0023", nsiErrorDescription)
  val e0024Resp: User = User("EEPP00000TFC", "", 400, "E0024", nsiErrorDescription)
  val e0025Resp: User = User("EEQQ00000TFC", "", 400, "E0025", nsiErrorDescription)
  val e0026Resp: User = User("EERR00000TFC", "", 400, "E0026", nsiErrorDescription)
  val e0401Resp: User = User("EESS00000TFC", "", 500, "E0401", nsiErrorDescription)
  val e0030Resp: User = User("EETT00000TFC", "", 400, "E0030", nsiErrorDescription)
  val e0031Resp: User = User("EEUU00000TFC", "", 400, "E0031", nsiErrorDescription)
  val e0032Resp: User = User("EEVV00000TFC", "", 400, "E0032", nsiErrorDescription)
  val e0033Resp: User = User("EEWW00000TFC", "", 400, "E0033", nsiErrorDescription)
  val e0034Resp: User = User("EEXX00000TFC", "", 503, "E0034", nsiErrorDescription)
  val e0035Resp: User = User("EEYY00000TFC", "", 400, "E0035", nsiErrorDescription)
  val e0040Resp: User = User("EEZZ00000TFC", "", 400, "E0040", nsiErrorDescription)
  val e0041Resp: User = User("EEBA00000TFC", "", 400, "E0041", nsiErrorDescription)
  val e0042Resp: User = User("EEBC00000TFC", "", 400, "E0042", nsiErrorDescription)
  val e0043Resp: User = User("EEBD00000TFC", "", 400, "E0043", nsiErrorDescription)
  val e9000Resp: User = User("EEBE00000TFC", "", 503, "E9000", nsiErrorDescription)
  val e9999Resp: User = User("EEBF00000TFC", "", 503, "E9999", nsiErrorDescription)
  val e8000Resp: User = User("EEBG00000TFC", "", 503, "E8000", nsiErrorDescription)
  val e8001Resp: User = User("EEBH00000TFC", "", 503, "E8001", nsiErrorDescription)
}
