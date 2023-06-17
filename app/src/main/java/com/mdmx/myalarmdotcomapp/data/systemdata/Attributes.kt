package com.mdmx.myalarmdotcomapp.data.systemdata

data class Attributes(
    val accessControlCurrentSystemMode: Int,
    val canScanForWifi: Boolean,
    val canWaitForWifiValidation: Boolean,
    val description: String,
    val hasSnapShotCameras: Boolean,
    val icon: String,
    val isInPartialLockdown: Boolean,
    val remainingImageQuota: Int,
    val supportsSecureArming: Boolean,
    val systemGroupName: String,
    val unitId: Int
)