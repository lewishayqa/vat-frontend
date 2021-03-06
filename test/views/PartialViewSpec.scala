/*
 * Copyright 2018 HM Revenue & Customs
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

package views

import models.requests.AuthenticatedRequest
import models.{VatDecEnrolment, VatNoEnrolment}
import org.scalatest.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.twirl.api.Html
import uk.gov.hmrc.domain.Vrn
import views.behaviours.ViewBehaviours
import views.html.partial

class PartialViewSpec extends ViewBehaviours with MockitoSugar {

  val messageKeyPrefix = "partial"

  val fakeSummary = Html("<p>This is the account summary</p>")


  def vatEnrolment(activated: Boolean = true) = VatDecEnrolment(Vrn("vrn"), isActivated = true)

  def authenticatedRequest = AuthenticatedRequest(FakeRequest(), "", vatEnrolment(true), VatNoEnrolment())

  def createView = () => partial(Vrn("VRN"), frontendAppConfig, fakeSummary)(fakeRequest, messages, authenticatedRequest)

  "Partial view" must {
    "pass the title" in {
      asDocument(createView()).text() must include("VAT")
    }

    "pass the vrn of the user" in {
      asDocument(createView()).text() must include("VAT registration number (VRN)")
    }

    "pass the account summary partial" in {
      asDocument(createView()).html() must include(fakeSummary.toString())
    }

    "have a more details link" in {
      assertLinkById(asDocument(createView()), "vat-details-link", "More VAT details", s"${frontendAppConfig.vatFrontendHost}/business-account/vat",
        "vat:Click:VAT overview")
    }


  }
}
