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

package uk.gov.hmrc.test.api.utils

import scala.collection.mutable
import scala.util.Random

object RandomUtils {
  def randomUppercaseString(length: Int): String = {
    val uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val random           = new Random

    val randomString = new mutable.StringBuilder(length)
    for (_ <- 1 to length) {
      val randomIndex = random.nextInt(uppercaseLetters.length)
      randomString.append(uppercaseLetters.charAt(randomIndex))
    }

    randomString.toString()
  }
}
