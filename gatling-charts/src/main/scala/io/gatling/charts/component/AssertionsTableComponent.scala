/**
 * Copyright 2011-2015 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.charts.component

import com.dongxiguo.fastring.Fastring.Implicits._

import io.gatling.core.assertion.AssertionResult
import io.gatling.core.util.StringHelper._
import io.gatling.core.util.HtmlHelper.HtmlRichString

private[charts] class AssertionsTableComponent(assertionResults: List[AssertionResult]) extends Component {

  def js = fast"""
	    $$('#container_exceptions').sortable('#container_exceptions');
    """

  private def resultStyle(assertionResult: AssertionResult) = if (assertionResult.result) "ok" else "ko"

  def html = if (assertionResults.isEmpty)
    EmptyFastring
  else
    fast"""<div class="statistics extensible-geant collapsed">
    <div class="title">
        <div class="title_collapsed" style="cursor: auto;">ASSERTIONS</div>
    </div>
    <table id="container_assertions" class="statistics-in extensible-geant">
        <thead>
            <tr>
                <th id="assert-col-1" class="header sortable"><span>Assertion</span></th>
                <th id="assert-col-2" class="header sortable"><span>Status</span></th>
            </tr>
        </thead>
		<tbody>
		    ${
      assertionResults.zipWithIndex.map {
        case (assertionResult, index) => fast"""
		    <tr>
		    	<td class="error-col-1 ${resultStyle(assertionResult)} total">${assertionResult.message.htmlEscape}<span class="value" style="display:none">$index</span></td>
		    	<td class="error-col-2 value ${resultStyle(assertionResult)} total">${if (assertionResult.result) "OK" else "KO"}</td>
		    </tr>"""
      }.mkFastring
    }
		</tbody>
    </table>
</div>
"""

  def jsFiles: Seq[String] = Seq.empty
}
