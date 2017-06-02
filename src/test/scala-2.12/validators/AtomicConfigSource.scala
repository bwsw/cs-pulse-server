package validators

/**
  * Created by diryavkin_dn on 02.06.17.
  */
import java.util.concurrent.atomic.AtomicReference
import scala.util.{Failure, Success, Try}
import com.typesafe.config.{Config, ConfigFactory}
/**
Use to tweak a Config without clearing and reloading a new config (for testing).
@author dwalend
  */
class AtomicConfigSource(baseConfig:Config) {
  val atomicConfigRef = new AtomicReference[Config](ConfigFactory.empty())

  /**
    * Get the Atomic Config. Be sure to use defs for all config values that might be changed.
    */
  def config:Config = atomicConfigRef.get().withFallback(baseConfig)

  /**
    * Use the config in a block of code.
    */
  def configForBlock[T](key:String,value:AnyRef,origin:String)(block: => T):T = {
    val configPairs = Map(key -> value)
    configForBlock(configPairs,origin)(block)
  }

  /**
    * Use the config in a block of code.
    */
  def configForBlock[T](configPairs:Map[String, _ <: AnyRef],origin:String)(block: => T):T = {
    import scala.collection.JavaConverters.mapAsJavaMapConverter

    val configPairsJava:java.util.Map[String, _ <: AnyRef] = configPairs.asJava
    val blockConfig:Config = ConfigFactory.parseMap(configPairsJava,origin)
    val originalConfig:Config = atomicConfigRef.getAndSet(blockConfig)
    val tryT:Try[T] = Try(block)

    val ok = atomicConfigRef.compareAndSet(blockConfig,originalConfig)

    tryT match {
      case Success(t) => {
        if(ok) t
        else throw new IllegalStateException(
          s"Expected config from ${blockConfig.origin()} to be from ${atomicConfigRef.get().origin()} instead.")
      }
      case Failure(x) => {
        if(ok) throw x
        else throw new IllegalStateException(
          s"Throwable in block and expected config from ${blockConfig.origin()} to be from ${atomicConfigRef.get().origin()} instead.",x)
      }
    }
  }
}
