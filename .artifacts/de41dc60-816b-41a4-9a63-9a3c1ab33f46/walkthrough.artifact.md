# Profile Icon Sync Implementation

I have completed the implementation to synchronize the profile icon with the database data in the `SettingsScreen`.

## Changes Made

### [SettingsViewModel.kt](file:///C:/Users/Seryie/AndroidStudioProjects/KidApp/app/src/main/java/com/example/kidapp/presentation/screens/settings/SettingsViewModel.kt)
- Added `keyToDrawableMap` to convert database icon keys (e.g., "rabbit") back to drawable resource IDs (`R.drawable.rabbit`).
- Added `getDrawableRes(iconKey: String?)` helper function.

### [SettingsScreen.kt](file:///C:/Users/Seryie/AndroidStudioProjects/KidApp/app/src/main/java/com/example/kidapp/presentation/screens/settings/SettingsScreen.kt)
- Updated the `DropdownMenuItem` `onClick` logic:
    - Now it calls `settingsViewModel.onSelectedIconChange()` with the child's icon from the database.
    - Also updates name and age as before.
- Fixed the `ProfileImage` component:
    - It now correctly displays the `selectedIcon` (falling back to the panda icon if none is selected).
    - Fixed the syntax error where `profileImage` was missing its argument.

## Verification Results

### Manual Verification
- Selecting a child from the dropdown now automatically updates:
    - The **Name** text field.
    - The **Age** text field.
    - The **Profile Icon** to match the child's current selection in the database.
