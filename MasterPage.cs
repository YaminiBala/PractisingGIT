using System;
using System.Collections.Generic;
using System.Linq;
using Comcast.Business.Functional.Tests.Utils;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;
using OpenQA.Selenium.Support.UI;
using System.Threading;
using System.Drawing;
using OpenQA.Selenium.Interactions;
using TechTalk.SpecFlow;
using Comcast.Business.Functional.Tests.DataClasses;
using System.Diagnostics;
using System.Net;
using RestSharp;
using Comcast.Business.Functional.TenonAccessibility;
//using Comcast.TestAutomation.Browser;

namespace Comcast.Business.Functional.Tests.PageClasses
{
    public abstract class MasterPage : DigitalDataHelper
    {
        // [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart > div.mini-cart-summary")]
        [FindsBy(How = How.CssSelector, Using = "div#shopping-cart.shopping-cart-container.mini-order-summary-content")]
        IWebElement miniCartSummary { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div > div.mini-cart-summary")]
        IWebElement miniCartSummaryInVoiceDoublePlayPage { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav--mobile > div.mini-cart-summary")]
        IWebElement miniCartSummaryMobile { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart >div>div>div>table>thead>tr>th.order-summary-heading")]
        IWebElement miniCartSummaryHeading { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart >div>div>div>table>thead>tr>th.order-summary-close")]
        IWebElement miniCartSummaryClose { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart >div>div>div>div>p>a")]
        IWebElement GoToCheckOutBtton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav--mobile >div>div>div>div>p>a")]
        IWebElement GoToCheckOutBttonMobile { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.buyflow-header-controls > div.buyflow-header-control buyflow-header-control--cart-indicator > div.mini-cart-summary")]
        IWebElement voiceDoublePlayMiniCartSummary { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.buyflow-header-controls > div > div > div > div > div > p > a")]
        IWebElement voiceDoublePlayGoChkOutButton { get; set; }



        [FindsBy(How = How.CssSelector, Using = "body")]
        IWebElement body { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.page-header")]
        IWebElement pageHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "a.acc-helper.acc-hidden")]
        IWebElement accessibilityHelper { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.page-banner")]
        IWebElement buyflowHeader { get; set; }

       // [FindsBy(How = How.CssSelector, Using = "a.logo-link")]
       // IWebElement comcastBusinessLogo { get; set; }

        [FindsBy(How = How.CssSelector, Using = "a.bsp-header-level1-logo-link")]
        IWebElement comcastBusinessLogo { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.logo")]
        IWebElement comcastBusinessBuyflowLogo { get; set; }

        IWebElement errorsListContainer { get; set; }
        IWebElement errorsListContainerHeader { get; set; }
        IWebElement errorsList { get; set; }

        [FindsBy(How = How.CssSelector, Using = "p.link-with-icon--left.link-with-icon.back-link > a")]
        IWebElement backToShopLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "p.link-with-icon--left.link-with-icon.back-link>a")]
        IWebElement backToHomePageLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li.nav-step._current-step")]
        IWebElement BuyflowProgressNavCurrentStep { get; set; }

        [FindsBy(How = How.CssSelector, Using = "ol.progress-nav")]
        IWebElement BuyflowProgressNavBar { get; set; }

        [FindsBy(How = How.CssSelector, Using = "ol.progress-nav.progress-nav-2 > li.nav-step._current-step")]
        IWebElement FrictionlessProgressNavCurrentStep { get; set; }

        [FindsBy(How = How.CssSelector, Using = "ol.progress-nav.progress-nav-2")]
        IWebElement FrictionlessProgressNavBar { get; set; }


        [FindsBy(How = How.CssSelector, Using = "div.order-summary")]
        IWebElement myOrderSummarySection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.order-summary-details")]
        IWebElement myOrderSummaryHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button.order-summary-btn")]
        IWebElement myOrderSummaryBtn { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.order-summary-dropdown")]
        IWebElement myOrderSummarydetails { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.order-summary-content")]
        IWebElement myOrderSummaryContent { get; set; }


        [FindsBy(How = How.CssSelector, Using = "div.order-total")]
        IWebElement monthlyTotal { get; set; }

        [FindsBy(How = How.CssSelector, Using = "td.package-name")]
        IWebElement offerName { get; set; }

        [FindsBy(How = How.CssSelector, Using = "td.package-price")]
        IWebElement offerPrice { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.order-summary-content")]

        IWebElement OrderSummary { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-sub")]
        IWebElement buyflowFooter { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer")]
        IWebElement globalFooter { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-column-container")]
        IWebElement globalFooterColumnContainer { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-column._hide-xlarge")]
        IWebElement globalFooterDesktopColumns { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-column-container > div > div:nth-child(1) > div:nth-child(1) > ul > li:nth-child(1) > a")]
        IWebElement globalFooterDesktopLink1 { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-column-container > div.container > div:nth-child(4)")]
        IWebElement globalFooterMobileColumns { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer > div.footer-sub")]
        IWebElement globalFooterSub { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-legal > p")]
        IWebElement globalFooterLegalText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-legal > ul > li > a")]
        IWebElement globalFooterLegalLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-social-media > ul")]
        IWebElement globalFooterSocialMediaLinks { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-column-container > div > div:nth-child(4) > div:nth-child(1) > ul > li:nth-child(1) > a")]
        IWebElement globalFooterMobileLink1 { get; set; }

        [FindsBy(How = How.CssSelector, Using = "h2[aria-describedby='ip-selection-description']")]
        IWebElement staticIPAccordionHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.radio-option-group")]
        IWebElement staticIpOptionsSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.form-checkbox-group.form-field--error")]
        IWebElement lobBusinessServicesCheckBoxGroup { get; set; }

        [FindsBy(How = How.CssSelector, Using = "fieldset.form-input-group.fieldset.form-field--error")]
        IWebElement lobBusinessSizeRadioButtonGroup { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[@id='justificationMessage-StaticIp5']/../fieldset")]
        IWebElement staticIp5CheckboxSelection { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[@id='justificationMessage-StaticIp13']/../fieldset")]
        IWebElement staticIp13CheckboxSelection { get; set; }
        //[FindsBy(How = How.CssSelector, Using = "div.error-dialog[data-component='form-errors']")]
        //IWebElement errorsListContainer { get; set; }

        [FindsBy(How = How.XPath, Using = "//header/div[contains(@class,'bsp-header')]")]
        IWebElement globalHeaderNew { get; set; }

        [FindsBy(How = How.CssSelector, Using = "header > div.utility-nav")]
        IWebElement utilityHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-right > ul.utility-nav-section.utility-nav-links")]
        IWebElement utilityNavigationLinks { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-right > div > span.call-number > span.phone-number.phone-replace")]
        IWebElement utilityNavSuportNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-right > div > span > i.icon")]
        IWebElement utilityNavPhoneIcon { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-login>a")]
        IWebElement globalSignInLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-navbar > div > i.icon.icon-logo")]
        IWebElement globalNavLogo { get; set; }

        [FindsBy(How = How.CssSelector, Using = "ul.section-slide-collection")]
        IWebElement OfferPlanSection { get; set; }

        //  [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart > span > button")]
        [FindsBy(How = How.CssSelector, Using = "div.bsp-header-level1-utility > a > span > span.bsp-profile-badge-count")]
        IWebElement shoppingCartIndicatorLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav--mobile > span > button")]
        IWebElement shoppingCartIndicatorLnkMobile { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart > span > button")]
        IWebElement disableShoppingCartIndicator { get; set; }

        //[FindsBy(How = How.CssSelector, Using = "i.icon-cart")]
        //IWebElement ShoppingCartIndicator { get; set; }


        [FindsBy(How = How.CssSelector, Using = "div.buyflow-header-control.buyflow-header-control--cart-indicator>div>div>div>div>p>a")]
        IWebElement GoToCheckOutBttonInVoiceDoublePlayPP { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.logo>img")]
        IWebElement comcastBusinessLogoInVoiceDoublePlayPage { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div > span > button")]
        IWebElement shoppingCartIndicatorLnkInVoiceDoublePlayPP { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div > span > button > span.accessibly-hidden")]
        IWebElement shoppingCartIndicatorAccessibilityTxtInVoiceDoublePlayPP { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div > span > button > span.offer-count")]
        IWebElement shoppingCartOfferCountInVoiceDoublePlayPP { get; set; }


        [FindsBy(How = How.CssSelector, Using = "i.icon-cart.icon-cart-full.icon")]
        IWebElement shoppingCartIndicatorFull { get; set; }

        [FindsBy(How = How.CssSelector, Using = "i.icon-cart.icon-cart-disabled.icon")]
        IWebElement shoppingCartIndicatorEmpty { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart > span > button > span.accessibly-hidden")]
        IWebElement shoppingCartIndicatorAccessibilityTxt { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav--mobile > span > button > span.accessibly-hidden")]
        IWebElement shoppingCartIndicatorAccessibilityTxtMobile { get; set; }

        // [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart > span > button > span.offer-count")]
        [FindsBy(How = How.CssSelector, Using = "div.bsp-header-level1-utility > a > span")]

        IWebElement shoppingCartOfferCount { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav--mobile > span > button > span.offer-count")]
        IWebElement shoppingCartOfferCountMobile { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-cart > span > a > i.icon-cart")]
        IWebElement shoppingCartDisabledIndicator { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-menu > ul > li:nth-child(1)")]
        IWebElement globalNavSmallMediumBusiness { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-menu > ul > li:nth-child(2)")]
        IWebElement globalNavEnterprise { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-menu > ul > li:nth-child(3)")]
        IWebElement globalNavOfferBundles { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.l2")]
        IWebElement globalNavL2Links { get; set; }

        [FindsBy(How = How.CssSelector, Using = "a.bsp-header-level1-nav-link")]
        IList<IWebElement> globalNavL1Links { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li:nth-child(1)>a.bsp-header-level1-nav-link")]
        IWebElement globalNavShopLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li:nth-child(2)>a.bsp-header-level1-nav-link")]
        IWebElement globalNavSupportLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li:nth-child(3)>a.bsp-header-level1-nav-link")]
        IWebElement globalNavMyAccountLink { get; set; }


        [FindsBy(How = How.CssSelector, Using = "li.l2-item.dropdown-trigger:nth-child(1)")]
        IWebElement internetL2Nav { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li.l2-item.dropdown-trigger:nth-child(2)")]
        IWebElement phoneL2Nav { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li.l2-item.dropdown-trigger:nth-child(3)")]
        IWebElement tvL2Nav { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li.l2-item.dropdown-trigger:nth-child(4)")]
        IWebElement cloudSolL2Nav { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li.l2-item.dropdown-trigger:nth-child(5)")]
        IWebElement bundlesL2Nav { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.sub-nav.sub-nav-dropdown.sub-nav-dropdown-fixed")]
        IWebElement subNavDropDown { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-menu > ul.header-navigation-menu-list")]
        IWebElement globalHeaderNavigationMenu { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.sub-nav-link-block--single.sub-nav-link-block")]
        IWebElement startShoppingMobile { get; set; }

        [FindsBy(How = How.CssSelector, Using = "li._active>div>div>div>ul.sub-nav-list-inline")]
        IWebElement resourceLibraryMobile { get; set; }



        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-contact > a.button.button-color-primary.button-micro")]
        IWebElement requestConsultationButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "header.header > nav.header-navigation")]
        IWebElement navigationHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "header > nav > div > div.header-navigation-navbar > div > i.icon.icon-logo > a.logo-link")]
        IWebElement navigationHeaderLogo { get; set; }

        [FindsBy(How = How.CssSelector, Using = "nav > div > div.header-navigation-contact > div.header-navigation-call > span > span.phone-number.phone-replace")]
        IWebElement navigationHeaderSupportNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "header > nav > div > div.header-navigation-contact > a.button.button-color-primary.button-micro")]
        IWebElement navigationHeaderRequestConsultationButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-search > form > button.desktop-search")]
        IWebElement navHeaderSearchIcon { get; set; }

        [FindsBy(How = How.XPath, Using = "//*[@id='search']")]
        IWebElement navHeaderSearchBoxInput { get; set; }

        [FindsBy(How = How.XPath, Using = "//*[@id='submit']")]
        IWebElement searchBtn { get; set; }


        [FindsBy(How = How.XPath, Using = "//*[@data-role='search-submit']")]
        IWebElement searchButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "input.bcp-btn.bcp-btn--primary.bcp-btn--small")]
        IWebElement searchButtonBCP { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.sfContentBlock")]
        IWebElement searchResults { get; set; }

        [FindsBy(How = How.Id, Using = "CPHMainContent_C001_ctl00_ctl00_resultsStats")]
        IWebElement searchResultsStats { get; set; }

        //newSearchComponents

       [FindsBy(How = How.CssSelector, Using = "button.uni-search-launch")]
        IWebElement newNavHeaderSearchIcon { get; set; }

        [FindsBy(How = How.Id, Using = "search")]
        IWebElement newNavSearchInputBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "uni-search-overlay.active")]
        IWebElement searchOverlay { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.uni-search-container.active > button.bsp-btn.uni-search-close")]
        IWebElement searchOverlayCloseButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button.bsp-btn.bsp-btn--blue.uni-search-submit")]
        IWebElement searchOverlaySubmitButton { get; set; }






        //new device header
        [FindsBy(How = How.CssSelector, Using = "div.uninav-mobile-header")]
        IWebElement newDeviceHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button[data-role='triptych-trigger']")]
        IWebElement newDeviceHeaderMenu { get; set; }

        [FindsBy(How = How.Id, Using = "ul.uninav-mobile-tabs")]
        IWebElement newDeviceHeaderTabs { get; set; }


        //sticky footer
        [FindsBy(How = How.CssSelector, Using = "div.header-navigation.header-navigation-fixed.background-color--blue")]
        IWebElement blueStickyFooter { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-container>div.container")]
        IWebElement stickyFooterSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-sticky-geolocator>div.geolocator>svg.svg-icon.svg-icon-map-pin")]
        IWebElement locationIcon { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-sticky-geolocator>div.geolocator>div.geolocator-wrapper>p.geolocator-location")]
        IWebElement locationText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-sticky-geolocator>div.geolocator>div.geolocator-wrapper>button.button-anchor.geolocator-update--header-navigation")]
        IWebElement checkAvailabilityFooter { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-contact > div.header-navigation-call")]
        IWebElement phoneNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-contact>a.button.button-color-secondary.button-small.button-request-consultation")]
        internal IWebElement getAfreeQuoteButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-call>span>span.phone-number.phone-replace")]
        IWebElement footerPhoneNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-section.utility-nav-call>span>span.phone-number.phone-replace")]
        IWebElement headerPhoneNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-section.utility-nav-geolocator>div>div>p>span>span.geolocator-output-city")]
        IWebElement headerCity { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.utility-nav-section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > p:nth-child(1) > span:nth-child(2) > span.geolocator-output-zipcode")]
        IWebElement headerZip { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-sticky-geolocator>div>div>p>span>span.geolocator-output-city")]
        IWebElement footerCity { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header > div.header-navigation div[class*='sticky-geolocator'] div.geolocator-wrapper > p > span[class*='output']")]
        IWebElement footerZip { get; set; }

        //new sticky footer

        [FindsBy(How = How.XPath, Using = "html/body/div/div/div[@class='geo-footer']/div[@class='geo-footer-container']")]
        IWebElement newStickyFooter { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.container.geo-footer-section-container")]
        IWebElement newStickyFooterSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-section>div>div>svg.bsp-profile-icon.svg-icon-map-pin.bsp-profile-icon--desktop-only")]
        IWebElement newLocationIcon { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-section>div>div>div>p.geolocator-location")]
        IWebElement newLocationText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-section>div>div>div>button.button-anchor.geolocator-update.geolocator-update-bsp.geolocator-update--utility-nav.geolocator-update--opens-drawer.bottom-trigger")]
        IWebElement newCheckAvailabilityFooter { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-cta>svg")]
        IWebElement newPhoneIcon { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-cta>span")]
        IWebElement newPhoneNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-cta>a.bsp-header-call-to-action-btn.bsp-btn.bsp-btn--blue")]
        internal IWebElement newGetAfreeQuoteButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-cta>span>span.phone-number.phone-replace")]
        IWebElement newFooterPhoneNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.bsp-header-call-to-action>span>span.phone-number.phone-replace")]
        IWebElement newHeaderPhoneNumber { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.bsp-geo-container.utility-nav>div>div>p>span>span.geolocator-output-zipcode")]
        IWebElement newHeaderZip { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.geo-footer-section>div>div>div>p>span>span.geolocator-output-zipcode")]
        IWebElement newFooterZip { get; set; }

        //

        [FindsBy(How = How.CssSelector, Using = "div.overlay-container")]
        IWebElement overlayContainer { get; set; }

        [FindsBy(How = How.Id, Using = "overlayTitle")]
        IWebElement overlayTitle { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button.button-icon[data-component='overlay-close']")]
        IWebElement overlayCloseBtn { get; set; }

        [FindsBy(How = How.CssSelector, Using = "#overlayDescription > div.overlay-confirm > p")]
        IWebElement overlayDescription { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button.button.button-small.button-color-secondary[data-component='overlay-close']")]
        IWebElement overlayCancelBtn { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.overlay-actions > button.button.button-small.button-color-primary")]
        IWebElement overlayOkBtn { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.overlay-container")]
        IWebElement overlayDisplayed { get; set; }

        [FindsBy(How = How.Id, Using = "overlayDescription")]
        IWebElement SpinnerText { get; set; }


        //SearchResultsPage//


        [FindsBy(How = How.CssSelector, Using = "h1")]
        IWebElement searchResultBannerHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "#search-results-header > div > p:nth-child(2) > span:nth-child(1)")]
        IWebElement totalSearchResults { get; set; }

        [FindsBy(How = How.CssSelector, Using = "#search-results-header > div > p:nth-child(2) > span:nth-child(2)")]
        IWebElement searchResultsForText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "#search-results-header > div > p:nth-child(2) > strong > span")]
        IWebElement searchTerm { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.global-search.global-search-results > div.component-main > div > ul")]
        IWebElement globalSearchResults { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.pagination-large > ul.pagination-list")]
        IWebElement paginationList { get; set; }

        [FindsBy(How = How.CssSelector, Using = "a.button.button-pagination.button-color-secondary[data-options='PageIndex: 1']")]
        IWebElement previousButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "a.button.button-pagination.button-color-secondary[data-options='PageIndex: 3']")]
        IWebElement nextButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = "#search-results-header > div > p:nth-child(2) > span:nth-child(1)")]
        IWebElement noSearchResult { get; set; }

        [FindsBy(How = How.CssSelector, Using = "legend.legend")]
        IWebElement numberOfPeopleHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "fieldset.fieldset > label.label.icon-radio")]
        IList<IWebElement> fourRadioOptions { get; set; }

        [FindsBy(How = How.CssSelector, Using = "#lead-generation-form > div.number-of-employees > fieldset > label:nth-child(2) > span.icon-radio-image")]
        IList<IWebElement> radioOption { get; set; }
       
        [FindsBy(How = How.CssSelector, Using = "div.sales-representative>fieldset.fieldset")]
        IWebElement salesRepSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.sales-representative>fieldset>legend")]
        IWebElement salesRepHeader { get; set; }

        [FindsBy(How = How.Id, Using = "SalesRepresentativeFirstName")]
        IWebElement salesRepFirstName { get; set; }

        [FindsBy(How = How.Id, Using = "#SalesRepresentativeLastName")]
        IWebElement salesRepLastName { get; set; }

        //Search AutoFill Suggestions //

        [FindsBy(How = How.CssSelector, Using = "div.search-autofill")]
        IWebElement searchAutofillDropdown { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.search-autofill._active > p.search-autofill-heading")]
        IWebElement suggestionText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.search-autofill._active > button.button-anchor.search-autofill-close")]
        IWebElement closeButton { get; set; }

        [FindsBy(How = How.CssSelector, Using = " div.search-autofill._active > ul.search-autofill-list")]
        IWebElement searchAutofillList { get; set; }


        [FindsBy(How = How.Id, Using = "next-button")]
        IWebElement buyflowPageNextBtn { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div#bs-header>div>a.nav-logo")]
        IWebElement businessHelpHeaderLogo { get; set; }

        [FindsBy(How = How.XPath, Using = "//ul[@id='header-nav']/li[2]/a")]
        IWebElement businessHelpHeaderMyAccLnk { get; set; }

        [FindsBy(How = How.XPath, Using = "//ul[@id='header-nav']/li[4]/a")]
        IWebElement businessHelpHeaderSupportLnk { get; set; }

        [FindsBy(How = How.XPath, Using = "//ul[@id='header-nav']/li[6]/a")]
        IWebElement businessHelpHeaderShopServiceLnk { get; set; }

        [FindsBy(How = How.XPath, Using = "//ul[@id='header-nav']/li[8]/a")]
        IWebElement businessHelpHeaderAppsMarketPlaceLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "a.footer-logo")]
        IWebElement businessHelpFooterLogo { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-container>p")]
        IWebElement businessHelpFooterCopyRight { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.footer-container>ul")]
        IWebElement businessHelpFooterLinks { get; set; }

        [FindsBy(How = How.CssSelector, Using = " a.click-to-chat-link")]
        IWebElement clickToChatLink { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button.triptych-activator")]
        IWebElement btnBreadcrum { get; set; }

        [FindsBy(How = How.CssSelector, Using = "button.triptych-activator")]
        private IWebElement btnCloseBreadCrum { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.header-navigation-utility-geolocator > div.geolocator > div > button[class*='mobile']")]
        private IWebElement customerLocationChangeLnkMobile { get; set; }

        /// <summary>
        /// service Order Agreement Section objects
        /// </summary>

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='SERVICE_ORDER_AGREEMENT']")]
        IWebElement serviceOrderAgreementSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='SERVICE_ORDER_AGREEMENT']> div> h2")]
        IWebElement serviceOrderAgreementSectionHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='SERVICE_ORDER_AGREEMENT'] >div > div > a.service-agreement-download-link")]
        IWebElement serviceOrderAgreementPDFLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='SERVICE_ORDER_AGREEMENT'] >div > div > button.print.button-anchor")]
        IWebElement serviceOrderAgreementPrintLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='SERVICE_ORDER_AGREEMENT'] > div.service-agreement-section> div.service-agreement-textbox")]
        IWebElement serviceOrderAgreementRichTextBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='SERVICE_ORDER_AGREEMENT'] > div.service-agreement-section.service-agreement-contract-field.form-field")]
        IWebElement ServiceOrderAgreementCheckBoxSection { get; set; }

        [FindsBy(How = How.Id, Using = "SERVICE_ORDER_AGREEMENT")]
        IWebElement serviceOrderAgreementCheckBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='SERVICE_ORDER_AGREEMENT'] > span.icon-checkbox-body.rich-text")]
        IWebElement serviceOrderAgreementCheckBoxRichText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='SERVICE_ORDER_AGREEMENT'] > div.service-agreement-section.service-agreement-contract-field.form-field > small")]
        IWebElement serviceOrderAgreementCheckBoxError { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='SERVICE_ORDER_AGREEMENT'] > span.icon-checkbox-body.rich-text > p > strong")]
        IWebElement customerNameUnderServiceOrderAgreement { get; set; }

        /// <summary>
        /// E911 Agreement Section objects
        /// </summary>

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='E911']")]
        IWebElement E911AgreementSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='E911']> div> h2")]
        IWebElement E911AgreementSectionHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='E911'] >div > div > a.service-agreement-download-link")]
        IWebElement E911AgreementPDFLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='E911'] >div > div > button.print.button-anchor")]
        IWebElement E911AgreementPrintLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='E911'] > div.service-agreement-section> div.service-agreement-textbox")]
        IWebElement E911AgreementRichTextBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='E911'] > div.service-agreement-section.service-agreement-contract-field.form-field")]
        IWebElement E911AgreementCheckBoxSection { get; set; }

        [FindsBy(How = How.Id, Using = "E911")]
        IWebElement E911serviceagreementCheckBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='E911'] > span.icon-checkbox-body.rich-text")]
        IWebElement E911AgreementCheckBoxRichText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='E911'] > div.service-agreement-section.service-agreement-contract-field.form-field > small")]
        IWebElement E911AgreementCheckBoxError { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='E911'] > span.icon-checkbox-body.rich-text > p > strong")]
        IWebElement customerNameUnderE911Agreement { get; set; }

        /// <summary>
        /// RCF contract objects
        /// </summary>

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RCF']")]
        IWebElement RCFAgreementSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RCF']> div> h2")]
        IWebElement RCFAgreementSectionHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RCF'] >div > div > a.service-agreement-download-link")]
        IWebElement RCFAgreementPDFLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RCF'] >div > div > button.print.button-anchor")]
        IWebElement RCFAgreementPrintLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RCF'] > div.service-agreement-section> div.service-agreement-textbox")]
        IWebElement RCFAgreementRichTextBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RCF'] > div.service-agreement-section.service-agreement-contract-field.form-field")]
        IWebElement RCFAgreementCheckBoxSection { get; set; }

        [FindsBy(How = How.Id, Using = "RCF")]
        IWebElement RCFAgreementCheckBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='RCF'] > span.icon-checkbox-body.rich-text")]
        IWebElement RCFAgreementCheckBoxRichText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='RCF'] > div.service-agreement-section.service-agreement-contract-field.form-field > small")]
        IWebElement RCFAgreementCheckBoxError { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='RCF'] > span.icon-checkbox-body.rich-text > p > strong")]
        IWebElement customerNameUnderRCFAgreement { get; set; }

        /// <summary>
        /// Resp Org Agreement Section Objects
        /// </summary>

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RESP_ORG']")]
        IWebElement RespOrgAgreementSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RESP_ORG']> div> h2")]
        IWebElement RespOrgAgreementSectionHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RESP_ORG'] >div > div > a.service-agreement-download-link")]
        IWebElement RespOrgAgreementPDFLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RESP_ORG'] >div > div > button.print.button-anchor")]
        IWebElement RespOrgAgreementPrintLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RESP_ORG'] > div.service-agreement-section> div.service-agreement-textbox")]
        IWebElement RespOrgAgreementRichTextBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='RESP_ORG'] > div.service-agreement-section.service-agreement-contract-field.form-field")]
        IWebElement RespOrgAgreementCheckBoxSection { get; set; }

        [FindsBy(How = How.Id, Using = "RESP_ORG")]
        IWebElement RespOrgAgreementCheckBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='RESP_ORG'] > span.icon-checkbox-body.rich-text")]
        IWebElement RespOrgAgreementCheckBoxRichText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='RESP_ORG'] > div.service-agreement-section.service-agreement-contract-field.form-field > small")]
        IWebElement RespOrgAgreementCheckBoxError { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='RESP_ORG'] > span.icon-checkbox-body.rich-text > p > strong")]
        IWebElement customerNameUnderRespOrgAgreement { get; set; }

        /// <summary>
        /// Number Transfer Agreement (LOA) Section objects
        /// </summary>

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='LETTER_OF_AGENCY']")]
        IWebElement NumberTransferAgreementSection { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='LETTER_OF_AGENCY']> div> h2")]
        IWebElement NumberTransferAgreementSectionHeader { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='LETTER_OF_AGENCY'] >div > div > a.service-agreement-download-link")]
        IWebElement NumberTransferAgreementPDFLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='LETTER_OF_AGENCY'] >div > div > button.print.button-anchor")]
        IWebElement NumberTransferAgreementPrintLnk { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='LETTER_OF_AGENCY'] > div.service-agreement-section> div.service-agreement-textbox")]
        IWebElement NumberTransferAgreementRichTextBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "div.service-agreement[data-sel='LETTER_OF_AGENCY'] > div.service-agreement-section.service-agreement-contract-field.form-field")]
        IWebElement NumberTransferAgreementCheckBoxSection { get; set; }

        [FindsBy(How = How.Id, Using = "LETTER_OF_AGENCY")]
        IWebElement NumberTransferAgreementCheckBox { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='LETTER_OF_AGENCY'] > span.icon-checkbox-body.rich-text")]
        IWebElement NumberTransferAgreementCheckBoxRichText { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='LETTER_OF_AGENCY'] > div.service-agreement-section.service-agreement-contract-field.form-field > small")]
        IWebElement NumberTransferAgreementCheckBoxError { get; set; }

        [FindsBy(How = How.CssSelector, Using = "label.service-agreement-checkbox.icon-checkbox[for='LETTER_OF_AGENCY'] > span.icon-checkbox-body.rich-text > p > strong")]
        IWebElement customerNameUnderNumberTransferAgreement { get; set; }

        //New Global Footer and Subfooter

        [FindsBy(How = How.XPath, Using = "//div[contains(@class,'bsp-footer-container')]/div/a")]
        IWebElement newComcastFooterLogo { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[contains(@class,'bsp-footer-container')]/div[@class='bsp-footer-column']")]
        IList<IWebElement> newFooterLinksPanel { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[contains(@class,'bsp-footer-container')]/div[@class='bsp-footer-column']/ul/li[contains(@class,'header')]/a")]
        IList<IWebElement> newFooterLinksPanelHeader { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[contains(@class,'bsp-subfooter')]/div/div/span")]
        IWebElement newCopyrightsLbl { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[@class='bsp-subfooter']/div/ul/li/a")]
        IList<IWebElement> newSocialMediaLnks { get; set; }

        [FindsBy(How = How.XPath, Using = "//div[contains(@class,'bsp-subfooter')]/div/div/ul/li/a")]
        IList<IWebElement> newSubFooterLnks { get; set; }


        TestSettings SettingsValue = new TestSettings();

        protected MasterPage(IWebDriver browser, Report report, Validations validations)
            : base(browser, report, validations)
        { }

        public bool pageTitle(string currentPageTitle)
        {
            try
            {
                string pageTitle = browser.Title;
                if (pageTitle == currentPageTitle)
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("pageTitle", exception);
            }
            return false;
        }

        internal bool resizeBrowser(string p0)
        {
            string expectedSize = "";
            int dWidth = 0;
            int dHeight = 0;
            switch (p0.ToLower())
            {
                case "iphone6":
                    dWidth = 375;
                    dHeight = 667;
                    browser.Manage().Window.Size = new Size(dWidth, dHeight);
                    expectedSize = "{Width=" + dWidth + ", Height=" + dHeight + "}";
                    break;
                case "ipadportrait":
                    dWidth = 768;
                    dHeight = 1024;
                    browser.Manage().Window.Size = new Size(dWidth, dHeight);
                    expectedSize = "{Width=" + dWidth + ", Height=" + dHeight + "}";
                    break;
                case "ipadlandscape":
                    dWidth = 1024;
                    dHeight = 768;
                    browser.Manage().Window.Size = new Size(dWidth, dHeight);
                    expectedSize = "{Width=" + dWidth + ", Height=" + dHeight + "}";
                    break;
                default:
                    validations.assertionFailed("resizeBrowser", "Choose a valid option!");
                    break;

            }
            //Thread.Sleep(100);
            string actualSize = browser.Manage().Window.Size.ToString();
            if (expectedSize == actualSize)
            {
                report.reportDoneEvent("Select Web Hosting", actualSize);
                return true;
            }
            else
                return false;
        }

        public bool isHeaderPresentforAccessibilyHelp()
        {
            try
            {
                if (isElementPresent(accessibilityHelper, "Accessibility tag Sikp to main page"))
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("", exception);
            }
            return false;
        }

        public bool isComcastBusinessLogoPresent()
        {
            try
            {
                if (isElementPresent(comcastBusinessLogo, "Logo"))
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("isComcastBusinessLogoPresent", exception);
            }
            return false;
        }

        public bool isComcastBusinessBuyflowLogoPresent()
        {
            try
            {
                if (isElementPresent(comcastBusinessBuyflowLogo, "Logo"))
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("isComcastBusinessBuyflowLogoPresent", exception);
            }
            return false;
        }

        public bool isBackToShopLnkCorrect()
        {
            try
            {
                if (!(testSettings.isMobileExecution || testSettings.isDesktopMobileVersion))
                {
                    if (backToShopLink.Text.Contains("Back"))
                        //backToShopLink.GetAttribute("href") == "http://business.comcast.com"
                        return true;
                }
                else
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("isBackToShopLnkCorrect", exception);
            }
            return false;
        }

        public bool isBackToShopLnkPresent()
        {
            try
            {
                if (!(testSettings.isMobileExecution || testSettings.isDesktopMobileVersion))
                {
                    if (isElementPresent(backToShopLink, "BackToShopLnk"))
                        return true;
                }
                else
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("isBackToShopLnkPresent", exception);
            }
            return false;
        }

        public bool isBackToHomePageLnkPresent()
        {
            try
            {
                if (isElementPresent(backToHomePageLink, "backToHomePageLink") && backToHomePageLink.Text.Contains("Home"))
                    return true;
            }
            catch (WebDriverException exception)
            {
                report.reportException("isBackToHomePageLnkPresent", exception);
            }
            return false;
        }

        public bool clickBackToShopLnk(string landingPage)
        {
            try
            {
                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    var appUrl = testSettings.ApplicationURL;
                    if (landingPage.Equals("Home"))
                        browser.Navigate().GoToUrl(appUrl);
                    else if (landingPage.Contains("Plans And Pricing"))
                    {
                        if (browser.Title.Contains("Internet") || landingPage.Contains("Internet"))
                            browser.Navigate().GoToUrl(appUrl + "/internet/business-internet");
                        else if (browser.Title.Contains("Voice") || landingPage.Contains("Voice"))
                            browser.Navigate().GoToUrl(appUrl + "/phone/business");
                        else if (browser.Title.Contains("Video") || landingPage.Contains("Video"))
                            browser.Navigate().GoToUrl(appUrl + "/tv/offices");
                        else if (browser.Title.Contains("Voice Double play") || landingPage.Contains("Voice Double play"))
                            browser.Navigate().GoToUrl(appUrl + "/planselection/mobility");
                    }
                }

                else
                {
                    //Thread.Sleep(2000);
                    waitForElementToBeClickable(backToShopLink, 120);                  
                    IWait<IWebDriver> wait = new WebDriverWait(browser, TimeSpan.FromSeconds(30.00));
                    wait.Until(driver1 => ((IJavaScriptExecutor)browser).ExecuteScript("return document.readyState").Equals("complete"));
                    //((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", backToShopLink);
                    //Thread.Sleep(7000);
                    waitForElementToBeClickable(backToShopLink, 120);
                   
                    Actions actions = new Actions(browser);
                    actions.MoveToElement(backToShopLink).Click().Perform();
                    
                }

                waitForElementLoad(comcastBusinessLogo, 120);
                return true;
               
            }
            catch (Exception exception)
            {
                report.reportException("clickBackToShopLnk", exception);
            }
            return false;
        }



        public IList<IWebElement> getAllErrorFields(IList<IWebElement> allFormFields)
        {
            try
            {
                IList<IWebElement> errorFormFields = new List<IWebElement>();
                foreach (IWebElement formField in allFormFields)
                {
                    if (formField.GetAttribute("class").Contains("parsley-error"))
                        errorFormFields.Add(formField);
                }
                return errorFormFields;
            }
            catch (Exception exception)
            {
                report.reportException("getAllErrorFields", exception);
            }
            return null;
        }

        public IWebElement getErrorMessage(IList<IWebElement> allErrorFormFields, string errorfield)
        {
            try
            {
                foreach (IWebElement errorFormField in allErrorFormFields)
                {
                    IWebElement errorFormInputField = errorFormField.FindElement(By.CssSelector("input.form-input"));
                    if (errorFormInputField.GetAttribute("Id").Equals(errorfield))
                    {
                        IWebElement errorMessage = errorFormField.FindElement(By.CssSelector("div.error-message"));
                        return errorMessage;
                    }
                }
            }
            catch (Exception exception)
            {
                report.reportException("getErrorMessage", exception);
            }
            return null;
        }

        public bool isPhoneNumberFormatted(string element, string value)
        {
            try
            {
                IWebElement myElement = browser.FindElement(By.Id(element));
                if (myElement.GetAttribute("value").Equals(value))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isPhoneNumberFormatted", exception);
            }
            return false;
        }

        public bool isFirstRequiredErrorFieldIsFocused(string errorField)
        {
            try
            {
                IWebElement activeElement = browser.SwitchTo().ActiveElement();
                if (activeElement.GetAttribute("Id").Equals(errorField))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isFirstRequiredErrorFieldIsFocused", exception);
            }
            return false;
        }

        public IList<IWebElement> getAllSuccessFields(IList<IWebElement> allFormFields)
        {
            try
            {
                IList<IWebElement> sucessFormFields = new List<IWebElement>();
                foreach (IWebElement formField in allFormFields)
                {
                    if (formField.GetAttribute("class").Contains("parsley-success"))
                        sucessFormFields.Add(formField);
                }
                return sucessFormFields;
            }
            catch (Exception exception)
            {
                report.reportException("getAllSuccessFields", exception);
            }
            return null;
        }

        public bool sendSomeKeys(string p0)
        {
            try
            {
                if (p0 == "Tab")
                {
                    browser.SwitchTo().ActiveElement().SendKeys(Keys.Tab);
                    return true;
                }
                else
                    return false;
            }
            catch (Exception exception)
            {
                report.reportException("sendSomeKeys", exception);
            }
            return false;
        }

        /// <summary>
        /// Progress navigation bar
        /// </summary>

        public bool isProgressNavBarDisplayed(string navType)
        {
            try
            {
                if (!(testSettings.isMobileExecution || testSettings.isDesktopMobileVersion))
                {
                    if (navType.equalsIgnoreCase("Frictionless"))
                    {
                        if (isElementPresent(FrictionlessProgressNavBar, "Frictionless Progress Nav bar"))
                            return true;
                    }
                    else
                    {
                        if (isElementPresent(BuyflowProgressNavBar, "Buyflow Progress Nav bar"))
                            return true;
                    }
                }
                else
                {
                    return true;
                }
            }
            catch (Exception exception)
            {
                report.reportException("isProgressNavBarDisplayed", exception);
            }
            return false;
        }

        public bool isProgressNavBarNotDisplayed()
        {
            try
            {
                if (BuyflowProgressNavBar.Displayed)
                    return false;
            }
            catch (Exception)
            {

            }
            return true;
        }

        public bool verifyPrograssNavMenuItems(string navType, Table menuItems)
        {
            try
            {
                if (!(testSettings.isDesktopMobileVersion || testSettings.isMobileExecution))
                {
                    IList<IWebElement> ProgressNavSteps = null;
                    if (navType.equalsIgnoreCase("frictionless"))
                    {
                        ProgressNavSteps = FrictionlessProgressNavBar.FindElements(By.CssSelector("li.nav-step"));
                    }
                    else
                    {
                        ProgressNavSteps = BuyflowProgressNavBar.FindElements(By.CssSelector("li.nav-step"));
                    }
                    if (menuItems.RowCount == 2)
                    {
                        if (ProgressNavSteps[0].Text.IndexOf((String)menuItems.Rows[0][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[1].Text.IndexOf((String)menuItems.Rows[1][0], StringComparison.OrdinalIgnoreCase) >= 0)
                            return true;
                    }
                    else if (menuItems.RowCount == 3)
                    {
                        if (ProgressNavSteps[0].Text.IndexOf((String)menuItems.Rows[0][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[1].Text.IndexOf((String)menuItems.Rows[1][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[2].Text.IndexOf((String)menuItems.Rows[2][0], StringComparison.OrdinalIgnoreCase) >= 0)
                            return true;
                    }
                    else if (menuItems.RowCount == 4)
                    {
                        if (ProgressNavSteps[0].Text.IndexOf((String)menuItems.Rows[0][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[1].Text.IndexOf((String)menuItems.Rows[1][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[2].Text.IndexOf((String)menuItems.Rows[2][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[3].Text.IndexOf((String)menuItems.Rows[3][0], StringComparison.OrdinalIgnoreCase) >= 0)
                            return true;
                    }
                    else if (menuItems.RowCount == 5)
                    {
                        if (ProgressNavSteps[0].Text.IndexOf((String)menuItems.Rows[0][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[1].Text.IndexOf((String)menuItems.Rows[1][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[2].Text.IndexOf((String)menuItems.Rows[2][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[3].Text.IndexOf((String)menuItems.Rows[3][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[4].Text.IndexOf((String)menuItems.Rows[4][0], StringComparison.OrdinalIgnoreCase) >= 0)
                            return true;
                    }
                    else if (menuItems.RowCount == 6)
                    {
                        if (ProgressNavSteps[0].Text.IndexOf((String)menuItems.Rows[0][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[1].Text.IndexOf((String)menuItems.Rows[1][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[2].Text.IndexOf((String)menuItems.Rows[2][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[3].Text.IndexOf((String)menuItems.Rows[3][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[4].Text.IndexOf((String)menuItems.Rows[4][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[5].Text.IndexOf((String)menuItems.Rows[5][0], StringComparison.OrdinalIgnoreCase) >= 0)
                            return true;
                    }
                    else if (menuItems.RowCount == 7)
                    {
                        if (ProgressNavSteps[0].Text.IndexOf((String)menuItems.Rows[0][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[1].Text.IndexOf((String)menuItems.Rows[1][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[2].Text.IndexOf((String)menuItems.Rows[2][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[3].Text.IndexOf((String)menuItems.Rows[3][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[4].Text.IndexOf((String)menuItems.Rows[4][0], StringComparison.OrdinalIgnoreCase) >= 0 && ProgressNavSteps[5].Text.IndexOf((String)menuItems.Rows[5][0], StringComparison.OrdinalIgnoreCase) >= 0
                            && ProgressNavSteps[6].Text.IndexOf((String)menuItems.Rows[6][0], StringComparison.OrdinalIgnoreCase) >= 0)
                            return true;
                    }


                }
                else
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("verifyPrograssNavMenuItems", exception);
            }
            return false;
        }

        public bool currentProgressNavTabValidation(string expectedCurrentNavbar, string navType)
        {
            try
            {
                if (!(testSettings.isMobileExecution || testSettings.isDesktopMobileVersion))
                {
                    if (navType.equalsIgnoreCase("frictionless"))
                    {
                        if (FrictionlessProgressNavCurrentStep.Text.Trim().IndexOf(expectedCurrentNavbar, StringComparison.OrdinalIgnoreCase) >= 0)
                            // && progressNavCurrentStep.GetCssValue("color").Equals("rgb(255, 255, 255)")
                            // && FrictionlessProgressNavCurrentStep.GetCssValue("border-color").Equals("#0e79c4"))
                            return true;
                    }
                    else
                    {
                        if (BuyflowProgressNavCurrentStep.Text.Trim().IndexOf(expectedCurrentNavbar, StringComparison.OrdinalIgnoreCase) >= 0
                       && BuyflowProgressNavCurrentStep.GetCssValue("font-weight").Equals("500"))
                            return true;
                    }
                }
                else
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("currentProgressNavTabValidation", exception);
            }
            return false;
        }

        public bool VerifyCurrentProgressNavStepIsNotClickable()
        {
            try
            {
                IList<IWebElement> ProgressNavSteps = BuyflowProgressNavBar.FindElements(By.CssSelector("li.nav-step"));
                IList<IWebElement> ProgressNavStepsWithLink = BuyflowProgressNavBar.FindElements(By.CssSelector("a.nav-step-link"));

                if (!ProgressNavStepsWithLink.Contains(BuyflowProgressNavCurrentStep))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("VerifyCurrentProgressNavStepIsNotClickable", exception);
            }
            return false;
        }


        public bool allProgressnavTabsStatusAfterAndBeforeCurrentNavTab(string navType)
        {
            try
            {
                if (!(testSettings.isMobileExecution || testSettings.isDesktopMobileVersion))
                {
                    IList<IWebElement> ProgressNavSteps = null;
                    IList<IWebElement> ProgressNavStepsWithLink = null;
                    IWebElement progressNavCurrentStep = null;
                    if (navType.equalsIgnoreCase("frictionless"))
                    {
                        ProgressNavSteps = FrictionlessProgressNavBar.FindElements(By.CssSelector("li.nav-step"));
                        ProgressNavStepsWithLink = FrictionlessProgressNavBar.FindElements(By.CssSelector("a.nav-step-link"));
                        progressNavCurrentStep = FrictionlessProgressNavCurrentStep;
                    }
                    else
                    {
                        ProgressNavSteps = BuyflowProgressNavBar.FindElements(By.CssSelector("li.nav-step"));
                        ProgressNavStepsWithLink = BuyflowProgressNavBar.FindElements(By.CssSelector("a.nav-step-link"));
                        progressNavCurrentStep = BuyflowProgressNavCurrentStep;
                    }

                    int currentProgressnavStepIndicator = ProgressNavSteps.IndexOf(progressNavCurrentStep) + 1;

                    if (currentProgressnavStepIndicator == 1 && progressNavCurrentStep.Text.Equals(ProgressNavSteps[0].Text))
                    {
                        if (ProgressNavStepsWithLink.Count.Equals(0))
                        {
                            report.reportDoneEvent("Active ProgressNav tabs ", "No Active Progressnav Tabs on " + progressNavCurrentStep.Text + " page");
                            return true;
                        }
                    }
                    else if (currentProgressnavStepIndicator == 2 && progressNavCurrentStep.Text.Equals(ProgressNavSteps[1].Text))
                    {
                        if (ProgressNavStepsWithLink.Count.Equals(1) && ProgressNavStepsWithLink[0].Text.Equals(ProgressNavSteps[0].Text))
                        {
                            report.reportDoneEvent("Active ProgressNav tabs on " + progressNavCurrentStep.Text + " Page", ProgressNavSteps[0].Text + " tab");
                            return true;
                        }
                    }
                    else if (currentProgressnavStepIndicator == 3 && progressNavCurrentStep.Text.Equals(ProgressNavSteps[2].Text))
                    {
                        if (ProgressNavStepsWithLink.Count.Equals(2) && ProgressNavStepsWithLink[0].Text.Equals(ProgressNavSteps[0].Text)
                                   && ProgressNavStepsWithLink[1].Text.Equals(ProgressNavSteps[1].Text))
                        {
                            report.reportDoneEvent("Active ProgressNav tabs on " + progressNavCurrentStep.Text + " Page", ProgressNavSteps[0].Text + " and " + ProgressNavSteps[1].Text + " tabs");
                            return true;
                        }
                    }
                    else if (currentProgressnavStepIndicator == 4 && progressNavCurrentStep.Text.Equals(ProgressNavSteps[3].Text))
                    {
                        if (ProgressNavStepsWithLink.Count.Equals(3) && ProgressNavStepsWithLink[0].Text.Equals(ProgressNavSteps[0].Text)
                             && ProgressNavStepsWithLink[1].Text.Equals(ProgressNavSteps[1].Text) && ProgressNavStepsWithLink[2].Text.Equals(ProgressNavSteps[2].Text))
                        {
                            report.reportDoneEvent("Active ProgressNav tabs on " + progressNavCurrentStep.Text + " Page", ProgressNavSteps[0].Text + ", " + ProgressNavSteps[1].Text + " and " + ProgressNavSteps[2].Text + " tabs");
                            return true;
                        }
                    }
                }
                else
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("allProgressnavTabsStatusAfterAndBeforeCurrentNavTab", exception);
            }
            return false;
        }

        public bool clickActiveProgressNavTab(string targetedProgressnavTab)
        {
            try
            {

                IList<IWebElement> ProgressNavStepsWithLink;
                if (testSettings.isDesktopMobileVersion || testSettings.isMobileExecution)
                {

                    ProgressNavStepsWithLink = browser.FindElements(By.CssSelector("a.edit"));
                }
                else
                {
                    ProgressNavStepsWithLink = BuyflowProgressNavBar.FindElements(By.CssSelector("a.nav-step-link"));
                }
                IWebElement requiredProgressNavTab = getRequiredProgressNavTab(ProgressNavStepsWithLink, targetedProgressnavTab);
                if (requiredProgressNavTab != null)
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", requiredProgressNavTab.Location.Y - 150));
                    requiredProgressNavTab.Click();
                    //Thread.Sleep(5000);
                    waitForApplicationToProcess();
                    return true;
                }

            }
            catch (Exception exception)
            {
                report.reportException("clickActiveProgressNavTab", exception);
            }
            return false;
        }

        private IWebElement getRequiredProgressNavTab(IList<IWebElement> ProgressNavStepsWithLink, string targetedProgressnavTab)
        {
            try
            {
                foreach (IWebElement activeProgressNavTab in ProgressNavStepsWithLink)
                {
                    if ((activeProgressNavTab.Text.IndexOf(targetedProgressnavTab, StringComparison.OrdinalIgnoreCase) >= 0) || (activeProgressNavTab.GetAttribute("data-page-name").IndexOf(targetedProgressnavTab, StringComparison.OrdinalIgnoreCase) >= 0))
                        return activeProgressNavTab;
                }
            }
            catch (Exception exception)
            {
                report.reportException("clickActiveProgressNavTab", exception);
            }
            return null;
        }

        public PageNotFound openPageNotFound(string p0)
        {
            try
            {
                browser.Navigate().GoToUrl(p0);
                report.reportDoneEvent("Page not found", "The 404 page not found is displayed");
            }
            catch (Exception exception)
            {
                report.reportException("Page not found", exception);
            }
            return new PageNotFound(browser, report, validations);
        }
        public PageEditor openPageEditorItem(string p0)
        {
            try
            {
                string user = testSettings.CMSUser;
                string password = testSettings.CMSPassword;
                string url = "http://" + user + ":" + password + "@" + testSettings.CMSAppUrl + p0;
                //string url = testSettings.CMSAppUrl.Split('/')[0] + "/" + "/" + testSettings.CMSAppUrl.Split('/')[2] + p0;
                var watch = Stopwatch.StartNew();
                browser.Navigate().GoToUrl(url);
                watch.Stop();
                var elapsedMs = watch.ElapsedMilliseconds;
                report.reportDoneEvent("Page Editor page", "is opened in " + elapsedMs + " Ms");
            }
            catch (Exception exception)
            {
                report.reportException("openPageEditorItem", exception);
            }
            return new PageEditor(browser, report, validations);
        }

        public SiteCore openSiteCore()
        {
            try
            {
                string user = testSettings.CMSUser;
                string password = testSettings.CMSPassword;
                string url = "http://" + user + ":" + password + "@" + testSettings.CMSAppUrl + "/sitecore/admin/ldaplogin.aspx";
                //string url = testSettings.CMSAppUrl.Split('/')[0] + "/" + "/" + testSettings.CMSAppUrl.Split('/')[2] + p0;
                browser.Navigate().GoToUrl(url);
                report.reportDoneEvent("SiteCore", testSettings.CMSAppUrl + " is opened");
            }
            catch (Exception exception)
            {
                report.reportException("openSiteCore", exception);
            }
            return new SiteCore(browser, report, validations);
        }


        public ErrorPage openErrorPage(string p0)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + p0;
                browser.Navigate().GoToUrl(url);
                report.reportDoneEvent("Error Page", "Correct error page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException("Error Page", exception);
            }
            return new ErrorPage(browser, report, validations);
        }

        public SubmitForm openSubmitFormPage(string p0)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + p0;
                browser.Navigate().GoToUrl(url);
                report.reportDoneEvent("Submit Form Page", "Correct error page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException("Error Page", exception);
            }
            return new SubmitForm(browser, report, validations);
        }

        public AdminTool openAdminTool()
        {
            try
            {
                string user = testSettings.CMSUser;
                string password = testSettings.CMSPassword;
                //string url = testSettings.AdminToolUrl;
                string url = "http://" + user + ":" + password + "@" + testSettings.AdminToolUrl;
                browser.Navigate().GoToUrl(url);
                //report.reportDoneEvent("AdminTool", testSettings.AdminToolUrl + "is opened");
                return new AdminTool(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("OpenAdminTool", exception);
            }
            return null;

        }

        public ProductsPage openProductPage(string p0)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + p0;
                browser.Navigate().GoToUrl(url);
                //Thread.Sleep(1000);
                report.reportDoneEvent(p0, "Correct page is displayed");

            }
            catch (Exception exception)
            {
                report.reportException(p0, exception);
            }
            return new ProductsPage(browser, report, validations);
        }

        public void openPaidSearchOffersPage(string appendUrl)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + appendUrl;
                browser.Navigate().GoToUrl(url);
                //Thread.Sleep(1000);
                report.reportDoneEvent(appendUrl, "Correct page is displayed");

            }
            catch (Exception exception)
            {
                report.reportException(appendUrl, exception);
            }
        }

        public bool isPaidSearchLandingPageDisplayed()
        {
            try
            {
                PaidSearchOffersPage paidSearchOffersPage = new PaidSearchOffersPage(browser, report, validations);
                if (!ScenarioContext.Current.ContainsKey("paidSearchOffersPage"))
                    ScenarioContext.Current.Add("paidSearchOffersPage", paidSearchOffersPage);                
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("isPaidSearchLandingPageDisplayed", exception);
            }
            return false;
        }

        public FrictionlessConfirmOrderDetails openFrictionPage(string frictionlessUrl, string flSessionID)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + frictionlessUrl + flSessionID;
                browser.Navigate().GoToUrl(url);
                if (isElementDisplayed(overlayDisplayed) && isElementDisplayed(SpinnerText))
                {
                    waitForApplicationToProcess();
                    return new FrictionlessConfirmOrderDetails(browser, report, validations);
                }
                //Thread.Sleep(1000);
                // report.reportDoneEvent("Frictionless page", "Displayed "+url);

            }
            catch (Exception exception)
            {
                report.reportException("openFrictionPage", exception);
            }
            return null;

        }

        public LeadGenFormPage openLeadGenFormPage(string p0)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + p0;
                browser.Navigate().GoToUrl(url);
                //Thread.Sleep(5000);
                report.reportDoneEvent(p0, "Correct page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException(p0, exception);
            }
            return new LeadGenFormPage(browser, report, validations);
        }

        public DisconnectPage openDisconnectPage(string p0)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + p0;
                browser.Navigate().GoToUrl(url);
                //Thread.Sleep(5000);
                report.reportDoneEvent(p0, "Correct page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException(p0, exception);
            }
            return new DisconnectPage(browser, report, validations);
        }

        public Localization openAutoAddTocartLocalizationPage(string url, string offerID)
        {
            try
            {
                string expectedUrl = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + url + offerID;
                browser.Navigate().GoToUrl(expectedUrl);
                //Thread.Sleep(5000);
                report.reportDoneEvent(url, "Correct page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException(url, exception);
            }
            return new Localization(browser, report, validations);
        }

        public ProductsPage openPage(string env, string p0)
        {
            try
            {
                //if (env.ToLower() == "prodtest")
                //{
                string url = "";
                if (TestSettings.Environment.ToUpper() == "QA" || TestSettings.Environment.ToUpper() == "INT")
                {
                    url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + "prodtest." + testSettings.ApplicationURL.Split('/')[2] + p0;
                }
                else
                {
                    url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + p0;
                }
                browser.Navigate().GoToUrl(url);
                //Thread.Sleep(5000);
                report.reportDoneEvent(p0, "Correct page is displayed");

                //}

            }
            catch (Exception exception)
            {
                report.reportException(p0, exception);
            }
            return new ProductsPage(browser, report, validations);
        }


        public CustomizeVoice openCustomizePage(string url)
        {
            try
            {
                string expectedUrl = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + url;
                browser.Navigate().GoToUrl(expectedUrl);
                //Thread.Sleep(5000);
                report.reportDoneEvent(url, "Correct page is displayed");

            }
            catch (Exception exception)
            {
                report.reportException("openCustomizePage", exception);
            }
            return new CustomizeVoice(browser, report, validations);

        }

        public string openProdTestHomePage(string environment)
        {
            try
            {
                if (environment.IndexOf("BSEE", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    browser.Navigate().GoToUrl(testSettings.ApplicationURL);
                    return "BSEE Home page";
                }
                else if (environment.IndexOf("GatewayLanding", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + "/gateway/landingpage";
                    browser.Navigate().GoToUrl(url);
                    return "GatewayLanding";
                }
                else
                {
                    if (testSettings.ApplicationURL.ToUpper().Contains(".QA") || testSettings.ApplicationURL.ToUpper().Contains(".INT"))
                    {
                        string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + "prodtest." + testSettings.ApplicationURL.Split('/')[2];
                        browser.Navigate().GoToUrl(url);
                        return "Protest Home page";
                    }
                    else if (testSettings.ApplicationURL.ToUpper().Contains(".DEV"))
                        return "Dev environment";
                    else if (testSettings.ApplicationURL.ToUpper().Contains(".STG") || testSettings.ApplicationURL.ToUpper().Contains(".PRD"))
                        return "Stage or prod environemnt";
                }
            }
            catch (Exception exception)
            {
                report.reportException("openProdTestHomePage", exception);
            }
            return null;

        }

        public BusinessInternetPage openBusinessInternetPage(string pageURL)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + pageURL;
                browser.Navigate().GoToUrl(url);
                report.reportDoneEvent(pageURL, "Correct page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException(pageURL, exception);
            }
            return new BusinessInternetPage(browser, report, validations);
        }


        public ProductsPage openBusinessInternetAddonsPage(string URL)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + URL;
                browser.Navigate().GoToUrl(url);
                report.reportDoneEvent(URL, "Correct page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException(URL, exception);
            }
            return new ProductsPage(browser, report, validations);
        }

        public bool isGlobalFooterDesktopViewPresent()
        {
            try
            {

                if (SettingsValue.isMobileExecution || SettingsValue.isDesktopMobileVersion)
                {
                    if (isElementDisplayedInThePage(globalFooterMobileColumns, 1)
                        && isElementDisplayedInThePage(globalFooterSub, 1) &&
                        isElementDisplayedInThePage(globalFooterLegalLink, 1)
                        && isElementDisplayedInThePage(globalFooterLegalText, 1) &&
                        isElementDisplayedInThePage(globalFooterSocialMediaLinks, 1))
                        return true;

                }
                else
                {

                    if (isElementDisplayedInThePage(globalFooterDesktopColumns, 1) &&
                        isElementDisplayedInThePage(globalFooterMobileColumns, 1)
                        && isElementDisplayedInThePage(globalFooterSub, 1) &&
                        isElementDisplayedInThePage(globalFooterLegalLink, 1)
                        && isElementDisplayedInThePage(globalFooterLegalText, 1) &&
                        isElementDisplayedInThePage(globalFooterSocialMediaLinks, 1))
                        return true;
                }

            }
            catch (Exception exception)
            {
                report.reportException("isGlobalFooterDesktopViewPresent", exception);
            }
            return false;
        }

        public bool isBuyflowFooterDesktopViewPresent()
        {
            try
            {
                if (isElementDisplayedInThePage(globalFooterSub, 1) && isElementDisplayedInThePage(globalFooterLegalLink, 1)
                    && isElementDisplayedInThePage(globalFooterLegalText, 1) && isElementDisplayedInThePage(globalFooterSocialMediaLinks, 1))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isBuyflowFooterDesktopViewPresent", exception);
            }
            return false;
        }

        public bool isGlobalFooterMobileViewPresent()
        {
            try
            {
                if (!isElementDisplayedInThePage(globalFooterDesktopColumns, 1) && isElementDisplayedInThePage(globalFooterMobileColumns, 1)
                    && isElementDisplayedInThePage(globalFooterSub, 1) && isElementDisplayedInThePage(globalFooterLegalLink, 1)
                    && isElementDisplayedInThePage(globalFooterLegalText, 1) && isElementDisplayedInThePage(globalFooterSocialMediaLinks, 1))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isGlobalFooterMobileViewPresent", exception);
            }
            return false;
        }

        public bool verifyFooterDesktopNew(string page)
        {
            try
            {
                if (page.IndexOf("global", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    if (isElementDisplayedInThePage(newComcastFooterLogo, 10) && (newCopyrightsLbl.Text.ToUpper().Contains("COMCAST CORPORATION"))
                    && newFooterLinksPanel.Count.Equals(newFooterLinksPanelHeader.Count) && newSocialMediaLnks.Count.Equals(4) && newSubFooterLnks.Count.Equals(6))
                        return true;
                } else
                {
                    if ((newCopyrightsLbl.Text.ToUpper().Contains("COMCAST CORPORATION")) && newSocialMediaLnks.Count.Equals(4) && newSubFooterLnks.Count.Equals(6))
                        return true;
                }
            }
            catch (Exception exception)
            {
                report.reportException("verifyGlobalFooterDesktopNew", exception);
            }
            return false;
        }

        public bool verifyGlobalFooterDesktop()
        {
            try
            {
                //if (globalFooterColumnContainer.Text.Contains("Business") && globalFooterColumnContainer.Text.Contains("Enterprise") &&
                //    globalFooterColumnContainer.Text.Contains("Contact") && globalFooterColumnContainer.Text.Contains("About") && 

                if (!SettingsValue.isMobileExecution && !SettingsValue.isDesktopMobileVersion)
                {
                    if (isElementDisplayedInThePage(globalFooterDesktopLink1, 1) &&
                        isElementDisplayedInThePage(globalFooterMobileLink1, 1)
                        && globalFooterLegalText.Text.Contains("Comcast Corporation"))
                        return true;
                }
                else
                {
                    if (isElementDisplayedInThePage(globalFooterMobileLink1, 1)
                        && globalFooterLegalText.Text.Contains("Comcast Corporation"))
                        return true;
                }


            }
            catch (Exception exception)
            {
                report.reportException("verifyGlobalFooterDetails", exception);
            }
            return false;
        }

        public bool verifyGlobalFooterMobile()
        {
            try
            {
                if (!globalFooterColumnContainer.Text.Contains("Tools") && !globalFooterColumnContainer.Text.Contains("Resource") &&
                    globalFooterColumnContainer.Text.Contains("Contact") && globalFooterColumnContainer.Text.Contains("About") &&
                    !isElementDisplayedInThePage(globalFooterDesktopLink1, 1) && isElementDisplayedInThePage(globalFooterMobileLink1, 1))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("verifyGlobalFooterDetails", exception);
            }
            return false;
        }

        public bool isBuyflowFooterPresent()
        {
            try
            {
                if (isElementPresent(buyflowFooter, "Footer"))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isBuyflowFooterPresent", exception);
            }
            return false;
        }

        public bool verifyBuyflowFooterDetails()
        {
            try
            {
                if (buyflowFooter.Text.Contains("Comcast"))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("verifyBuyflowFooterDetails", exception);
            }
            return false;
        }

        /// <summary>
        /// Validation Error message list
        /// </summary>

        public bool isValidationErrorMessagesListDisplayedAsLinks(Table errorMessagesList)
        {
            //string errorText = "";
            try
            {
                if (overlayDisplayed.GetAttribute("class").Contains("show") && overlayDisplayed.GetAttribute("aria-hidden").Equals("false"))
                {
                    
                    errorsListContainer = browser.FindElement(By.CssSelector("div.overlay-localization > div.form-errors.error-dialog"));
                    errorsListContainerHeader = browser.FindElement(By.CssSelector("div.overlay-localization > div.form-errors.error-dialog  > h3.error-dialog-heading"));
                    errorsList = errorsListContainer.FindElement(By.CssSelector("ul.error-dialog-list"));
                }
                else
                {
                    
                    IList<IWebElement> errorsListContainers = browser.FindElements(By.CssSelector("div.form-errors.error-dialog"));
                   
                    if (errorsListContainers.Count > 1)
                    {
                        foreach (IWebElement errorsListContainerlist in errorsListContainers)
                        {
                            if (isElementPresent(errorsListContainerlist, "errorsListContainerlist"))
                            {
                                errorsListContainer = errorsListContainerlist;                              
                                break;
                            }
                        }

                    }
                    else
                    {
                        errorsListContainer = errorsListContainers[0];
                    }

                    //errorsListContainerHeader = browser.FindElement(By.Id("errorSummaryHeading"));
                    errorsList = errorsListContainer.FindElement(By.CssSelector("ul.error-dialog-list"));
                }
                // IList<IWebElement> validationErrorMessagesList = errorsList.FindElements(By.CssSelector("li[class*='error']"));
                IList<IWebElement> validationErrorMessagesList = errorsList.FindElements(By.XPath("//li[@class='error-dialog--error']"));
                Console.WriteLine("validationErrorMessagesList"+ validationErrorMessagesList.Count);
                if (isElementPresent(errorsListContainer, "Error list Container"))
                //&& isElementPresent(errorsListContainerHeader, "ValidationErrors List"))
                {
                    int matchListCount = 0;
                    //int rowIndex = 0;

                    for (int rowIndex = 0; rowIndex < errorMessagesList.RowCount; rowIndex++)
                    {

                        foreach (IWebElement validationErrorMessage in validationErrorMessagesList)
                        {
                            if (validationErrorMessage.Displayed)
                            {
                                Console.WriteLine("Validation error" + validationErrorMessage.Text);
                                Console.WriteLine((validationErrorMessage.Text.IndexOf((String)errorMessagesList.Rows[rowIndex][0], StringComparison.OrdinalIgnoreCase) >= 0) ||
                               (validationErrorMessage.FindElement(By.XPath(".//a")).GetAttribute("href").IndexOf((String)errorMessagesList.Rows[rowIndex][0], StringComparison.OrdinalIgnoreCase) >= 0));

                                    //This next line will compare your errorMessagesList to either the text of the error or the href.
                                    if ((validationErrorMessage.Text.IndexOf((String)errorMessagesList.Rows[rowIndex][0], StringComparison.OrdinalIgnoreCase) >= 0) ||
                                (validationErrorMessage.FindElement(By.XPath(".//a")).GetAttribute("href").IndexOf((String)errorMessagesList.Rows[rowIndex][0], StringComparison.OrdinalIgnoreCase) >= 0))
                                {
                                    if ((String)errorMessagesList.Rows[rowIndex][0] == "static-ip-radio")
                                    {
                                        if (validationErrorMessage.Text.Contains(staticIPAccordionHeader.Text))
                                        {
                                            report.reportDoneEvent((String)errorMessagesList.Rows[rowIndex][0] + " Validation error message", "\"" + validationErrorMessage.Text + "\"" + " Displayed in the list as link and contains static IP accordion header text");
                                            matchListCount++;
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        Console.WriteLine("at last coming here");
                                        report.reportDoneEvent((String)errorMessagesList.Rows[rowIndex][0] + " Validation error message", "\"" + validationErrorMessage.Text + "\"" + " Displayed in the list as link");
                                        matchListCount++;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                    Console.WriteLine("matchListCount"+ matchListCount);
                    Console.WriteLine("errorMessagesList.RowCount"+ errorMessagesList.RowCount);
                    if (matchListCount == errorMessagesList.RowCount)
                        return true;
                }
            }
            catch (Exception exception)
            {
                //report.reportDoneEvent("failed while looking for error message for " + errorText, "isValidationErrorMessagesListDisplayedAsLinks");
                report.reportException("isValidationErrorMessagesListDisplayedAsLinks", exception);
            }
            return false;
        }

        public bool validationErrorMessageLinkFunctionalityVerification()
        {
            try
            {
                int focussedFieldCount = 0;
                int displayedValidationErrorMessagesCount = 0;

                if (overlayDisplayed.GetAttribute("class").Contains("show") && overlayDisplayed.GetAttribute("aria-hidden").Equals("false"))
                {

                    errorsListContainer = browser.FindElement(By.CssSelector("div.overlay-localization > div.form-errors.error-dialog"));
                    errorsListContainerHeader = browser.FindElement(By.CssSelector("div.overlay-localization > div.form-errors.error-dialog  > h3.error-dialog-heading"));
                    errorsList = errorsListContainer.FindElement(By.CssSelector("ul.error-dialog-list"));
                }
                else
                {
                    IList<IWebElement> errorsListContainers = browser.FindElements(By.CssSelector("div.form-errors.error-dialog"));

                    if (errorsListContainers.Count > 1)
                    {
                        foreach (IWebElement errorsListContainerlist in errorsListContainers)
                        {
                            if (isElementPresent(errorsListContainerlist, "errorsListContainerlist"))
                            {
                                errorsListContainer = errorsListContainerlist;
                                break;
                            }
                        }

                    }
                    else
                    {
                        errorsListContainer = errorsListContainers[0];
                    }
                }


                errorsList = errorsListContainer.FindElement(By.CssSelector("ul.error-dialog-list"));
                IList<IWebElement> validationErrorMessagesList = errorsList.FindElements(By.CssSelector("li.error-dialog--error > a"));
                foreach (IWebElement validationErrorMessage in validationErrorMessagesList)
                {
                    if (validationErrorMessage.Displayed)
                    {
                        displayedValidationErrorMessagesCount++;
                        //Thread.Sleep(1000);
                        waitForElementLoad(validationErrorMessage, 120);
                        ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", validationErrorMessage.Location.Y - 150));
                        //Thread.Sleep(800);
                        //waitForElementLoad(validationErrorMessage, 5);
                        validationErrorMessage.Click();
                        //Thread.Sleep(12000);
                        Thread.Sleep(2000);
                        IWebElement activeElement = browser.SwitchTo().ActiveElement();
                        //report.reportDoneEvent("current field", activeElement.GetAttribute("id"));
                        //report.reportDoneEvent("current field", validationErrorMessage.GetAttribute("href")); 
                        if (validationErrorMessage.GetAttribute("href").Contains("#BusinessServices"))
                        {
                            IList<IWebElement> lobBusinessServices = lobBusinessServicesCheckBoxGroup.FindElements(By.CssSelector("label > input"));
                            if (activeElement.GetAttribute("Id").Equals(lobBusinessServices[0].GetAttribute("Id")))
                            {
                                focussedFieldCount++;
                                report.reportDoneEvent("Validation error message", activeElement.GetAttribute("Id"));
                            }
                        }
                        else if (validationErrorMessage.GetAttribute("href").Contains("#BusinessSize"))
                        {
                            IList<IWebElement> lobBusinessServices = lobBusinessSizeRadioButtonGroup.FindElements(By.CssSelector("label > input"));
                            if (activeElement.GetAttribute("Id").Equals(lobBusinessServices[0].GetAttribute("Id")))
                            {
                                focussedFieldCount++;
                                report.reportDoneEvent("Validation error message", activeElement.GetAttribute("Id"));
                            }
                        }

                        else if (validationErrorMessage.GetAttribute("href").Contains(activeElement.GetAttribute("name")) && activeElement.GetAttribute("name").Equals("static-ip-radio"))
                        {
                            Thread.Sleep(1000);
                            IList<IWebElement> staticIPRadioBtns = staticIpOptionsSection.FindElements(By.TagName("input"));
                            if (activeElement.GetAttribute("Id") == staticIPRadioBtns[0].GetAttribute("Id"))
                            {
                                focussedFieldCount++;
                                report.reportDoneEvent("Validation error message", activeElement.GetAttribute("Id"));
                            }
                        }

                        else if (validationErrorMessage.GetAttribute("href").Contains("5"))
                        {
                            IList<IWebElement> lobBusinessServices = staticIp5CheckboxSelection.FindElements(By.CssSelector("div"));
                            foreach (IWebElement justificationCheckbox in lobBusinessServices)
                            {
                                if (isElementPresent(justificationCheckbox, "justificationCheckbox") && (justificationCheckbox.Displayed) && (justificationCheckbox.Enabled))
                                    return true;
                            }

                            //if (activeElement.GetAttribute("input").Equals(lobBusinessServices[1].GetAttribute("Id")))
                            //focussedFieldCount++;
                        }
                        else if (validationErrorMessage.GetAttribute("href").Contains("13"))
                        {
                            IList<IWebElement> lobBusinessServices = staticIp13CheckboxSelection.FindElements(By.CssSelector("div"));
                            foreach (IWebElement justificationCheckbox in lobBusinessServices)
                            {
                                if (isElementPresent(justificationCheckbox, "justificationCheckbox") && (justificationCheckbox.Displayed) && (justificationCheckbox.Enabled))
                                    return true;
                            }

                            //if (activeElement.GetAttribute("input").Equals(lobBusinessServices[1].GetAttribute("Id")))
                            //focussedFieldCount++;
                        }
                        else if (validationErrorMessage.GetAttribute("href").IndexOf(activeElement.GetAttribute("Id")) >= 0)
                        {
                            focussedFieldCount++;
                            report.reportDoneEvent("Validation error message", validationErrorMessage.GetAttribute("href"));
                        }
                        else if (validationErrorMessage.GetAttribute("href").Contains(((activeElement.FindElement(By.XPath(".."))).FindElement(By.TagName("input"))).GetAttribute("name")))
                        {
                            focussedFieldCount++;
                            report.reportDoneEvent("Validation error message", validationErrorMessage.GetAttribute("href"));
                        }
                        else
                            break;
                    }
                    else
                        report.reportDoneEvent(validationErrorMessage.Text, "is not displayed");
                    ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", errorsList.Location.Y - 50));
                }
                if (focussedFieldCount == displayedValidationErrorMessagesCount)
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("validationErrorMessageLinkFunctionalityVerification", exception);
            }
            return false;
        }

        public bool isValidationErrorMessagesListBoxDisplayed()
        {
            try
            {
                if (isElementPresent(errorsListContainer, "Error list Container") == false && errorsListContainer.GetAttribute("style").Contains("none"))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isValidationErrorMessagesListBoxDisplayed", exception);
            }
            return false;
        }

        public bool refereshthePage()
        {
            try
            {
                browser.Navigate().Refresh();
                Thread.Sleep(1000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("refereshthePage", exception);
            }
            return false;
        }


        public bool isUtilityNavigationLinksSupportNumberDisplayed(string input)
        {
            TestSettings SettingsValue = new TestSettings();
            if (SettingsValue.isMobileExecution || SettingsValue.isDesktopMobileVersion)
            {
                //Thread.Sleep(3000);
                btnBreadcrum.Click();
                Thread.Sleep(3000);
            }
            try
            {
                if (!isElementDisplayed(globalHeaderNew))
                {
                    if (input == "utility_nav_links")
                    {
                        IList<IWebElement> utilityNavigation = utilityNavigationLinks.FindElements(By.CssSelector("div.utility-nav-right > ul > li"));
                        foreach (IWebElement options in utilityNavigation)
                        {
                            IWebElement utilityNavLink = options.FindElement(By.CssSelector("a"));
                            {
                                isElementPresent(utilityNavLink, "Utility Navigation Link");
                                //  utilityNavLink.GetAttribute("href").Contains("comcast.com");
                            }
                        }
                        return true;
                    }

                    else if (input == "support_number")
                    {
                        if (isElementPresent(utilityNavSuportNumber, " Utility Navigation Support Number"))
                            //&& isElementPresent(utilityNavPhoneIcon, " Utility Navigation Phone Icon for support number"))
                            return true;

                    }
                }
                else
                {
                    return true;
                }

            }
            catch (Exception exception)
            {
                report.reportException("isUtilityNavigationLinksDisplayed", exception);
            }
            return false;


        }


        public bool isGlobalSignInLinkDisplayed()
        {
            try
            {
                isElementPresent(globalSignInLink, "Sign in Link");
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("isGlobalSignInLinkDisplayed", exception);
            }
            return false;
        }



        public bool isGlobalNavLogoPresent()
        {
            try
            {
                isElementPresent(globalNavLogo, "Global Nav Link");
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("isGlobalNavLogoPresent", exception);
            }
            return false;

        }


        public bool isGlobalNavigationLinksPresent()
        {
            try
            {
                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    if (!(isElementPresent(globalHeaderNavigationMenu)))
                        btnBreadcrum.Click();
                    Thread.Sleep(2000);
                    {
                        btnCloseBreadCrum.Click();
                        Thread.Sleep(2000);
                    }
                    return true;
                }


                else if (isElementPresent(globalNavSmallMediumBusiness, "Global Navigation Products and Services")
                 && isElementPresent(globalNavEnterprise, "Global Navigation Business Solutions")
                    && isElementPresent(globalNavOfferBundles, "Global Navigation Offers and Bundles"))
                    return true;

                else if (isElementPresent(globalNavShopLink, "globalNavShopLink") && isElementPresent(globalNavSupportLink, "globalNavSupportLink") &&
             isElementPresent(globalNavMyAccountLink, "globalNavMyAccountLink") && isElementPresent(internetL2Nav, "internetL2Nav") &&
             isElementPresent(phoneL2Nav, "phoneL2Nav") && isElementPresent(tvL2Nav, "tvL2Nav") &&
             isElementPresent(cloudSolL2Nav, "cloudSolL2Nav") && isElementPresent(bundlesL2Nav, "bundlesL2Nav"))
                    return true;
               

            }
            catch (Exception exception)
            {
                report.reportException("isGlobalNavigationLinksPresent", exception);
            }
            return false;

        }
        /// Number of employes radio option
        /// </summary>
        public bool isNumberOfPeopleRadioOptionIsDisplayed(Table numberOfPeopleOptions)
        {
            try
            {
                if ((isElementPresent(numberOfPeopleHeader, "Number of People Section Header")) && (!numberOfPeopleHeader.Text.Equals(""))
                    && (fourRadioOptions.Count.Equals(4)) && (!fourRadioOptions[0].Text.Equals("")) && (!fourRadioOptions[1].Text.Equals("")) && (!fourRadioOptions[2].Text.Equals("")) && (!fourRadioOptions[3].Text.Equals("")))
                    return true;
                else
                    return false;
            }
            catch (Exception exception)
            {
                report.reportException("isNumberOfPeopleRadioOptionIsDisplayed", exception);
            }
            return false;
        }
        public bool isSalesRepresentativeSectionDisplayed()
        {
            try
            {

                if ((isElementPresent(salesRepSection, "salesRepSection")) &&
                    (isElementPresent(salesRepHeader, "salesRepHeader")) &&
                    (isElementPresent(salesRepFirstName, "salesRepFirstName")) &&
                    (isElementPresent(salesRepLastName, "salesRepLastName")))
                    return true;
                else
                    return false;
            }
            catch (Exception exception)
            {
                report.reportException("isSalesRepresentativeSectionDisplayed", exception);
            }
            return false;
        }

        public bool clickOnRequiredRadioOption(string numberOfPeople)
        {
            try
            {
                switch (numberOfPeople)
                {
                    case ("1-7"):
                        radioOption[0].Click();
                        break;
                    case ("8-20"):
                        radioOption[2].Click();
                        break;
                    case ("21-50"):
                        radioOption[1].Click();
                        break;
                    case (">50"):
                        radioOption[3].Click();
                        break;
                }

                report.reportDoneEvent("clickOnRequiredRadioOption", numberOfPeople);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickOnRequiredRadioOption", exception);
            }
            return false;
        }


        /// <summary>
        /// Shopping cart indicator methods
        /// </summary>
        /// <returns></returns>

        public bool shoppingCartIndicatorIsNotDisplayedonGlobalHeader()
        {
            try
            {
                
               
              if(!(isElementExistsInPage(By.CssSelector("div.header-navigation-cart > span > button"),5)))
               return true;
            }
            catch (Exception exception)
            {
                report.reportException("shoppingCartIndicatorIsDisplayedonGlobalHeader", exception);
            }
            return false;
        }

        public bool isShoppingCartIndicatorDisplayedonGlobalHeader(string productPage)
        {
            try
            {
                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    if (shoppingCartOfferCountMobile.GetAttribute("aria-hidden").Equals("true") && shoppingCartIndicatorLnkMobile.GetAttribute("class").IndexOf("disabled", StringComparison.OrdinalIgnoreCase) < 0 && shoppingCartIndicatorAccessibilityTxtMobile.Enabled)
                        return true;
                }
                else if (productPage.Equals("Business Internet product") || (productPage.Equals("Thank You")))
                {
                    Console.WriteLine(shoppingCartOfferCount.GetAttribute("aria-hidden").Equals("true"));
                    Console.WriteLine(!shoppingCartIndicatorLnk.Text.Equals("0"));
                    if (shoppingCartOfferCount.GetAttribute("aria-hidden").Equals("true") && (!shoppingCartIndicatorLnk.Text.Equals("0")))
                        return true;
                }
                else if (productPage.Equals("Voice double play Landing"))
                {
                    if (shoppingCartOfferCountInVoiceDoublePlayPP.GetAttribute("aria-hidden").Equals("true") && shoppingCartIndicatorLnkInVoiceDoublePlayPP.GetAttribute("class").IndexOf("disabled", StringComparison.OrdinalIgnoreCase) < 0)
                        //&& shoppingCartIndicatorAccessibilityTxt.Enabled)
                        return true;
                }
                else if (productPage.Equals("TV Offices"))
                {
                    if (shoppingCartOfferCountInVoiceDoublePlayPP.GetAttribute("aria-hidden").Equals("true") && shoppingCartIndicatorLnkInVoiceDoublePlayPP.GetAttribute("class").IndexOf("disabled", StringComparison.OrdinalIgnoreCase) < 0)
                        //&& shoppingCartIndicatorAccessibilityTxt.Enabled)
                        return true;
                }

            }
            catch (Exception exception)
            {
                report.reportException("isShoppingCartIndicatorDisplayedonGlobalHeader", exception);
            }
            return false;
        }

        public bool isMiniShoppingCartDisplayedinVoiceDoublePlayPage()
        {
            try
            {
                if (isElementPresent(miniCartSummaryInVoiceDoublePlayPage))
                    return true;

            }
            catch (Exception exception)
            {
                report.reportException("isMiniShoppingCartDisplayedinVoiceDoublePlayPage", exception);
            }
            return false;
        }

        public bool isMiniShoppingCartDisplayed()
        {
            try
            {
                if (testSettings.isDesktopMobileVersion || testSettings.isMobileExecution)
                {
                    if (isElementPresent(miniCartSummaryMobile))
                        return true;
                }
                else
                {
                    if (isElementPresent(miniCartSummary))
                        return true;
                }
            }
            catch (Exception exception)
            {
                report.reportException("isShoppingCartIndicatorDisplayedonGlobalHeader", exception);
            }
            return false;
        }

        public bool clickOnShoppingCartIndicator()
        {
            try
            {
                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", shoppingCartIndicatorLnkMobile);
                    shoppingCartIndicatorLnkMobile.Click();
                }
                else
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", shoppingCartIndicatorLnk);
                    shoppingCartIndicatorLnk.Click();
                }
                Thread.Sleep(2000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickOnShoppingCartIndicator", exception);
            }
            return false;
        }


        public bool clickOnShoppingCartIndicator(string productPage)
        {
            try
            {
                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", shoppingCartIndicatorLnkMobile);
                    shoppingCartIndicatorLnkMobile.Click();
                }
                else
                {
                    if (productPage.Equals("in the page"))
                    {
                        ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", shoppingCartIndicatorLnk);
                        shoppingCartIndicatorLnk.Click();
                    }
                    else if (productPage.Equals("in Voice Double Play Plans and Pricing Page"))
                    {
                        ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", shoppingCartIndicatorLnkInVoiceDoublePlayPP);
                        shoppingCartIndicatorLnkInVoiceDoublePlayPP.Click();

                    }
                }
                Thread.Sleep(2000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickOnShoppingCartIndicator", exception);
            }
            return false;

        }


        public LeadGenFormPage changeOptionAnswerID(string changeOption, AddressDetails address)
        {
            try
            {
                if (changeOption.IndexOf("IP", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    IList<IWebElement> customizeOptionsNameList = browser.FindElements(By.CssSelector("span.radio-option-name"));

                    foreach (IWebElement customizeOptionsName in customizeOptionsNameList)
                    {
                        if (customizeOptionsName.Text.IndexOf(changeOption, StringComparison.OrdinalIgnoreCase) >= 0)
                        {
                            IWebElement customizeOption = customizeOptionsName.FindElement(By.XPath("../../../.."));
                            IWebElement customizeOptionRadioBtn = customizeOption.FindElement(By.CssSelector("input[type='radio']"));

                            ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].setAttribute('data-answer-id', 'invalidAnswerID')", customizeOptionRadioBtn);
                            ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", customizeOptionRadioBtn.Location.Y - 250));
                            //ShortSleep();
                            customizeOptionRadioBtn.FindElement(By.XPath("..")).FindElement(By.CssSelector("span.icon-radio-image")).Click();
                            return new LeadGenFormPage(browser, report, validations);
                        }
                    }
                }

            }
            catch (Exception exception)
            {
                report.reportException("changeOfferIDorCustomizeOptionAnswerID", exception);
            }
            return null;
        }

        public bool clickGlobalnavLink(string globalNavLink)
        {
            try
            {
                if (testSettings.isDesktopMobileVersion || testSettings.isMobileExecution)
                {                   

                    if ((isElementPresent(newDeviceHeader)) && (!(isElementPresent(newDeviceHeaderTabs))))

                    {
                        if (isElementPresent(newDeviceHeaderMenu))
                        {
                            newDeviceHeaderMenu.Click();
                            waitForElementLoad(newDeviceHeaderTabs, 1);
                            //Thread.Sleep(2000);

                        }
                        
                    }

                    else if (!(isElementPresent(globalHeaderNavigationMenu)))

                    {
                        btnBreadcrum.Click();
                        Thread.Sleep(2000);
                    }

                }

                {
                    if (globalNavLink.IndexOf("SmallMediumBusinessServices", StringComparison.OrdinalIgnoreCase) >= 0)
                    {
                        if (isElementPresent(globalNavSmallMediumBusiness, "Global Nav Small Medium Business Services Link"))
                        {
                            //globalNavSmallMediumBusiness.Click();
                            globalHeaderNavigationMenu.FindElement(By.XPath(".//li[1]/button")).Click();
                            return true;
                        }
                    }
                    else if (globalNavLink.IndexOf("Enterprise", StringComparison.OrdinalIgnoreCase) >= 0)
                    {
                        isElementPresent(globalNavEnterprise, "Global Nav Enterprise");
                        globalHeaderNavigationMenu.FindElement(By.XPath(".//li[2]/button")).Click();
                        //globalNavEnterprise.Click();
                        return true;
                    }
                    else if (globalNavLink.IndexOf("Offers&Bundles", StringComparison.OrdinalIgnoreCase) >= 0)
                    {
                        isElementPresent(globalNavOfferBundles, "Global Nav Resource Library");
                        globalHeaderNavigationMenu.FindElement(By.XPath(".//li[3]/button")).Click();
                        //globalNavOfferBundles.Click();
                        return true;
                    }

                    else if (globalNavLink.IndexOf("Shop", StringComparison.OrdinalIgnoreCase) >= 0)
                    {

                        IWebElement globalNavL1Links = browser.FindElement(By.CssSelector("ul.bsp-header-level1-nav-list>li"));
                        if (isElementPresent(globalNavL1Links, "globalNavL1Links"))
                        {
                            waitForElementToBeClickable(globalNavShopLink, 5);
                            globalNavShopLink.Click();
                            waitForApplicationToProcess();
                            return true;
                        }
                        
                    }

                }
            }

            catch (Exception exception)
            {
                report.reportException("clickGlobalnavLink", exception);
            }
            return false;

        }

        public bool subMenuLinksDisplayed(string globalNavLink)
        {

            try
            {
                bool result = false;
                //Thread.Sleep(5000);


                if (globalNavLink == "SmallMediumBusinessServices")
                {
                    if (globalNavSmallMediumBusiness.GetAttribute("class").Contains("active"))

                        if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                        {
                            IWebElement firstLinkListMobile = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(2) > button.heading.h3.button-anchor"));
                            IWebElement secondLinkListMobile = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(3) > button.heading.h3.button-anchor"));
                            IWebElement thirdLinkListMobile = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(4) > button.heading.h3.button-anchor"));

                            if (isElementPresent(firstLinkListMobile, "Internet Link List")
                                      && isElementPresent(secondLinkListMobile, "Phone Link List")
                                      && isElementPresent(thirdLinkListMobile, "TV Link List"))
                                result = true;
                        }
                        else
                        {
                            {
                                //IWebElement primaryBlockHeading = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-link-block-item--primary > a.heading.h3"));
                                //IWebElement primaryBlockDescription = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-link-block-item--primary > p.sub-nav-group-text"));
                                //IWebElement secondaryBlockHeading = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-link-block-item--secondary > a.heading.h3"));
                                //IWebElement secondaryBlockDescription = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-link-block-item--secondary > p.sub-nav-group-text"));
                                IWebElement firstLinkList = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(2) > a.heading.h3.button-anchor"));
                                IWebElement secondLinkList = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(3) > a.heading.h3.button-anchor"));
                                IWebElement thirdLinkList = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(4) > a.heading.h3.button-anchor"));

                                //if (isElementPresent(primaryBlockHeading, "Primary Block Heading")
                                //      && isElementPresent(primaryBlockDescription, "Primary Block Description")
                                //      && isElementPresent(secondaryBlockHeading, "secondary Block Heading")
                                //      && isElementPresent(secondaryBlockDescription, "secondary Block Description")
                                if (isElementPresent(firstLinkList, "Internet Link List")
                                      && isElementPresent(secondLinkList, "Phone Link List")
                                      && isElementPresent(thirdLinkList, "TV Link List"))
                                    result = true;

                            }
                        }
                }


                else if (globalNavLink == "Enterprise" && globalNavEnterprise.GetAttribute("class").Contains("active"))
                {

                    if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                    {
                        IWebElement primaryBlockHeadingMobile = globalNavEnterprise.FindElement(By.CssSelector(" div > div.sub-nav-section > div > div.sub-nav-link-block.width--25-percent > div.sub-nav-link-block-item--primary"));
                        IWebElement secondaryBlockHeadingMobile = globalNavEnterprise.FindElement(By.CssSelector("div.sub-nav-link-block-item--secondary"));

                        if (isElementPresent(primaryBlockHeadingMobile, "Primary Block Heading")
                         && isElementPresent(secondaryBlockHeadingMobile, "secondary Block Heading"))

                            result = true;
                    }
                    else
                    {
                        IWebElement primaryBlockHeading = globalNavEnterprise.FindElement(By.CssSelector(" div > div.sub-nav-section > div > div.sub-nav-link-block.width--25-percent > div.sub-nav-link-block-item--primary > a.heading.h3"));
                        IWebElement primaryBlockDescription = globalNavEnterprise.FindElement(By.CssSelector("div.sub-nav-link-block-item--primary > p.sub-nav-group-text"));
                        IWebElement secondaryBlockHeading = globalNavEnterprise.FindElement(By.CssSelector("div.sub-nav-link-block-item--secondary > a.heading.h3"));
                        IWebElement secondaryBlockDescription = globalNavEnterprise.FindElement(By.CssSelector("div.sub-nav-link-block-item--secondary > p.sub-nav-group-text"));
                        //IWebElement firstLinkList = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(2) > ul > li.link-list-heading-item > a.sub-nav-list-anchor"));
                        //IWebElement secondLinkList = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(3) > ul > li.link-list-heading-item > a.sub-nav-list-anchor"));
                        //IWebElement thirdLinkList = globalHeaderNavigationMenu.FindElement(By.CssSelector("div.sub-nav-section > div > div:nth-child(4) > ul > li.link-list-heading-item > a.sub-nav-list-anchor"));


                        if (isElementPresent(primaryBlockHeading, "Primary Block Heading")
                         && isElementPresent(primaryBlockDescription, "Primary Block Description")
                         && isElementPresent(secondaryBlockHeading, "secondary Block Heading")
                         && isElementPresent(secondaryBlockDescription, "secondary Block Description"))
                            //&& isElementPresent(firstLinkList, "Enterprise")
                            //&& isElementPresent(secondLinkList, "Manage Enterprise Solutions")
                            //&& isElementPresent(thirdLinkList,"Phone"))   

                            result = true;
                    }


                }


                else if (globalNavLink == "Offers&Bundles" && globalNavOfferBundles.GetAttribute("class").Contains("active"))
                {
                    if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                    {


                        if (isElementPresent(startShoppingMobile, "startShoppingMobile")
                         && isElementPresent(resourceLibraryMobile, "resourceLibraryMobile"))

                            result = true;
                    }

                    else
                    {
                        IWebElement primaryOfferBlock = globalNavOfferBundles.FindElement(By.CssSelector("div.sub-nav-offer-block"));
                        IWebElement primaryOfferBlockHeader = primaryOfferBlock.FindElement(By.CssSelector("span.heading.h3"));
                        IWebElement primaryOfferBlockDescription = primaryOfferBlock.FindElement(By.CssSelector("div.sub-nav-group-text.rich-text"));
                        IWebElement primaryOfferBlockPrice = primaryOfferBlock.FindElement(By.CssSelector("div.offer > div.price"));
                        IWebElement viewDetailButton = primaryOfferBlock.FindElement(By.CssSelector("div.offer > div.offer-cta"));
                        IWebElement bundleGroup = globalNavOfferBundles.FindElement(By.CssSelector("div.sub-nav-link-block--single.sub-nav-link-block"));
                        IWebElement bundleGroupHeader = bundleGroup.FindElement(By.CssSelector("span.heading.h3"));
                        IWebElement bundleGroupDescription = bundleGroup.FindElement(By.CssSelector("p.sub-nav-group-text"));
                        IWebElement bundleOfferButton = bundleGroup.FindElement(By.CssSelector("div.offer > div.offer-cta > a.button.button-medium.button-color-secondary"));


                        if (isElementPresent(primaryOfferBlock, "Primary Offer Block")
                            && isElementPresent(primaryOfferBlockHeader, "Primary Offer Block Header")
                            && isElementPresent(primaryOfferBlockDescription, "Primary Group Description")
                            && isElementPresent(primaryOfferBlockPrice, "Primary Group Price")
                            && isElementPresent(viewDetailButton, "Primary View Detail Button")
                            && isElementPresent(bundleGroupHeader, "Bundle Group Header")
                            && isElementPresent(bundleGroupDescription, "Bundle Group description")
                            && isElementPresent(bundleOfferButton, "Bundle Offer Button"))

                            result = true;
                    }

                }
                else if (globalNavLink == "L2")
                {

                    if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                    {
                        IList<IWebElement> l1DeviceTabs = browser.FindElements(By.CssSelector("ul.uninav-mobile-tabs>li"));
                        if (l1DeviceTabs.Count == 3)
                        {
                            IList<IWebElement> tabContents = browser.FindElements(By.CssSelector("div.uninav-mobile-container > div"));
                            var n = 0;
                            foreach (IWebElement tabContent in tabContents)
                            {
                                
                                l1DeviceTabs[n].Click();
                                IWebElement l2Device = tabContent.FindElement(By.XPath("./div[@class='uninav-mobile-top']"));
                                IWebElement dviceSearch = tabContent.FindElement(By.XPath("./div[@class='uninav-mobile-middle']"));
                                IWebElement deviceL0 = tabContent.FindElement(By.XPath("./div[@class='uninav-mobile-bottom']"));

                                if ((isElementPresent(l2Device, "L2Device")) && (isElementPresent(dviceSearch, "dviceSearch")) && (isElementPresent(deviceL0, "dviceSearch")))
                                    {
                                      IList<IWebElement> l2Items = l2Device.FindElements(By.XPath("./ul[@class='l2-nav-primary']/li"));
                                      foreach(IWebElement l2Item in l2Items)
                                      {
                                        if (l2Item.GetAttribute("class").Contains("dropdown-trigger"))
                                        {
                                            IWebElement l2ItemLink = l2Item.FindElement(By.XPath("./a/span"));
                                            {
                                                if ((!(isElementPresent(l2ItemLink, "l2ItemLink"))) && (l2ItemLink.Equals("")))
                                                {
                                                    result = false;
                                                    break;
                                                }

                                                else
                                                {
                                                    l2ItemLink.Click();
                                                    IWebElement backButton = l2Item.FindElement(By.CssSelector("button.back-btn"));
                                                    IList<IWebElement> l2SubNavItems = l2Item.FindElements(By.XPath(".//ul[@class='l2-subnav']/li[@class='l2-subnav-item']/a"));
                                                    foreach (IWebElement l2SubNavItem in l2SubNavItems)
                                                    {
                                                        if (!(l2SubNavItem.GetAttribute("href").Contains("business")))
                                                        {
                                                            IWebElement l2SubNavLinkItem = l2SubNavItem.FindElement(By.XPath("/span"));
                                                            {
                                                                if ((!(isElementPresent(l2SubNavItem, "l2SubNavItem"))) && (l2SubNavLinkItem.Equals("")))
                                                                {
                                                                    result = false;
                                                                    break;
                                                                }
                                                            }

                                                        }
                                                        else if (l2SubNavItem.GetAttribute("href").Contains("business"))
                                                        {
                                                            if ((!(isElementPresent(l2ItemLink, "l2ItemLink"))) && (l2ItemLink.Equals("")))
                                                            {
                                                                result = false;
                                                                break;
                                                            }
                                                        }

                                                    }
                                                        backButton.Click();
                                                        waitForElementLoad(l2Item, 1);
                                                }
                                            }
                                            
                                        }
                                        else
                                        {
                                            IWebElement l2ItemLink = l2Item.FindElement(By.CssSelector("a"));
                                            if (!(isElementPresent(l2ItemLink, "l2ItemLink") && (!(l2ItemLink.GetAttribute("href").Equals("")))))
                                            {
                                                result = false;
                                                break;
                                            }
                                        }
                                     }
                                            
                                   }n++;
                            }result = true;
                         }

                      }

                   else
                    {
                        IList<IWebElement> l2Items = globalNavL2Links.FindElements(By.CssSelector("div.l2-nav>div>ul>li"));
                        var linkscount = 0;
                        foreach (IWebElement l2Item in l2Items)
                        {
                            if (l2Item.GetAttribute("class").Contains("dropdown-trigger"))
                            {
                                IWebElement l2ItemLink = l2Item.FindElement(By.CssSelector("a>span"));
                                if (isElementPresent(l2ItemLink, "l2ItemLink") && (!(l2ItemLink.Equals(""))))
                                {
                                    Actions builder = new Actions(browser);
                                    builder.MoveToElement(l2ItemLink).Build().Perform();
                                    Thread.Sleep(1500);
                                    IList<IWebElement> l2SubnavLinks = l2Item.FindElements(By.CssSelector("ul>li"));
                                    foreach (IWebElement l2SubnavLink in l2SubnavLinks)
                                    {
                                        IWebElement l2Subnav = l2SubnavLink.FindElement(By.CssSelector("a"));
                                        if ((l2Subnav.Text.Equals("")) || (l2Subnav.GetAttribute("href").Equals("")))
                                        {
                                            result = false;
                                            break;
                                        }
                                    }
                                }
                                linkscount++;
                            }
                            else
                            {
                                IWebElement l2ItemLink = l2Item.FindElement(By.CssSelector("a"));
                                if (isElementPresent(l2ItemLink, "l2ItemLink") && (!(l2ItemLink.GetAttribute("href").Equals(""))))
                                    linkscount++;
                            }

                            if ((linkscount == l2Items.Count) && (l2Items.Count == 6))
                            {
                                result = true;
                            }

                        }
                    }

                    return result;
                }
                return result;

            }


            catch (Exception exception)
            {
                report.reportException("subMenuLinksDisplayed", exception);
                return false;
            }
                return false;

            }
        public bool subNavInlineLinksDisplayed(string globalNavLink)
        {

            try
            {
                bool result = false;

                if (globalNavSmallMediumBusiness.GetAttribute("class").Contains("active"))
                {

                    IList<IWebElement> helperNavMenuLinks = globalHeaderNavigationMenu.FindElements(By.CssSelector("div.header-navigation-menu > ul > li:nth-child(1) > div > div.helper-nav > div > ul.sub-nav-list-inline > li"));

                    foreach (IWebElement options in helperNavMenuLinks)
                    {
                        IWebElement helperNavLink = options.FindElement(By.CssSelector("a.link-icon"));
                        isElementPresent(helperNavLink, "Helper Navigation links");
                    }
                    result = true;
                }

                if (globalNavEnterprise.GetAttribute("class").Contains("active"))
                {
                    IList<IWebElement> helperNavMenuLinks = globalHeaderNavigationMenu.FindElements(By.CssSelector("div.header-navigation-menu > ul > li:nth-child(2) > div > div.helper-nav > div > ul.sub-nav-list-inline > li"));

                    foreach (IWebElement options in helperNavMenuLinks)
                    {
                        IWebElement helperNavLink = options.FindElement(By.CssSelector("a.link-icon"));
                        isElementPresent(helperNavLink, "Helper Navigation links");

                    }
                    result = true;
                }


                if (globalNavOfferBundles.GetAttribute("class").Contains("active"))
                {

                    IList<IWebElement> helperNavMenuLinks = globalHeaderNavigationMenu.FindElements(By.CssSelector("div.header-navigation-menu > ul > li:nth-child(3) > div > div.helper-nav > div > ul.sub-nav-list-inline > li"));

                    foreach (IWebElement options in helperNavMenuLinks)
                    {
                        IWebElement helperNavLink = options.FindElement(By.CssSelector("a.link-icon"));

                        isElementPresent(helperNavLink, "Helper Navigation links");

                    }
                    result = true;
                }

                return result;
            }

            catch (Exception exception)
            {
                report.reportException("subNavInlineLinksDisplayed", exception);
                return false;
            }

        }



        public ProductsPage selectProductPageLnk(string productPageURL, string globalNavMenuLnk)
        {
            try
            {
                Actions actions = new Actions(browser);

                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    if (globalNavMenuLnk.IndexOf("footer", StringComparison.OrdinalIgnoreCase) >= 0)
                    {
                        ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", globalFooter.Location.Y - 150));
                        IList<IWebElement> footerLnks = globalFooter.FindElements(By.TagName("a"));
                        IWebElement expectedProductPageLnk = getExpectedProductPageLnk(footerLnks, productPageURL);
                        if (isElementPresent(expectedProductPageLnk, "expectedProductPageLnk"))
                        {
                            ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", expectedProductPageLnk.Location.Y - 150));
                            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                            //Thread.Sleep(3000);
                            return new ProductsPage(browser, report, validations);
                        }
                    }
                    else
                    {
                        browser.Navigate().GoToUrl(testSettings.ApplicationURL + productPageURL);
                        //Thread.Sleep(3000);
                        return new ProductsPage(browser, report, validations);
                    }
                }
                else
                {
                    browser.Navigate().GoToUrl(testSettings.ApplicationURL + productPageURL);
                    return new ProductsPage(browser, report, validations);
                }
                //******************************************************Commenting the code untill new or old header remains stable*******************************************//

                //else if (isElementPresent(globalNavL2Links, "globalNavL2Links"))
                //{
                //    if (globalNavMenuLnk.IndexOf("Internet", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        waitForElementLoad(globalNavL2Links, 5);
                //        Actions builder = new Actions(browser);
                //        builder.MoveToElement(internetL2Nav).Build().Perform();
                //        Thread.Sleep(1500);
                //        if (internetL2Nav.GetAttribute("class").Contains("active"))
                //        {
                //            IList<IWebElement> businessInternetLink = internetL2Nav.FindElements(By.CssSelector("ul>li>a"));
                //            IWebElement expectedProductPageLnk = getExpectedProductPageLnk(businessInternetLink, productPageURL);
                //            waitForElementToBeClickable(expectedProductPageLnk, 20);
                //            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                //            //expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }
                //    }

                //    else if (globalNavMenuLnk.IndexOf("Phone", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        waitForElementLoad(globalNavL2Links, 5);
                //        Actions builder = new Actions(browser);
                //        builder.MoveToElement(phoneL2Nav).Build().Perform();
                //        Thread.Sleep(1500);
                //        if (phoneL2Nav.GetAttribute("class").Contains("active"))
                //        {
                //            IList<IWebElement> businessPhoneLink = phoneL2Nav.FindElements(By.CssSelector("ul>li>a"));
                //            IWebElement expectedProductPageLnk = getExpectedProductPageLnk(businessPhoneLink, productPageURL);
                //            waitForElementToBeClickable(expectedProductPageLnk, 20);
                //            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                //            //expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }
                //    }

                //    else if (globalNavMenuLnk.IndexOf("TV", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        waitForElementLoad(globalNavL2Links, 5);
                //        Actions builder = new Actions(browser);
                //        builder.MoveToElement(tvL2Nav).Build().Perform();
                //        Thread.Sleep(1500);
                //        if (tvL2Nav.GetAttribute("class").Contains("active"))
                //        {
                //            IList<IWebElement> businessTVLink = tvL2Nav.FindElements(By.CssSelector("ul>li>a"));
                //            IWebElement expectedProductPageLnk = getExpectedProductPageLnk(businessTVLink, productPageURL);
                //            waitForElementToBeClickable(expectedProductPageLnk, 20);
                //            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                //            //expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }
                //    }
                //}
                //else
                //{
                //    ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", globalHeaderNavigationMenu.Location.Y - 250));
                //    waitForElementToBeClickable(globalHeaderNavigationMenu, 5);

                //    if (globalNavMenuLnk.IndexOf("SmallMediumBusinessServices", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        waitForElementToBeClickable(globalNavSmallMediumBusiness, 20);
                //        actions.MoveToElement(globalNavSmallMediumBusiness).Click().Perform();
                //        Thread.Sleep(1000);

                //        if (globalNavSmallMediumBusiness.GetAttribute("class").Equals("_active"))
                //        {
                //            IList<IWebElement> productServicesLnks = globalNavSmallMediumBusiness.FindElements(By.TagName("a"));
                //            IWebElement expectedProductPageLnk = getExpectedProductPageLnk(productServicesLnks, productPageURL);
                //            waitForElementToBeClickable(expectedProductPageLnk, 20);
                //            expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }
                //    }
                //    else if (globalNavMenuLnk.IndexOf("Enterprise", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        waitForElementToBeClickable(globalNavEnterprise, 5);
                //        actions.MoveToElement(globalNavEnterprise).Click().Perform();
                //        Thread.Sleep(100);
                //        if (globalNavEnterprise.GetAttribute("class").Equals("_active"))
                //        {
                //            IList<IWebElement> businessSolutionsLnks = globalNavEnterprise.FindElements(By.TagName("a"));
                //            IWebElement expectedProductPageLnk = getExpectedProductPageLnk(businessSolutionsLnks, productPageURL);
                //            waitForElementToBeClickable(expectedProductPageLnk, 20);
                //            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                //            //expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }
                //    }
                //    else if (globalNavMenuLnk.IndexOf("OffersandBundles", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        waitForElementToBeClickable(globalNavOfferBundles, 5);
                //        actions.MoveToElement(globalNavOfferBundles).Click().Perform();
                //        Thread.Sleep(100);
                //        if (globalNavOfferBundles.GetAttribute("class").Equals("_active"))
                //        {
                //            IList<IWebElement> offersAndBundlesLnks = globalNavOfferBundles.FindElements(By.TagName("a"));
                //            IWebElement expectedProductPageLnk = getExpectedProductPageLnk(offersAndBundlesLnks, productPageURL);
                //            waitForElementToBeClickable(expectedProductPageLnk, 20);
                //            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                //            //expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }

                //    }
                //    else if (globalNavMenuLnk.IndexOf("footer", StringComparison.OrdinalIgnoreCase) >= 0)
                //    {
                //        ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", globalFooter.Location.Y - 150));

                //        IList<IWebElement> footerLnks = globalFooter.FindElements(By.TagName("a"));
                //        IWebElement expectedProductPageLnk = getExpectedProductPageLnk(footerLnks, productPageURL);
                //        if (isElementPresent(expectedProductPageLnk, "expectedProductPageLnk"))
                //        {
                //            ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", expectedProductPageLnk.Location.Y - 150));
                //            //((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", expectedProductPageLnk);
                //            actions.MoveToElement(expectedProductPageLnk).Click().Perform();
                //            //expectedProductPageLnk.Click();
                //            waitUntilDocumentComplete();
                //            return new ProductsPage(browser, report, validations);
                //        }
                //    }

               
            }
            catch (Exception exception)
            {
                report.reportException("selectProductPageLnk", exception);
            }
            return null;
        }


        private IWebElement getExpectedProductPageLnk(IList<IWebElement> globalNavMenuSubLnks, string productPageURL)
        {
            try
            {
                foreach (IWebElement subMenuLnk in globalNavMenuSubLnks)
                {
                    if (subMenuLnk.GetAttribute("href").IndexOf(productPageURL) >= 0 && isElementPresent(subMenuLnk))
                        return subMenuLnk;
                }
            }
            catch (Exception exception)
            {
                report.reportException("getExpectedProductPageLnk", exception);
            }
            return null;
        }



        public bool pageScrollDownOnMyOrderSummaryCollapsestate()
        {
            try
            {
                ((IJavaScriptExecutor)browser).ExecuteScript("scroll(0, 250);");
                //((IJavaScriptExecutor)browser).ExecuteScript("scrollTo(0, 250);");
                //((IJavaScriptExecutor)browser).ExecuteScript("window.scrollBy(0,250)", "");
                //Actions actions = new Actions(browser);
                //actions.SendKeys(Keys.End).Perform();
                Thread.Sleep(1000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("pageScrollOnMyOrderSummaryCollapsestate", exception);
            }
            return false;
        }


        public bool pageScrollUpOnMyOrderSummaryCollapsestate()
        {
            try
            {
                ((IJavaScriptExecutor)browser).ExecuteScript("scroll(0, -250);");
                //Actions actions = new Actions(browser);
                //actions.SendKeys(Keys.Home).Perform();
                waitForElementLoad(buyflowHeader, 1);
                Thread.Sleep(200);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("pageScrollOnMyOrderSummaryCollapsestate", exception);
            }
            return false;
        }

        public bool stickyNavigation()
        {
            try
            {
                if (isElementPresent(navigationHeader, "Sticky Navigation Header")
                    && navigationHeader.GetAttribute("class").Contains("fixed")
                      && isElementPresent(navigationHeaderLogo, "Navigation Global Nav Logo")
                         && isElementPresent(navigationHeaderRequestConsultationButton, "Navigation Request Consultation Button")
                             && isElementPresent(navigationHeaderSupportNumber, "Navigation Utility Nav Support Number")
                                 && !isElementPresent(navHeaderSearchBoxInput, "Search"))
                    return true;
            }

            catch (Exception exception)
            {
                report.reportException("stickyNavigation", exception);
            }
            return false;
        }

        public bool stickyFooterIsDisplayed()
        {
            try
            {
                if (isElementPresent(blueStickyFooter, "Sticky footer")
                    && blueStickyFooter.GetAttribute("class").Contains("fixed")
                    && isElementPresent(stickyFooterSection, "Sticky footer section")
                    && isElementPresent(locationIcon, "Location icon")
                    && isElementPresent(locationText, "Location text")
                    && isElementPresent(checkAvailabilityFooter, "Check availability")
                    && isElementPresent(phoneNumber, "Phone number")
                    && isElementPresent(getAfreeQuoteButton, "Get a free quote button")
                    )
                    return true;

                else if (isElementPresent(newStickyFooter, "newStickyFooter")
                    && isElementPresent(newStickyFooterSection, "newStickyFooterSection")
                    && isElementPresent(newLocationIcon, "newLocationIcon")
                    && isElementPresent(newLocationText, "newLocationText")
                    && isElementPresent(newCheckAvailabilityFooter, "newCheckAvailabilityFooter")
                    && isElementPresent(newPhoneIcon, "newPhoneIcon")
                    && isElementPresent(newPhoneNumber, "newPhoneNumber")
                    && isElementPresent(newGetAfreeQuoteButton, "newGetAfreeQuoteButton")
                    )
                        return true;


            }

            catch (Exception exception)
            {
                report.reportException("stickyFooter", exception);
            }
            return false;
        }

        public bool stickyFooterIsNotDisplayed()
        {
            try
            {
                if (!isElementPresent(blueStickyFooter, "Sticky footer")||(!isElementPresent(newStickyFooter, "newStickyFooter")))
                    return true;
            }

            catch (Exception exception)
            {
                report.reportException("stickyFooter", exception);
            }
            return false;
        }
        public bool isLocationUpdatedSame()
        {
            try
            {
                if (footerZip.Text.Trim().Equals(headerZip.Text.Trim()))
                    //&& (footerCity.Text.Trim().Equals(headerCity.Text.Trim())))
                    return true;
                else if (newFooterZip.Text.Trim().Equals(newHeaderZip.Text.Trim()))
                    return true;
            }

            catch (Exception exception)
            {
                report.reportException("isLocationUpdatedSame", exception);
            }
            return false;
        }

        public bool isPhoneNumberSame()
        {
            try
            {
                if (isElementPresent(footerPhoneNumber, "footerPhoneNumber"))
                {
                    if (footerPhoneNumber.Text.Trim().Equals(headerPhoneNumber.Text.Trim()))
                    return true;
                }
                    
                else if (isElementPresent(newFooterPhoneNumber, "newFooterPhoneNumber"))
                {
                    if (newFooterPhoneNumber.Text.Trim().Equals(newHeaderPhoneNumber.Text.Trim()))
                    return true;
                }

            }

            catch (Exception exception)
            {
                report.reportException("Phone number", exception);
            }
            return false;
        }
        public bool scrollUp()
        {
            try
            {
                if (isElementPresent(footerPhoneNumber, "footerPhoneNumber"))
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", headerPhoneNumber);
                    Thread.Sleep(1000);
                    return true;
                }
               
               else if (isElementPresent(newFooterPhoneNumber, "newFooterPhoneNumber"))
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", newHeaderPhoneNumber);
                    Thread.Sleep(1000);
                    return true;
                }

            }
            catch (Exception exception)
            {
                report.reportException("scrolling up the page", exception);
            }
            return false;
        }

        public bool productsPageScrollUp()
        {
            try
            {
                Actions actions = new Actions(browser);
                actions.SendKeys(Keys.Home).Perform();
                waitForElementLoad(utilityHeader, 1);
                Thread.Sleep(200);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("pageScrollOnMyOrderSummaryCollapsestate", exception);
            }
            return false;
        }

        public bool stickyNavHeaderNotDisplayed()
        {
            try
            {
                if (!navigationHeader.GetAttribute("class").Contains("fixed")
                     && isElementPresent(globalNavLogo, "Global Nav Logo")
                         && isElementPresent(requestConsultationButton, "Request Consultation Button"))
                    //&& isElementPresent(utilityNavSuportNumber, "Utility Nav Support Number"))
                    //&& isElementPresent(navHeaderSearchBoxInput, "Search"))
                    return true;

            }

            catch (Exception exception)
            {
                report.reportException("stickyNavHeaderNotDisplayed", exception);
            }
            return false;
        }

        public bool clickOnSearchIcon(string searchType)
        {
            try
            {
                if(searchType.equalsIgnoreCase("old"))
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", navHeaderSearchIcon);
                    navHeaderSearchIcon.Click();
                    waitForElementToBeClickable(navHeaderSearchBoxInput, 2);

                    return true;

                }
                else if(searchType.equalsIgnoreCase("new"))
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", newNavHeaderSearchIcon);
                    newNavHeaderSearchIcon.Click();
                    waitForElementLoad(searchOverlay, 3);
                    if (isElementPresent(searchOverlayCloseButton, "searchOverlayCloseButton") && isElementPresent(searchOverlaySubmitButton, "searchOverlaySubmitButton")
                        && isElementPresent(newNavSearchInputBox, "newNavSearchInputBox"))
                      return true;

                }
              
            }
            catch (Exception exception)
            {
                report.reportException("clickOnSeachIcon", exception);
            }
            return false;
        }



        public bool searchFor(string searchText, string searchMethod)
        {
            try
            {
                string originalTab = browser.WindowHandles[0];
                if (navHeaderSearchBoxInput.Displayed)
                {
                    navHeaderSearchBoxInput.Click();
                    navHeaderSearchBoxInput.SendKeys(searchText);
                    if (navHeaderSearchBoxInput.GetAttribute("value").Equals(searchText))
                    {
                        validations.assertionPass("Searching", searchText + " was entered successfully into the search text box");
                        if (searchMethod.ToLower() == "search button")
                        {
                            if (isElementPresent(searchButton, "searchButton"))
                            {
                                searchButton.Click();
                            }
                            else
                            {
                                searchButtonBCP.Click();
                            }
                        }
                        else
                            navHeaderSearchBoxInput.SendKeys(Keys.Enter);
                        Thread.Sleep(3000);
                        //waitForElementLoad(globalSearchResults, 10);
                        //string searchTab = browser.WindowHandles[1];
                        //browser.SwitchTo().Window(searchTab);

                        //if (globalSearchResults.Text.Contains("results for " + searchText))
                        ////{
                        ////    validations.assertionPass("Searching", "Successfully searched for " + searchText);
                        ////    //browser.Close();
                        ////    //browser.SwitchTo().Window(originalTab);
                        ////    navHeaderSearchBoxInput.SendKeys(Keys.Tab);//to tab out of the search box.
                        ////}
                        //else
                        //    validations.assertionFailed("Searching", searchText + " text was not found");
                    }
                    else if (newNavSearchInputBox.Displayed)
                    {

                        newNavSearchInputBox.Click();
                        newNavSearchInputBox.SendKeys(searchText);
                        if (newNavSearchInputBox.GetAttribute("value").Equals(searchText))
                        {
                            validations.assertionPass("Searching", searchText + " was entered successfully into the New search text box");
                            if (searchMethod.ToLower() == "search button")
                            {
                                if (isElementPresent(searchOverlaySubmitButton, "searchButton"))
                                {
                                    searchOverlaySubmitButton.Click();
                                }
                                else
                                {
                                    searchButtonBCP.Click();
                                }
                            }
                            else
                                newNavSearchInputBox.SendKeys(Keys.Enter);
                            Thread.Sleep(3000);
                        }
                    }
                    else
                    {
                        validations.assertionFailed("Searching", searchText + " was not entered into the New search text box");
                        return false;
                    }
                }
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("searchFor", exception);
            }
            return false;
        }



        /// <summary>
        /// Advitrack numbers validation methods
        /// </summary>

        public bool isAdvitrakPhoneNumberDisplayed(string expectedPhoneNumbertype, string verificationPage)
        {
            try
            {
                if (timeSensitiveChecks())
                {
                    int displayedPhoneNumbersCount = 0;
                    int matchedPhonenumbersCount = 0;
                    string matchedPhonenumber = null;
                    IList<IWebElement> phoneNumbers = browser.FindElements(By.CssSelector("span.phone-number.phone-replace"));
                    foreach (IWebElement phoneNumber in phoneNumbers)
                    {
                        if (isElementPresent(phoneNumber, "phoneNumber"))
                        {
                            displayedPhoneNumbersCount++;
                            string number = String.Concat(phoneNumber.Text.Where(Char.IsDigit));
                            if (matchedPhonenumber == null)
                            {
                                matchedPhonenumber = number;
                                matchedPhonenumbersCount++;
                            }
                            else
                            {
                                if (number.Equals(matchedPhonenumber))
                                    matchedPhonenumbersCount++;
                            }

                        }
                    }
                    if (displayedPhoneNumbersCount == matchedPhonenumbersCount)
                    {

                        validations.assertionPass(expectedPhoneNumbertype + " contact phone number", matchedPhonenumber + " is displayed on " + verificationPage);
                        return true;
                    }
                    else
                        validations.assertionFailed(expectedPhoneNumbertype + " contact phone number", matchedPhonenumber + " is not displayed for all phone instances on" + verificationPage);
                }
                else
                {
                    report.reportDoneEvent("Phone number check", " not done since we are outside of business hours.");
                    return true;
                }

            }

            catch (Exception exception)
            {
                report.reportException("isAdvitrakPhoneNumberDisplayed", exception);
            }
            return false;
        }

        public bool isShoppingCartDisabledIndicator()
        {

            try
            {
                // waitForElementLoad(shoppingCartIndicatorLnk, 10);
                if(!(isElementExistsInPage(By.CssSelector("div.header - navigation - cart > span > button"), 5000)));
                return true;
                // if (disableShoppingCartIndicator.GetAttribute("class").IndexOf("disabled", StringComparison.OrdinalIgnoreCase) >= 0)
                // && shoppingCartIndicatorEmpty.GetAttribute("class").IndexOf("disabled",StringComparison.OrdinalIgnoreCase) >=0)             

            }
            catch (Exception exception)
            {
                report.reportException("shoppingCartDisabledIndicator", exception);
            }
            return false;
        }



        public bool isEquipmentChangeOverlayDisplayed()
        {
            try
            {
                waitForApplicationToProcess();
                if (isElementPresent(overlayContainer, "Equipment Change Overlay Container"))
                    if (overlayContainer.GetAttribute("class").Contains("show")
                         && isElementPresent(overlayTitle, "Overlay title") && isElementPresent(overlayCloseBtn, "Overlay Close button")
                        && isElementPresent(overlayDescription, "Overlay Description")
                        && isElementPresent(overlayOkBtn, "OK button") && isElementPresent(overlayCancelBtn, "Cancel Button"))
                        return true;
            }
            catch (Exception exception)
            {
                report.reportException("isEquipmentChangeOverlayDisplayed", exception);
            }
            return false;
        }

        public bool clickCloseBtnOnTheEquipmentChangeOverlay()
        {
            try
            {
                overlayCloseBtn.Click();
                Thread.Sleep(500);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickCloseBtnOnTheEquipmentChangeOverlay", exception);
            }
            return false;
        }

        public bool isEquipmentChangeOverlayClosed()
        {
            try
            {
                waitForApplicationToProcess();
                if (!overlayContainer.GetAttribute("class").Contains("show"))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isEquipmentChangeOverlayClosed", exception);
            }
            return false;
        }

        public bool clickCancelBtnOnTheEquipmentChangeOverlay()
        {
            try
            {
                overlayCancelBtn.Click();
                Thread.Sleep(500);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickCancelBtnOnTheEquipmentChangeOverlay", exception);
            }
            return false;
        }

        public bool clickOKBtnOnTheEquipmentChangeOverlay()
        {
            try
            {
                overlayOkBtn.Click();
                Thread.Sleep(2000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickOKBtnOnTheEquipmentChangeOverlay", exception);
            }
            return false;
        }


        public bool isSearchResultsPageDisplayed(string searchText)
        {
            try
            {
                waitForElementLoad(globalSearchResults, 10);

                if (isElementPresent(searchResultBannerHeader, "Search Banner Header") && isElementPresent(totalSearchResults, "Total Search Results")
                    && isElementPresent(searchResultsForText, "Search Results for Text") && isElementPresent(searchTerm, "Search Term"))
                {
                    IList<IWebElement> searchResult = globalSearchResults.FindElements(By.CssSelector("li.global-search-results-item"));
                    foreach (IWebElement search in searchResult)
                    {
                        IWebElement resultHeading = search.FindElement(By.CssSelector("h2 > a.item-heading-link"));
                        IWebElement desc = search.FindElement(By.CssSelector("p.item-description"));
                        IWebElement highlightedSearchTerm = search.FindElement(By.CssSelector("p > strong:nth-child(1)"));
                        if (isElementPresent(resultHeading, "Search Result Header") && isElementPresent(desc, "Search Description")
                            && isElementPresent(highlightedSearchTerm))
                        {
                            highlightedSearchTerm.Text.Equals(searchText);
                        }

                    }


                }
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("isSearchResultsPageDisplayed", exception);
            }
            return false;
        }


        public bool isSearchPaginationDisplayed()
        {
            try
            {
                if (isElementPresent(paginationList, "Pagination list"))
                //  && isElementPresent(nextButton, "Next Button")) 
                //&& isElementNotPresent(previousButton, "Previous Button"))
                {
                    IList<IWebElement> pageCount = paginationList.FindElements(By.CssSelector("li.pagination-list-item"));

                    if (pageCount.Count() >= 3)
                    {
                        IWebElement firstPage = paginationList.FindElement(By.CssSelector("li:nth-child(1) > a.button.button-pagination.button-pagination--number.button-color-primary"));
                        IWebElement secondPage = paginationList.FindElement(By.CssSelector("li:nth-child(2) > a.button.button-pagination.button-pagination--number.button-color-secondary"));
                        if (isElementPresent(firstPage, "First Page") && isElementPresent(secondPage, "Second Page"))
                        {
                            ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", secondPage);
                            secondPage.Click();
                            Thread.Sleep(4000);

                            if (isElementPresent(previousButton, "Previous button"))
                            {
                                ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", previousButton.Location.Y));
                                //((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", previousButton);
                                waitForElementLoad(previousButton, 5);
                                previousButton.Click();
                                waitForElementToBeNotPresent(previousButton, 10); //Previous Button disappears
                                nextButton.Click();
                                waitForElementLoad(previousButton, 10); //Previous Button Appears
                            }
                        }

                    }

                }


                return true;
            }
            catch (Exception exception)
            {
                report.reportException("isSearchPaginationDisplayed", exception);
            }
            return false;
        }



        public bool noSearchResultsPage(int searchResult)
        {
            try
            {
                waitForElementLoad(noSearchResult, 20);
                noSearchResult.Equals(searchResult);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("noSearchResultsPage", exception);
            }
            return false;
        }


        public bool isSearchAutofillSuggestionsDisplayed(string searchText)
        {
            try
            {
                if (navHeaderSearchBoxInput.Displayed)
                {
                    navHeaderSearchBoxInput.Click();
                    navHeaderSearchBoxInput.SendKeys(searchText);
                    MediumSleep();
                    if (navHeaderSearchBoxInput.GetAttribute("value").Equals(searchText) && isElementPresent(searchAutofillDropdown, "Search Autofill Dropdown") && searchAutofillDropdown.GetAttribute("class").Contains("active")
                      && isElementPresent(searchAutofillList, "Search Auto Fill List")
                      && isElementPresent(suggestionText, "Suggestion Text") && isElementPresent(closeButton, "Close Button"))
                        return true;
                }


            }
            catch (Exception exception)
            {
                report.reportException("isSearchAutofillSuggestionsDisplayed", exception);
            }
            return false;
        }

        public bool clickCloseButton()
        {
            try
            {
                if (isElementPresent(closeButton, "Close Button"))
                    closeButton.Click();
                ShortSleep();
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickCloseButton", exception);
            }
            return false;
        }

        public bool searchAutofillSuggestionsClosed()
        {
            try
            {
                if (isElementPresent(searchAutofillDropdown, "Search Autofill Dropdown") && !searchAutofillDropdown.GetAttribute("class").Contains("active"))
                    ShortSleep();
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("searchAutofillSuggestionsClosed", exception);
            }
            return false;
        }

        public string selectAutofillSuggestionOption(int autofillOption)
        {
            try
            {
                IList<IWebElement> autofillSearchSuggestions = searchAutofillList.FindElements(By.CssSelector("li.search-autofill-list-item"));

                if (autofillSearchSuggestions.Count >= autofillOption && autofillSearchSuggestions.Count <= 4)
                {

                    string selectedAddressOption = autofillSearchSuggestions[autofillOption - 1].FindElement(By.TagName("span")).Text;
                    autofillSearchSuggestions[autofillOption - 1].FindElement(By.Id("search-autofill-result-3")).Click();

                    waitForElementToBeNotPresent(searchAutofillList, 6);
                    waitForApplicationToProcess();
                    return selectedAddressOption;
                }

            }
            catch (Exception exception)
            {
                report.reportException("selectAutofillSuggestionOption", exception);
            }
            return null;
        }


        /// <summary>
        /// Buyflow Footer navigation
        /// </summary>

        public bool isNextBtnPresent()
        {
            try
            {
                ((IJavaScriptExecutor)browser).ExecuteScript(string.Format("window.scrollTo(0, {0});", buyflowPageNextBtn.Location.Y));
                if (isElementPresent(buyflowPageNextBtn, "Buyflow page Next Button "))
                    return true;
            }
            catch (Exception exception)
            {
                report.reportException("isNextBtnPresent", exception);
            }
            return false;
        }



        public AccountInformation clickOnNextBtnForAccountInfoPage()
        {
            try
            {
                //Thread.Sleep(1000);
                buyflowPageNextBtn.Click();
                Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new AccountInformation(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForAccountInfoPage", exception);
            }
            return null;
        }


        public CustomizeVideo clickOnNextBtnForVideoCustomizePage()
        {
            try
            {
                //Thread.Sleep(1000);
                buyflowPageNextBtn.Click();
                //Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new CustomizeVideo(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForVideoCustomizePage", exception);
            }
            return null;
        }

        public CustomizeVoice clickOnNextBtnForVoiceCustomizePage()
        {
            try
            {
                //Thread.Sleep(1000);
                buyflowPageNextBtn.Click();
                //Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new CustomizeVoice(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForVoiceCustomizePage", exception);
            }
            return null;
        }


        public LeadGenFormPage clickOnNextBtnForLeadGenFormPage()
        {
            try
            {
                //Thread.Sleep(1000);
                buyflowPageNextBtn.Click();
                waitForApplicationToProcess();
                return new LeadGenFormPage(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForLeadGenFormPage", exception);
            }
            return null;
        }

        public Installation clickOnNextBtnForInstallationPage()
        {
            try
            {
                //Thread.Sleep(1000);
                buyflowPageNextBtn.Click();
                //Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new Installation(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForInstallationPage", exception);
            }
            return null;
        }
        public Review clickOnNextBtnForReviewPage()
        {
            try
            {
                buyflowPageNextBtn.Click();
                // Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new Review(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForReviewPage", exception);
            }
            return null;
        }


        public PhoneNumberTransferAgreement clickOnNextBtnForNumberTransferAgreementPage()
        {
            try
            {
                buyflowPageNextBtn.Click();
                // Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new PhoneNumberTransferAgreement(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForNumberTransferAgreementPage", exception);
            }
            return null;
        }


        public FrictionlessRequiredDocsAndApprovals clickOnNextBtnForFLRequiredDocsAndApprovalsPage()
        {
            try
            {
                buyflowPageNextBtn.Click();
                //Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new FrictionlessRequiredDocsAndApprovals(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForFLRequiredDocsAndApprovalsPage", exception);
            }
            return null;
        }

        public PhoneNumberTransferAgreement clickOnNextBtnForFLPhoneNumberTransferAgreementPage()
        {
            try
            {
                buyflowPageNextBtn.Click();
                //Thread.Sleep(1000);
                waitForApplicationToProcess();
                return new PhoneNumberTransferAgreement(browser, report, validations);
            }
            catch (Exception exception)
            {
                report.reportException("clickOnNextBtnForFLPhoneNumberTransferAgreementPage", exception);
            }
            return null;
        }
        public bool isNextContractLengthBtnPresent()
        {
            if (isElementPresent(buyflowPageNextBtn, "Buyflow page Next Button"))
            {
                return true;
            }
            return false;
        }


        public bool liveChatSectionIsDisplayedAndClickable(string content)
        {
            try
            {
                waitForApplicationToProcess();
                Thread.Sleep(500);
                if (content.Contains("not"))
                {
                    if (isElementNotPresent(clickToChatLink))
                        return true;
                }
                else
                {
                    if (isElementPresent(clickToChatLink, "clickToChatLink"))
                        return true;
                }

            }
            catch (Exception exception)
            {
                report.reportException("liveChatSectionIsDisplayedAndClickable", exception);
            }
            return false;
        }



        public bool clickOnCheckoutButton()
        {
            try
            {
                waitForApplicationToProcess();

                if (testSettings.isMobileExecution || testSettings.isDesktopMobileVersion)
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", GoToCheckOutBttonMobile);
                    GoToCheckOutBttonMobile.Click();
                }
                else
                {
                    ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", GoToCheckOutBtton);
                    GoToCheckOutBtton.Click();
                }

                Thread.Sleep(1000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickOnCheckoutButton", exception);
            }
            return false;
        }


        public bool clickOnCheckoutButtonInVoiceDoublePlayPage()
        {
            try
            {
                waitForApplicationToProcess();
                ((IJavaScriptExecutor)browser).ExecuteScript("arguments[0].scrollIntoView(false);", voiceDoublePlayGoChkOutButton);
                voiceDoublePlayGoChkOutButton.Click();


                Thread.Sleep(1000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("clickOnCheckoutButtonInVoiceDoublePlayPage", exception);
            }
            return false;
        }

        public BusinessHelpHomePage openHelpAndSupportPage()
        {
            try
            {
                if ((browser.Url.ToUpper().Trim().Contains("DEV")) && (!browser.Url.ToUpper().Trim().Contains("PRODTEST")))
                {
                    browser.Navigate().GoToUrl("https://business.dev9.cable.comcast.com/help-and-support/");
                }
                else if ((browser.Url.ToUpper().Trim().Contains("DEV")) && (browser.Url.ToUpper().Trim().Contains("PRODTEST")))
                {
                    browser.Navigate().GoToUrl("https://prodtest.business.dev9.cable.comcast.com/help-and-support/");
                }

                else if ((browser.Url.ToUpper().Trim().Contains("QA")) && (!browser.Url.ToUpper().Trim().Contains("PRODTEST")))
                {
                    browser.Navigate().GoToUrl("https://business.qa9.cable.comcast.com/help-and-support/");
                }
                else if ((browser.Url.ToUpper().Trim().Contains("QA")) && (browser.Url.ToUpper().Trim().Contains("PRODTEST")))
                {
                    browser.Navigate().GoToUrl("https://prodtest.business.qa9.cable.comcast.com/help-and-support/");
                }
                else if ((browser.Url.ToUpper().Trim().Contains("INT")) && (!browser.Url.ToUpper().Trim().Contains("PRODTEST")))
                {
                    browser.Navigate().GoToUrl("https://business.int.comcast.com/help-and-support/");
                }
                else if ((browser.Url.ToUpper().Trim().Contains("INT")) && (browser.Url.ToUpper().Trim().Contains("PRODTEST")))
                {
                    browser.Navigate().GoToUrl("https://prodtest.business.int.comcast.com/help-and-support/");
                }
                else if (browser.Url.ToUpper().Trim().Contains("STG"))
                {
                    browser.Navigate().GoToUrl("https://businesshelp.stg.comcast.com/help-and-support/");
                }
                else
                {
                    browser.Navigate().GoToUrl("https://businesshelp.comcast.com/help-and-support/");
                }
                Thread.Sleep(2000);
                report.reportDoneEvent("Help-and-Support", "Correct page is displayed");
            }
            catch (Exception exception)
            {
                report.reportException("Help-and-Support", exception);
            }
            return new BusinessHelpHomePage(browser, report, validations);
        }



        protected internal virtual void verifyElementDisplayed(IWebElement element, string elementName, string stepName)
        {
            if (isElementPresent(element))
            {
                validations.assertionPass(stepName, elementName + " is displayed as expected");
            }
            else
            {
                validations.assertionFailed(stepName, elementName + " is NOT displayed");
            }
        }

        public virtual Page validateLinksAndUrl(IWebElement link, string expectedUrl)
        {
            try
            {

                string actualUrl = link.GetAttribute("href");
                if (actualUrl.Contains(expectedUrl))
                {
                    validations.assertionPass("validateLinkAndUrl", "Expected URL is '" + expectedUrl + "'. Actual URL is '" + actualUrl + "'");
                }
                else
                {
                    validations.assertionFailed("validateLinkAndUrl", "urls are not matching");
                }

            }
            catch (Exception e)
            {
                validations.assertionFailed("validateLinkAndUrl", e.Message);
            }

            return this;

        }

        public virtual Page validateLinksTxt(IWebElement link, string expectedLnkTxt)
        {
            try
            {

                string actualLnkTxt = link.Text.Trim();
                if (actualLnkTxt.Contains(expectedLnkTxt))
                {
                    validations.assertionPass("validateLinksTxt", "Expected Link Text is '" + expectedLnkTxt + "'.Actual Link Text is '" + actualLnkTxt + "'");
                }
                else
                {
                    validations.assertionFailed("validateLinksTxt", "Link Texts are not matching");
                }

            }
            catch (Exception e)
            {
                validations.assertionFailed("validateLinksTxt", e.Message);
            }

            return this;

        }


        public ProductsPage OpenDeadURLorVanityURL(string envOrPage, string DeadorVanityurl, out string actualRedirectURL, out int statusCode)
        {
            try
            {
                string url = testSettings.ApplicationURL.Split('/')[0] + "/" + "/" + testSettings.ApplicationURL.Split('/')[2] + DeadorVanityurl;
                HttpWebRequest webRequest = (HttpWebRequest)WebRequest
               .Create(url);
                webRequest.AllowAutoRedirect = true;
                HttpWebResponse response =
                (HttpWebResponse)webRequest.GetResponse();
                actualRedirectURL = response.ResponseUri.AbsoluteUri;
                response.Close();
                HttpWebRequest webRequest1 = (HttpWebRequest)WebRequest
              .Create(url);
                webRequest1.AllowAutoRedirect = false;
                HttpWebResponse response1 =
                (HttpWebResponse)webRequest1.GetResponse();
                statusCode = (int)response1.StatusCode;

                return new ProductsPage(browser, report, validations);
            }
            catch (WebException webexception)
            {
                if (Convert.ToString(webexception.Status).Equals("Timeout") || Convert.ToString(((System.Net.HttpWebResponse)(webexception.Response)).StatusCode).Equals("NotFound"))
                {

                    report.reportException("Time out Error or 404", webexception);
                    statusCode = 0;
                    actualRedirectURL = "";
                    return null;
                }

                else if (Convert.ToString(((System.Net.HttpWebResponse)(webexception.Response)).StatusCode).Equals("Gone"))
                {
                    statusCode = 410;
                    actualRedirectURL = webexception.Response.ResponseUri.OriginalString; ;
                    //report.reportDoneEvent("Dead URL redirected to ", "Page not available page with 410 HTTP code");
                    return new ProductsPage(browser, report, validations);
                }

            }
            statusCode = 0;
            actualRedirectURL = ""; return null;
        }

        public ExceptionReportingLog openExceptionLog(string visitorSessionID = null)
        {
            try
            {
                string exceptionLogUrl = "";
                TestSettings testsettings = new TestSettings();
                if (testsettings.ApplicationURL.IndexOf("Dev", StringComparison.OrdinalIgnoreCase) >= 0)
                    exceptionLogUrl = "http://occomdb-dt-q06/Reports/Pages/Report.aspx?ItemPath=%2fComcast.Exception.Reporting+Dev-Int2%2fOverview";
                else if (testsettings.ApplicationURL.IndexOf("QA", StringComparison.OrdinalIgnoreCase) >= 0)
                    exceptionLogUrl = "http://pacdcdevcmsdb1.cable.comcast.com/ReportServer/Pages/ReportViewer.aspx?/Comcast.Exception.Reporting+QA/Overview";
                else if (testsettings.ApplicationURL.IndexOf("INT", StringComparison.OrdinalIgnoreCase) >= 0)
                    exceptionLogUrl = "http://comrptdb-wc-2s/Reports/Pages/Report.aspx?ItemPath=/Comcast.Framework.Exception.Reporting/Overview";
                else if (testsettings.ApplicationURL.IndexOf("STG", StringComparison.OrdinalIgnoreCase) >= 0)
                    exceptionLogUrl = "http://reports.stg.xfinity.com/Reports_SSRS/Pages/Report.aspx?ItemPath=%2fComcast.Framework.Exception.Reporting%2fOverview";
                else
                    exceptionLogUrl = "http://reports.comcast.com/Reports/Pages/Report.aspx?ItemPath=%2fComcast.Framework.Exception.Reporting%2fOverview";

                if (visitorSessionID != null)
                    browser.Navigate().GoToUrl(exceptionLogUrl + "&SessionId=" + visitorSessionID);
                else
                    browser.Navigate().GoToUrl(exceptionLogUrl);

            }
            catch (Exception ex)
            {
                report.reportException("openExceptionLog", ex);
            }
            return new ExceptionReportingLog(browser, report, validations);

        }

        public string OpenSCMSOrderDetailsInSFDCAPIUrl(string SCMSOrderNumber)
        {
            try
            {

                string apiUrl = "";
                TestSettings testsettings = new TestSettings();
                if (testsettings.ApplicationURL.IndexOf("QA", StringComparison.OrdinalIgnoreCase) >= 0)
                    apiUrl = "http://services.qa9.xfinity.com/BusinessService/12.01/api/cart/";
                else if (testsettings.ApplicationURL.IndexOf("INT", StringComparison.OrdinalIgnoreCase) >= 0)
                    apiUrl = "http://services.int.xfinity.com/BusinessService/12.01/api/cart/";
                else if (testsettings.ApplicationURL.IndexOf("STG", StringComparison.OrdinalIgnoreCase) >= 0)
                    apiUrl = "http://services.stg.xfinity.com/BusinessService/12.01/api/cart/";
                else
                    apiUrl = "http://services.xfinity.com/BusinessService/12.01/api/cart/";

                var client = new RestClient(apiUrl);
                var request = new RestRequest(SCMSOrderNumber, Method.GET);
                var response = client.Execute<Entities.ShoppingCartResponse>(request);
                if (response.StatusCode == HttpStatusCode.OK)
                {
                    if (response.Data.Messages[0].Code.Equals("0")
                        && response.Data.ShoppingCart.Customer.FirstName != null && response.Data.ShoppingCart.Customer.CompanyName != null
                        && response.Data.ShoppingCart.Customer.BusinessPhoneNumber != null && response.Data.ShoppingCart.Customer.EmailAddress != null
                        && response.Data.ShoppingCart.Customer.ServiceAddress.Address1 != null && response.Data.ShoppingCart.Customer.ServiceAddress.City != null
                        && response.Data.ShoppingCart.Customer.ServiceAddress.State != null && response.Data.ShoppingCart.Customer.ServiceAddress.ZipCode != null
                         && response.Data.ShoppingCart.Customer.BillingAddress.Address1 != null && response.Data.ShoppingCart.Customer.BillingAddress.City != null
                        && response.Data.ShoppingCart.Customer.BillingAddress.State != null && response.Data.ShoppingCart.Customer.BillingAddress.ZipCode != null
                        && response.Data.ShoppingCart.Installation.TimeSlot != null && !response.Data.ShoppingCart.Installation.Date.Equals(DateTime.MinValue)
                        && response.Data.ShoppingCart.Offer != null)
                        return "OFT";
                    else if (response.Data.Messages[0].Code.Equals("1000"))
                        return string.Empty;
                }
            }
            catch (Exception ex)
            {
                report.reportException("OpenOrderDetailsInSFDCAPIUrl", ex);
            }
            return string.Empty;
        }

        public bool isAgreementSectionDisplayed(string agrementType)
        {
            try
            {
                if (agrementType.IndexOf("Service", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    if (isElementPresent(serviceOrderAgreementSection, "serviceOrderAgreementSection") && isElementPresent(serviceOrderAgreementSectionHeader, "serviceOrderAgreementSectionHeader") && isElementPresent(serviceOrderAgreementPDFLnk, "serviceOrderAgreementPDFLnk")
                        && isElementPresent(serviceOrderAgreementPrintLnk, "serviceOrderAgreementPrintLnk") && isElementPresent(serviceOrderAgreementRichTextBox, "serviceOrderAgreementRichTextBox")
                        && isElementPresent(serviceOrderAgreementCheckBox, "serviceOrderAgreementCheckBox") && isElementPresent(serviceOrderAgreementCheckBoxRichText, "serviceOrderAgreementCheckBoxRichText")
                        && isElementPresent(serviceOrderAgreementCheckBoxError, "serviceOrderAgreementCheckBoxError") == false && isElementPresent(customerNameUnderServiceOrderAgreement, "customerNameUnderServiceOrderAgreement"))
                        return true;
                }
                else if (agrementType.IndexOf("E911", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    if (isElementPresent(E911AgreementSection, "E911AgreementSection") && isElementPresent(E911AgreementSectionHeader, "E911AgreementSectionHeader") && isElementPresent(E911AgreementPDFLnk, "E911AgreementPDFLnk")
                           && isElementPresent(E911AgreementPrintLnk, "E911AgreementPrintLnk") && isElementPresent(E911AgreementRichTextBox, "E911AgreementRichTextBox")
                           && isElementPresent(E911serviceagreementCheckBox, "E911serviceagreementCheckBox") && isElementPresent(E911AgreementCheckBoxRichText, "E911AgreementCheckBoxRichText")
                           && isElementPresent(E911AgreementCheckBoxError, "E911AgreementCheckBoxError") == false && isElementPresent(customerNameUnderE911Agreement, "customerNameUnderE911Agreement"))
                        return true;
                }
                else if (agrementType.IndexOf("LETTER_OF_AGENCY", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    if (isElementPresent(NumberTransferAgreementSection, "NumberTransferAgreementSection") && isElementPresent(NumberTransferAgreementSectionHeader, "NumberTransferAgreementSectionHeader") && isElementPresent(NumberTransferAgreementPDFLnk, "NumberTransferAgreementPDFLnk")
                        && isElementPresent(NumberTransferAgreementPrintLnk, "NumberTransferAgreementPrintLnk") && isElementPresent(NumberTransferAgreementRichTextBox, "NumberTransferAgreementRichTextBox")
                        && isElementPresent(NumberTransferAgreementCheckBox, "NumberTransferAgreementCheckBox") && isElementPresent(NumberTransferAgreementCheckBoxRichText, "NumberTransferAgreementCheckBoxRichText")
                        && isElementPresent(NumberTransferAgreementCheckBoxError, "NumberTransferAgreementCheckBoxError") == false && isElementPresent(customerNameUnderNumberTransferAgreement, "customerNameUnderNumberTransferAgreement"))
                        return true;
                }

                else if (agrementType.IndexOf("RESP_ORG", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    if (isElementPresent(RespOrgAgreementSection, "RespOrgAgreementSection") && isElementPresent(RespOrgAgreementSectionHeader, "RespOrgAgreementSectionHeader") && isElementPresent(RespOrgAgreementPDFLnk, "RespOrgAgreementPDFLnk")
                        && isElementPresent(RespOrgAgreementPrintLnk, "RespOrgAgreementPrintLnk") && isElementPresent(RespOrgAgreementRichTextBox, "RespOrgAgreementRichTextBox")
                        && isElementPresent(RespOrgAgreementCheckBox, "RespOrgAgreementCheckBox") && isElementPresent(RespOrgAgreementCheckBoxRichText, "RespOrgAgreementCheckBoxRichText")
                        && isElementPresent(RespOrgAgreementCheckBoxError, "RespOrgAgreementCheckBoxError") == false && isElementPresent(customerNameUnderRespOrgAgreement, "customerNameUnderRespOrgAgreement"))
                        return true;
                }
                else if (agrementType.IndexOf("RCF", StringComparison.OrdinalIgnoreCase) >= 0)
                {
                    if (isElementPresent(RCFAgreementSection, "RCFAgreementSection") && isElementPresent(RCFAgreementSectionHeader, "RCFAgreementSectionHeader") && isElementPresent(RCFAgreementPDFLnk, "RCFAgreementPDFLnk")
                        && isElementPresent(RCFAgreementPrintLnk, "RCFAgreementPrintLnk") && isElementPresent(RCFAgreementRichTextBox, "RCFAgreementRichTextBox")
                        && isElementPresent(RCFAgreementCheckBox, "RCFAgreementCheckBox") && isElementPresent(RCFAgreementCheckBoxRichText, "RCFAgreementCheckBoxRichText")
                        && isElementPresent(RCFAgreementCheckBoxError, "RCFAgreementCheckBoxError") == false && isElementPresent(customerNameUnderRCFAgreement, "customerNameUnderRCFAgreement"))
                        return true;
                }
            }
            catch (Exception exception)
            {
                report.reportException("isAgreementSectionDisplayed", exception);
            }
            return false;
        }

        public bool isSessionCleared()
        {
            try
            {
                browser.Manage().Cookies.DeleteAllCookies();
                Thread.Sleep(1000);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("isInternetSpeedCheckPageDisplayedWithOoklaModule", exception);
            }
            return false;
        }

        public bool accessibilityCheck(string pageName)
        {
            try
            {
                new TenonHelper().validateAccessibilityErrors(pageName, browser.PageSource);
                return true;
            }
            catch (Exception exception)
            {
                report.reportException("accessibilityCheck", exception);
            } return false;
        }

        public string accessibilityLogReport()
        {
            try
            {
                var accessibilityErrors = GlobalGenericList<TenonResult>.GetAccessibilityData();
                var path = ReportPath.Instance.getReportPath();
                new TenonExcelReportHelper(path, ScenarioContext.Current.ScenarioInfo.Title).CreateReport(accessibilityErrors);
                return path;
            }
            catch (Exception exception)
            {
                report.reportException("accessibilityLogReport", exception);
            } return null;
        }
    }
}