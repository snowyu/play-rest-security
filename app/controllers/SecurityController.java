package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.DynamicForm;
import play.data.validation.Constraints;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;

import static play.libs.Json.toJson;
import static play.mvc.Controller.request;
import static play.mvc.Controller.response;

public class SecurityController extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";


    public static User getUser() {
        return (User)Http.Context.current().args.get("user");
    }

    // returns an authToken
    public Result login() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        //DynamicForm loginForm = Form.form().bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }

        Login login = loginForm.get();
        User user = User.findByEmailAddressAndPassword(login.emailAddress, login.password);

        // String emailAddress = loginForm.get("emailAddress");
        // String password = loginForm.get("password");
        // User user = User.findByEmailAddressAndPassword(emailAddress, password);

        if (user == null) {
            return unauthorized();
        }
        else {
            String authToken = user.createToken();
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authToken);
            response().setCookie(AUTH_TOKEN, authToken);
            return ok(authTokenJson);
        }
    }

    @Security.Authenticated(Secured.class)
    public Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getUser().deleteAuthToken();
        return redirect("/");
    }

    public static class Login {

        @Constraints.Required
        @Constraints.Email
        public String emailAddress;

        @Constraints.Required
        public String password;

    }


}
