package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

class TermsRoute : ComposableRoute {
    override val height = mutableStateOf(80.dp)
    override val requireNav = false

    @Composable
    override fun HeaderContent(scope: BoxScope) {
        HeaderText("Terms")
    }

    @Composable
    override fun BodyContent(scope: BoxScope) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            TermsHeader()
            Spacer(modifier = Modifier.height(24.dp))
            TermsSection(
                title = "Use of the Application",
                content = """
                StayFlow is designed to facilitate the booking and management of rooms in a hotel chain. Users can:
                
                • Search and filter available rooms by city or proximity.
                • View room details, including location, number of beds, and price per night.
                • Make bookings by selecting specific dates.
                • Access their profile and manage current and future reservations.
            """.trimIndent()
            )

            TermsSection(
                title = "Registration and Authentication",
                content = """
                To use the app's features, users must register by providing a valid email address and creating a secure password. Users are responsible for maintaining the confidentiality of their credentials and reporting any unauthorized use of their account.
            """.trimIndent()
            )

            TermsSection(
                title = "Privacy and Data Protection",
                content = """
                StayFlow collects and uses personal information, such as name and email, solely for internal identification and reservation management purposes. We commit to:
                    
                • Not sharing or selling personal information to third parties without the user's explicit consent.
                • Complying with applicable data protection regulations, such as Mexico's Federal Law on the Protection of Personal Data Held by Private Parties and Colombia's Law 1581 of 2012.
                • Implementing appropriate security measures to protect user information.
            """.trimIndent()
            )

            TermsSection(
                title = "Legal Compliance of Rooms",
                content = """
                All rooms listed on StayFlow comply with the legal requirements for short-term rentals in their respective jurisdictions.
                In Colombia:
                Tourist housing must be registered with the National Tourism Registry (RNT), in accordance with Decree 2590 of 2009 and Law 2068 of 2020.
                In Mexico:
                In Mexico City, the Tourism Law has been amended to establish a host registry and limit digital platform rentals to a maximum of six months per year.
            """.trimIndent()
            )

            TermsSection(
                title = "Cancellation Policies",
                content = """
                Users may cancel reservations through the app, subject to the following conditions:
                
                • Cancellations must be made before the scheduled check-in date.
                • After the check-in date, cancellations and refunds are not allowed.
                • Some bookings may be subject to specific cancellation policies depending on the city.
            """.trimIndent()
            )

            TermsSection(
                title = "Connectivity and Service Availability",
                content = """
                StayFlow requires an active internet connection to function properly. Service availability may be affected by factors beyond our control, such as network outages or system maintenance. We do not guarantee continuous and uninterrupted availability of the app.
            """.trimIndent()
            )

            TermsSection(
                title = "Modifications to the Terms",
                content = """
                We reserve the right to modify these terms and conditions at any time. Changes will take effect once published in the app. Users are encouraged to periodically review the terms to stay informed of any updates.
            """.trimIndent()
            )

            TermsSection(
                title = "Contact",
                content = """
                For inquiries, technical support, or more information about these terms and conditions, users may contact us at:
                
                Email: jsebastianvera@ucompensar.edu.co
            """.trimIndent()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @Composable
    fun TermsHeader() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Terms and Conditions of Use",
                style = Typography.headlineMedium,
                color = AppTheme.palette.Text
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "By accessing and using the StayFlow application, the user agrees to comply " +
                        "with these terms and conditions, as well as the applicable laws and " +
                        "regulations in Colombia and Mexico.",
                style = Typography.bodyMedium,
                color = AppTheme.palette.Subtext0
            )
        }
    }

    @Composable
    fun TermsSection(title: String, content: String) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = title,
                style = Typography.titleLarge,
                color = AppTheme.palette.Text
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                style = Typography.bodyMedium,
                color = AppTheme.palette.Subtext1
            )
        }
    }

}