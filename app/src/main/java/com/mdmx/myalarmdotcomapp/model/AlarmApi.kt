//package com.mdmx.myalarmdotcomapp
//
//import com.mdmx.myalarmdotcomapp.datamodels.systemid.AvailableSystemItem
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.Headers
//
//val key = MyApp.cookies["afg"];
//interface AlarmApi{
//        @Headers("Accept : application/vnd.api+json",
//                "AjaxRequestUniqueKey : $key"
//        )
//
//
//        @GET("/systems/availableSystemItems")
//        suspend fun availableSystemItems(
//           // @Query("symbol") symbol: String
//        ): AvailableSystemItem
//}
//
////        val identities = Jsoup.connect(IDENTITIES_URL)
////            .ignoreContentType(true)
////            .method(Connection.Method.GET)
////            .header("Accept", "application/vnd.api+json")
////            .cookies(cookies)
////            .header("AjaxRequestUniqueKey", ajaxKey)
////            .referrer("https://www.alarm.com/web/system/home")
////            .userAgent(USER_AGENT)
////            .execute()
////            text = identities.body().toString()
////