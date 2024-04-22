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

package uk.gov.hmrc.test.api.client

import io.restassured.RestAssured.{`given`, config}
import io.restassured.config.HeaderConfig.headerConfig
import io.restassured.http.ContentType
import io.restassured.specification.{ProxySpecification, RequestSpecification}
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.utils.Zap.{isEnabled, proxyPort, proxyServer}

trait RestAssured {
  val url: String                       = TestConfiguration.url("emcs") + "/" + TestConfiguration.getConfigValue("emcs-api-uri")

  def initiateProxy(requestSpec: RequestSpecification): Unit =
    if (isEnabled) {
      val proxySpec = ProxySpecification.host(proxyServer).withPort(proxyPort).withScheme("http")
      requestSpec.proxy(proxySpec)
    }

  def getRequestSpec: RequestSpecification = {
    val requestSpec = given()
      .config(config().headerConfig(headerConfig().overwriteHeadersWithName("Authorization", "Content-Type")))
      .contentType(ContentType.XML)
      .baseUri(url)
    initiateProxy(requestSpec)
    requestSpec
  }

//  def clearQueryParam(requestSpecification: RequestSpecification): Unit = {
//    val filterableRequestSpecification = requestSpecification.asInstanceOf[FilterableRequestSpecification]
//    val params                         = new ConcurrentHashMap[String, Object](filterableRequestSpecification.getQueryParams)
//    params.forEach({ case (key, _) => filterableRequestSpecification.removeQueryParam(key) })
//  }
}
