package svas

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.twitter.finatra.json.modules.FinatraJacksonModule

object SvasJacksonModule extends FinatraJacksonModule {

  override protected val propertyNamingStrategy: PropertyNamingStrategy =
    new PropertyNamingStrategy

}
