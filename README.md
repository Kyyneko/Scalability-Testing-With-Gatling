# Performance Testing with Gatling

A performance testing project using **Gatling** to evaluate the scalability and stability of the target API.

---

## 📚 General Information

- **Project Name**: Performance Testing with Gatling
- **Tools**:
  - Gatling (3.13.1)
  - Maven
- **Programming Language**: Java (version 23.0.1, released 2024-10-15)
- **Target API**: [https://regres.in/api](https://regres.in/api)

---

## 🔧 Maven Configuration

### Project Information

| **Field**    | **Value**                  |
| ------------ | -------------------------- |
| `groupId`    | Unique project group ID    |
| `artifactId` | Performance Testing Module |
| `version`    | 1.0                        |

### Dependencies

- **gatling-charts-highcharts**
  - Provides graphical reports for Gatling results.
  - Scope: `test` (only needed during testing).

### Build Plugins

- **maven-compiler-plugin**: Configures Java compilation settings.
- **gatling-maven-plugin**: Enables running Gatling simulation classes.

---

## 🚀 Gatling Simulation Configuration

### HTTP Protocol Configuration

- **Base URL**: `https://regres.in/api`
- **Accept Header**: Configured to accept JSON responses.

### Test Scenarios

1. **Get User Request**

   - **Objective**: Test the GET `/users?page=2` endpoint.
   - **Validation**: Response status `200` and saved user IDs.

2. **Create User Request**

   - **Objective**: Test the POST `/users` endpoint.
   - **Validation**: Response status `201` with correct user data.

3. **Get Single User Request**

   - **Objective**: Test retrieval of a single user's data.
   - **Validation**: Response status `200` and matching user ID.

4. **Invalid Create User Request**
   - **Objective**: Test server validation with invalid data.
   - **Validation**: Response status `400` (Bad Request).

### User Load Configuration

- **Valid Scenarios**:
  - `rampUsers(100).during(60)`: Gradual increase to 100 users within 1 minute.
  - `constantUsersPerSec(50).during(120)`: Constant 50 users per second for 2 minutes.
- **Invalid Scenarios**:
  - Lower load with 20 initial users and 20 stable users.

---

## ✅ Expected Results

### API Response Validation

- All scenarios return expected status codes (`200`, `201`, `400`).

### Performance Stability

- API handles simulated load without significant errors.

### Data Accuracy

- API outputs match the inputs provided in POST requests.

---

## 📊 Key Metrics

1. **Response Time**: Average and maximum response time per endpoint.
2. **Throughput**: Number of successful requests per second.
3. **Error Rate**: Ratio of failed to total requests.
4. **Stability Metrics**: Evaluate performance under high load.

---

## 👤 Author

**Mahendra Kirana M.B**

- Student ID: H071221058
- Program: Information Systems
- Faculty: Mathematics and Natural Sciences
- University: Universitas Hasanuddin
- Year: 2024

---

## 📝 License

MIT License

Copyright (c) 2024 Mahendra Kirana M.B

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
