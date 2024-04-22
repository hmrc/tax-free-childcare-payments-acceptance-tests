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

import uk.gov.hmrc.test.api.specs.CommonSpec

import java.io.{File, PrintWriter}
import scala.sys.process._

object GenerateClientIdAndBoxId extends CommonSpec {

  var clientId: String = "NCsyTDlRuHASnhqpC9klrDCDLYAm"
  var boxId: String    = ""
  var count: Int       = 0

  def getIds(token: String): Unit = {

    val scriptPath = "src/test/resources/test-push-poll-setup-draft.sh"

    val file = scala.io.Source.fromFile(scriptPath)
    val templateScript: String = file.mkString
    val modifiedScript: String = templateScript.replace("PLACEHOLDER_TOKEN", token)

    file.close()

    // Create a new script file with the modified content
    val outputFilePath = "src/test/resources/test-push-poll-setup.sh"
    val outputFile     = new PrintWriter(new File(outputFilePath))
    outputFile.write(modifiedScript)
    outputFile.close()

    // Make the script executable (optional)
    new File(outputFilePath).setExecutable(true)

    val shellScriptOutput: String = s"bash $outputFilePath".!!.trim

    clientId = extractValue(shellScriptOutput, "Client Id is: ", "\n")
    boxId = extractValue(shellScriptOutput, "boxId\":\"", "\"}")
    println(s"ClientId & BoxId : $clientId & $boxId")
    if (boxId == "") {
      count += 1
      Thread.sleep(30 * 1000)
      println(s"count no. of attempts for box ID : $count")
      getIds(token)
    }
  }

  def extractValue(inputString: String, startPhrase: String, endPhrase: String): String = {
    var result: String = ""
    val pattern        = s"""$startPhrase([^$endPhrase]*)""".r.unanchored
    val res            = inputString match {
      case pattern(res) =>
        println(res)
        result += res
      case _            => "No match"
    }
    result
  }

}
