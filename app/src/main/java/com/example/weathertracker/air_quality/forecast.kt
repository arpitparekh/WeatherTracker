package com.example.weathertracker.air_quality

import com.google.gson.annotations.SerializedName

data class Astro(

	@field:SerializedName("moonset")
	val moonset: String? = null,

	@field:SerializedName("moon_illumination")
	val moonIllumination: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("moon_phase")
	val moonPhase: String? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null,

	@field:SerializedName("moonrise")
	val moonrise: String? = null
)

data class AirQuality(

	@field:SerializedName("no2")
	val no2: Double? = null,

	@field:SerializedName("o3")
	val o3: Double? = null,

	@field:SerializedName("us-epa-index")
	val usEpaIndex: Int? = null,

	@field:SerializedName("so2")
	val so2: Double? = null,

	@field:SerializedName("pm2_5")
	val pm25: Double? = null,

	@field:SerializedName("pm10")
	val pm10: Double? = null,

	@field:SerializedName("co")
	val co: Double? = null,

	@field:SerializedName("gb-defra-index")
	val gbDefraIndex: Int? = null
)

data class Forecast(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("forecast")
	val forecast: Forecast? = null,

	@field:SerializedName("forecastday")
	val forecastday: List<ForecastdayItem?>? = null
)

data class Day(

	@field:SerializedName("avgvis_km")
	val avgvisKm: Double? = null,

	@field:SerializedName("uv")
	val uv: Double? = null,

	@field:SerializedName("avgtemp_f")
	val avgtempF: Double? = null,

	@field:SerializedName("avgtemp_c")
	val avgtempC: Double? = null,

	@field:SerializedName("daily_chance_of_snow")
	val dailyChanceOfSnow: Int? = null,

	@field:SerializedName("maxtemp_c")
	val maxtempC: Double? = null,

	@field:SerializedName("maxtemp_f")
	val maxtempF: Double? = null,

	@field:SerializedName("mintemp_c")
	val mintempC: Double? = null,

	@field:SerializedName("avgvis_miles")
	val avgvisMiles: Double? = null,

	@field:SerializedName("daily_will_it_rain")
	val dailyWillItRain: Int? = null,

	@field:SerializedName("mintemp_f")
	val mintempF: Double? = null,

	@field:SerializedName("totalprecip_in")
	val totalprecipIn: Double? = null,

	@field:SerializedName("avghumidity")
	val avghumidity: Double? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("maxwind_kph")
	val maxwindKph: Double? = null,

	@field:SerializedName("maxwind_mph")
	val maxwindMph: Double? = null,

	@field:SerializedName("daily_chance_of_rain")
	val dailyChanceOfRain: Int? = null,

	@field:SerializedName("totalprecip_mm")
	val totalprecipMm: Double? = null,

	@field:SerializedName("daily_will_it_snow")
	val dailyWillItSnow: Int? = null
)

data class Location(

	@field:SerializedName("localtime")
	val localtime: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("localtime_epoch")
	val localtimeEpoch: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("tz_id")
	val tzId: String? = null
)

data class HourItem(

	@field:SerializedName("feelslike_c")
	val feelslikeC: Double? = null,

	@field:SerializedName("feelslike_f")
	val feelslikeF: Double? = null,

	@field:SerializedName("wind_degree")
	val windDegree: Int? = null,

	@field:SerializedName("windchill_f")
	val windchillF: Double? = null,

	@field:SerializedName("windchill_c")
	val windchillC: Double? = null,

	@field:SerializedName("temp_c")
	val tempC: Double? = null,

	@field:SerializedName("temp_f")
	val tempF: Double? = null,

	@field:SerializedName("cloud")
	val cloud: Int? = null,

	@field:SerializedName("wind_kph")
	val windKph: Double? = null,

	@field:SerializedName("wind_mph")
	val windMph: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("dewpoint_f")
	val dewpointF: Double? = null,

	@field:SerializedName("will_it_rain")
	val willItRain: Int? = null,

	@field:SerializedName("uv")
	val uv: Double? = null,

	@field:SerializedName("heatindex_f")
	val heatindexF: Double? = null,

	@field:SerializedName("dewpoint_c")
	val dewpointC: Double? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("precip_in")
	val precipIn: Double? = null,

	@field:SerializedName("heatindex_c")
	val heatindexC: Double? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("gust_mph")
	val gustMph: Double? = null,

	@field:SerializedName("pressure_in")
	val pressureIn: Double? = null,

	@field:SerializedName("chance_of_rain")
	val chanceOfRain: Int? = null,

	@field:SerializedName("gust_kph")
	val gustKph: Double? = null,

	@field:SerializedName("precip_mm")
	val precipMm: Double? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("will_it_snow")
	val willItSnow: Int? = null,

	@field:SerializedName("vis_km")
	val visKm: Double? = null,

	@field:SerializedName("time_epoch")
	val timeEpoch: Int? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("chance_of_snow")
	val chanceOfSnow: Int? = null,

	@field:SerializedName("pressure_mb")
	val pressureMb: Double? = null,

	@field:SerializedName("vis_miles")
	val visMiles: Double? = null
)

data class ForecastdayItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("astro")
	val astro: Astro? = null,

	@field:SerializedName("date_epoch")
	val dateEpoch: Int? = null,

	@field:SerializedName("hour")
	val hour: List<HourItem?>? = null,

	@field:SerializedName("day")
	val day: Day? = null
)

data class Condition(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)

data class Current(

	@field:SerializedName("feelslike_c")
	val feelslikeC: Double? = null,

	@field:SerializedName("uv")
	val uv: Double? = null,

	@field:SerializedName("last_updated")
	val lastUpdated: String? = null,

	@field:SerializedName("feelslike_f")
	val feelslikeF: Double? = null,

	@field:SerializedName("wind_degree")
	val windDegree: Int? = null,

	@field:SerializedName("last_updated_epoch")
	val lastUpdatedEpoch: Int? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("precip_in")
	val precipIn: Double? = null,

	@field:SerializedName("air_quality")
	val airQuality: AirQuality? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("gust_mph")
	val gustMph: Double? = null,

	@field:SerializedName("temp_c")
	val tempC: Double? = null,

	@field:SerializedName("pressure_in")
	val pressureIn: Double? = null,

	@field:SerializedName("gust_kph")
	val gustKph: Double? = null,

	@field:SerializedName("temp_f")
	val tempF: Double? = null,

	@field:SerializedName("precip_mm")
	val precipMm: Double? = null,

	@field:SerializedName("cloud")
	val cloud: Int? = null,

	@field:SerializedName("wind_kph")
	val windKph: Double? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("wind_mph")
	val windMph: Double? = null,

	@field:SerializedName("vis_km")
	val visKm: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("pressure_mb")
	val pressureMb: Double? = null,

	@field:SerializedName("vis_miles")
	val visMiles: Double? = null
)
