# AI Model Proxy

A Spring Boot proxy server that converts OpenAI API requests and responses to Qwen3 model format. This allows you to use OpenAI-compatible clients with Qwen3 models transparently.

## Features

- **OpenAI API Compatibility**: Supports the standard OpenAI chat completions endpoint (`/v1/chat/completions`)
- **Automatic Format Conversion**: Converts requests from OpenAI format to Qwen3 format and responses back to OpenAI format
- **Model Transparency**: Clients can request any OpenAI model (e.g., `gpt-3.5-turbo`, `gpt-4`) and it will be proxied to Qwen3
- **Health Check Endpoint**: `/v1/health` for monitoring
- **Models Endpoint**: `/v1/models` for OpenAI-compatible model listing

## Quick Start

### Prerequisites

- Java 17 or later
- Maven 3.6 or later
- Qwen3 API key (Alibaba Cloud DashScope)

### Configuration

1. Copy the example configuration:
   ```bash
   cp application-example.properties src/main/resources/application-local.properties
   ```

2. Update the configuration with your Qwen3 API key:
   ```properties
   qwen3.api.api-key=your-actual-qwen3-api-key-here
   ```

3. Set the API key via environment variable (recommended for production):
   ```bash
   export QWEN3_API_KEY=your-actual-qwen3-api-key-here
   ```

### Build and Run

1. Build the application:
   ```bash
   mvn clean package
   ```

2. Run the application:
   ```bash
   java -jar target/ai-model-proxy-1.0-SNAPSHOT.jar
   ```

   Or using Maven:
   ```bash
   mvn spring-boot:run
   ```

The server will start on port 8080 by default.

### Usage

Once running, you can use any OpenAI-compatible client to interact with the proxy:

#### Example with curl:

```bash
curl -X POST http://localhost:8080/v1/chat/completions \
  -H "Content-Type: application/json" \
  -d '{
    "model": "gpt-3.5-turbo",
    "messages": [
      {
        "role": "user",
        "content": "Hello, how are you?"
      }
    ],
    "temperature": 0.7,
    "max_tokens": 100
  }'
```

#### Example with Python OpenAI client:

```python
import openai

# Configure to use the proxy
openai.api_base = "http://localhost:8080/v1"
openai.api_key = "not-needed-for-proxy"  # API key is configured on proxy

response = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "user", "content": "Hello, how are you?"}
    ],
    temperature=0.7,
    max_tokens=100
)

print(response.choices[0].message.content)
```

### API Endpoints

- `POST /v1/chat/completions` - Chat completions (OpenAI compatible)
- `GET /v1/models` - List available models
- `GET /v1/health` - Health check

### Configuration Options

| Property | Default | Description |
|----------|---------|-------------|
| `qwen3.api.base-url` | `https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation` | Qwen3 API endpoint |
| `qwen3.api.api-key` | - | Your Qwen3 API key (required) |
| `qwen3.api.model` | `qwen-turbo` | Default Qwen3 model to use |
| `qwen3.api.timeout` | `30000` | Request timeout in milliseconds |
| `server.port` | `8080` | Server port |

### Docker Support

You can also run the application using Docker:

```bash
# Build the application
mvn clean package

# Build Docker image
docker build -t ai-model-proxy .

# Run with environment variable
docker run -p 8080:8080 -e QWEN3_API_KEY=your-key-here ai-model-proxy
```

### Development

To run tests:
```bash
mvn test
```

To run with specific profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Architecture

The proxy consists of several key components:

1. **OpenAI Compatible Controller**: Handles incoming OpenAI-format requests
2. **API Transformation Service**: Converts between OpenAI and Qwen3 formats
3. **Qwen3 API Service**: Manages communication with the Qwen3 API
4. **Proxy Service**: Orchestrates the entire request/response flow

### Supported Features

- ✅ Chat completions
- ✅ Message history
- ✅ Temperature, max_tokens, top_p parameters
- ✅ Error handling and logging
- ✅ Health monitoring

### Limitations

- Streaming responses not yet implemented
- Some advanced OpenAI parameters may not be supported by Qwen3
- Function calling not implemented

### Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `mvn test`
5. Submit a pull request

### License

This project is licensed under the MIT License.