/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-10-17 at 21:34:49 UTC 
 * Modify at your own risk.
 */

package com.example.shraddha.cmpe277.backend.sensorApi;

/**
 * Service definition for SensorApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link SensorApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class SensorApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the sensorApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "sensorApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public SensorApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  SensorApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getSensor".
   *
   * This request holds the parameters needed by the sensorApi server.  After setting any optional
   * parameters, call the {@link GetSensor#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetSensor getSensor(java.lang.Long id) throws java.io.IOException {
    GetSensor result = new GetSensor(id);
    initialize(result);
    return result;
  }

  public class GetSensor extends SensorApiRequest<com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor> {

    private static final String REST_PATH = "sensor/{id}";

    /**
     * Create a request for the method "getSensor".
     *
     * This request holds the parameters needed by the the sensorApi server.  After setting any
     * optional parameters, call the {@link GetSensor#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetSensor#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetSensor(java.lang.Long id) {
      super(SensorApi.this, "GET", REST_PATH, null, com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetSensor setAlt(java.lang.String alt) {
      return (GetSensor) super.setAlt(alt);
    }

    @Override
    public GetSensor setFields(java.lang.String fields) {
      return (GetSensor) super.setFields(fields);
    }

    @Override
    public GetSensor setKey(java.lang.String key) {
      return (GetSensor) super.setKey(key);
    }

    @Override
    public GetSensor setOauthToken(java.lang.String oauthToken) {
      return (GetSensor) super.setOauthToken(oauthToken);
    }

    @Override
    public GetSensor setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetSensor) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetSensor setQuotaUser(java.lang.String quotaUser) {
      return (GetSensor) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetSensor setUserIp(java.lang.String userIp) {
      return (GetSensor) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetSensor setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetSensor set(String parameterName, Object value) {
      return (GetSensor) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertSensor".
   *
   * This request holds the parameters needed by the sensorApi server.  After setting any optional
   * parameters, call the {@link InsertSensor#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor}
   * @return the request
   */
  public InsertSensor insertSensor(com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor content) throws java.io.IOException {
    InsertSensor result = new InsertSensor(content);
    initialize(result);
    return result;
  }

  public class InsertSensor extends SensorApiRequest<com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor> {

    private static final String REST_PATH = "sensor";

    /**
     * Create a request for the method "insertSensor".
     *
     * This request holds the parameters needed by the the sensorApi server.  After setting any
     * optional parameters, call the {@link InsertSensor#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertSensor#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor}
     * @since 1.13
     */
    protected InsertSensor(com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor content) {
      super(SensorApi.this, "POST", REST_PATH, content, com.example.shraddha.cmpe277.backend.sensorApi.model.Sensor.class);
    }

    @Override
    public InsertSensor setAlt(java.lang.String alt) {
      return (InsertSensor) super.setAlt(alt);
    }

    @Override
    public InsertSensor setFields(java.lang.String fields) {
      return (InsertSensor) super.setFields(fields);
    }

    @Override
    public InsertSensor setKey(java.lang.String key) {
      return (InsertSensor) super.setKey(key);
    }

    @Override
    public InsertSensor setOauthToken(java.lang.String oauthToken) {
      return (InsertSensor) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertSensor setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertSensor) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertSensor setQuotaUser(java.lang.String quotaUser) {
      return (InsertSensor) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertSensor setUserIp(java.lang.String userIp) {
      return (InsertSensor) super.setUserIp(userIp);
    }

    @Override
    public InsertSensor set(String parameterName, Object value) {
      return (InsertSensor) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link SensorApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link SensorApi}. */
    @Override
    public SensorApi build() {
      return new SensorApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link SensorApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setSensorApiRequestInitializer(
        SensorApiRequestInitializer sensorapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(sensorapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
