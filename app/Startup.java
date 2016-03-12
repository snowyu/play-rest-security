/*It's so ugly for java instead of GlobalSettings.onStart:
To make it eager, see GlobalModule.java and GlobalInterface.java
*/

import models.User;
import play.Application;
//import play.Configuration;
//import play.Environment;
import play.api.db.evolutions.ApplicationEvolutions;
import play.Play;
import javax.inject.Inject;
import javax.inject.Singleton;
import utils.DemoData;
import play.Logger;

@Singleton
//public class Startup implements StartupInterface {
public class Startup {
  /* hook the ApplicationLifecycle lifecycle to onStop*/
  @Inject
  public Startup(Application application, ApplicationEvolutions evolutions) {
    //hook the ApplicationEvolutions to startup after evolutions.
    //Logger.info("Startup Inject");
    // load the demo data in dev mode
    if (application.isDev() && (User.find.all().size() == 0)) {
        DemoData.loadDemoData();
    }
    // lifecycle.addStopHook(() -> {
    //   Logger.info("Stopping");
    //   this.shutdown();

    //   Logger.info("Saving modules data");
    //   for(Module m: controllers.Application.modules){
    //     m.saveData();
    //   }
    //   return F.Promise.pure(null);
    // });
  }
}
