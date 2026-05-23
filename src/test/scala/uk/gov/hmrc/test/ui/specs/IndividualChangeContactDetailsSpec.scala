/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.test.ui.specs

import uk.gov.hmrc.test.ui.pages.*
import uk.gov.hmrc.test.ui.pages.ChangeContactDetails.ContactDetailsUpdatedPage
import uk.gov.hmrc.test.ui.pages.ChangeContactDetails.Ind.*
import uk.gov.hmrc.test.ui.specs.tags.*

class IndividualChangeContactDetailsSpec extends BaseSpec {

  Feature("Individual Change User Contact Details") {

    // Scenarios covered
    // 1. Individual user change contact details

    Scenario("1 - Individual user change contact details", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as an individual not connected to a business")
      AuthLoginPage.loginAsIndividualOrOrganisationForChange("W234567890")
      And("the Individual user clicks on 'Change' link to change their email address on the 'Change your contact details' page")
      ChangeContactIndDetailsPage.clickOnLink(ChangeContactIndDetailsPage.emailChangeLink)
      And("the Individual user enters a different email in 'What is your email address?' page")
      ChangeContactIndEmailPage.enterEmailAddress("changedEmail@test.com")
      And("the Individual user clicks on 'Change' link to change phone preference on the 'Change your contact details' page")
      ChangeContactIndDetailsPage.clickOnLink(ChangeContactIndDetailsPage.havePhoneNumberChangeLink)
      And("the Individual user selects 'Yes' in the 'Can we contact you by phone' page")
      ChangeContactIndHavePhone.select("Yes")
      And("the Individual user enters a different phone in 'What is your phone number?' page")
      ChangeContactIndPhonePage.enterIndividualPhoneNumber("1234567890")
      And("the Individual user clicks on 'Change' link to change their phone number on the 'Change your contact details' page")
      ChangeContactIndDetailsPage.clickOnLink(ChangeContactIndDetailsPage.phoneNumberChangeLink)
      And("the Individual user enters a different phone in 'What is your phone number?' page")
      ChangeContactIndPhonePage.enterIndividualPhoneNumber("07589585409")
      And("the Individual user clicks on 'Confirm and send' in the 'Change your contact details' page")
      ChangeContactIndDetailsPage.onPageSubmitById()
      Then("the Individual user is routed to 'Contact details updated' page")
      ContactDetailsUpdatedPage.onPage()
    }
  }
}
