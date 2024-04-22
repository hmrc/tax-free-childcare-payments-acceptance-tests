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

import scala.io.Source
import scala.xml.{Elem, Node, Text, XML}

object XmlUtils {

  def getRequestXmlFileAsString(directory: String, fileName: String): String =
    Source.fromResource(s"xml/$directory/$fileName.xml").getLines().mkString

  def updateXmlTagValue(xmlString: String, tag: String, text: String): String = {
    val xml = XML.loadString(xmlString)

    def updateNode(node: Node): Node =
      node match {
        case elem: Elem if elem.label == tag =>
          elem.copy(child = Text(text))
        case elem: Elem                      =>
          elem.copy(child = elem.child.map(updateNode))
        case other                           => other
      }

    val updatedXml = xml.map(updateNode)
    updatedXml.toString()
  }

  def updateXmlTagValue(xmlString: String): String = {
    val xml = XML.loadString(xmlString)

    def updateNode(node: Node): Node =
      node match {
        case elem: Elem =>
          elem.copy(child = elem.child.map(updateNode))
        case other      => other
      }

    val updatedXml = xml.map(updateNode)
    updatedXml.toString()
  }

  def getTextFromTagName(xmlString: String, tagName: String): String = {
    val xml              = XML.loadString(xmlString)
    val matchingElements = xml \\ tagName
    if (matchingElements.nonEmpty) {
      matchingElements.head.text
    } else {
      "Tag not found"
    }
  }
}
