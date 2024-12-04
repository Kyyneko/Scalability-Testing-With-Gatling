package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ScalabilityTesting extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
        .baseUrl("https://reqres.in/api")
        .acceptHeader("application/json");

    // Skenario 1: Get Users Request
    private ScenarioBuilder getUsersScenario = scenario("Get Users Request")
        .exec(http("Get Users Request")
            .get("/users?page=2")
            .check(status().is(200))
            .check(jsonPath("$.data[0].id").saveAs("userId"))
        );

    // Skenario 2: Create User Request
    private ScenarioBuilder createUserScenario = scenario("Create User Request")
        .exec(http("Create User Request")
            .post("/users")
            .body(StringBody("{ \"name\": \"Andi\", \"job\": \"QA Tester\" }")).asJson()
            .check(status().is(201))
            .check(jsonPath("$.name").is("Andi"))
            .check(jsonPath("$.job").is("QA Tester"))
        );

    // Skenario 3: Get Single User Request
    private ScenarioBuilder getSingleUserScenario = scenario("Get Single User Request")
        .exec(http("Get Single User Request")
            .get("/users/2")
            .check(status().is(200))
            .check(jsonPath("$.data.id").is("2"))
        );

    // Skenario 4: Invalid Create User Request
    private ScenarioBuilder invalidCreateUserScenario = scenario("Invalid Create User Request")
        .exec(http("Invalid Create User Request")
            .post("/users")
            .body(StringBody("{ \"name\": \"\", \"job\": \"\" }")).asJson()
            .check(status().is(400))
        );

    // Penyesuaian injeksi pengguna dengan variasi beban
    {
        setUp(
            // Injeksi bertahap (ramp-up) dan periode stabil (steady-state) untuk setiap skenario
            getUsersScenario.injectOpen(
                rampUsers(100).during(60),        // 100 pengguna dalam 1 menit
                constantUsersPerSec(50).during(120)  // 50 pengguna tetap selama 2 menit
            ),
            createUserScenario.injectOpen(
                rampUsers(100).during(60),
                constantUsersPerSec(50).during(120)
            ),
            getSingleUserScenario.injectOpen(
                rampUsers(100).during(60),
                constantUsersPerSec(50).during(120)
            ),
            invalidCreateUserScenario.injectOpen(
                rampUsers(50).during(60),         // Beban lebih rendah untuk permintaan invalid
                constantUsersPerSec(20).during(120)
            )
        ).protocols(httpProtocol);
    }
}
