package com.mdmx.myalarmdotcomapp.data.systemdata

data class Relationships(
    val cameras: Cameras,
    val configuration: Configuration,
    val garageDoors: GarageDoors,
    val geoDevices: GeoDevices,
    val imageSensors: ImageSensors,
    val lights: Lights,
    val locks: Locks,
    val remoteTemperatureSensors: RemoteTemperatureSensors,
    val sensors: Sensors,
    val thermostats: Thermostats,
    val waterSensors: WaterSensors,
    val waterValves: WaterValves
)