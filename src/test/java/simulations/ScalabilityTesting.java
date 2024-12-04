package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ScalabilityTesting extends Simulation {

    // Konfigurasi HTTP protocol
    private HttpProtocolBuilder httpProtocol = http
        .baseUrl("https://reqres.in/api")  // Base URL reqres.in
        .acceptHeader("application/json");

    // Skenario simulasi - Get Users Request
    private ScenarioBuilder getUsersScenario = scenario("Get Users Request")
        .exec(http("Get Users Request")
            .get("/users?page=2")  // Endpoint untuk mendapatkan daftar pengguna
            .check(status().is(200))  // Periksa status 200 OK
            .check(jsonPath("$.data[0].id").saveAs("userId"))  // Menyimpan id pengguna pertama
        );

    // Skenario simulasi - Create User Request
    private ScenarioBuilder createUserScenario = scenario("Create User Request")
        .exec(http("Create User Request")
            .post("/users")
            .body(StringBody("{ \"name\": \"Andi\", \"job\": \"QA Tester\" }")).asJson()
            .check(status().is(201))  // Periksa status 201 Created
            .check(jsonPath("$.name").is("Andi"))  // Memastikan nama yang dibuat sesuai
            .check(jsonPath("$.job").is("QA Tester"))  // Memastikan pekerjaan yang dibuat sesuai
        );

    // Skenario simulasi - Get Single User Request
    private ScenarioBuilder getSingleUserScenario = scenario("Get Single User Request")
        .exec(http("Get Single User Request")
            .get("/users/2")  // Endpoint untuk mendapatkan pengguna berdasarkan ID
            .check(status().is(200))  // Periksa status 200 OK
            .check(jsonPath("$.data.id").is("2"))  // Memastikan ID pengguna sesuai
        );

    // Skenario simulasi - Invalid Create User Request
    private ScenarioBuilder invalidCreateUserScenario = scenario("Invalid Create User Request")
        .exec(http("Invalid Create User Request")
            .post("/users")
            .body(StringBody("{ \"name\": \"\", \"job\": \"\" }")).asJson()  // Data tidak valid
            .check(status().is(400))  // Memeriksa status 400 Bad Request
        );

    // Setup simulasi dengan peningkatan jumlah pengguna dan durasi lebih lama
    {
        setUp(
            // Meningkatkan pengguna secara bersamaan untuk masing-masing skenario
            getUsersScenario.injectOpen(rampUsers(500).during(120)),  // 500 pengguna selama 2 menit untuk getUsersScenario
            createUserScenario.injectOpen(rampUsers(500).during(120)),  // 500 pengguna selama 2 menit untuk createUserScenario
            getSingleUserScenario.injectOpen(rampUsers(500).during(120)),  // 500 pengguna selama 2 menit untuk getSingleUserScenario
            invalidCreateUserScenario.injectOpen(rampUsers(500).during(120))  // 500 pengguna selama 2 menit untuk invalidCreateUserScenario
        ).protocols(httpProtocol);  // Menyertakan protokol HTTP
    }
}
