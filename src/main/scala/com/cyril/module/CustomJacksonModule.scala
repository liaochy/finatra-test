package com.cyril.filter

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.twitter.finatra.json.modules.FinatraJacksonModule

object CustomJacksonModule extends FinatraJacksonModule {

  override val propertyNamingStrategy = {   new DoNothingStrategy}

}

 class DoNothingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
   override def translate(s: String): String =  s
 }