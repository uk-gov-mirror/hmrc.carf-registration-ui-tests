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

package uk.gov.hmrc.test.ui.pages

import org.openqa.selenium.By
import uk.gov.hmrc.test.ui.conf.TestConfiguration
import uk.gov.hmrc.test.ui.pages.ChangeContactDetails.Ind.ChangeContactIndDetailsPage
import uk.gov.hmrc.test.ui.pages.ChangeContactDetails.Org.ChangeContactOrgDetailsPage

object AuthLoginPage extends BasePage {
  override val pageUrl: String             = TestConfiguration.url("auth-login-stub") + "/gg-sign-in"
  private val redirectUrl: String          = TestConfiguration.url("carf-registration-frontend")
  private val redirectUrlForChange: String = TestConfiguration.url("carf-change-contact-frontend")

  private val redirectionUrlById: By           = By.id("redirectionUrl")
  private val affinityGroupById: By            = By.id("affinityGroupSelect")
  private val credentialRoleById: By           = By.id("credential-role-select")
  private val ninoValueById: By                = By.id("nino")
  private val presetDropDownById: By           = By.id("presets-dropdown")
  private val presetAddById: By                = By.id("add-preset")
  private val identifierValueCtField: By       = By.id("input-4-0-value")
  private val enrolmentKeyField: By            = By.id("enrolment[0].name")
  private val identifierNameField: By          = By.id("input-0-0-name")
  private val identifierValueField: By         = By.id("input-0-0-value")
  private val authSubmitById: By               = By.id("submit-top")
  private val identifierValueNinoField: String = generateNino(individualNino)
  private val identifierCtValue: String        = generateUtr(autoMatchedCtUtrForUK)

  private def authLoginPage: this.type = {
    navigateTo(pageUrl)
    onPage()
    this
  }

  private def selectAffinityGroup(affinityGroup: String): Unit =
    selectDropdownById(affinityGroupById).selectByVisibleText(affinityGroup)

  private def selectCredentialRole(credentialRole: String): Unit =
    selectDropdownById(credentialRoleById).selectByVisibleText(credentialRole)

  private def enterNinoValue(): Unit =
    sendKeys(ninoValueById, identifierValueNinoField)

  private def addCtPreset(): Unit =
    selectDropdownById(presetDropDownById).selectByVisibleText("CT")
    click(presetAddById)
    sendKeys(identifierValueCtField, identifierCtValue)

  private def addCarfId(carfID: String): Unit =
    sendKeys(enrolmentKeyField, "HMRC-CARF-ORG")
    sendKeys(identifierNameField, "CARFID")
    sendKeys(identifierValueField, carfID)

  private def submitAuthPage(): Unit = click(authSubmitById)

  private def submitAuth(affinityGroup: String, credentialRole: String)(additionalFormFields: => Unit = ()): Unit =
    authLoginPage
    sendKeys(redirectionUrlById, redirectUrl)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    additionalFormFields
    submitAuthPage()

  private def submitAuthForChange(affinityGroup: String, credentialRole: String)(additionalFormFields: => Unit = ()): Unit =
    authLoginPage
    sendKeys(redirectionUrlById, redirectUrlForChange)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    additionalFormFields
    submitAuthPage()

  def loginAsOrgAdminWithoutCtUtr(): OrgRegistrationTypePage.type = {
    submitAuth("Organisation", "User")()
    OrgRegistrationTypePage
  }

  def loginAsOrgAdminWithCtUtr(): OrgRegistrationTypePage.type = {
    submitAuth("Organisation", "User")(addCtPreset())
    OrgRegistrationTypePage // Need to include the step for the proper page display
  }

  def loginAsOrgAssistant(): OrgAssistantPage.type = {
    submitAuth("Organisation", "Assistant")()
    OrgAssistantPage
  }

  def loginAsIndividualWithNino(): IndRegistrationTypePage.type = {
    submitAuth("Individual", "User")()
    IndRegistrationTypePage
  }

  def loginAsIndividualWithoutNino(): IndRegistrationTypePage.type = {
    submitAuth("Individual", "User")()
    IndRegistrationTypePage
  }

  def loginAsAgentAsUser(): AgentKickOutPage.type = {
    submitAuth("Agent", "User")()
    AgentKickOutPage
  }

  def loginAsIndividualOrOrganisationForChange(carfID: String): BasePage = {
    submitAuthForChange("Individual", "User")(addCarfId(carfID))
    carfID match {
      case id if id.startsWith("W") => ChangeContactIndDetailsPage
      case id if id.startsWith("Q") => ChangeContactOrgDetailsPage
      case _                        => throw new IllegalArgumentException(s"Unexpected carfID prefix: $carfID")
    }
  }
}
