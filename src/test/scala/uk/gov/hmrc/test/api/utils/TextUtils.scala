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
import uk.gov.hmrc.test.api.utils.DateUtils.updateDate

object TextUtils {
  def updateLRNOnInputAndOutputTestDataString(payload: String, dynamicLrn: String): String =
    payload.replace("LRNQA2MMddHHmmssS", dynamicLrn)

  def updateDateOnInputAndOutputTestDataString(payload: String, month: Int): String =
    payload.replace("YYYY-MM-DD", updateDate(month))

  def updateTextString(payload: String, targetValue: String, value: String): String =
    payload.replace(targetValue, value)
}
