package es.automation.feeder.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.automation.batch.dto.AutomationDataDto;
import es.automation.feeder.dto.Person;
import es.automation.feeder.dto.TestDetails;
import es.automation.feeder.dto.TestResponse;
import es.automation.feeder.service.TestService;
import es.automation.feeder.validators.CommonValidator;
import es.automation.feeder.validators.TestDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
public class AutomationController {

    private final TestService testService;
    private final CommonValidator commonValidator;
    private final TestDetailsValidator testDetailsValidator;

    private final String CONTENT_TYPE = "Content-Type";
    private final String TEXT_PLAIN = "text/plain";

    private final Logger LOGGER = LoggerFactory.getLogger(AutomationController.class);

    @Autowired
    public AutomationController(final TestService testService, final CommonValidator commonValidator, final TestDetailsValidator testDetailsValidator) {
        this.testService = testService;
        this.commonValidator = commonValidator;
        this.testDetailsValidator = testDetailsValidator;
    }

    @GetMapping(value = "/smth",
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    Mono<Person> smth() {
        return Mono.just(new Person("Mumu"));
    }

    @GetMapping("/smth2")
    public Mono<String> doSomething() {
        final WebClient client = WebClient.create("http://127.0.0.1:8082");

        WebClient.ResponseSpec responseSpec = client.get().uri("/automation/api/v1/smth").retrieve();

        responseSpec.onStatus(HttpStatus::is1xxInformational, clientResponse ->
            {
                clientResponse.statusCode();
                return Mono.error(new RuntimeException());
            }
        );



        Map map = responseSpec.bodyToMono(Map.class).block();

        final ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final WebClient client2 = WebClient.create("http://127.0.0.1:8082");
        Mono<ClientResponse> clientResponseMono = client2.get().uri("/automation/api/v1/smth").exchange();

        Map map2 = clientResponseMono.flatMapMany(clientResponse -> clientResponse.bodyToFlux(Map.class)).blockLast();

        return Mono.just(jsonInString);
    }

    @GetMapping("/smth3")
    public Mono<String> doSomething3() {
        final WebClient client = WebClient.builder().defaultHeader("Content-Type", "text/plain").baseUrl("http://127.0.0.1:3000").build();

        WebClient.ResponseSpec responseSpec = client.get().uri("/").retrieve();

        responseSpec.onStatus(HttpStatus::is1xxInformational, clientResponse ->
                {
                    clientResponse.statusCode();
                    return Mono.error(new RuntimeException());
                }
        );



        // Map map = responseSpec.bodyToMono(Map.class).block();

        // String s = responseSpec.bodyToMono(String.class).block();

        /*
        final ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(new HashMap<>());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        */

        final WebClient client2 = WebClient.builder().defaultHeader("Content-Type", "text/plain").baseUrl("http://127.0.0.1:3000").build();
        Mono<ClientResponse> clientResponseMono = client2.get().uri("/").exchange();

        //Map map2 = clientResponseMono.flatMapMany(clientResponse -> clientResponse.bodyToFlux(Map.class)).blockLast();

        String response = clientResponseMono.flatMap(resp -> resp.bodyToMono(String.class)).block();

        return Mono.just("");
    }

    @PostMapping(value = "/addTest")
    public Mono<TestResponse> addTest(@Valid @RequestBody TestDetails testDetails, BindingResult bindingResult) {

        commonValidator.validate(bindingResult);

        testDetailsValidator.validate(testDetails);

        final TestResponse testResponse = testService.saveTest(testDetails);

        return Mono.just(testResponse);
    }



    private static final String COLON = ":";

    public List<Map<String, Object>> doWebClientCall(final AutomationDataDto automationDataDto) {

        /*if (automationDataDto != null) {
            return null;
        }*/

        final List<Map<String, Object>> results = new ArrayList<>();

        final List<TestDetails> testsToBeProcessed = automationDataDto.getAllTestsToBeProcessed();

        if (testsToBeProcessed.isEmpty()) {
            return results;
        }

        for (TestDetails testDetails : testsToBeProcessed) {
            results.add(retrieveRestResponse(testDetails));
        }

        if (automationDataDto != null) {
            //throw new CustomException(new ErrorObject().httpCode("400").moreInformation("More Information..."));
        }

        return results;
    }

    public Map<String, Object> retrieveRestResponse(final TestDetails testDetails) {
        final HttpMethod httpMethod = HttpMethod.resolve(testDetails.getHttpMethod());

        final WebClient.Builder webClientBuilder = WebClient.builder().baseUrl(testDetails.getBaseUri() + COLON + testDetails.getPort());

        final Map<String, String> requestHeaders = testDetails.getRequestHeaders();

        boolean json = true;

        for (String key : requestHeaders.keySet()) {
            if (key.equals(CONTENT_TYPE)) {
                if (requestHeaders.get(key).equals(TEXT_PLAIN)) {
                    json = false;
                }
            }
            webClientBuilder.defaultHeader(key, requestHeaders.get(key));
        }

        final WebClient webClient = webClientBuilder.build();

        final WebClient.ResponseSpec responseSpec;
        final WebClient.RequestHeadersUriSpec<?> request;

        Map<String, Object> map = new HashMap<>();

        if (httpMethod != null) {
            Mono<ClientResponse> clientResponseMono = null;
            switch (httpMethod) {
                case GET:
                    clientResponseMono = webClient.get().uri(testDetails.getRelativeUri()).exchange();
                    break;
                case POST:
                    clientResponseMono = webClient.post().uri(testDetails.getRelativeUri()).body(createBody(testDetails)).exchange();
                    break;
                case DELETE:
                    clientResponseMono = webClient.delete().uri(testDetails.getRelativeUri()).exchange();
                    break;
                case PUT:
                    clientResponseMono = webClient.put().uri(testDetails.getRelativeUri()).body(createBody(testDetails)).exchange();
                    break;
                case OPTIONS:
                    clientResponseMono = webClient.options().uri(testDetails.getRelativeUri()).exchange();
                    break;
                default:
                    request = null;
                    clientResponseMono = null;
                    break;
            }

            if (clientResponseMono != null) {
                if (!json) {
                    //String body = clientResponseMono.flatMap(clientResponse -> clientResponse.toEntity(String.class)).block();
                    String body = clientResponseMono.flatMap(resp -> resp.bodyToMono(String.class)).block();
                    map.put(TEXT_PLAIN, body);
                    //map.put(TEXT_PLAIN, clientResponseMono.flatMapMany(clientResponse -> clientResponse.bodyToFlux(String.class)).blockLast());
                } else {
                    map = clientResponseMono.flatMapMany(clientResponse -> clientResponse.bodyToFlux(Map.class)).blockLast();
                }
            }

        }
        return map;
    }

    private BodyInserter<Map, ReactiveHttpOutputMessage> createBody(final TestDetails testDetails) {
        final String body = testDetails.getBody();
        final ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> obj = null;
        try {
            obj = mapper.readValue(body, new TypeReference<Map<String,Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BodyInserters.fromObject(obj);
    }

    private BodyInserter<MultiValueMap, ClientHttpRequest> createBody0(final TestDetails testDetails) {
        LinkedMultiValueMap map3 = new LinkedMultiValueMap();

        map3.add("key1", "value1");
        map3.add("key2", "value2");

        final String body = testDetails.getBody();
        final ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> obj = null;
        try {
            obj = mapper.readValue(body, new TypeReference<Map<String,Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BodyInserters.fromFormData(map3);
    }
}
