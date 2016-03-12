import com.google.inject.AbstractModule;

// add play.modules.enabled += "OnStartModule" to application.conf
public class OnStartModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    //Logger.info("StartupModule enter");
    //bind(StartupInterface.class).to(Startup.class).asEagerSingleton();
    bind(Startup.class).asEagerSingleton();
  }
}