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

case class User(nino: String, childName: String, statusCode: Int, errorCode: String, errorDescription: String)

object User {
  implicit val userJsonFormat: OFormat[User] = Json.format[User]
  val correlationId                          = "5c5ef9c2-72e8-4d4f-901e-9fec3db8c64b"
  val eppUniqueCusId                         = "12345678910"
  val eppRegReff                             = "EPPRegReffEPPReg"
  val outboundChildPayReff                   = "AAAA00000TFC"
  val childDOB                               = "2018-05-23"
  val paymentAmount                          = 12345
  val ccpRegReference                        = "RegReff123456"
  val ccpPostcode                            = "AB12 3CD"
  val payeeType                              = "ccp"
  val ninoEndsWithA: User                    = User("AB123456A", "Peter Pan", 200, "", "")
  val ninoEndsWithB: User                    = User("AB123456B", "Benjamin Button", 200, "", "")
  val ninoEndsWithC: User                    = User("AB123456C", "Christopher Columbus", 200, "", "")
  val ninoEndsWithD: User                    = User("AB123456D", "Donald Duck", 200, "", "")
  val ninoE0000: User                        = User(
    "AA110000A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0001: User = User(
    "AA110001A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0002: User = User(
    "AA110002A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0003: User = User(
    "AA110003A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0004: User = User(
    "AA110004A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0005: User = User(
    "AA110005A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0006: User = User("AA110006A", "", 502, "BAD_GATEWAY", "Bad Gateway")

  val ninoE0007: User = User(
    "AA110007A",
    "",
    500,
    "INTERNAL_SERVER_ERROR",
    "The server encountered an error and couldn't process the request"
  )

  val ninoE0008: User = User("AA110008A", "", 400, "BAD_REQUEST", "Request data is invalid or missing")

  val ninoE0009: User = User("AA110009A", "", 400, "BAD_REQUEST", "Request data is invalid or missing")

  val ninoE0010: User = User("AA110010A", "", 400, "BAD_REQUEST", "Request data is invalid or missing")

  val ninoE0020: User = User("AA110020A", "", 400, "BAD_REQUEST", "Request data is invalid or missing")

  val ninoE0021: User = User("AA110021A", "", 400, "BAD_REQUEST", "Request data is invalid or missing")

  val ninoE0022: User = User("AA110022A", "", 502, "BAD_GATEWAY", "Bad Gateway")

  val ninoE0024: User = User("AA110024A", "", 400, "BAD_REQUEST", "Request data is invalid or missing")

  val ninoE9000: User = User("AA119000A", "", 502, "BAD_GATEWAY", "Bad Gateway")

  val ninoE9999: User = User("AA119999A", "", 502, "BAD_GATEWAY", "Bad Gateway")

  val ninoE8000: User = User("AA118000A", "", 503, "SERVICE_UNAVAILABLE", "The service is currently unavailable")

  val ninoE8001: User = User("AA118001A", "", 503, "SERVICE_UNAVAILABLE", "The service is currently unavailable")

}
