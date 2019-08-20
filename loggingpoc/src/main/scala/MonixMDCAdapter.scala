import java.{util => ju}

import monix.execution.misc.Local
import org.slf4j.spi.MDCAdapter

/**
  * MonixMDCAdapter is the Monix Task Local version of LogbackMDCAdapter, using Monix Task Local instead of ThreadLocal
  * to manage the context map.  This allows developers to still use the MDC interface when logging contextual
  * information within a Task.
  * TODO: This uses MDCAdapter interface rather than the Log4jMDCAdapter as the latter wrongly define an any map
  * for setContextMap and incorrectly blames MDCAdapter. Needs a PR against Log4J2
  *
  */
class MonixMDCAdapter extends MDCAdapter {
  private[this] val map =
    Local[ju.Map[String, String]](ju.Collections.emptyMap())

  /**
    * Handle MDC.put, adding/replacing the key value pair into the Task Local context map
    * @param key
    * @param value
    */
  override def put(key: String, value: String): Unit = {
    if (map() eq ju.Collections.EMPTY_MAP) {
      map := new ju.HashMap()
    }
    val _ = map().put(key, value)
    ()
  }

  /**
    * Handle MDC.get, retrieving the value from Task Local context map given the key
    * @param key
    * @return
    */
  override def get(key: String): String = map().get(key)

  /**
    * Hanlde MDC.remove, removing the value from Task Local context map given the key
    * @param key
    */
  override def remove(key: String): Unit = {
    val _ = map().remove(key)
    ()
  }

  /**
    * Handle MDC.clear, clearing the Task Local context map
    */
  override def clear(): Unit =
    map.clear() // Note: we're resetting the Local to default, not clearing the actual hashmap

  /**
    * Handle MDC.getCopyOfContextMap, creating a new java.util.HashMap and filling it with the contents of the Task
    * Local context map
    * @return
    */
  override def getCopyOfContextMap: ju.Map[String, String] =
    new ju.HashMap(map())

  /**
    * Handle MDC.setContextMap, replacing the contents of the Task Local context map with contextMap
    * @param contextMap
    */
  @SuppressWarnings(
    Array("org.wartremover.warts.AsInstanceOf", "unchecked", "rawtypes")
  )
  override def setContextMap(contextMap: ju.Map[String, String]): Unit =
    map := new ju.HashMap(contextMap)

}
