import com.typesafe.config.ConfigFactory;
import play.inject.guice.*;
import play.ApplicationLoader;
import play.Environment;
import play.Configuration;
import play.Logger;

public class CustomApplicationLoader extends GuiceApplicationLoader {

	@Override
	public GuiceApplicationBuilder builder(ApplicationLoader.Context context) {
		final Environment environment = context.environment();
		GuiceApplicationBuilder builder = initialBuilder.in(environment);
		//GuiceApplicationBuilder builder = super.builder(context);
		Configuration config = context.initialConfiguration();
		if (environment.isTest()) {
			Logger.info("load test.conf");
			config = merge("test.conf", config);
			//builder = builder.bindings(new TestModule());
		} else if (environment.isDev()) {
			Logger.info("load dev.conf");
			config = merge("dev.conf", config);
			//builder = builder.bindings(new DevModule());
		} else if (environment.isProd()) {
			Logger.info("load prod.conf");
			config = merge("prod.conf", config);
			//builder = builder.bindings(new DevModule());
		} else {
			throw new IllegalStateException("No such mode.");
		}
		builder.loadConfig(config);
		return builder;
	}

	private Configuration merge(String configName, Configuration currentConfig) {
		return new Configuration(currentConfig.getWrappedConfiguration().$plus$plus(new play.api.Configuration(ConfigFactory.load(configName))));
	}
}


/* the scala version:

class CustomApplicationLoader extends GuiceApplicationLoader {

		override protected def builder(context: Context): GuiceApplicationBuilder = {
				val builder = initialBuilder.in(context.environment).overrides(overrides(context): _*)
				context.environment.mode match {
						case Prod =>
								// start mode
								val prodConf = Configuration(ConfigFactory.load("prod.conf"))
								builder.loadConfig(prodConf ++ context.initialConfiguration).bindings(new ProdModule());
						case Dev =>
								// run mode
								val devConf = Configuration(ConfigFactory.load("dev.conf"))
								builder.loadConfig(devConf ++ context.initialConfiguration).bindings(new DevModule());
						case Test =>
								// test mode
								val testConf = Configuration(ConfigFactory.load("test.conf"))
								builder.loadConfig(testConf ++ context.initialConfiguration).bindings(new TestModule());
				}
		}
}
*/