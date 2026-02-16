/*
 * Copyright 2026 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */
package org.mifospay.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import mobile_wallet.feature.settings.generated.resources.Res
import mobile_wallet.feature.settings.generated.resources.feature_settings_change_passcode
import mobile_wallet.feature.settings.generated.resources.feature_settings_change_password
import mobile_wallet.feature.settings.generated.resources.feature_settings_disable_account
import mobile_wallet.feature.settings.generated.resources.feature_settings_faq
import mobile_wallet.feature.settings.generated.resources.feature_settings_log_out
import mobile_wallet.feature.settings.generated.resources.outline_logout
import mobile_wallet.feature.settings.generated.resources.outline_password
import mobile_wallet.feature.settings.generated.resources.outline_pin
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.vectorResource
import org.mifospay.core.designsystem.icon.MifosIcons

/**
 * A sealed class representing all items available on the settings screen.
 * Each object holds metadata for a specific setting, such as its title, icon,
 * and the MVI action to dispatch, making it easy to generate UI components dynamically.
 *
 * @property title The string resource for the setting's title.
 * @property icon The icon to display, wrapped in [SettingsIcon] to support both
 *   [ImageVector] constants and [DrawableResource] references.
 * @property action The [SettingsAction] to dispatch when the item is tapped.
 * @property isDestructive Whether the item should render in the error/destructive color.
 */
sealed class SettingsItem(
    val title: StringResource,
    val icon: SettingsIcon,
    val action: SettingsAction,
    val isDestructive: Boolean = false,
) {
    /** Represents the 'FAQ' information screen. */
    data object Faq : SettingsItem(
        title = Res.string.feature_settings_faq,
        icon = SettingsIcon.Vector(MifosIcons.OutlinedInfo),
        action = SettingsAction.NavigateToFaqScreen,
    )

    /** Represents the 'Change Password' setting. */
    data object ChangePassword : SettingsItem(
        title = Res.string.feature_settings_change_password,
        icon = SettingsIcon.Drawable(Res.drawable.outline_password),
        action = SettingsAction.ChangePassword,
    )

    /** Represents the 'Change Passcode' setting. */
    data object ChangePasscode : SettingsItem(
        title = Res.string.feature_settings_change_passcode,
        icon = SettingsIcon.Drawable(Res.drawable.outline_pin),
        action = SettingsAction.ChangePasscode,
    )

    /** Represents the 'Log Out' action. */
    data object Logout : SettingsItem(
        title = Res.string.feature_settings_log_out,
        icon = SettingsIcon.Drawable(Res.drawable.outline_logout),
        action = SettingsAction.Logout,
    )

    /** Represents the 'Disable Account' destructive action. */
    data object DisableAccount : SettingsItem(
        title = Res.string.feature_settings_disable_account,
        icon = SettingsIcon.Vector(MifosIcons.OutlinedLock),
        action = SettingsAction.DisableAccount,
        isDestructive = true,
    )
}

/**
 * A sealed interface that abstracts over the two ways icons are provided in this module:
 * - [Vector]: a pre-built [ImageVector] constant (e.g. from [MifosIcons]).
 * - [Drawable]: a [DrawableResource] that must be resolved at composition time via [vectorResource].
 */
sealed interface SettingsIcon {
    data class Vector(val imageVector: ImageVector) : SettingsIcon
    data class Drawable(val resource: DrawableResource) : SettingsIcon
}

/**
 * Resolves the [SettingsIcon] to an [ImageVector] for use in composables.
 */
@Composable
fun SettingsIcon.resolve(): ImageVector = when (this) {
    is SettingsIcon.Vector -> imageVector
    is SettingsIcon.Drawable -> vectorResource(resource)
}

/**
 * The ordered list of items displayed on the settings screen.
 * To add, remove, or reorder settings, edit this list.
 */
internal val settingsItems: List<SettingsItem> = listOf(
    SettingsItem.Faq,
    SettingsItem.ChangePassword,
    SettingsItem.ChangePasscode,
    SettingsItem.Logout,
    SettingsItem.DisableAccount,
)
