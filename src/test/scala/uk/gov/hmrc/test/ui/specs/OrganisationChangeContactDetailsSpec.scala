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
import uk.gov.hmrc.test.ui.pages.ChangeContactDetails.Org.*
import uk.gov.hmrc.test.ui.specs.tags.*

class OrganisationChangeContactDetailsSpec extends BaseSpec {

  Feature("Organisation Change User Contact Details") {

    // Scenarios covered
    // 1. Organisation user change contact details - all optional fields empty

    Scenario("1 - Organisation user change contact details - all optional fields empty", RegistrationTests, ZapTests) {

      Given("the Organisation user logs in with a valid CARF ID")
      AuthLoginPage.loginAsIndividualOrOrganisationForChange("Q234567890")
      And("the Organisation user clicks on 'Change' link to change the first contact name on the 'Change your contact details for your organisation' page")
      ChangeContactOrgDetailsPage.clickOnLink(ChangeContactOrgDetailsPage.firstContactNameChangeLink)
      And("the Organisation user enters a different first contact name in 'What is the name of the person or team we should contact?' page")
      ChangeContactOrgContactNamePage.enterContactName("changedName")
      And("the Organisation user clicks on 'Change' link to change the first contact email on the 'Change your contact details for your organisation' page")
      ChangeContactOrgDetailsPage.clickOnLink(ChangeContactOrgDetailsPage.firstContactEmailChangeLink)
      And("the Organisation user enters a different email address in 'What is the email address for [first contact name]?' page")
      ChangeContactOrgEmailPage.enterEmailAddress("changedEmail@test.com")
      And("the Organisation user clicks on 'Change' link to change the first contact phone preference on the 'Change your contact details for your organisation' page")
      ChangeContactOrgDetailsPage.clickOnLink(ChangeContactOrgDetailsPage.firstContactHavePhoneChangeLink)
      And("the Organisation user selects 'Yes' in the 'Can we contact [first contact name] by phone?' page")
      ChangeContactOrgHavePhone.select("Yes")
      And("the Organisation user enters a phone number in 'What is the phone number for [first contact name]?' page")
      ChangeContactOrgPhonePage.enterFirstContactPhoneNumber("1234567890")
      And("the Organisation user clicks on 'Change' link to change the second contact preference on the 'Change your contact details for your organisation' page")
      ChangeContactOrgDetailsPage.clickOnLink(ChangeContactOrgDetailsPage.haveSecondContactChangeLink)
      And("the Organisation user selects 'Yes' in the 'Is there someone else we can contact if [second contact name] is not available?' page")
      ChangeContactOrgHaveSecondContact.select("Yes")
      And("the Organisation user enters a name in 'What is the name of the second person or team we should contact?' page")
      ChangeContactOrgSecondContactNamePage.enterContactName("New SecondContact", isProvideMode = true)
      And("the Organisation user enters an email in 'What is the email address for [Second Contact Name]?' page")
      ChangeContactOrgSecondContactEmailPage.enterEmailAddress("NewSecondContactEmail@test.com", isProvideMode = true)
      And("the Organisation user clicks on 'Yes' in 'Can we contact [second contact name] by phone?' page")
      ChangeContactOrgSecondContactHavePhone.select("Yes", ChangeContactOrgSecondContactHavePhone.providePageUrl)
      And("the Organisation user enters a phone number in 'What is the phone number for [second contact name]?' page")
      ChangeContactOrgSecondContactPhonePage.enterSecondContactPhoneNumber("1234567890", isProvideMode = true)
      And("the Organisation user clicks on 'Confirm and send' in the 'Change your contact details' page")
      ChangeContactOrgDetailsPage.onPageSubmitById()
      Then("the Organisation user is routed to 'Contact details updated' page")
      ContactDetailsUpdatedPage.onPage()
    }
  }
}
