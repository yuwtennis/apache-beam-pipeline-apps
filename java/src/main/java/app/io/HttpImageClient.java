package app.io;

import autovalue.shaded.com.google.common.base.Preconditions;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.ByteStreams;
import com.google.protobuf.ByteString;
import org.apache.beam.io.requestresponse.*;
import org.apache.beam.sdk.values.KV;

import java.io.IOException;
import java.io.InputStream;

public class HttpImageClient
        implements Caller<KV<String, ImageRequest>, KV<String,ImageResponse>> {

    private static final int STATUS_TOO_MANY_REQUESTS = 429;
    private static final int STATUS_TIMEOUT = 408;
    private static final HttpRequestFactory REQUEST_FACTORY = new NetHttpTransport().createRequestFactory();
    public static HttpImageClient of() {
        return new HttpImageClient();
    }

    /**
     * Invokes an HTTP GET request from the {@param request}, returning an {@link ImageResponse}
     * containing the image data
     */
    @Override
    public KV<String, ImageResponse> call (KV<String, ImageRequest> requestKV)
        throws UserCodeExecutionException {
        String key =  requestKV.getKey();
        ImageRequest request = requestKV.getValue();
        Preconditions.checkArgument(request != null);
        GenericUrl url = new GenericUrl(request.getImageUrl());

        try {
            HttpRequest imageRequest = REQUEST_FACTORY.buildGetRequest(url);
            HttpResponse response = imageRequest.execute();

            if (response.getStatusCode() >= 500) {
                //Tells transform to repeat the request
                throw new UserCodeRemoteSystemException(response.getStatusMessage());
            }

            if (response.getStatusCode() >= 400) {
                switch (response.getStatusCode()) {
                    case STATUS_TOO_MANY_REQUESTS:
                        // Tells transform to repeat the request
                        throw new UserCodeQuotaException(response.getStatusMessage());
                    case STATUS_TIMEOUT:
                        // Tells transform to repeat the request
                        throw new UserCodeTimeoutException(response.getStatusMessage());
                    default:
                        // Tells the transform to emit immediately into failure PCollection.
                        throw new UserCodeExecutionException(response.getStatusMessage());
                }
            }

            InputStream is = response.getContent();
            byte[] bytes = ByteStreams.toByteArray(is);

            return KV.of(
                    key,
                    ImageResponse.builder()
                            .setMimeType(request.getMimeType())
                            .setData(ByteString.copyFrom(bytes))
                            .build());
        } catch(IOException e) {
                // Tells the transform to emit immediately into failure PCollection.
                throw new UserCodeExecutionException(e);
        }
    }
}

