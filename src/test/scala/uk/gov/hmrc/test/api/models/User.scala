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

case class User(nino: String, childName: String)

object User {
  implicit val userJsonFormat: OFormat[User] = Json.format[User]
  val correlationId                          = "5c5ef9c2-72e8-4d4f-901e-9fec3db8c64b"
  val eppUniqueCusId                         = "12345678910"
  val eppRegReff                             = "EPPRegReffEPPReg"
  val outboundChildPayReff                   = "AAAA00000TFC"
  val childDOB                               = "2018-05-23"
  val paymentAmount                          = 1234.56
  val ccpRegReference                        = "string"
  val ccpPostcode                            = "AB12 3CD"
  val payeeType                              = "ccp"
  val ninoEndsWithA: User                    = User("AB123456A", "Peter Pan")
  val ninoEndsWithB: User                    = User("AB123456B", "Benjamin Button")
  val ninoEndsWithC: User                    = User("AB123456C", "Christopher Columbus")
  val ninoEndsWithD: User                    = User("AB123456D", "Donald Duck")
}
